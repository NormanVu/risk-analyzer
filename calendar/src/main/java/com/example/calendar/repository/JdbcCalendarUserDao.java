package com.example.calendar.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.example.calendar.domain.CalendarUser;

@Repository
public class JdbcCalendarUserDao implements CalendarUserDao {

	private final JdbcOperations jdbcOperations;

	@Autowired
	public JdbcCalendarUserDao(JdbcOperations jdbcOperations) {
		Assert.notNull(jdbcOperations, "jdbcOperations cannot be null");
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	@Transactional(readOnly = true)
	public CalendarUser getUser(Integer id) {
		return jdbcOperations.queryForObject(
				"SELECT ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME FROM CALENDAR_USERS WHERE ID = ?",
				CALENDAR_USER_MAPPER, id);
	}

	@Override
	@Transactional(readOnly = true)
	public CalendarUser findUserByEmail(String email) {
		Assert.notNull(email, "Email cannot be null");
		try {
			return jdbcOperations.queryForObject(
					"SELECT ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME FROM CALENDAR_USERS WHERE EMAIL = ?",
					CALENDAR_USER_MAPPER, email);
		} catch (EmptyResultDataAccessException notFound) {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<CalendarUser> findUsersByEmail(String email) {
		Assert.hasText(email, "Email cannot be blank");
		return jdbcOperations.query(
				"SELECT ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME FROM CALENDAR_USERS WHERE EMAIL LIKE ? ORDER BY ID",
				CALENDAR_USER_MAPPER, email + "%");
	}

	@Override
	public Integer createUser(final CalendarUser user) {
		Assert.notNull(user, "User cannot be null");
		Assert.isNull(user.getId(), "userToAdd.getId() must be null when creating a new user");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcOperations.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO CALENDAR_USERS (EMAIL, PASSWORD, FIRST_NAME, LAST_NAME) VALUES (?, ?, ?, ?)",
						new String[] { "ID" });
				ps.setString(1, user.getEmail());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getFirstName());
				ps.setString(4, user.getLastName());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}


	private static final RowMapper<CalendarUser> CALENDAR_USER_MAPPER = new CalendarUserRowMapper("CALENDAR_USERS.");

	/**
	 * Create a new RowMapper that resolves {@link CalendarUser}'s given a
	 * column label prefix. By allowing the prefix to be specified we can reuse
	 * the same {@link RowMapper} for joins in other tables.
	 * 
	 * @author Rob Winch
	 * 
	 */
	static class CalendarUserRowMapper implements RowMapper<CalendarUser> {
		private final String columnLabelPrefix;

		public CalendarUserRowMapper(String columnLabelPrefix) {
			this.columnLabelPrefix = columnLabelPrefix;
		}

		public CalendarUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			CalendarUser user = new CalendarUser();
			user.setId(rs.getInt(columnLabelPrefix + "ID"));
			user.setEmail(rs.getString(columnLabelPrefix + "EMAIL"));
			user.setPassword(rs.getString(columnLabelPrefix + "PASSWORD"));
			user.setFirstName(rs.getString(columnLabelPrefix + "FIRST_NAME"));
			user.setLastName(rs.getString(columnLabelPrefix + "LAST_NAME"));
			return user;
		}
	};
}