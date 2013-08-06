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

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.api.DistributionChannelRepository;
import com.danielpacak.riskanalyzer.frontend.repository.api.FacilityRepository;
import com.danielpacak.riskanalyzer.frontend.web.form.DistributionChannelForm;

@Controller
@RequestMapping(value = "/distribution-channel")
public class DistributionChannelController {

	private DistributionChannelRepository distributionChannelRepository;

	private FacilityRepository facilityRepository;

	@Autowired
	public DistributionChannelController(DistributionChannelRepository distributionChannelRepository,
			FacilityRepository facilityRepository) {
		this.distributionChannelRepository = distributionChannelRepository;
		this.facilityRepository = facilityRepository;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestBody DistributionChannelForm form) throws Exception {
		distributionChannelRepository.save(form.getDistributionChannel(), form.getSourceId(),
				form.getTargetId());
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	DistributionChannelForm read(@PathVariable("id") String distributionChannelId) throws Exception {
		DistributionChannel channel = distributionChannelRepository.findOne(distributionChannelId);
		List<Facility> facilities = facilityRepository.findAll();
		return new DistributionChannelForm(channel, facilities);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") String distributionChannelId) throws Exception {
		distributionChannelRepository.delete(distributionChannelId);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

}
