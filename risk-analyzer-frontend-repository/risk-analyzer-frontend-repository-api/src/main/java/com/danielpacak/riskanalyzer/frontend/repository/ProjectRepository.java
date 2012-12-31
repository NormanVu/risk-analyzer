package com.danielpacak.riskanalyzer.frontend.repository;

import java.util.List;

import com.danielpacak.riskanalyzer.domain.Project;

public interface ProjectRepository {
	Project save(Project project);

	Project findOne(String projectId);

	void delete(String projectId);

	List<Project> findAll();

}
