package com.gmail.giacomo.ui.views.unauth;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import com.gmail.giacomo.app.security.SecurityConfiguration;
import com.gmail.giacomo.app.security.SecurityUtils;
import com.gmail.giacomo.app.security.StrongPasswordGenerator;
import com.gmail.giacomo.app.security.StrongPasswordValidator;
import com.gmail.giacomo.backend.data.entity.User;
import com.gmail.giacomo.backend.repositories.UserRepository;
import com.gmail.giacomo.ui.utils.AppConst;
import com.gmail.giacomo.ui.views.user.HomeView;


@Route
@PageTitle(AppConst.TITLE_REGISTER)
@HtmlImport("styles/shared-styles.html")
@Viewport(AppConst.VIEWPORT)
public class RegisterView extends VerticalLayout
	implements BeforeEnterObserver {

	private static final long serialVersionUID = 1L;
	
	private TextField txtUsername = new TextField("Username:");
	private PasswordField txtPassword = new PasswordField("Password:");
	private PasswordField txtPasswordConfirm = new PasswordField("Confirm password:");
	private Button btnGeneratePassword = new Button("Generate password");
	private Button btnRegister = new Button(AppConst.TITLE_REGISTER);
	private Button btnLogin = new Button("Back to login");
	private TextField txtGeneratedPassword = new TextField("Your generated password");
	
	@Autowired
	SecurityConfiguration securityConfiguration;
	
	@Autowired
	public UserRepository userRepository;
	
	public RegisterView() {
	
		setupLayout();
		setupListeners();
		
	}

	private void setupListeners() {
		btnRegister.addClickListener(click -> {
			if (txtUsername.getValue().isBlank()) {
				Notification.show("Please enter username.");
				txtUsername.focus();
				return;
			}
			
			if (txtPassword.getValue().isBlank()) {
				Notification.show("Please enter password.");
				txtPassword.focus();
				return;
			}
			
			if (txtPasswordConfirm.getValue().isBlank()) {
				Notification.show("Please enter password confirmation.");
				txtPasswordConfirm.focus();
				return;
			}
			
			if (!txtPassword.getValue().equals(txtPasswordConfirm.getValue())) {
				Notification.show("Passwords do not match.");
				txtPassword.clear();
				txtPasswordConfirm.clear();
				txtPassword.focus();
				return;
			}
		
			if (!StrongPasswordValidator.validate(txtPassword.getValue()).isValid()) {
				Notification.show("Password does not meet security requirements.");
				txtPassword.clear();
				txtPasswordConfirm.clear();
				txtPassword.focus();
				return;
			}
			
			User user = new User(txtUsername.getValue(), txtPassword.getValue(), securityConfiguration);
			try {
				userRepository.save(user);
				getUI().ifPresent(ui -> ui.navigate(AppConst.PAGE_LOGIN));
				Notification.show("User " + user.getUsername() + " created.");
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				Notification.show("User " + user.getUsername() + " already exists.");
			}
			
		});
		
		btnLogin.addClickListener(click -> {
			btnLogin.getUI().ifPresent(ui -> ui.navigate(AppConst.PAGE_LOGIN));
		});
		
		btnGeneratePassword.addClickListener(click -> {
			String password = StrongPasswordGenerator.generatePassword(AppConst.DEFAULT_PASSWORD_LENGTH);
			
			txtGeneratedPassword.setValue(password);
			txtGeneratedPassword.setVisible(true);
			txtPassword.setReadOnly(true);
			txtPasswordConfirm.setReadOnly(true);
			
			txtPassword.setValue(password);
			txtPasswordConfirm.setValue(password);
		});
	}

	private void setupLayout() {
		this.setWidth("100%");
		this.setMargin(false);
		this.setSpacing(false);
		
		txtGeneratedPassword.setVisible(false);
		
		add(getFormContainer());
		
		txtUsername.setWidth("90%");
		txtPassword.setWidth("90%");
		txtPasswordConfirm.setWidth("90%");
		btnRegister.setWidth("90%");
		btnLogin.setWidth("90%");
		btnGeneratePassword.setWidth("90%");
		
		btnRegister.setThemeName("primary");
		btnLogin.setThemeName("seconday");
		
	}

	private VerticalLayout getFormContainer() {
		VerticalLayout formContainer = new VerticalLayout();
		formContainer.setMargin(false);
		formContainer.setWidth("100%");
		formContainer.setSpacing(false);
		
		Html title = new Html("<h1>Register</h1>");
		Html instructions = new Html("<h3>Insert your new user details below</h3>");
		Html passwordRules = new Html("<h4>Password rules: length between 8 and 128,<br>1 cap, 1low, 1symbol, 1number, no spaces</h4>");
		
		VerticalLayout formLayout = new VerticalLayout();
		formLayout.add(title, instructions, passwordRules, txtUsername,
				txtPassword, txtPasswordConfirm, txtGeneratedPassword, btnGeneratePassword,
				btnRegister, btnLogin);
		formLayout.setWidth("-1");
		formLayout.setMargin(false);
		formLayout.setHorizontalComponentAlignment(Alignment.CENTER, txtUsername);
		formLayout.setHorizontalComponentAlignment(Alignment.CENTER, txtPassword);
		formLayout.setHorizontalComponentAlignment(Alignment.CENTER, txtPasswordConfirm);
		formLayout.setHorizontalComponentAlignment(Alignment.CENTER, btnRegister);
		formLayout.setHorizontalComponentAlignment(Alignment.CENTER, btnLogin);
		formLayout.setHorizontalComponentAlignment(Alignment.CENTER, btnGeneratePassword);
		
		formContainer.add(formLayout);
		formContainer.setHorizontalComponentAlignment(Alignment.CENTER, formLayout);
		
		return formContainer;
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (SecurityUtils.isUserLoggedIn()) {
			// Needed manually to change the URL because of https://github.com/vaadin/flow/issues/4189
			UI.getCurrent().getPage().getHistory().replaceState(null, "");
			event.rerouteTo(HomeView.class);
		}
	}



}
