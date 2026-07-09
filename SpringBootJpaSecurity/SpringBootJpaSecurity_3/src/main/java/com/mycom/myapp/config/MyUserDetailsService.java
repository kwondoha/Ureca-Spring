package com.mycom.myapp.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// kwondoha/1234
		return switch (username) {
			case "kwondoha" -> User.builder()
								.username("kwondoha")
								.password(DemoPasswords.ENCODED)
								.roles("CUSTOMER")
								.build();

			default -> throw new UsernameNotFoundException("User Not Found");
		};
	}
}
