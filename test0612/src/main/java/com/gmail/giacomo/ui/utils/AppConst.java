package com.gmail.giacomo.ui.utils;

import java.util.Locale;

import org.springframework.data.domain.Sort;

public class AppConst {

	public static final Locale APP_LOCALE = Locale.US;

	public static final String PAGE_USERS = "users";
	public static final String PAGE_CREDIT_CARDS = "creditcards";
	public static final String PAGE_REGISTER = "register";
	public static final String PAGE_HOME = "";
	public static final String PAGE_LOGIN = "login";

	public static final String TITLE_STOREFRONT = "Storefront";
	public static final String TITLE_DASHBOARD = "Dashboard";
	public static final String TITLE_USERS = "Users";
	public static final String TITLE_CREDIT_CARDS = "Credit Cards";
	public static final String TITLE_HOME = "Home";
	public static final String TITLE_LOGOUT = "Logout";
	public static final String TITLE_NOT_FOUND = "Page was not found";
	public static final String TITLE_ACCESS_DENIED = "Access denied";
	public static final String TITLE_REGISTER = "Register";

	public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

	public static final String VIEWPORT = "width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes";

	public static final int NOTIFICATION_DURATION = 4000;

	public static final int DEFAULT_PASSWORD_LENGTH = 8;
	
	private AppConst() {
		// util class
	}

}
