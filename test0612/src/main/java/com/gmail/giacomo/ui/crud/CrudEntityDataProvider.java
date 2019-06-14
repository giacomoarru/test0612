package com.gmail.giacomo.ui.crud;

import java.util.List;

import com.gmail.giacomo.app.security.CurrentUser;
import com.gmail.giacomo.backend.data.entity.AbstractEntity;
import com.gmail.giacomo.backend.service.FilterableCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.QuerySortOrderBuilder;

public class CrudEntityDataProvider<T extends AbstractEntity> extends FilterablePageableDataProvider<T, String> {

	private static final long serialVersionUID = 1L;
	
	private final FilterableCrudService<T> crudService;
	private List<QuerySortOrder> defaultSortOrders;
	private CurrentUser currentUser;
	
	public CrudEntityDataProvider(FilterableCrudService<T> crudService, CurrentUser currentUser) {
		this.crudService = crudService;
		this.currentUser = currentUser;
		setSortOrders();
	}

	private void setSortOrders() {
		QuerySortOrderBuilder builder = new QuerySortOrderBuilder();
		builder.thenAsc("id");
		defaultSortOrders = builder.build();
	}

	@Override
	protected Page<T> fetchFromBackEnd(Query<T, String> query, Pageable pageable) {
		return crudService.findAnyMatching(query.getFilter(), currentUser, pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		return defaultSortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<T, String> query) {
		return (int) crudService.countAnyMatching(query.getFilter(), currentUser);
	}

}

