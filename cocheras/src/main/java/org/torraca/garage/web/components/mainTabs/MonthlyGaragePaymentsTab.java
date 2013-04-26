package org.torraca.garage.web.components.mainTabs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.torraca.garage.model.DailyGaragePayment;
import org.torraca.garage.model.Employee;
import org.torraca.garage.model.Expense;
import org.torraca.garage.model.Garage;
import org.torraca.garage.model.MonthlyGaragePayment;
import org.torraca.garage.web.DailyGaragePaymentEditor;
import org.torraca.garage.web.MyVaadinUI;
import org.torraca.garage.web.DailyGaragePaymentEditor.DailyGaragePaymentSavedEvent;
import org.torraca.garage.web.DailyGaragePaymentEditor.DailyGaragePaymentSavedListener;
import org.torraca.garage.web.components.CustomTable;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

public class MonthlyGaragePaymentsTab extends CustomComponent {

	public MonthlyGaragePaymentsTab() {
		VerticalLayout l = new VerticalLayout();
		l.setCaption("Ingresos Mensuales");
		l.setMargin(true);
		l.setSpacing(true);

		JPAContainer<MonthlyGaragePayment> monthlyGaragePayments = JPAContainerFactory.make(
				MonthlyGaragePayment.class, MyVaadinUI.PERSISTENCE_UNIT);

		Table monthlyGaragePaymentsTable = new CustomTable(monthlyGaragePayments);

		l.addComponent(monthlyGaragePaymentsTable);
		
		setCompositionRoot(l);

	}
}
