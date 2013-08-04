package com.danielpacak.riskanalyzer.frontend.repository.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.api.DistributionChannelRepository;

public class JpaDistributionChannelRepository implements DistributionChannelRepository {

	private static final Logger logger = LoggerFactory.getLogger(JpaDistributionChannelRepository.class);
	private EntityManagerFactory emf;

	public JpaDistributionChannelRepository(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@CacheEvict(value = "distribution-channel", key = "#distributionChannel.id", condition = "#distributionChannel.id != null")
	public DistributionChannel save(DistributionChannel distributionChannel, String sourceId, String targetId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Facility source = em.find(Facility.class, sourceId);
		Facility target = em.find(Facility.class, targetId);

		distributionChannel.setId(isBlank(distributionChannel.getId()) ? UUID.randomUUID().toString()
				: distributionChannel.getId());
		distributionChannel.setSource(source);
		distributionChannel.setTarget(target);

		em.merge(distributionChannel);

		em.getTransaction().commit();
		return distributionChannel;
	}

	boolean isBlank(String string) {
		return "".equals(string);
	}

	public List<DistributionChannel> findAll() {
		EntityManager em = emf.createEntityManager();
		final String queryString = "SELECT o FROM " + DistributionChannel.class.getName() + " o";
		Query q = em.createQuery(queryString);
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<DistributionChannel> nodes = q.getResultList();
		em.getTransaction().commit();
		return nodes;
	}

	@CacheEvict(value = "distribution-channel")
	public void delete(final String distributionChannelId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		DistributionChannel edge = em.find(DistributionChannel.class, distributionChannelId);
		em.remove(edge);
		em.getTransaction().commit();
	}

	@Cacheable(value = "distribution-channel")
	public DistributionChannel findOne(final String distributionChannelId) {
		logger.debug("Expensive retrieval of distribution channel with id [{}]", distributionChannelId);
		EntityManager em = emf.createEntityManager();
		return em.find(DistributionChannel.class, distributionChannelId);
	}

}
