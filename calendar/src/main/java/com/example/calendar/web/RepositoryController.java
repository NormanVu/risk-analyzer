package com.example.calendar.web;

import javax.validation.Valid;

import org.eclipse.egit.github.core.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		return new ModelAndView("repos/show", "repo", repo);
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

}
