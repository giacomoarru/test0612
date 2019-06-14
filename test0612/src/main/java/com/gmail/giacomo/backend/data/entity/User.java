package com.gmail.giacomo.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gmail.giacomo.app.security.SecurityConfiguration;

@Entity(name="UserInfo")
public class User extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Size(max = 60)
	@Column(unique = true)
	private String username;

	@NotNull
	@Size(min = 60, max = 60)
	private String passwordHash;

	@NotBlank
	@Size(max = 20)
	private String role = com.gmail.giacomo.backend.data.Role.USER;
	
	public User() {
		// An empty constructor is needed for all beans
	}

	/**
	 * Creates a user with the USER role
	 * @param username
	 * @param password
	 */
	public User(String username, String password, SecurityConfiguration securityConfiguration) {
		this.username = username;
		this.passwordHash = securityConfiguration.passwordEncoder().encode(password);
	}
	
	public void setPassword(String password, SecurityConfiguration securityConfiguration) {
		this.passwordHash = securityConfiguration.passwordEncoder().encode(password);
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return username;
	}



}
