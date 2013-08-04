package com.danielpacak.riskanalyzer.frontend.repository.neo4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.domain.Facility.Kind;
import com.danielpacak.riskanalyzer.domain.Facility.Type;
import com.danielpacak.riskanalyzer.frontend.repository.api.FacilityRepository;

public class FacilityRepositoryNeo4jImpl implements FacilityRepository {

	GraphDatabaseService databaseService;

	public FacilityRepositoryNeo4jImpl(GraphDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Override
	public Facility save(Facility facility) {
		Transaction transaction = databaseService.beginTx();
		try {
			Node node = null;
			if (isBlank(facility.getId())) {
				node = databaseService.createNode();
				facility.setId(String.valueOf(node.getId()));
			} else {
				node = databaseService.getNodeById(Long.valueOf(facility.getId()));
			}

			node.setProperty("name", facility.getName());
			node.setProperty("kind", facility.getKind().name());
			node.setProperty("description", facility.getDescription());

			node.setProperty("address", facility.getAddress());
			node.setProperty("longitude", facility.getLongitude());
			node.setProperty("latitude", facility.getLatitude());

			node.setProperty("riskCategory1", facility.getRiskCategory1());
			node.setProperty("riskCategory2", facility.getRiskCategory2());
			node.setProperty("riskCategory3", facility.getRiskCategory3());

			node.setProperty("recoveryTime1", facility.getRecoveryTime1());
			node.setProperty("recoveryTime2", facility.getRecoveryTime2());
			node.setProperty("recoveryTime3", facility.getRecoveryTime3());

			node.setProperty("type", facility.getType().name());

			transaction.success();
			return facility;
		} finally {
			transaction.finish();
		}
	}

	@Override
	public void delete(String facilityId) {
		Transaction transaction = databaseService.beginTx();
		try {
			Node facilityNode = databaseService.getNodeById(Long.valueOf(facilityId));
			facilityNode.delete();
			transaction.success();
		} finally {
			transaction.finish();
		}
	}

	@Override
	public Facility findOne(String facilityId) {
		Node facilityNode = databaseService.getNodeById(Long.valueOf(facilityId));
		return map(facilityNode);
	}

	@Override
	public List<Facility> findAll() {
		Index<Node> facilityIndex = databaseService.index().forNodes(Facility.class.getName());
		Iterator<Node> iterator = facilityIndex.get("class", Facility.class.getName()).iterator();
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
		f.setKind(Kind.valueOf((String) n.getProperty("kind")));
		f.setDescription((String) n.getProperty("description"));
		f.setAddress((String) n.getProperty("address"));
		f.setLatitude((Double) n.getProperty("latitude"));
		f.setLongitude((Double) n.getProperty("longitude"));

		f.setRiskCategory1((Double) n.getProperty("riskCategory1"));
		f.setRiskCategory2((Double) n.getProperty("riskCategory2"));
		f.setRiskCategory3((Double) n.getProperty("riskCategory3"));

		f.setRecoveryTime1((Double) n.getProperty("recoveryTime1"));
		f.setRecoveryTime2((Double) n.getProperty("recoveryTime2"));
		f.setRecoveryTime3((Double) n.getProperty("recoveryTime3"));
		f.setType(Type.valueOf((String) n.getProperty("type")));
		return f;

	}

	boolean isBlank(String string) {
		return string == null || "".equals(string);
	}

}
