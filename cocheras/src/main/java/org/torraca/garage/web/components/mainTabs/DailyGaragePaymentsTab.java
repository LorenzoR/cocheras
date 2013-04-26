package org.torraca.garage.web.components.mainTabs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.torraca.garage.model.DailyGaragePayment;
import org.torraca.garage.model.Employee;
import org.torraca.garage.model.Expense;
import org.torraca.garage.model.Garage;
import org.torraca.garage.web.DailyGaragePaymentEditor;
import org.torraca.garage.web.MyVaadinUI;
import org.torraca.garage.web.DailyGaragePaymentEditor.DailyGaragePaymentSavedEvent;
import org.torraca.garage.web.DailyGaragePaymentEditor.DailyGaragePaymentSavedListener;
import org.torraca.garage.web.components.CustomTable;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
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

public class DailyGaragePaymentsTab extends CustomComponent {

	private JPAContainer<DailyGaragePayment> dailyGaragePayments;
	
	private Table dailyGaragePaymentsTable;
	
	public DailyGaragePaymentsTab() {
		VerticalLayout l = new VerticalLayout();
		l.setCaption("Ingresos Diarios");
		l.setMargin(true);
		l.setSpacing(true);

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setWidth("100%");
		horizontalLayout.setMargin(new MarginInfo(false, true, false, true));

		Button buttonWithBill = new Button("Con Factura");
		buttonWithBill.setStyleName(Reindeer.BUTTON_LINK);
		buttonWithBill.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				dailyGaragePayments = getPayments(true);
				dailyGaragePaymentsTable.setContainerDataSource(dailyGaragePayments);
				// garages = getGarages(floorParam);
				//
				// garagesTable.setContainerDataSource(garages);

			}
		});
		horizontalLayout.addComponent(buttonWithBill);

		Button buttonWithoutBill = new Button("Sin Factura");
		buttonWithoutBill.setStyleName(Reindeer.BUTTON_LINK);
		buttonWithoutBill.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				dailyGaragePayments = getPayments(false);
				dailyGaragePaymentsTable.setContainerDataSource(dailyGaragePayments);
				// garages = getGarages(floorParam);
				//
				// garagesTable.setContainerDataSource(garages);

			}
		});
		horizontalLayout.addComponent(buttonWithoutBill);

		Button buttonTotal = new Button("Total");
		buttonTotal.setStyleName(Reindeer.BUTTON_LINK);
		buttonTotal.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

				dailyGaragePayments = JPAContainerFactory
						.make(DailyGaragePayment.class, MyVaadinUI.PERSISTENCE_UNIT);
				dailyGaragePaymentsTable.setContainerDataSource(dailyGaragePayments);
				// garages = getGarages(floorParam);
				//
				// garagesTable.setContainerDataSource(garages);

			}
		});
		horizontalLayout.addComponent(buttonTotal);

		l.addComponent(horizontalLayout);

		dailyGaragePayments = JPAContainerFactory
				.make(DailyGaragePayment.class, MyVaadinUI.PERSISTENCE_UNIT);

		dailyGaragePaymentsTable = new CustomTable(dailyGaragePayments,
				null, new String[] { "24hs", "1 Hora", "24hs $", "Factura",
						"8hs", "8hs $", "1 Hora $", "Fecha" },
				new ValueColumnGenerator("$ %.2f"));

		l.addComponent(dailyGaragePaymentsTable);

		Button newDailyPaymentButton = new Button("Add");
		newDailyPaymentButton.addClickListener(new Button.ClickListener() {

			// @Override
			public void buttonClick(ClickEvent event) {
				final BeanItem<DailyGaragePayment> newDailyGaragePaymentItem = new BeanItem<DailyGaragePayment>(
						new DailyGaragePayment());
				DailyGaragePaymentEditor dailyGaragePaymentEditor = new DailyGaragePaymentEditor(
						newDailyGaragePaymentItem);
				dailyGaragePaymentEditor
						.addListener(new DailyGaragePaymentSavedListener() {
							// @Override
							public void dailyGaragePaymentSaved(
									DailyGaragePaymentSavedEvent event) {
								dailyGaragePayments
										.addEntity(newDailyGaragePaymentItem
												.getBean());
							}
						});
				UI.getCurrent().addWindow(dailyGaragePaymentEditor);
			}
		});

		l.addComponent(newDailyPaymentButton);

		setCompositionRoot(l);
	}

	/** Formats the value in a column containing Double objects. */
	private class ValueColumnGenerator implements Table.ColumnGenerator {
		String format; /* Format string for the Double values. */

		/**
		 * Creates double value column formatter with the given format string.
		 */
		public ValueColumnGenerator(String format) {
			this.format = format;
		}

		/**
		 * Generates the cell containing the Double value. The column is
		 * irrelevant in this use case.
		 */
		public Component generateCell(Table source, Object itemId,
				Object columnId) {
			// Get the object stored in the cell as a property
			Property prop = source.getItem(itemId).getItemProperty(columnId);

			System.out.println("ITEMID " + itemId);
			System.out.println("COLUMNID " + columnId);

			Long qtyByHour = (Long) source.getContainerProperty(itemId,
					"qtyByHour").getValue();
			Long qtyBy8Hours = (Long) source.getContainerProperty(itemId,
					"qtyBy8Hours").getValue();
			Long qtyBy24Hours = (Long) source.getContainerProperty(itemId,
					"qtyBy24Hours").getValue();

			Double amountByHour = (Double) source.getContainerProperty(itemId,
					"amountByHour").getValue();
			Double amountBy8Hours = (Double) source.getContainerProperty(
					itemId, "amountBy8Hours").getValue();
			Double amountBy24Hours = (Double) source.getContainerProperty(
					itemId, "amountBy24Hours").getValue();

			Double sum = qtyByHour + qtyBy8Hours + qtyBy24Hours + amountByHour
					+ amountBy8Hours + amountBy24Hours;

			// if (prop != null && prop.getType().equals(Double.class)) {
			Label label = new Label(String.format(format,
					new Object[] { (Double) sum }));

			// Set styles for the column: one indicating that it's
			// a value and a more specific one with the column
			// name in it. This assumes that the column name
			// is proper for CSS.

			label.addStyleName("column-type-value");
			label.addStyleName("column-" + (String) columnId);
			return label;
			// }
			// return null;
		}
	}
	
	private JPAContainer<DailyGaragePayment> getPayments(final boolean withBill) {
		dailyGaragePayments.getEntityProvider().setQueryModifierDelegate(
				new DefaultQueryModifierDelegate() {
					@Override
					public void filtersWillBeAdded(
							CriteriaBuilder criteriaBuilder,
							CriteriaQuery<?> query, List<Predicate> predicates) {
						Root<?> fromGarage = query.getRoots().iterator().next();

						// Add a "WHERE age > 116" expression
						Path<Boolean> floorPath = fromGarage
								.<Boolean> get("withBill");
						predicates.add(criteriaBuilder.equal(floorPath, withBill));
					}
				});

		return dailyGaragePayments;
	}

}
