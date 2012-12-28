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

import com.danielpacak.riskanalyzer.domain.Facility;

public class FacilityRepositoryJpaImplTest {

	EntityManagerFactory emf;
	EntityManager em;
	EntityTransaction transaction;
	FacilityRepositoryJpaImpl repository;

	@Before
	public void beforeTest() {
		emf = mock(EntityManagerFactory.class);
		em = mock(EntityManager.class);
		transaction = mock(EntityTransaction.class);
		when(emf.createEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(transaction);
		repository = new FacilityRepositoryJpaImpl(emf);
	}

	@Test
	public void testSave() throws Exception {
		Facility node = new Facility();
		repository.save(node);
		InOrder inOrder = Mockito.inOrder(em, transaction);
		inOrder.verify(transaction).begin();
		inOrder.verify(em).merge(node);
		inOrder.verify(em).flush();
		inOrder.verify(transaction).commit();
	}

	@Test
	public void testDelete() throws Exception {
		String nodeId = "13";
		Facility node = new Facility();
		when(em.find(Facility.class, nodeId)).thenReturn(node);

		repository.delete(nodeId);
		InOrder inOrder = Mockito.inOrder(em, transaction);
		inOrder.verify(transaction).begin();
		inOrder.verify(em).remove(node);
		inOrder.verify(transaction).commit();
	}

	@Test
	public void testFindOne() throws Exception {
		String nodeId = "13";
		Facility node = new Facility();
		when(em.find(Facility.class, nodeId)).thenReturn(node);
		Facility foundNode = repository.findOne(nodeId);

		Assert.assertEquals(node, foundNode);
	}

	@Test
	public void testFindAll() throws Exception {
		Query q = Mockito.mock(Query.class);
		List<Facility> queryResult = new ArrayList<Facility>();
		when(q.getResultList()).thenReturn(queryResult);

		when(
				em.createQuery("SELECT o FROM " + Facility.class.getName()
						+ " o")).thenReturn(q);

		List<Facility> allNodes = repository.findAll();
		Assert.assertEquals(queryResult, allNodes);

		InOrder inOrder = Mockito.inOrder(q, transaction);

		inOrder.verify(transaction).begin();
		inOrder.verify(q).getResultList();
		inOrder.verify(transaction).commit();
	}

}
