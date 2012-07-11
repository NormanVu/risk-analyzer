package com.scirisk.riskanalyzer.service;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.scirisk.riskanalyzer.entity.NetworkNode;

public class NetworkServiceTest {

	ApplicationContext applicationContext;
	NetworkService networkService;

	@Before
	public void setup() throws Exception {
		this.applicationContext = new ClassPathXmlApplicationContext("spring/context.xml");
		this.networkService = applicationContext.getBean(NetworkService.class);
	}

	@Test
	public void testMe() throws Exception {
		Assert.assertNotNull(applicationContext);
		//NetworkService networkService = applicationContext.getBean(NetworkService.class);
		Assert.assertNotNull(networkService);
		NetworkNode networkNode = new NetworkNode();
		
		networkNode.setId(UUID.randomUUID().toString());
		networkNode.setName("NodeName");
		networkNode.setDescription("NodeDescription");
		
		NetworkNode savedNetworkNode = networkService.save(networkNode);
		Assert.assertNotNull(savedNetworkNode);
		
		System.out.println(networkService.findAll());
		
		networkService.delete(savedNetworkNode);
		System.out.println(networkService.findAll());
	}

}
