package com.scirisk.riskanalyzer.repository.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.scirisk.riskanalyzer.domain.DistributionNetwork;
import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.DistributionNetworkRepository;

public class DistributionNetworkRepositoryJpaImpl implements DistributionNetworkRepository {

	private EntityManagerFactory emf;
	
	public DistributionNetworkRepositoryJpaImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@SuppressWarnings("unchecked")
	public DistributionNetwork read() {
		EntityManager em = emf.createEntityManager();
		Query nodeQuery = em.createQuery("SELECT o FROM "
				+ Facility.class.getName() + " o");
		Query edgeQuery = em.createQuery("SELECT o FROM "
				+ DistributionChannel.class.getName() + " o");
		em.getTransaction().begin();

		List<Facility> nodes = nodeQuery.getResultList();
		List<DistributionChannel> edges = edgeQuery.getResultList();

		em.getTransaction().commit();

		DistributionNetwork network = new DistributionNetwork();
		network.setNodes(nodes);
		network.setEdges(edges);

		return network;
	}

	// TODO ADD OPTION TO APPEND NODES
	public void save(final DistributionNetwork network) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.createQuery("DELETE FROM " + DistributionChannel.class.getName())
				.executeUpdate();
		em.createQuery("DELETE FROM " + Facility.class.getName())
				.executeUpdate();

		Map<String, String> nodeIdMap = new HashMap<String, String>();

		// persist nodes and populate nodeIdMap
		for (Facility nn : network.getNodes()) {
			String fakeId = nn.getId();
			nn.setId(null);
			em.persist(nn);
			em.flush();
			String generatedId = nn.getId();
			nodeIdMap.put(fakeId, generatedId);
		}

		// persist edges with references to nodes created in the same
		// transaction
		for (DistributionChannel ne : network.getEdges()) {
			String sourceId = nodeIdMap.get(ne.getSource().getId());
			String targetId = nodeIdMap.get(ne.getTarget().getId());
			Facility source = em.find(Facility.class, sourceId);
			Facility target = em.find(Facility.class, targetId);
			ne.setSource(source);
			ne.setTarget(target);
			em.persist(ne);
		}

		em.getTransaction().commit();
	}

}