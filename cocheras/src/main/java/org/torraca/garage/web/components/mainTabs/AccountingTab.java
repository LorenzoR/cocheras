package org.torraca.garage.web.components.mainTabs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.torraca.garage.model.DailyGaragePayment;
import org.torraca.garage.model.Employee;
import org.torraca.garage.model.Expense;
import org.torraca.garage.model.Garage;
import org.torraca.garage.model.MonthlyGaragePayment;
import org.torraca.garage.model.MonthlyGaragePayment.PaymentType;
import org.torraca.garage.web.MyVaadinUI;
import org.torraca.garage.web.components.CustomTable;
import org.torraca.garage.web.components.H2;
import org.torraca.garage.web.components.Ruler;
import org.torraca.garage.web.components.forms.DailyGaragePaymentEditor;
import org.torraca.garage.web.components.forms.DailyGaragePaymentEditor.DailyGaragePaymentSavedEvent;
import org.torraca.garage.web.components.forms.DailyGaragePaymentEditor.DailyGaragePaymentSavedListener;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

public class AccountingTab extends CustomComponent {

	private Map<Date, Double> payments;

	public AccountingTab() {
		VerticalLayout l = new VerticalLayout();
		l.setMargin(true);
		l.setSpacing(true);

		H2 title = new H2("Contabilidad");

		l.addComponent(title);
		l.addComponent(new Ruler());

		payments = new HashMap<Date, Double>();

		JPAContainer<MonthlyGaragePayment> monthlyGaragePayments = JPAContainerFactory
				.make(MonthlyGaragePayment.class, MyVaadinUI.PERSISTENCE_UNIT);

		monthlyGaragePayments.sort(new String[] { "date" },
				new boolean[] { true });

		Collection<Object> items = monthlyGaragePayments.getItemIds();

		Iterator<Object> iterator = items.iterator();

		Calendar minDate = new GregorianCalendar(4000, 1, 1);

		while (iterator.hasNext()) {
			Object item = iterator.next();

			MonthlyGaragePayment payment = monthlyGaragePayments.getItem(item)
					.getEntity();

			if (payment.getPaymentType() == PaymentType.PRESENT) {

				Double newValue;
				if (payments.containsKey(payment.getDate().getTime())) {
					newValue = payments.get(payment.getDate().getTime())
							+ (payment.getMoney() / payment.getQtyMonths());
				} else {
					newValue = payment.getMoney() / payment.getQtyMonths();
				}

				payments.put(payment.getDate().getTime(), newValue);

				if (payment.getDate().getTimeInMillis() < minDate
						.getTimeInMillis()) {

					minDate = payment.getDate();
				}

			} else {
				System.out.println("DATE ES " + payment.getDate().getTime());
				// int sign = 0;
				// if (payment.getPaymentType() == PaymentType.BACKWARD) {
				// sign = -1;
				// } else if (payment.getPaymentType() == PaymentType.FORWARD) {
				// sign = 1;
				// }
				int i = payment.getQtyMonths();
				Calendar date = payment.getDate();
				Calendar auxDate = (Calendar) date.clone();

				System.out.println("BACKWARDS");
				System.out.println("RESTO " + i);

				// SimpleDateFormat fmt = new SimpleDateFormat("MM-yyyy");
				// fmt.setCalendar((GregorianCalendar) date);
				//
				// String dateFormatted = fmt.format(((GregorianCalendar)
				// date).getTime());

				while (i > 0) {
					System.out.println("*****AGREGO EN " + auxDate.getTime());

					if (auxDate.getTimeInMillis() < minDate.getTimeInMillis()) {
						minDate = auxDate;
						System.out.println("MINDATE AHORA ES "
								+ minDate.getTime());
					}

					Double newValue;
					if (payments.containsKey(auxDate.getTime())) {
						newValue = payments.get(auxDate.getTime())
								+ (payment.getMoney() / payment.getQtyMonths());
					} else {
						newValue = payment.getMoney() / payment.getQtyMonths();
					}

					payments.put(auxDate.getTime(), newValue);
					auxDate.add(
							Calendar.MONTH,
							payment.getPaymentType() == PaymentType.BACKWARD ? -1
									: 1);
					i--;
				}

				// int i = payment.getQtyMonths();
				// Calendar date = payment.getDate();
				// Calendar auxDate = (Calendar) date.clone();
				//
				// System.out.println("FORWARD");
				// System.out.println("SUMO " + i);
				//
				// while (i > 0) {
				// System.out.println("*****AGREGO EN "
				// + auxDate.getTime());
				//
				// if (auxDate.getTimeInMillis() < minDate
				// .getTimeInMillis()) {
				// minDate = auxDate;
				// }
				//
				// Double newValue;
				// if (payments.containsKey(auxDate.getTime())) {
				// newValue = payments.get(auxDate.getTime())
				// + (payment.getMoney() / payment
				// .getQtyMonths());
				// } else {
				// newValue = payment.getMoney()
				// / payment.getQtyMonths();
				// }
				//
				// payments.put(auxDate.getTime(), newValue);
				// auxDate.add(Calendar.MONTH, 1);
				// i--;
				// }

			}

		}

		Container container = new IndexedContainer();

		// Define the properties (columns) if required by container
		container.addContainerProperty("date", Date.class, "none");
		container.addContainerProperty("money", Double.class, 0.0);

		Set<Entry<Date, Double>> paymentsSet = payments.entrySet();

		Iterator<Entry<Date, Double>> paymentsIterator = paymentsSet.iterator();

		System.out.println("***** HASH MAP!!");
		System.out.println("MINDATE ES " + minDate.getTime());

		int i = 0;

		while (paymentsIterator.hasNext()) {
			Entry<Date, Double> entry = paymentsIterator.next();

			SimpleDateFormat fmt = new SimpleDateFormat("MM-yyyy");

			// fmt.setCalendar((GregorianCalendar) entry.getKey());
			//
			// String dateFormatted = fmt.format(((GregorianCalendar) entry
			// .getKey()).getTime());

			Object id = container.addItem();

			container.getContainerProperty(id, "date").setValue(entry.getKey());
			container.getContainerProperty(id, "money").setValue(
					entry.getValue());

			// String rowId = "row" + i;
			//
			// container.addItem(rowId);
			//
			// container.getItem(rowId).addItemProperty("date",
			// new ObjectProperty(entry.getKey()));
			// container.getItem(rowId).addItemProperty("money",
			// new ObjectProperty(entry.getValue()));
		}

		Table accountingTable = new CustomTable(container);

		accountingTable.sort(new String[] { "date" }, new boolean[] { false });

		l.addComponent(accountingTable);

		setCompositionRoot(l);

	}
}
