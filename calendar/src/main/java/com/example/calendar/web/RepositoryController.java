package com.example.calendar.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.eclipse.egit.github.core.Repository;
import org.jenkins.client.JobTemplateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danielpacak.jenkins.ci.client.JenkinsClient;
import com.danielpacak.jenkins.ci.client.JenkinsXmlClient;
import com.example.calendar.domain.CalendarUser;
import com.example.calendar.service.RepositoryService;
import com.example.calendar.service.UserContext;
import com.example.calendar.web.model.CreateRepoForm;

@Controller
@RequestMapping("/repos")
public class RepositoryController {

	private final RepositoryService repositoryService;
	private final UserContext userContext;

	@Autowired
	public RepositoryController(RepositoryService repositoryService,
			UserContext userContext) {
		this.repositoryService = repositoryService;
		this.userContext = userContext;
	}

	@RequestMapping("/my")
	public ModelAndView repos() throws Exception {
		return new ModelAndView("repos/my", "repos",
				repositoryService.findByUser(userContext));
	}

	@RequestMapping("/{repoName}")
	public ModelAndView show(@PathVariable String repoName) throws Exception {
		Repository repo = repositoryService.findByUser(userContext, repoName);
		
		final String jobName = getJenkinsJobName(repoName);
		
		JenkinsClient jenkinsClient = new JenkinsXmlClient("localhost", 8080);
		Long lastBuildId = jenkinsClient.getLastBuildId(jobName);
		String lastBuildStatus = jenkinsClient.getBuildStatus(jobName, lastBuildId);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("repo", repo);
		model.put("releaseStatus", lastBuildStatus);
		return new ModelAndView("repos/show", model);
	}

	@RequestMapping("/form")
	public String createRepoForm(@ModelAttribute CreateRepoForm createEventForm) {
		return "repos/create";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String createRepo(@Valid CreateRepoForm createRepoForm,
			BindingResult result, RedirectAttributes redirectAttributes)
			throws Exception {
		if (result.hasErrors()) {
			return "repos/create";
		}
		repositoryService.create(userContext, createRepoForm.getName(),
				createRepoForm.getDescription());

		redirectAttributes.addFlashAttribute("message",
				"Successfully created new repository");
		return "redirect:/repos/my";
	}

	String getJenkinsJobName(String repoName) {
		return new StringBuilder(repoName).append("-release").toString();
	}

	@RequestMapping(value = "/release/{repoName}", method = RequestMethod.GET)
	public String release(@PathVariable String repoName,
			RedirectAttributes redirect) throws Exception {
		System.out.println("SYSOUT RELEASING " + repoName);

		Repository repository = repositoryService.findByUser(userContext,
				repoName);

		CalendarUser user = userContext.getCurrentUser();

		JenkinsClient jenkinsClient = new JenkinsXmlClient("localhost", 8080);
		final String jobName = getJenkinsJobName(repoName);

		JobTemplateHelper helper = new JobTemplateHelper();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("description",
				"Release and deploy new version of this component.");
		params.put("username", "danielpacak"); // get from logged in user
		params.put("password", user.getPassword());
		params.put("cloneUrl", repository.getCloneUrl());

		String configXml = helper.renderTemplate(
				"jenkins/job/config/maven-release-project.ftl", params);

		jenkinsClient.createJob(jobName, IOUtils.toInputStream(configXml));
		jenkinsClient.scheduleABuild(jobName);
		String buildUrl = "<a href='#'>http://localhost:8080/job/" + jobName
				+ "</a>";
		redirect.addFlashAttribute("message",
				"Successfuly scheduled release build. You can check the status here: "
						+ buildUrl);

		return "redirect:/repos/my";
	}

}
