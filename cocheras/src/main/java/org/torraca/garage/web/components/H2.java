package org.torraca.garage.web.components;

import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

public class H2 extends Label {
	public H2(String caption) {
		super(caption);
		setSizeUndefined();
		setStyleName(Reindeer.LABEL_H2);
	}
}
