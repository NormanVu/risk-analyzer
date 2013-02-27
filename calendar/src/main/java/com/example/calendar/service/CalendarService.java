package com.example.calendar.service;

import java.util.List;

import com.example.calendar.domain.CalendarUser;
import com.example.calendar.domain.Event;

public interface CalendarService {

	CalendarUser getUser(Integer userId);

	CalendarUser findUserByEmail(String email);

	List<CalendarUser> findUsersByEmail(String partialEmail);

	Integer createUser(CalendarUser user);

	Event getEvent(Integer eventId);

	Integer createEvent(Event event);

	List<Event> findForUser(Integer userId);

	List<Event> getEvents();

}
