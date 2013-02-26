package com.danielpacak.riskanalyzer.frontend.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.FacilityRepository;

@Controller
@RequestMapping(value = "/facility")
public class FacilityController {

	@Autowired
	FacilityRepository facilityRepository;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestBody Facility node)
			throws Exception {
		facilityRepository.save(node);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Facility read(@PathVariable("id") String nodeId) throws Exception {
		return facilityRepository.findOne(nodeId);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") String nodeId)
			throws Exception {
		facilityRepository.delete(nodeId);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	List<Facility> findAll() throws Exception {
		return facilityRepository.findAll();
	}

}
