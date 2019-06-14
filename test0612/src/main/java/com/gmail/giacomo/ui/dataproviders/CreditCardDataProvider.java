package com.gmail.giacomo.ui.dataproviders;

import java.util.stream.Stream;

import org.springframework.data.domain.PageRequest;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.gmail.giacomo.app.security.CurrentUser;
import com.gmail.giacomo.backend.data.entity.CreditCard;
import com.gmail.giacomo.backend.service.CreditCardService;

@SpringComponent
@UIScope
public class CreditCardDataProvider extends AbstractBackEndDataProvider<CreditCard, String> {

	private static final long serialVersionUID = 1L;
	
	private final CreditCardService creditCardService;
	private CurrentUser currentUser;

	public CreditCardDataProvider(CreditCardService creditCardService, CurrentUser currentUser) {
		this.creditCardService = creditCardService;
		this.currentUser = currentUser;
	}

	@Override
	protected int sizeInBackEnd(Query<CreditCard, String> query) {
		return (int) creditCardService.countAnyMatching(query.getFilter(), currentUser);
	}

	@Override
	public Stream<CreditCard> fetchFromBackEnd(Query<CreditCard, String> query) {
		return creditCardService.findAnyMatching(query.getFilter(), currentUser, PageRequest.of(query.getOffset(), query.getLimit()))
				.stream();
	}

}
