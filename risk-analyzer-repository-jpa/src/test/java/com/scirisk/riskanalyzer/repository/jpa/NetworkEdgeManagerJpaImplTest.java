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
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.jpa.NetworkEdgeManagerJpaImpl;

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
		manager = new NetworkEdgeManagerJpaImpl(emf);
	}

	@Test
	public void testSave() throws Exception {
		String edgeId = "13";
		Double purchasingVolume = new Double(0.5);
		String sourceId = "113";
		String targetId = "311";

		DistributionChannel edge = new DistributionChannel();
		edge.setId(edgeId);
		edge.setPurchasingVolume(purchasingVolume);
		Facility source = new Facility();
		source.setId(sourceId);
		Facility target = new Facility();
		target.setId(targetId);

		Mockito.when(em.find(Facility.class, sourceId)).thenReturn(source);
		Mockito.when(em.find(Facility.class, targetId)).thenReturn(target);

		manager.save(edge, sourceId, targetId);
		InOrder inOrder = Mockito.inOrder(em, transaction);
		inOrder.verify(transaction).begin();
		ArgumentCaptor<DistributionChannel> argument = ArgumentCaptor.forClass(DistributionChannel.class);
		inOrder.verify(em).merge(argument.capture());
		Assert.assertEquals(edgeId, argument.getValue().getId());
		Assert.assertEquals(purchasingVolume, argument.getValue().getPurchasingVolume());
		Assert.assertEquals(source, argument.getValue().getSource());
		Assert.assertEquals(target, argument.getValue().getTarget());
		inOrder.verify(transaction).commit();
	}

	@Test
	public void testFindOne() throws Exception {
		String edgeId = "13";
		DistributionChannel edge = new DistributionChannel();
		when(em.find(DistributionChannel.class, edgeId)).thenReturn(edge);
		DistributionChannel foundEdge = manager.findOne(edgeId);

		Assert.assertEquals(edge, foundEdge);
	}

	@Test
	public void testFindAll() throws Exception {
		Query q = Mockito.mock(Query.class);
		List<DistributionChannel> queryResult = new ArrayList<DistributionChannel>();
		when(q.getResultList()).thenReturn(queryResult);

		when(
				em.createQuery("SELECT o FROM " + DistributionChannel.class.getName()
						+ " o")).thenReturn(q);

		List<DistributionChannel> allEdges = manager.findAll();
		Assert.assertEquals(queryResult, allEdges);

		InOrder inOrder = Mockito.inOrder(q, transaction);

		inOrder.verify(transaction).begin();
		inOrder.verify(q).getResultList();
		inOrder.verify(transaction).commit();
	}

	@Test
	public void testDelete() throws Exception {
		String edgeId = "13";
		DistributionChannel edge = new DistributionChannel();
		when(em.find(DistributionChannel.class, edgeId)).thenReturn(edge);

		manager.delete(edgeId);
		InOrder inOrder = Mockito.inOrder(em, transaction);
		inOrder.verify(transaction).begin();
		inOrder.verify(em).remove(edge);
		inOrder.verify(transaction).commit();
	}

}
