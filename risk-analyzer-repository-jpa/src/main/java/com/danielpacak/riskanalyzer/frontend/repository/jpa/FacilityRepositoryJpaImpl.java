package com.danielpacak.riskanalyzer.frontend.repository.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.FacilityRepository;

public class FacilityRepositoryJpaImpl implements FacilityRepository {

	private EntityManagerFactory emf;

	public FacilityRepositoryJpaImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public Facility save(final Facility node) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		node.setId(isBlank(node.getId()) ? UUID.randomUUID().toString() : node
				.getId());
		em.merge(node);
		em.flush();
		em.getTransaction().commit();
		return node;
	}

	public List<Facility> findAll() {
		EntityManager em = emf.createEntityManager();
		final String queryString = "SELECT o FROM " + Facility.class.getName()
				+ " o";
		Query q = em.createQuery(queryString);
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Facility> nodes = q.getResultList();
		em.getTransaction().commit();
		return nodes;
	}

	public void delete(final String nodeId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Facility nn = em.find(Facility.class, nodeId);
		em.remove(nn);
		em.getTransaction().commit();
	}

	public Facility findOne(final String nodeId) {
		EntityManager em = emf.createEntityManager();
		Facility node = em.find(Facility.class, nodeId);
		return node;
	}

	boolean isBlank(String string) {
		return "".equals(string);
	}

}
