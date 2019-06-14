package com.gmail.giacomo.ui.views.user;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.gmail.giacomo.app.security.CurrentUser;
import com.gmail.giacomo.app.utils.CreditCardValidation;
import com.gmail.giacomo.backend.data.Role;
import com.gmail.giacomo.backend.data.entity.CreditCard;
import com.gmail.giacomo.backend.data.entity.User;
import com.gmail.giacomo.backend.service.CreditCardService;
import com.gmail.giacomo.ui.MainView;
import com.gmail.giacomo.ui.crud.AbstractCrudView;
import com.gmail.giacomo.ui.utils.AppConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;


import java.util.Collections;

@Route(value = AppConst.PAGE_CREDIT_CARDS, layout = MainView.class)
@PageTitle(AppConst.TITLE_CREDIT_CARDS)
@Secured({Role.USER, Role.ADMIN})
public class CreditCardView extends AbstractCrudView<CreditCard> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	public CreditCardView(CreditCardService service, CurrentUser currentUser) {
		super(CreditCard.class, service, new Grid<>(), createForm(currentUser.getUser()), currentUser);
	}

	@Override
	protected void setupGrid(Grid<CreditCard> grid) {
		if (currentUser.getUser().getRole().equals(Role.ADMIN)) {
			grid.addColumn(CreditCard::getUser).setHeader("User").setFlexGrow(10);
		}
		grid.addColumn(CreditCard::getName).setHeader("Credit Card").setFlexGrow(10);
		grid.addColumn(CreditCard::getNumber).setHeader("Number");
		grid.addColumn(CreditCard::getExpiry).setHeader("Expiry Date");
	}

	@Override
	protected String getBasePage() {
		return AppConst.PAGE_CREDIT_CARDS;
	}

	private static BinderCrudEditor<CreditCard> createForm(User user) {
		ComboBox<User> cboUser = new ComboBox<>("User");
		cboUser.setItems(Collections.singletonList(user));
		cboUser.setValue(user);
		cboUser.setItemLabelGenerator(User::getUsername);
		
		TextField txtName = new TextField("Credit Card Holder Name");
		txtName.getElement().setAttribute("colspan", "2");
		txtName.setMaxLength(100);
		
		TextField txtNumber = new TextField("Credit Card Number");
		txtNumber.getElement().setAttribute("colspan", "1");
		txtNumber.setMaxLength(16);
		
		TextField txtExpiry = new TextField("Credit Card Expiry Date (YY/MM)");
		txtExpiry.getElement().setAttribute("colspan", "1");
		txtExpiry.setMaxLength(5);

		FormLayout form;
		
		if (user.getRole().equals(Role.ADMIN))
			form = new FormLayout(cboUser, txtName, txtNumber, txtExpiry);
		else 
			form = new FormLayout(txtName, txtNumber, txtExpiry);

		BeanValidationBinder<CreditCard> binder = new BeanValidationBinder<>(CreditCard.class);
		
		binder.forField(cboUser).bind("user");
		binder.forField(txtName).withValidator(item ->
		item.length() >= 3,
				"Invalid name.").bind("name");
		binder.forField(txtNumber).withValidator(item ->
		item.length() >= 13,
				"Invalid credit card number.").bind("number");
		binder.forField(txtExpiry).withValidator(item -> 
		CreditCardValidation.validateCardExpiryDate(item),
				"Invalid credit card expiry date.")
		.bind("expiry");
		
		return new BinderCrudEditor<CreditCard>(binder, form) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isValid() {
				return binder.validate().isOk();
			}
		};
	}

}
