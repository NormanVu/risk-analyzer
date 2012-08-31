package com.scirisk.riskanalyzer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

@Controller
@RequestMapping(value = "/edge")
public class EdgeController {

	@Autowired
	DistributionChannelRepository networkEdgeManager;

	@Autowired
	FacilityRepository networkNodeManager;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> save(NetworkEdgeFormBean formBean)
			throws Exception {
		
		networkEdgeManager.save(formBean.getNetworkEdge(),
				formBean.getSourceId(), formBean.getTargetId());
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	NetworkEdgeFormBean read(@PathVariable("id") String edgeId) throws Exception {
		DistributionChannel edge = networkEdgeManager.findOne(edgeId);
		List<Facility> nodes = networkNodeManager.findAll();
		return new NetworkEdgeFormBean(edge, nodes);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") String edgeId)
			throws Exception {
		networkEdgeManager.delete(edgeId);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

}
