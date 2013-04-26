package org.torraca.garage.web.components.mainTabs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.torraca.garage.model.Expense;
import org.torraca.garage.web.MyVaadinUI;
import org.torraca.garage.web.components.CustomTable;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Table.ColumnGenerator;

public class ExpensesTab extends CustomComponent {

	public ExpensesTab() {
		VerticalLayout l = new VerticalLayout();
		l.setMargin(true);
		l.setSpacing(true);

		JPAContainer<Expense> expenses;

		expenses = JPAContainerFactory.make(Expense.class,
				MyVaadinUI.PERSISTENCE_UNIT);

		// Table expensesTable = createTable(expenses, new String[] { "Precio",
		// "Número Cochera" }, null);
		//Table expensesTable = createTable(expenses, null, null);
		//CustomTable expensesTable = new CustomTable(expenses);

		CustomTable expensesTable = new CustomTable(expenses);
		
		l.addComponent(expensesTable);

		setCompositionRoot(l);
	}

	private static Table createTable(Container dataSource,
			String[] columnHeaders, ColumnGenerator columnGenerator) {

		Table table = new Table(null, dataSource) {
			@Override
			protected String formatPropertyValue(Object rowId, Object colId,
					Property property) {

				if (((String) colId).contains("price")) {
					return "$ " + String.valueOf(property.getValue());
				}

				if (property.getType() == Calendar.class) {

					SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
					fmt.setCalendar((GregorianCalendar) property.getValue());
					String dateFormatted = fmt
							.format(((GregorianCalendar) property.getValue())
									.getTime());

					return dateFormatted;
				}

				return super.formatPropertyValue(rowId, colId, property);
			}
		};

		// table.setColumnHeader("date", "Fecha");
		ArrayList<Object> columns = new ArrayList<Object>(Arrays.asList(table
				.getVisibleColumns()));
		columns.remove(0);

		table.setVisibleColumns(columns.toArray());

		if (columnHeaders != null) {
			table.setColumnHeaders(columnHeaders);
		}

		if (columnGenerator != null) {
			table.addGeneratedColumn("total", columnGenerator);
		}

		table.setImmediate(true);

		return table;
	}
}
