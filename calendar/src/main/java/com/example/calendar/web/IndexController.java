package com.example.calendar.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String indexPage(HttpServletRequest request) {
		System.out.println("user principal: " + request.getUserPrincipal());
		System.out.println("is user admin: " + request.isUserInRole("ROLE_ADMIN"));
		return "index";
	}

}
