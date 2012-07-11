package com.scirisk.riskanalyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.scirisk.riskanalyzer.entity.NetworkNode;

public interface NetworkNodeRepository extends
		CrudRepository<NetworkNode, String> {
	
	@Query("FROM NetworkNode")
	List<NetworkNode> findAllNetworkNodes();

}
