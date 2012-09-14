package com.scirisk.riskanalyzer.repository.neo4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.tooling.GlobalGraphOperations;

import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class DistributionChannelRepositoryNeo4jImpl implements
		DistributionChannelRepository {

	GraphDatabaseService databaseService;

	public DistributionChannelRepositoryNeo4jImpl(
			GraphDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Override
	public DistributionChannel save(DistributionChannel distributionChannel,
			String sourceId, String targetId) {

		Transaction transaction = databaseService.beginTx();
		try {
			Node sourceNode = databaseService.getNodeById(Long
					.valueOf(sourceId));
			Node targetNode = databaseService.getNodeById(Long
					.valueOf(targetId));

			Relationship relationship = null;

			if (isBlank(distributionChannel.getId())) {
				relationship = sourceNode.createRelationshipTo(targetNode,
						FacilityRelationship.distributesTo);
				distributionChannel.setId(String.valueOf(relationship.getId()));
			} else {
				relationship = databaseService.getRelationshipById(Long
						.valueOf(distributionChannel.getId()));
			}

			relationship.setProperty("purchasingVolume",
					distributionChannel.getPurchasingVolume());
			transaction.success();
			return distributionChannel;
		} finally {
			transaction.finish();
		}
	}

	@Override
	public void delete(String distributionChannelId) {
		Transaction transaction = databaseService.beginTx();
		try {
			Relationship relationship = databaseService
					.getRelationshipById(Long.valueOf(distributionChannelId));
			relationship.delete();
			transaction.success();
		} finally {
			transaction.finish();
		}
	}

	@Override
	public DistributionChannel findOne(String distributionChannelId) {
		Relationship relationship = databaseService.getRelationshipById(Long
				.valueOf(distributionChannelId));
		DistributionChannel channel = new DistributionChannel();
		channel.setId(String.valueOf(relationship.getId()));
		channel.setPurchasingVolume((Double) relationship
				.getProperty("purchasingVolume"));

		String sourceId = String.valueOf(relationship.getStartNode().getId());
		String targetId = String.valueOf(relationship.getEndNode().getId());
		FacilityRepository facilityRepository = new FacilityRepositoryNeo4jImpl(
				databaseService);

		channel.setSource(facilityRepository.findOne(sourceId));
		channel.setTarget(facilityRepository.findOne(targetId));

		return channel;
	}

	@Override
	public List<DistributionChannel> findAll() {
		Iterator<Relationship> iterator = GlobalGraphOperations
				.at(databaseService).getAllRelationships().iterator();
		List<DistributionChannel> channels = new ArrayList<DistributionChannel>();
		while (iterator.hasNext()) {
			Relationship relationship = iterator.next();
			DistributionChannel channel = new DistributionChannel();
			channel.setId(String.valueOf(relationship.getId()));
			channel.setPurchasingVolume((Double) relationship
					.getProperty("purchasingVolume"));

			String sourceId = String.valueOf(relationship.getStartNode()
					.getId());
			String targetId = String.valueOf(relationship.getEndNode().getId());
			FacilityRepository facilityRepository = new FacilityRepositoryNeo4jImpl(
					databaseService);

			channel.setSource(facilityRepository.findOne(sourceId));
			channel.setTarget(facilityRepository.findOne(targetId));
			channels.add(channel);
		}

		return channels;
	}

	static enum FacilityRelationship implements RelationshipType {
		distributesTo
	}

	boolean isBlank(String string) {
		return string == null || "".equals(string);
	}

}
