package com.danielpacak.riskanalyzer.frontend.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.danielpacak.riskanalyzer.domain.DistributionNetwork;
import com.danielpacak.riskanalyzer.frontend.repository.api.DistributionNetworkRepository;
import com.danielpacak.riskanalyzer.frontend.service.api.NetworkMarshaller;
import com.danielpacak.riskanalyzer.frontend.service.api.NetworkParser;

@Controller
public class NetworkController {

	private DistributionNetworkRepository distributionNetworkRepository;

	private NetworkMarshaller networkMarshaller;
	// TODO NETWORK UNMARSHALLER
	private NetworkParser networkParser;

	@Autowired
	public NetworkController(DistributionNetworkRepository distributionNetworkRepository,
			NetworkMarshaller networkMarshaller, NetworkParser networkParser) {
		this.distributionNetworkRepository = distributionNetworkRepository;
		this.networkMarshaller = networkMarshaller;
		this.networkParser = networkParser;
	}

	@RequestMapping(value = "/network", method = RequestMethod.GET)
	public @ResponseBody
	DistributionNetwork getNetworkForGoogleMap() throws Exception {
		return distributionNetworkRepository.read();
	}

	@RequestMapping(value = "/network.xml", method = RequestMethod.GET)
	public void exportToXml(HttpServletRequest request, HttpServletResponse response) throws Exception {

		DistributionNetwork network = distributionNetworkRepository.read();
		response.setContentType("application/xml");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		String file = "risk-analyzer-network-export-" + dateFormat.format(new Date()) + ".xml";
		response.setHeader("Content-Disposition", "attachment;filename=" + file);

		networkMarshaller.marshall(network, response.getOutputStream());
	}

	@RequestMapping(value = "/network", method = RequestMethod.POST)
	public void importFromXml(@RequestParam("networkXml") MultipartFile networkXml, HttpServletResponse resp)
			throws Exception {

		Boolean result = true;
		if (!networkXml.isEmpty()) {
			DistributionNetwork network = networkParser.parse(networkXml.getInputStream());
			distributionNetworkRepository.save(network);
		} else {
			result = false;
		}


		// FIXME This text/html content type is probably a bug in ExtJS 4
		resp.setContentType("text/html");
		resp.getWriter().printf("{success: %s}", result);
	}

}
