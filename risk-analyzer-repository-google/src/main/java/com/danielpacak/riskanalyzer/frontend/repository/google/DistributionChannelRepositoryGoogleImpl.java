package com.danielpacak.riskanalyzer.frontend.repository.google;

import java.util.ArrayList;
import java.util.List;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.DistributionChannelRepository;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class DistributionChannelRepositoryGoogleImpl implements
		DistributionChannelRepository {

	private DatastoreService datastoreService;

	public DistributionChannelRepositoryGoogleImpl(
			DatastoreService datastoreService) {
		this.datastoreService = datastoreService;
	}

	public DistributionChannel save(DistributionChannel channel,
			String sourceId, String targetId) {

		Entity entity = null;
		if (!isBlank(channel.getId())) {
			Key channelKey = KeyFactory.createKey(
					DistributionChannel.class.getSimpleName(),
					Long.valueOf(channel.getId()));
			try {
				entity = datastoreService.get(channelKey);
			} catch (EntityNotFoundException e) {
				throw new IllegalArgumentException(
						"Cannot find network edge entity [" + channel.getId()
								+ "].");
			}
		} else {
			entity = new Entity(DistributionChannel.class.getSimpleName());
		}

		Key sourceKey = KeyFactory.createKey(
				Facility.class.getName(), Long.valueOf(sourceId));
		Key targetKey = KeyFactory.createKey(
				Facility.class.getName(), Long.valueOf(targetId));

		entity.setProperty("purchasingVolume", channel.getPurchasingVolume());
		entity.setProperty("sourceId", sourceKey);
		entity.setProperty("targetId", targetKey);

		datastoreService.beginTransaction();
		Key generatedKey = datastoreService.put(entity);
		datastoreService.getCurrentTransaction().commit();
		channel.setId(String.valueOf(generatedKey.getId()));
		return channel;
	}

	public DistributionChannel findOne(String distributionChannelId) {
		Key channelKey = KeyFactory.createKey(
				DistributionChannel.class.getSimpleName(),
				Long.valueOf(distributionChannelId));
		try {
			Entity edgeEntity = datastoreService.get(channelKey);
			
			DistributionChannel channel = map(edgeEntity);

			Key sourceKey = (Key) edgeEntity.getProperty("sourceId");
			Key targetKey = (Key) edgeEntity.getProperty("targetId");

			Entity sourceEntity = datastoreService.get(sourceKey);
			Entity targetEntity = datastoreService.get(targetKey);

			channel.setSource(mapFacility(sourceEntity));
			channel.setTarget(mapFacility(targetEntity));
			return channel;
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException(
					"Cannot find network edge entity [" + distributionChannelId
							+ "].");
		}
	}

	public void delete(String channelId) {
		Key channelKey = KeyFactory.createKey(DistributionChannel.class.getSimpleName(), Long.valueOf(channelId));
		datastoreService.beginTransaction();
		datastoreService.delete(channelKey);
		datastoreService.getCurrentTransaction().commit();
	}

	public List<DistributionChannel> findAll() {
		Query q = new Query(DistributionChannel.class.getSimpleName());
		PreparedQuery pq = datastoreService.prepare(q);
		List<DistributionChannel> channels = new ArrayList<DistributionChannel>();
		for (Entity channelEntity : pq.asIterable()) {
			try {
				DistributionChannel channel = map(channelEntity);

				Key sourceKey = (Key) channelEntity.getProperty("sourceId");
				Key targetKey = (Key) channelEntity.getProperty("targetId");

				Entity sourceEntity = datastoreService.get(sourceKey);
				Entity targetEntity = datastoreService.get(targetKey);

				channel.setSource(mapFacility(sourceEntity));
				channel.setTarget(mapFacility(targetEntity));

				channels.add(channel);
			} catch (EntityNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return channels;
	}

	private DistributionChannel map(Entity channelEntity) {
		DistributionChannel channel = new DistributionChannel();
		channel.setId(String.valueOf(channelEntity.getKey().getId()));
		channel.setPurchasingVolume((Double) channelEntity
				.getProperty("purchasingVolume"));
		return channel;
	}

	private Facility mapFacility(Entity nodeEntity) {
		Facility n = new Facility();
		n.setId(String.valueOf(nodeEntity.getKey().getId()));
		n.setName((String) nodeEntity.getProperty("name"));
		n.setLatitude((Double) nodeEntity.getProperty("latitude"));
		n.setLongitude((Double) nodeEntity.getProperty("longitude"));
		return n;
	}

	boolean isBlank(String string) {
		return "".equals(string);
	}

}
