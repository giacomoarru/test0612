package com.gmail.giacomo.ui.views.admin;

import static com.gmail.giacomo.ui.utils.AppConst.PAGE_USERS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.gmail.giacomo.app.security.CurrentUser;
import com.gmail.giacomo.backend.data.Role;
import com.gmail.giacomo.backend.data.entity.User;
import com.gmail.giacomo.backend.service.UserService;
import com.gmail.giacomo.ui.MainView;
import com.gmail.giacomo.ui.crud.AbstractCrudView;
import com.gmail.giacomo.ui.utils.AppConst;

@Route(value = PAGE_USERS, layout = MainView.class)
@PageTitle(AppConst.TITLE_USERS)
@Secured(Role.ADMIN)
public class UsersView extends AbstractCrudView<User> {

	private static final long serialVersionUID = 1L;

	@Autowired
	public UsersView(UserService service, CurrentUser currentUser, PasswordEncoder passwordEncoder) {
		super(User.class, service, new Grid<>(), createForm(passwordEncoder), currentUser);
	}

	@Override
	public void setupGrid(Grid<User> grid) {
		grid.addColumn(User::getUsername).setWidth("270px").setHeader("Username").setFlexGrow(5);
		grid.addColumn(User::getRole).setHeader("Role").setWidth("150px");
	}

	@Override
	protected String getBasePage() {
		return PAGE_USERS;
	}

	private static BinderCrudEditor<User> createForm(PasswordEncoder passwordEncoder) {
		TextField username = new TextField("username");
		username.getElement().setAttribute("colspan", "2");
		PasswordField password = new PasswordField("Password");
		password.getElement().setAttribute("colspan", "2");
		ComboBox<String> role = new ComboBox<>();
		role.getElement().setAttribute("colspan", "2");
		role.setLabel("Role");

		FormLayout form = new FormLayout(username, password, role);

		BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);

		ListDataProvider<String> roleProvider = DataProvider.ofItems(Role.getAllRoles());
		role.setItemLabelGenerator(s -> s != null ? s : "");
		role.setDataProvider(roleProvider);

		binder.bind(username, "username");
		binder.bind(role, "role");

		binder.forField(password).bind(user -> password.getEmptyValue(), (user, pass) -> {
					if (!password.getEmptyValue().equals(pass)) {
						user.setPasswordHash(passwordEncoder.encode(pass));
					}
				});

		return new BinderCrudEditor<User>(binder, form) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isValid() {
				return binder.validate().isOk();
			}
		};
	}
}
