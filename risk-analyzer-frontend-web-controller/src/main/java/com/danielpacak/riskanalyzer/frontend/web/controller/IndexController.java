package com.danielpacak.riskanalyzer.frontend.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "WEB-INF/view/index.jsp";
	}

}
