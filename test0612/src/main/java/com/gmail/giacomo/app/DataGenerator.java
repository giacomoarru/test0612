package com.gmail.giacomo.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.gmail.giacomo.app.security.SecurityConfiguration;
import com.gmail.giacomo.backend.data.Role;
import com.gmail.giacomo.backend.data.entity.User;
import com.gmail.giacomo.backend.repositories.UserRepository;

@SpringComponent
public class DataGenerator implements HasLogger {

	private UserRepository userRepository;

	@Autowired
	SecurityConfiguration securityConfiguration;
	
	@Autowired
	public DataGenerator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostConstruct
	public void loadData() {
		if (userRepository.count() != 0L) {
			getLogger().info("Using existing database");
			return;
		}

		createAdmin(userRepository);

	}


	private User createAdmin(UserRepository userRepository) {
		return userRepository.save(
				createUser("admin", "admin", Role.ADMIN));
	}

	public User createUser(String username, String password, String role) {
		User user = new User(username, password, securityConfiguration);
		user.setRole(role);
		return user;
	}
}
