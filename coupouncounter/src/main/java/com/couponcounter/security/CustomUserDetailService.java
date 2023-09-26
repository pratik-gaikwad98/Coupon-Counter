package com.couponcounter.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.couponcounter.entity.Role;
import com.couponcounter.entity.Users;
import com.couponcounter.repository.UsersRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UsersRepo usersRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = usersRepo.findByEmail(username);
		isUserNull(users);
		List<Role> roles = users.getRoles();
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		User user = new User(users.getEmail(), users.getPassword(), authorities);
		return user;
	}

	private boolean isUserNull(Users users) {
		if (users == null) {
			throw new BadCredentialsException("INVALID_CREDENTIALS");
		}
		return false;
	}
}