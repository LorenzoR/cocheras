package org.torraca.garage.web.components.mainTabs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.torraca.garage.model.Employee;
import org.torraca.garage.model.Expense;
import org.torraca.garage.model.SalaryAdvance;
import org.torraca.garage.web.MyVaadinUI;
import org.torraca.garage.web.components.CustomTable;
import org.torraca.garage.web.components.H2;
import org.torraca.garage.web.components.Ruler;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Table.ColumnGenerator;

public class SalaryAdvancesTab extends CustomComponent {

	public SalaryAdvancesTab() {
		VerticalLayout l = new VerticalLayout();
		l.setMargin(true);
		l.setSpacing(true);
		l.setImmediate(true);
		
		H2 title = new H2("Adelantos de Sueldo");
		
		System.out.println("SAAAALAAAAARIOSSSS");
		
		l.addComponent(title);
		l.addComponent(new Ruler());

		JPAContainer<SalaryAdvance> salaryAdvances = JPAContainerFactory.make(
				SalaryAdvance.class, MyVaadinUI.PERSISTENCE_UNIT);

		Table salaryAdvancesTable = new CustomTable(salaryAdvances);

		l.addComponent(salaryAdvancesTable);
		
		setCompositionRoot(l);

	}

}
