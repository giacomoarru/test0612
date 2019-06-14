package com.gmail.giacomo.app.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.gmail.giacomo.ui.components.OfflineBanner;
import com.gmail.giacomo.ui.views.unauth.LoginView;
import com.gmail.giacomo.ui.views.user.HomeView;

/**
 * Adds before enter listener to check access to views.
 * Adds the Offline banner.
 * 
 */
@SpringComponent
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			ui.add(new OfflineBanner());
			ui.addBeforeEnterListener(this::beforeEnter);
		});
	}

	/**
	 * Reroutes the user if she is not authorized to access the view. 
	 *
	 * @param event
	 *            before navigation event with event details
	 */
	private void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted = SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if (!accessGranted) {
			UI.getCurrent().getPage().getHistory().replaceState(null, "");
			if (SecurityUtils.isUserLoggedIn()) {
				event.rerouteTo(HomeView.class);
			} else {
				event.rerouteTo(LoginView.class);
			}
		}
	}
}
