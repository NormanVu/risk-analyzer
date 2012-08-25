package com.scirisk.riskanalyzer.web;

import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.model.NetworkMarshaller;
import com.scirisk.riskanalyzer.model.NetworkParser;
import com.scirisk.riskanalyzer.model.NetworkValidationException;
import com.scirisk.riskanalyzer.persistence.NetworkManager;

@Controller
public class NetworkController {

	@Autowired
	private NetworkManager networkManager;

	@Autowired
	private NetworkMarshaller networkMarshaller;

	@Autowired
	private NetworkParser networkParser;

	@RequestMapping(value = "/network/map", method = RequestMethod.GET)
	public @ResponseBody
	Network getNetworkForGoogleMap() throws Exception {
		return networkManager.read();
	}

	@RequestMapping(value = "/network/tree", method = RequestMethod.GET)
	public void getNetworkForTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json");
		Network network = networkManager.read();

		Collection<NetworkNode> nodes = network.getNodes();
		Collection<NetworkEdge> edges = network.getEdges();

		JSONObject nodeFolder = new JSONObject();
		nodeFolder.element("text", "Node");
		nodeFolder.element("cls", "folder");
		nodeFolder.element("expanded", true);
		nodeFolder.element("children", nodesToJson(nodes));

		JSONObject edgeFolder = new JSONObject();
		edgeFolder.element("text", "Edge");
		edgeFolder.element("cls", "folder");
		edgeFolder.element("expanded", true);
		edgeFolder.element("children", edgesToJson(edges));

		JSONArray networkChildren = new JSONArray();
		networkChildren.add(nodeFolder);
		networkChildren.add(edgeFolder);

		JSONObject networkJson = new JSONObject();
		networkJson.element("text", "Network");
		networkJson.element("cls", "folder");
		networkJson.element("expanded", true);
		networkJson.element("children", networkChildren);

		JSONArray root = new JSONArray();
		root.add(networkJson);

		response.getWriter().print(root.toString());
	}

	@RequestMapping(value = "/network.xml", method = RequestMethod.GET)
	public void exportToXml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Network network = networkManager.read();
		response.setContentType("application/xml");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		String file = "risk-analyzer-network-export-"
				+ dateFormat.format(new Date()) + ".xml";
		response.setHeader("Content-Disposition", "attachment;filename=" + file);

		networkMarshaller.marshall(network, response.getOutputStream());
	}

	@RequestMapping(value = "/network", method = RequestMethod.POST)
	public void importFromXml(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);

		boolean isMulitpart = ServletFileUpload.isMultipartContent(req);
		if (isMulitpart) {

			ServletFileUpload uploadHandler = new ServletFileUpload();
			try {
				FileItemIterator iterator = uploadHandler.getItemIterator(req);
				while (iterator.hasNext()) {
					FileItemStream fis = iterator.next();
					if (!fis.isFormField()) {
						InputStream is = fis.openStream();
						Network network = networkParser.parse(is);

						networkManager.save(network);
						break; // stop iterating
					}
				}
				// out.println("{success: true}");
			} catch (FileUploadException e) {
				e.printStackTrace();
				// out.println("{success: false}");
				jsonResponse.put("success", false);
			} catch (NetworkValidationException e) {
				e.printStackTrace();
				// out.println("{success: false}");
				jsonResponse.put("success", false);
			}
		} else {
			// out.println("{success: false}");
			jsonResponse.put("success", false);
		}
		out.println(jsonResponse.toString(2));
	}

	private JSONArray nodesToJson(final Collection<NetworkNode> nodes) {
		JSONArray nodesArray = new JSONArray();
		for (NetworkNode n : nodes) {
			JSONObject nodeObject = new JSONObject();
			nodeObject.element("id", "n_" + n.getId());
			nodeObject.element("text", n.getName());
			nodeObject.element("leaf", true);
			nodesArray.add(nodeObject);
		}
		return nodesArray;
	}

	private JSONArray edgesToJson(final Collection<NetworkEdge> edges) {
		JSONArray edgesArray = new JSONArray();
		for (NetworkEdge e : edges) {
			JSONObject edgeObject = new JSONObject();
			edgeObject.element("id", "e_" + e.getId());
			final String caption = e.getSource().getName() + " > "
					+ e.getTarget().getName();
			edgeObject.element("text", caption);
			edgeObject.element("leaf", true);
			edgesArray.add(edgeObject);
		}
		return edgesArray;
	}

}
