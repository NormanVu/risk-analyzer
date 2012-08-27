package com.scirisk.riskanalyzer.persistence.jpa;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.scirisk.riskanalyzer.domain.NetworkEdge;

public class NetworkEdgeManagerJpaImplTest {
	EntityManagerFactory emf;
	EntityManager em;
	EntityTransaction transaction;
	NetworkEdgeManagerJpaImpl manager;

	@Before
	public void beforeTest() {
		emf = mock(EntityManagerFactory.class);
		em = mock(EntityManager.class);
		transaction = mock(EntityTransaction.class);
		when(emf.createEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(transaction);
		manager = new NetworkEdgeManagerJpaImpl();
		manager.emf = emf;
	}

	@Test
	public void testFindOne() throws Exception {
		Long edgeId = new Long(13);
		NetworkEdge edge = new NetworkEdge();
		when(em.find(NetworkEdge.class, edgeId)).thenReturn(edge);
		NetworkEdge foundEdge = manager.findOne(edgeId);

		Assert.assertEquals(edge, foundEdge);
	}

	@Test
	public void testFindAll() throws Exception {
		Query q = Mockito.mock(Query.class);
		List<NetworkEdge> queryResult = new ArrayList<NetworkEdge>();
		when(q.getResultList()).thenReturn(queryResult);

		when(
				em.createQuery("SELECT o FROM " + NetworkEdge.class.getName()
						+ " o")).thenReturn(q);

		List<NetworkEdge> allEdges = manager.findAll();
		Assert.assertEquals(queryResult, allEdges);

		InOrder inOrder = Mockito.inOrder(q, transaction);

		inOrder.verify(transaction).begin();
		inOrder.verify(q).getResultList();
		inOrder.verify(transaction).commit();
	}

	@Test
	public void testDelete() throws Exception {
		Long edgeId = new Long(13);
		NetworkEdge edge = new NetworkEdge();
		when(em.find(NetworkEdge.class, edgeId)).thenReturn(edge);

		manager.delete(edgeId);
		InOrder inOrder = Mockito.inOrder(em, transaction);
		inOrder.verify(transaction).begin();
		inOrder.verify(em).remove(edge);
		inOrder.verify(transaction).commit();
	}

}
