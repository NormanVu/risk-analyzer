package com.scirisk.riskanalyzer.repository.neo4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class FacilityRepositoryNeo4jImpl implements FacilityRepository {

	GraphDatabaseService databaseService;

	public FacilityRepositoryNeo4jImpl(GraphDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Override
	public Facility save(Facility facility) {
		Transaction transaction = databaseService.beginTx();
		Index<Node> facilityIndex = databaseService.index().forNodes(
				Facility.class.getName());
		try {
			Node facilityNode = databaseService.createNode();
			// String facilityId = UUID.randomUUID().toString();
			// facilityNode.setProperty("id", facilityId);

			facilityNode.setProperty("class", Facility.class.getName());
			facilityIndex.add(facilityNode, "class", Facility.class.getName());

			facilityNode.setProperty("name", facility.getName());

			facilityNode.setProperty("address", facility.getAddress());
			facilityNode.setProperty("longitude", facility.getLongitude());
			facilityNode.setProperty("latitude", facility.getLatitude());

			facilityNode.setProperty("riskCategory1",
					facility.getRiskCategory1());
			facilityNode.setProperty("riskCategory2",
					facility.getRiskCategory2());
			facilityNode.setProperty("riskCategory3",
					facility.getRiskCategory3());

			facilityNode.setProperty("recoveryTime1",
					facility.getRecoveryTime1());
			facilityNode.setProperty("recoveryTime2",
					facility.getRecoveryTime2());
			facilityNode.setProperty("recoveryTime3",
					facility.getRecoveryTime3());

			transaction.success();
			facility.setId(String.valueOf(facilityNode.getId()));
			return facility;
		} finally {
			transaction.finish();
		}
	}

	@Override
	public void delete(String facilityId) {
		Transaction transaction = databaseService.beginTx();
		try {
			Node facilityNode = databaseService.getNodeById(Long
					.valueOf(facilityId));
			facilityNode.delete();
			transaction.success();
		} finally {
			transaction.finish();
		}
	}

	@Override
	public Facility findOne(String facilityId) {
		Node facilityNode = databaseService.getNodeById(Long
				.valueOf(facilityId));
		return map(facilityNode);
	}

	@Override
	public List<Facility> findAll() {
		Index<Node> facilityIndex = databaseService.index().forNodes(
				Facility.class.getName());
		Iterator<Node> iterator = facilityIndex.get("class",
				Facility.class.getName()).iterator();
		List<Facility> facilities = new ArrayList<Facility>();
		while (iterator.hasNext()) {
			Node facilityNode = iterator.next();
			facilities.add(map(facilityNode));
		}

		return facilities;
	}

	Facility map(Node n) {
		Facility f = new Facility();
		f.setId(String.valueOf(n.getId()));
		f.setName((String) n.getProperty("name"));
		f.setAddress((String) n.getProperty("address"));
		f.setLatitude((Double) n.getProperty("latitude"));
		f.setLongitude((Double) n.getProperty("longitude"));

		f.setRiskCategory1((Double) n.getProperty("riskCategory1"));
		f.setRiskCategory2((Double) n.getProperty("riskCategory2"));
		f.setRiskCategory3((Double) n.getProperty("riskCategory3"));

		f.setRecoveryTime1((Double) n.getProperty("recoveryTime1"));
		f.setRecoveryTime2((Double) n.getProperty("recoveryTime2"));
		f.setRecoveryTime3((Double) n.getProperty("recoveryTime3"));
		return f;

	}

}
