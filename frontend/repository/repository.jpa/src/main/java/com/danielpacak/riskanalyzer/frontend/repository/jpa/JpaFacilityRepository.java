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

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.api.FacilityRepository;

public class JpaFacilityRepository implements FacilityRepository {

	private static final Logger logger = LoggerFactory.getLogger(JpaFacilityRepository.class);
	private EntityManagerFactory emf;

	public JpaFacilityRepository(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@CacheEvict(value = "facility", key = "#facility.id", condition = "#facility.id != null")
	public Facility save(final Facility facility) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		facility.setId(isBlank(facility.getId()) ? UUID.randomUUID().toString() : facility.getId());
		em.merge(facility);
		em.flush();
		em.getTransaction().commit();
		return facility;
	}

	public List<Facility> findAll() {
		EntityManager em = emf.createEntityManager();
		final String queryString = "SELECT o FROM " + Facility.class.getName() + " o";
		Query q = em.createQuery(queryString);
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Facility> nodes = q.getResultList();
		em.getTransaction().commit();
		return nodes;
	}

	@CacheEvict(value = "facility")
	public void delete(final String facilityId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Facility nn = em.find(Facility.class, facilityId);
		em.remove(nn);
		em.getTransaction().commit();
	}

	@Cacheable(value = "facility")
	public Facility findOne(final String facilityId) {
		logger.debug("Expensive retrieval of facility with id [{}]", facilityId);
		EntityManager em = emf.createEntityManager();
		Facility node = em.find(Facility.class, facilityId);
		return node;
	}

	boolean isBlank(String string) {
		return "".equals(string);
	}

}
