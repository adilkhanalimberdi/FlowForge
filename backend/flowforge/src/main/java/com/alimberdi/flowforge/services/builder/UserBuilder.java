package com.alimberdi.flowforge.services.builder;

import com.alimberdi.flowforge.domain.entities.User;
import com.alimberdi.flowforge.domain.enums.UserRole;

public class UserBuilder implements Builder<User> {

	private User user;

	public UserBuilder() {
		this.user = new User();
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}

	public UserBuilder firstName(String firstName) {
		user.setFirstName(firstName);
		return this;
	}

	public UserBuilder lastName(String lastName) {
		user.setLastName(lastName);
		return this;
	}

	public UserBuilder email(String email) {
		user.setEmail(email);
		return this;
	}

	public UserBuilder password(String password) {
		user.setPassword(password);
		return this;
	}

	public UserBuilder profilePictureUrl(String profilePictureUrl) {
		user.setProfilePictureUrl(profilePictureUrl);
		return this;
	}

	public UserBuilder role(UserRole role) {
		user.setRole(role);
		return this;
	}

	@Override
	public User build() {
		User result = this.user;
		this.user = null;
		return result;
	}

}
