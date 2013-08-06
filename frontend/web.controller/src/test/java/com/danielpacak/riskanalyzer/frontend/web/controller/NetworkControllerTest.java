package com.danielpacak.riskanalyzer.frontend.web.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import com.danielpacak.riskanalyzer.domain.DistributionNetwork;
import com.danielpacak.riskanalyzer.frontend.repository.api.DistributionNetworkRepository;
import com.danielpacak.riskanalyzer.frontend.service.api.NetworkMarshaller;
import com.danielpacak.riskanalyzer.frontend.service.api.NetworkParser;

/**
 * Tests for {@link NetworkController}.
 */
@RunWith(MockitoJUnitRunner.class)
public class NetworkControllerTest {

	@Mock
	DistributionNetworkRepository networkRepository;

	@Mock
	NetworkMarshaller networkMarshaller;

	@Mock
	NetworkParser networkParser;

	NetworkController controller;

	@Before
	public void beforeTest() throws Exception {
		controller = new NetworkController(networkRepository, networkMarshaller, networkParser);
	}

	@Test
	public void testGetNetworkForGoogleMap() throws Exception {
		DistributionNetwork network = new DistributionNetwork();
		when(networkRepository.read()).thenReturn(network);

		Assert.assertEquals(network, controller.getNetworkForGoogleMap());
		verify(networkRepository).read();
	}

	@Test
	public void testImportFromXmlWithNonEmptyFile() throws Exception {

		MultipartFile networkXml = mock(MultipartFile.class);
		when(networkXml.isEmpty()).thenReturn(false);
		InputStream inputStream = mock(InputStream.class);
		when(networkXml.getInputStream()).thenReturn(inputStream);
		DistributionNetwork network = mock(DistributionNetwork.class);
		when(networkParser.parse(inputStream)).thenReturn(network);

		HttpServletResponse httpResponse = mock(HttpServletResponse.class);
		PrintWriter writer = mock(PrintWriter.class);
		when(httpResponse.getWriter()).thenReturn(writer);

		controller.importFromXml(networkXml, httpResponse);

		verify(networkParser).parse(inputStream);
		verify(networkRepository).save(network);
		// FIXME This text/html content type is probably a bug in ExtJS 4
		verify(httpResponse).setContentType("text/html");

	}

	@Test
	public void testImportFromXmlWithEmptyFile() throws Exception {

		MultipartFile networkXml = mock(MultipartFile.class);
		when(networkXml.isEmpty()).thenReturn(true);

		HttpServletResponse httpResponse = mock(HttpServletResponse.class);
		PrintWriter writer = mock(PrintWriter.class);
		when(httpResponse.getWriter()).thenReturn(writer);

		controller.importFromXml(networkXml, httpResponse);
		// FIXME This text/html content type is probably a bug in ExtJS 4
		verify(httpResponse).setContentType("text/html");
	}

}
