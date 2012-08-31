package com.scirisk.riskanalyzer.repository.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;

public class DistributionChannelRepositoryJpaImpl implements DistributionChannelRepository {

	private EntityManagerFactory emf;

	public DistributionChannelRepositoryJpaImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public DistributionChannel save(DistributionChannel edge, String sourceId, String targetId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Facility source = em.find(Facility.class, sourceId);
		Facility target = em.find(Facility.class, targetId);

		edge.setId(isBlank(edge.getId()) ? UUID.randomUUID().toString() : edge.getId());
		edge.setSource(source);
		edge.setTarget(target);

		em.merge(edge);

		em.getTransaction().commit();
		return edge;
	}
	
	boolean isBlank(String string) {
		return "".equals(string);
	}

	public List<DistributionChannel> findAll() {
		EntityManager em = emf.createEntityManager();
		final String queryString = "SELECT o FROM "
				+ DistributionChannel.class.getName() + " o";
		Query q = em.createQuery(queryString);
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<DistributionChannel> nodes = q.getResultList();
		em.getTransaction().commit();
		return nodes;
	}

	public void delete(final String edgeId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		DistributionChannel edge = em.find(DistributionChannel.class, edgeId);
		em.remove(edge);
		em.getTransaction().commit();
	}

	public DistributionChannel findOne(final String edgeId) {
		EntityManager em = emf.createEntityManager();
		return em.find(DistributionChannel.class, edgeId);
	}

}
