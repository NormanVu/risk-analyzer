package com.danielpacak.riskanalyzer.frontend.repository.google;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

// TODO Implement tests
@RunWith(PowerMockRunner.class)
@PrepareForTest({ KeyFactory.class, Key.class })
public class FacilityRepositoryGoogleImplTest {

	/*@Test
	public void testDelete() throws Exception {
		String facilityId = "13";
		DatastoreService datastoreService = mock(DatastoreService.class);
		Transaction currentTransaction = mock(Transaction.class);
		when(datastoreService.getCurrentTransaction()).thenReturn(
				currentTransaction);

		mockStatic(KeyFactory.class);
		Key key = mock(Key.class);
		when(KeyFactory.createKey(FACILITY_ENTITY, Long.valueOf(facilityId))).thenReturn(key);

		FacilityRepositoryGoogleImpl facilityRepository = new FacilityRepositoryGoogleImpl(
				datastoreService);

		facilityRepository.delete(facilityId);
		InOrder inOrder = inOrder(datastoreService, currentTransaction);
		inOrder.verify(datastoreService).beginTransaction();
		inOrder.verify(datastoreService).delete(key);
		inOrder.verify(currentTransaction).commit();
	}*/
	
	@Test
	public void testFindAll() throws Exception {
		DatastoreService datastoreService = mock(DatastoreService.class);
		System.out.println(DistributionChannel.class.getSimpleName());
	}
	
	
	/*public List<Facility> findAll() {
		Query q = new Query(FACILITY_ENTITY);
		PreparedQuery pq = datastoreService.prepare(q);
		List<Facility> nodes = new ArrayList<Facility>();
		for (Entity nodeEntity : pq.asIterable()) {
			Facility node = map(nodeEntity);
			nodes.add(node);
		}
		return nodes;
	}*/

}
