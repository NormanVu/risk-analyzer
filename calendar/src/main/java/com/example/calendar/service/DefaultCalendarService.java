package com.example.calendar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.example.calendar.domain.CalendarUser;
import com.example.calendar.domain.Event;
import com.example.calendar.repository.CalendarUserDao;
import com.example.calendar.repository.EventDao;

@Component
public class DefaultCalendarService implements CalendarService {

	private final EventDao eventDao;
	private final CalendarUserDao userDao;

	@Autowired
	public DefaultCalendarService(EventDao eventDao, CalendarUserDao userDao) {
		Assert.notNull(eventDao, "EventDao cannot be null");
		Assert.notNull(userDao, "UserDao cannot be null");

		this.eventDao = eventDao;
		this.userDao = userDao;
	}

	public Event getEvent(Integer eventId) {
		return eventDao.getEvent(eventId);
	}

	public Integer createEvent(Event event) {
		return eventDao.createEvent(event);
	}

	public List<Event> findForUser(Integer userId) {
		return eventDao.findForUser(userId);
	}

	public List<Event> getEvents() {
		return eventDao.getEvents();
	}

	public CalendarUser getUser(Integer id) {
		return userDao.getUser(id);
	}

	public CalendarUser findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	public List<CalendarUser> findUsersByEmail(String emailPattern) {
		return userDao.findUsersByEmail(emailPattern);
	}

	public Integer createUser(CalendarUser user) {
		return userDao.createUser(user);
	}

}
