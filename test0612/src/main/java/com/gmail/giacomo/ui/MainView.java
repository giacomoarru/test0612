package com.gmail.giacomo.ui;

import com.gmail.giacomo.ui.utils.AppConst;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.PWA;
import com.gmail.giacomo.app.security.SecurityUtils;
import com.gmail.giacomo.ui.views.HasConfirmation;
import com.gmail.giacomo.ui.views.admin.UsersView;
import com.gmail.giacomo.ui.views.user.CreditCardView;
import com.gmail.giacomo.ui.views.user.HomeView;


@Viewport(AppConst.VIEWPORT)
@PWA(name = "test0612Application", shortName = "test0612",
		startPath = "login",
		backgroundColor = "#227aef", themeColor = "#227aef",
		offlinePath = "offline-page.html",
		offlineResources = {"images/offline-login-banner.jpg"})
public class MainView extends AbstractAppRouterLayout {

	private static final long serialVersionUID = 1L;
	
	private final ConfirmDialog confirmDialog;

	public MainView() {
		this.confirmDialog = new ConfirmDialog();
		confirmDialog.setCancelable(true);
		confirmDialog.setConfirmButtonTheme("raised tertiary error");
		confirmDialog.setCancelButtonTheme("raised tertiary");

		getElement().appendChild(confirmDialog.getElement());
	}

	@Override
	protected void configure(AppLayout appLayout, AppLayoutMenu menu) {
		appLayout.setBranding(new Span("test0612"));

		if (SecurityUtils.isUserLoggedIn()) {
			if (SecurityUtils.isAccessGranted(HomeView.class)) {
				setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.HOME.create(), AppConst.TITLE_HOME, AppConst.PAGE_HOME));
			}
			if (SecurityUtils.isAccessGranted(UsersView.class)) {
				setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.USER.create(), AppConst.TITLE_USERS, AppConst.PAGE_USERS));
			}
			if (SecurityUtils.isAccessGranted(CreditCardView.class)) {
				setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.CREDIT_CARD.create(), AppConst.TITLE_CREDIT_CARDS, AppConst.PAGE_CREDIT_CARDS));
			}
			setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.ARROW_RIGHT.create(), AppConst.TITLE_LOGOUT, e ->
					UI.getCurrent().getPage().executeJavaScript("location.assign('logout')")));
		}
		getElement().addEventListener("search-focus", e -> {
			appLayout.getElement().getClassList().add("hide-navbar");
		});

		getElement().addEventListener("search-blur", e -> {
			appLayout.getElement().getClassList().remove("hide-navbar");
		});
	}

	private void setMenuItem(AppLayoutMenu menu, AppLayoutMenuItem menuItem) {
		menuItem.getElement().setAttribute("theme", "icon-on-top");
		menu.addMenuItem(menuItem);
	}

	@Override
	public void showRouterLayoutContent(HasElement content) {
		super.showRouterLayoutContent(content);

		this.confirmDialog.setOpened(false);
		if (content instanceof HasConfirmation) {
			((HasConfirmation) content).setConfirmDialog(this.confirmDialog);
		}
	}
}
