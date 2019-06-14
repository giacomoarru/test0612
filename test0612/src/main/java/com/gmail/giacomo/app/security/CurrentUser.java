package com.gmail.giacomo.app.security;

import com.gmail.giacomo.backend.data.entity.User;

@FunctionalInterface
public interface CurrentUser {

	User getUser();
}
