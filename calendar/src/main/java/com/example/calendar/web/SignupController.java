package com.example.calendar.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.calendar.domain.CalendarUser;
import com.example.calendar.service.CalendarService;
import com.example.calendar.service.UserContext;
import com.example.calendar.web.model.SignupForm;

@Controller
public class SignupController {

	private final UserContext userContext;
	private final CalendarService calendarService;

	@Autowired
	public SignupController(UserContext userContext, CalendarService calendarService) {
		Assert.notNull(userContext, "UserContext cannot be null");
		Assert.notNull(calendarService, "CalendarService cannot be null");
		this.userContext = userContext;
		this.calendarService = calendarService;
	}

	@RequestMapping("/signup/form")
	public String signup(@ModelAttribute SignupForm signupForm) {
		return "signup/form";
	}

	@RequestMapping(value = "/signup/new", method = RequestMethod.POST)
	public String signup(@Valid SignupForm signupForm, BindingResult result, RedirectAttributes redirectAttributes) {
		System.out.println("elo ziomus");
		if (result.hasErrors()) {
			return "signup/form";
		}

		String email = signupForm.getEmail();
		if (calendarService.findUserByEmail(email) != null) {
			result.rejectValue("email", "errors.signup.email", "Email address is already in use.");
			return "signup/form";
		}

		CalendarUser user = new CalendarUser();
		user.setEmail(email);
		user.setFirstName(signupForm.getFirstName());
		user.setLastName(signupForm.getLastName());
		user.setPassword(signupForm.getPassword());

		int id = calendarService.createUser(user);
		user.setId(id);
		userContext.setCurrentUser(user);

		redirectAttributes.addFlashAttribute("message", "You have successfully signed up and logged in.");
		return "redirect:/";
	}
}
