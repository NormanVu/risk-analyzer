package com.example.calendar.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.calendar.domain.CalendarUser;
import com.example.calendar.repository.CalendarUserDao;

@Component
public class CalendarUserDetailsService implements UserDetailsService {

	private final CalendarUserDao calendarUserDao;

	@Autowired
	public CalendarUserDetailsService(CalendarUserDao calendarUserDao) {
		this.calendarUserDao = calendarUserDao;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CalendarUser user = calendarUserDao.findUserByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username/password.");
		}
		return new CalendarUserDetails(user);
	}

	private final class CalendarUserDetails extends CalendarUser implements UserDetails {
		CalendarUserDetails(CalendarUser user) {
			setId(user.getId());
			setEmail(user.getEmail());
			setFirstName(user.getFirstName());
			setLastName(user.getLastName());
			setPassword(user.getPassword());
		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			return CalendarUserAuthorityUtils.createAuthorities(this);
		}

		public String getUsername() {
			return getEmail();
		}

		public boolean isAccountNonExpired() {
			return true;
		}

		public boolean isAccountNonLocked() {
			return true;
		}

		public boolean isCredentialsNonExpired() {
			return true;
		}

		public boolean isEnabled() {
			return true;
		}
	}

}
