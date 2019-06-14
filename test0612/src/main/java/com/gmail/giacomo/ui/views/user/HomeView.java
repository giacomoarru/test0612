package com.gmail.giacomo.ui.views.user;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.gmail.giacomo.app.security.CurrentUser;
import com.gmail.giacomo.backend.data.Role;
import com.gmail.giacomo.ui.MainView;
import com.gmail.giacomo.ui.utils.AppConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;


@Route(value = AppConst.PAGE_HOME, layout = MainView.class)
@PageTitle(AppConst.TITLE_HOME)
@Secured({Role.USER, Role.ADMIN})
public class HomeView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Autowired
	public HomeView(CurrentUser currentUser) {
		Html title = new Html("<h1>Welcome " + currentUser.getUser().getUsername() + "</h1>");
		Html helptext = new Html("<p>Use the menu to navigate through the app sections.</p>");
		
		
		add(title, helptext);
	}


}
