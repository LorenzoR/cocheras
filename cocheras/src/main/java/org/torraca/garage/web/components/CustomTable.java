package org.torraca.garage.web.components;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.text.WordUtils;

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

		int i = 0;
		boolean hasIdColumn = false;

		for (Object aColumn : columns) {
			if (String.valueOf(aColumn).equals("id")) {
				hasIdColumn = true;
				break;
			}
			i++;
		}

		if (hasIdColumn) {
			columns.remove(i);
		}

		setVisibleColumns(columns.toArray());

		setImmediate(true);

	}

	public CustomTable(Container dataSource, String[] fields,
			String[] columnHeaders, ColumnGenerator columnGenerator) {
		this(dataSource);

		if (fields != null) {
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

		if (((String) colId).contains("month")) {
			String monthName = new DateFormatSymbols().getMonths()[(Integer) property
					.getValue()];
			return WordUtils.capitalize(monthName);
		}

		if (property.getType() == Calendar.class) {

			SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
			//fmt.setCalendar((GregorianCalendar) property.getValue());
			String dateFormatted = fmt.format(((GregorianCalendar) property
					.getValue()).getTime());
			
//			Calendar calendar = (GregorianCalendar) property.getValue();
//			
//			return calendar.getTime().toString();

			return dateFormatted;
		}

		if (property.getType() == Date.class) {

			SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");

			return fmt.format(property.getValue());
		}

		return super.formatPropertyValue(rowId, colId, property);
	}
}
