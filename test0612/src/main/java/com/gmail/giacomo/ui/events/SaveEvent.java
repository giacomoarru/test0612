package com.gmail.giacomo.ui.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class SaveEvent extends ComponentEvent<Component> {

	private static final long serialVersionUID = 1L;

	public SaveEvent(Component source, boolean fromClient) {
		super(source, fromClient);
	}

}