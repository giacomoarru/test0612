package com.gmail.giacomo.ui.views.unauth;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.gmail.giacomo.app.security.SecurityUtils;
import com.gmail.giacomo.ui.utils.AppConst;
import com.gmail.giacomo.ui.views.user.HomeView;

@Route
@PageTitle("test0612")
@HtmlImport("styles/shared-styles.html")
@Viewport(AppConst.VIEWPORT)
public class LoginView extends LoginOverlay implements AfterNavigationObserver, BeforeEnterObserver {

	private static final long serialVersionUID = 1L;
	
	public LoginView() {
		LoginI18n i18n = LoginI18n.createDefault();
		i18n.setHeader(new LoginI18n.Header());
		i18n.getHeader().setTitle("test0612");
		i18n.getHeader().setDescription("Admin: admin/admin");
		i18n.setAdditionalInformation(null);
		i18n.setForm(new LoginI18n.Form());
		i18n.getForm().setSubmit("Sign in");
		i18n.getForm().setTitle("Sign in");
		i18n.getForm().setUsername("Username");
		i18n.getForm().setPassword("Password");
		i18n.getForm().setForgotPassword("Register");
		setI18n(i18n);
		
		setForgotPasswordButtonVisible(true);
		
		addForgotPasswordListener(click -> click.getSource().getUI().ifPresent(ui -> ui.navigate(AppConst.PAGE_REGISTER)));
		setAction(AppConst.PAGE_LOGIN);
		setOpened(true);
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		setError(
				event.getLocation().getQueryParameters().getParameters().containsKey(
					"error"));
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event)  {
		if (SecurityUtils.isUserLoggedIn()) {
			// Needed manually to change the URL because of https://github.com/vaadin/flow/issues/4189
			UI.getCurrent().getPage().getHistory().replaceState(null, "");
			
			event.rerouteTo(HomeView.class);
		}
	}
	
}
