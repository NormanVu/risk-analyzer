package com.example.calendar.service;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.Repository;

public interface RepositoryService {

	Repository create(UserContext context, String name, String description)
			throws IOException;

	List<Repository> findByUser(UserContext context) throws IOException;

	Repository findByUser(UserContext userContext, String repoName)
			throws IOException;

}
