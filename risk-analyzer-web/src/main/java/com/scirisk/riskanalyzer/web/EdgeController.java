package com.scirisk.riskanalyzer.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkEdgeManager;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;
import com.scirisk.riskanalyzer.web.bean.NetworkEdgeFormBean;

@Controller
@RequestMapping(value = "/edge")
public class EdgeController {

	@Autowired
	NetworkEdgeManager networkEdgeManager;

	@Autowired
	NetworkNodeManager networkNodeManager;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> save(NetworkEdgeFormBean edge)
			throws Exception {
		networkEdgeManager.save(edge.getId(), edge.getPurchasingVolume(),
				edge.getSourceId(), edge.getTargetId());
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	NetworkEdgeFormBean read(@PathVariable("id") Long edgeId) throws Exception {
		NetworkEdge edge = networkEdgeManager.findOne(edgeId);
		List<NetworkNode> nodes = networkNodeManager.findAll();
		return new NetworkEdgeFormBean(edge, nodes);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") Long edgeId)
			throws Exception {
		networkEdgeManager.delete(edgeId);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

}
