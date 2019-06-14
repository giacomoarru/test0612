package com.gmail.giacomo.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Objects;

@Entity
public class CreditCard extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "{test0612.name.required}")
	@Size(max = 100)
	private String name;

	@NotBlank(message = "{test0612.number.required}")
	@Size(max = 16)
	@Column(unique = true)
	private String number;

	@JoinColumn(name = "user", referencedColumnName = "username", nullable=false) 
	@ManyToOne(fetch = FetchType.LAZY) 
	private User user;
	
	@NotBlank(message = "{test0612.expiry.required}")
	private String expiry;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	@Override
	public String toString() {
		return number;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		CreditCard that = (CreditCard) o;
		return Objects.equals(name, that.name) &&
				Objects.equals(number, that.number);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), name, number);
	}
	
	
}
