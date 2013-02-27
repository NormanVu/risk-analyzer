package com.example.calendar.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.calendar.domain.CalendarUser;

public class SpringSecurityUserContext implements UserContext {

	private final CalendarService calendarService;

	public SpringSecurityUserContext(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	@Override
	public CalendarUser getCurrentUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return null;
		}

		String email = authentication.getName();
		return calendarService.findUserByEmail(email);
	}

	@Override
	public void setCurrentUser(CalendarUser user) {
		throw new UnsupportedOperationException();
	}

}
