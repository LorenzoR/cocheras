package org.torraca.garage.web.components;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public class CustomTable extends Table {

	public CustomTable(Container dataSource) {

		setContainerDataSource(dataSource);

		// Remove id column
		ArrayList<Object> columns = new ArrayList<Object>(Arrays.asList(this
				.getVisibleColumns()));
		columns.remove(0);

		setVisibleColumns(columns.toArray());

		setImmediate(true);

	}

	public CustomTable(Container dataSource, String[] fields,
			String[] columnHeaders, ColumnGenerator columnGenerator) {
		this(dataSource);
		
		if ( fields != null ) {
			setVisibleColumns((Object[]) fields);
		}

		if (columnHeaders != null) {
			setColumnHeaders(columnHeaders);
		}

		if (columnGenerator != null) {
			addGeneratedColumn("total", columnGenerator);
		}

		setImmediate(true);

	}

	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property) {

		if (((String) colId).contains("price")) {
			return "$ " + String.valueOf(property.getValue());
		}

		if (property.getType() == Calendar.class) {

			SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
			fmt.setCalendar((GregorianCalendar) property.getValue());
			String dateFormatted = fmt.format(((GregorianCalendar) property
					.getValue()).getTime());

			return dateFormatted;
		}

		return super.formatPropertyValue(rowId, colId, property);
	}
}
