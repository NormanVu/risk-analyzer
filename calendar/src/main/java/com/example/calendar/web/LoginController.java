package com.example.calendar.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value = "/login/form.do", method = RequestMethod.GET)
	public String loginFormPage() {
		return "login";
	}

}
