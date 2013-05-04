package org.torraca.garage.web.components.converters;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class DateToCalendarConverter implements Converter<Date, Calendar> {

	public Calendar convertToModel(Date value, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value == null) {
			return null;
		} else {
			Calendar tCalendar = Calendar.getInstance();
			tCalendar.setTime(value);

			return tCalendar;
		}
	}

	public Date convertToPresentation(Calendar value, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		// TODO Auto-generated method stub
		return value == null ? null : value.getTime();
	}

	public Class<Calendar> getModelType() {
		// TODO Auto-generated method stub
		return Calendar.class;
	}

	public Class<Date> getPresentationType() {
		// TODO Auto-generated method stub
		return Date.class;
	}

}
