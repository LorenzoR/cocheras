package org.torraca.garage.web.components;

import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

public class H1 extends Label {
	public H1(String caption) {
		super(caption);
		setSizeUndefined();
		setStyleName(Reindeer.LABEL_H1);
	}
}
