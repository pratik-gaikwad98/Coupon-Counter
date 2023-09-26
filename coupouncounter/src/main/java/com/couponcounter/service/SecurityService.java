package com.couponcounter.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.couponcounter.dto.UsersDto;
import com.couponcounter.entity.Role;
import com.couponcounter.entity.UserAuthenticationDetail;
import com.couponcounter.entity.Users;
import com.couponcounter.exceptions.InvalidUserException;
import com.couponcounter.exceptions.UserForbiddenErrorHandler;
import com.couponcounter.helper.CookieUtil;
import com.couponcounter.repository.RoleRepository;
import com.couponcounter.repository.UserAuthenticationRepository;
import com.couponcounter.repository.UsersRepo;
import com.couponcounter.requests.AuthenticationRequest;
import com.couponcounter.response.AuthenticationResponse;
import com.couponcounter.security.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class SecurityService {

	@Autowired
	UserAuthenticationRepository userAuthRepository;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsersRepo usersRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserAuthenticationRepository userAuthRepo;

	@Autowired
	private CookieUtil cookieUtil;

	@Value("${authentication-test.auth.accessTokenCookieName}")
	private String accessTokenCookieName;

	public AuthenticationResponse login(AuthenticationRequest userLogin) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
		Users users = usersRepo.findByEmail(userLogin.getUsername());
		String token = jwtProvider.generateAccessToken(users.getUsername(), users.getEmail(), getRolesNames(users));
		updateAuthDetailTable(userLogin.getUsername(), token);
		AuthenticationResponse jwtResponse = new AuthenticationResponse(token);
		return jwtResponse;
	}

	public void logout() {
		userAuthRepository.deleteById(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	public Map<String, String> register(UsersDto userDto) {
		Map<String, String> message = new HashMap<>();
		Users existingUser = usersRepo.findByEmail(userDto.getEmail());
		if (existingUser != null) {
			throw new InvalidUserException("Email already exists!");
		}
		Users user = new Users(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), userDto.getEmail(),
				getRoles(userDto));
		usersRepo.save(user);
		message.put("message", "User Add Succesfull");
		return message;
	}

	private List<String> getRolesNames(Users users) {
		List<Role> roles = users.getRoles();
		List<String> roleNames = new ArrayList<>();
		for (Role role : roles) {
			roleNames.add(role.getRole());
		}
		return roleNames;
	}

	public void userLogin(HttpServletResponse response, AuthenticationRequest loginRequest) throws Exception {
		String newAccessToken = login(loginRequest).getToken();
		cookieUtil.addTokenCookie(response, newAccessToken, accessTokenCookieName);
	}

	public void userLogout(HttpServletResponse response) {
		cookieUtil.deleteTokenCookie(response, accessTokenCookieName);
		cookieUtil.deleteTokenCookie(response, "SESSION");
		userAuthRepository.deleteById(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	private List<Role> getRoles(UsersDto userDto) {
		List<Role> roles = new ArrayList<>();
		List<String> selectedRoles = userDto.getRole();
		for (String userRole : selectedRoles) {
			Role role = roleRepository.findByRole(userRole.toUpperCase());
			if (role != null && (!userRole.equalsIgnoreCase("ROLE_ADMIN") || !userRole.equalsIgnoreCase("ROLE_USER"))) {
				roles.add(role);
			}else {
				throw new InvalidUserException("Role sholud be 'ROLE_ADMIN' OR 'ROLE_USER'");
			}
		}
		return roles;
	}

	private void updateAuthDetailTable(String username, String token) {
		UserAuthenticationDetail user = userAuthRepository.findById(username).orElse(null);
		if (user == null) {
			userAuthRepository
					.save(new UserAuthenticationDetail(username, token, jwtProvider.extractExpiration(token)));
		} else {
			user.setAccessToken(token);
			user.setExpiryTime(jwtProvider.extractExpiration(token));
			userAuthRepository.save(user);
		}
	}

	public boolean isUserAuthenticated(String username) throws UserForbiddenErrorHandler {
		UserAuthenticationDetail user = userAuthRepo.findById(username).orElse(null);
		if (user == null) {
			throw new UserForbiddenErrorHandler("Please Login!!");
		} else if (!jwtProvider.validateToken(user.getAccessToken())) {
			throw new UserForbiddenErrorHandler("Please Login!!");
		}
		return true;
	}

	public String getJwtToken(HttpServletRequest request) {
		String jwt;
		if (getClient(request).contains("Postman")) {
			jwt = getJwtFromRequest(request);

		} else {
			jwt = cookieUtil.readServletCookie(request, accessTokenCookieName);
		}
		return jwt;
	}

	public String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization"), jwt = null;
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			String accessToken = bearerToken.substring(7);
			if (accessToken == null) {
				return null;
			}
			jwt = accessToken;
		}
		return jwt;
	}

	public String getClient(HttpServletRequest request) {
		String userAgent = ((HttpServletRequestWrapper) request).getHeader("User-Agent");
		if (userAgent.contains("Mozilla") || userAgent.contains("Chrome")) {
			return "Web";
		} else if (userAgent.contains("iOS")) {
			return "iOS";
		} else if (userAgent.contains("Android")) {
			return "Android";
		} else {
			return "Postman";
		}
	}
}
