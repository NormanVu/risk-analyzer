package com.scirisk.riskanalyzer.repository.jpa;

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

import com.scirisk.riskanalyzer.domain.NetworkNode;

public class NetworkNodeManagerJpaImplTest {

	EntityManagerFactory emf;
	EntityManager em;
	EntityTransaction transaction;
	NetworkNodeManagerJpaImpl manager;

	@Before
	public void beforeTest() {
		emf = mock(EntityManagerFactory.class);
		em = mock(EntityManager.class);
		transaction = mock(EntityTransaction.class);
		when(emf.createEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(transaction);
		manager = new NetworkNodeManagerJpaImpl(emf);
	}

	@Test
	public void testSave() throws Exception {
		NetworkNode node = new NetworkNode();
		manager.save(node);
		InOrder inOrder = Mockito.inOrder(em, transaction);
		inOrder.verify(transaction).begin();
		inOrder.verify(em).merge(node);
		inOrder.verify(em).flush();
		inOrder.verify(transaction).commit();
	}

	@Test
	public void testDelete() throws Exception {
		Long nodeId = new Long(13);
		NetworkNode node = new NetworkNode();
		when(em.find(NetworkNode.class, nodeId)).thenReturn(node);

		manager.delete(nodeId);
		InOrder inOrder = Mockito.inOrder(em, transaction);
		inOrder.verify(transaction).begin();
		inOrder.verify(em).remove(node);
		inOrder.verify(transaction).commit();
	}

	@Test
	public void testFindOne() throws Exception {
		Long nodeId = new Long(13);
		NetworkNode node = new NetworkNode();
		when(em.find(NetworkNode.class, nodeId)).thenReturn(node);
		NetworkNode foundNode = manager.findOne(nodeId);

		Assert.assertEquals(node, foundNode);
	}

	@Test
	public void testFindAll() throws Exception {
		Query q = Mockito.mock(Query.class);
		List<NetworkNode> queryResult = new ArrayList<NetworkNode>();
		when(q.getResultList()).thenReturn(queryResult);

		when(
				em.createQuery("SELECT o FROM " + NetworkNode.class.getName()
						+ " o")).thenReturn(q);

		List<NetworkNode> allNodes = manager.findAll();
		Assert.assertEquals(queryResult, allNodes);

		InOrder inOrder = Mockito.inOrder(q, transaction);

		inOrder.verify(transaction).begin();
		inOrder.verify(q).getResultList();
		inOrder.verify(transaction).commit();
	}

}
