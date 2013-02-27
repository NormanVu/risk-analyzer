package com.example.calendar.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.example.calendar.domain.CalendarUser;
import com.example.calendar.domain.Event;

@Repository
public class JdbcEventDao implements EventDao {
	
	private Logger logger = LoggerFactory.getLogger(JdbcEventDao.class);

	private final JdbcOperations jdbcOperations;

	@Autowired
	public JdbcEventDao(JdbcOperations jdbcOperations) {
		Assert.notNull(jdbcOperations, "JdbcOperations must not be null");
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	@Transactional(readOnly = true)
	public Event getEvent(int eventId) {
		return jdbcOperations.queryForObject(EVENT_QUERY + " and e.id = ?", EVENT_ROW_MAPPER, eventId);
	}

	@Override
	public int createEvent(final Event event) {
		logger.debug("Creating a new event: {}", event);
		Assert.notNull(event, "event cannot be null");
		Assert.isNull(event.getId(), "event.getId() must be null when creating a new Message");

		final CalendarUser owner = event.getOwner();
		Assert.notNull(owner, "event.getOwner() cannot be null");
		final CalendarUser attendee = event.getAttendee();
		Assert.notNull(attendee, "attendee.getOwner() cannot be null");
		final Calendar when = event.getStartsAt();
		Assert.notNull(when, "event.getWhen() cannot be null");

		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcOperations.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"insert into events (starts_at,summary,description,owner_id,attendee_id) values (?, ?, ?, ?, ?)",
						PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setDate(1, new java.sql.Date(when.getTimeInMillis()));
				ps.setString(2, event.getSummary());
				ps.setString(3, event.getDescription());
				ps.setInt(4, owner.getId());
				ps.setObject(5, attendee == null ? null : attendee.getId());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Event> findForUser(int userId) {
		return jdbcOperations.query(EVENT_QUERY + " and (e.owner_id = ? or e.attendee_id = ?) order by e.id",
				EVENT_ROW_MAPPER, userId, userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Event> getEvents() {
		return jdbcOperations.query(EVENT_QUERY + " order by e.id", EVENT_ROW_MAPPER);
	}

	private static final RowMapper<Event> EVENT_ROW_MAPPER = new RowMapper<Event>() {
		public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
			CalendarUser attendee = ATTENDEE_ROW_MAPPER.mapRow(rs, rowNum);
			CalendarUser owner = OWNER_ROW_MAPPER.mapRow(rs, rowNum);

			Event event = new Event();
			event.setId(rs.getInt("events.id"));
			event.setSummary(rs.getString("events.summary"));
			event.setDescription(rs.getString("events.description"));
			Calendar when = Calendar.getInstance();
			when.setTime(rs.getDate("events.starts_at"));
			event.setStartsAt(when);
			event.setAttendee(attendee);
			event.setOwner(owner);
			return event;
		}
	};

	private static final RowMapper<CalendarUser> ATTENDEE_ROW_MAPPER = new JdbcCalendarUserDao.CalendarUserRowMapper(
			"attendee_");
	private static final RowMapper<CalendarUser> OWNER_ROW_MAPPER = new JdbcCalendarUserDao.CalendarUserRowMapper(
			"owner_");

	private static final String EVENT_QUERY = "select e.id, e.summary, e.description, e.starts_at, "
			+ "owner.id as owner_id, owner.email as owner_email, owner.password as owner_password, owner.first_name as owner_first_name, owner.last_name as owner_last_name, "
			+ "attendee.id as attendee_id, attendee.email as attendee_email, attendee.password as attendee_password, attendee.first_name as attendee_first_name, attendee.last_name as attendee_last_name "
			+ "from events as e, calendar_users as owner, calendar_users as attendee "
			+ "where e.owner_id = owner.id and e.attendee_id = attendee.id";
}
