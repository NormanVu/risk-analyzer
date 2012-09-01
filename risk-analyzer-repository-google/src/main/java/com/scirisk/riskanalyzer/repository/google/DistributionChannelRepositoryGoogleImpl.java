package com.scirisk.riskanalyzer.repository.google;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;

public class DistributionChannelRepositoryGoogleImpl implements
		DistributionChannelRepository {

	private DatastoreService datastoreService;

	public DistributionChannelRepositoryGoogleImpl(
			DatastoreService datastoreService) {
		this.datastoreService = datastoreService;
	}

	public DistributionChannel save(DistributionChannel edge, String sourceId,
			String targetId) {
		Key sourceKey = KeyFactory.createKey("NetworkNode", sourceId);
		Key targetKey = KeyFactory.createKey("NetworkNode", targetId);

		Entity entity = null;
		if (edge.getId() != null) {
			Key edgeKey = KeyFactory.createKey("NetworkEdge", edge.getId());
			try {
				entity = datastoreService.get(edgeKey);
			} catch (EntityNotFoundException e) {
				throw new IllegalArgumentException(
						"Cannot find network edge entity [" + edge.getId()
								+ "].");
			}
		} else {
			entity = new Entity("NetworkEdge");
		}

		entity.setProperty("purchasingVolume", edge.getId());
		entity.setProperty("sourceId", sourceKey);
		entity.setProperty("targetId", targetKey);
		datastoreService.beginTransaction();
		datastoreService.put(entity);
		datastoreService.getCurrentTransaction().commit();
		return edge;
	}

	public DistributionChannel findOne(String edgeId) {
		Key edgeKey = KeyFactory.createKey("NetworkEdge", edgeId);
		try {
			Entity edgeEntity = datastoreService.get(edgeKey);
			DistributionChannel edge = createEdge(edgeEntity);

			Key sourceKey = (Key) edgeEntity.getProperty("sourceId");
			Key targetKey = (Key) edgeEntity.getProperty("targetId");

			Entity sourceEntity = datastoreService.get(sourceKey);
			Entity targetEntity = datastoreService.get(targetKey);

			edge.setSource(createNode(sourceEntity));
			edge.setTarget(createNode(targetEntity));
			return edge;
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException(
					"Cannot find network edge entity [" + edgeId + "].");
		}
	}

	public void delete(final String edgeId) {
		Key edgeKey = KeyFactory.createKey("NetworkEdge", edgeId);
		datastoreService.beginTransaction();
		datastoreService.delete(edgeKey);
		datastoreService.getCurrentTransaction().commit();
	}

	public List<DistributionChannel> findAll() {
		Query q = new Query("NetworkEdge");
		PreparedQuery pq = datastoreService.prepare(q);
		ArrayList<DistributionChannel> edges = new ArrayList<DistributionChannel>();
		for (Entity edgeEntity : pq.asIterable()) {
			try {
				DistributionChannel edge = createEdge(edgeEntity);

				Key sourceKey = (Key) edgeEntity.getProperty("sourceId");
				Key targetKey = (Key) edgeEntity.getProperty("targetId");

				Entity sourceEntity = datastoreService.get(sourceKey);
				Entity targetEntity = datastoreService.get(targetKey);

				edge.setSource(createNode(sourceEntity));
				edge.setTarget(createNode(targetEntity));

				edges.add(edge);
			} catch (EntityNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return edges;
	}

	private DistributionChannel createEdge(Entity edgeEntity) {
		DistributionChannel edge = new DistributionChannel();
		edge.setId(edgeEntity.getKey().toString());
		edge.setPurchasingVolume((Double) edgeEntity
				.getProperty("purchasingVolume"));
		return edge;
	}

	private Facility createNode(Entity nodeEntity) {
		Facility n = new Facility();
		n.setId(nodeEntity.getKey().toString());
		n.setName((String) nodeEntity.getProperty("name"));
		n.setLatitude((Double) nodeEntity.getProperty("latitude"));
		n.setLongitude((Double) nodeEntity.getProperty("longitude"));
		return n;
	}

}
