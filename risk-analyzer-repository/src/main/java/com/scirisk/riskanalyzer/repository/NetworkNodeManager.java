package com.scirisk.riskanalyzer.repository;

import java.util.List;

import com.scirisk.riskanalyzer.domain.Facility;

public interface NetworkNodeManager {

	Facility save(Facility node);

	Facility findOne(String nodeId);

	void delete(String nodeId);

	List<Facility> findAll();

}