package com.example;

import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Test;


public class GitHubLearningTest {

	@Test
	public void login() throws Exception {
		GitHubClient client = new GitHubClient();
		client.setCredentials("danielpacak", "28RueLacan");
		RepositoryService repositoryService = new RepositoryService(client);
		List<Repository> repositories = repositoryService.getRepositories("danielpacak");
		for (Repository r : repositories) {
			System.out.println(r);
		}
	}
	
}
