package org.torraca.garage.web.components.forms;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.torraca.garage.model.DailyGaragePayment;

import com.vaadin.data.Item;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DateToLongConverter;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class EmployeeEditor extends Window implements
		Button.ClickListener, FormFieldFactory {

	private final Item personItem;
	private Form editorForm;
	private Button saveButton;
	private Button cancelButton;

	public EmployeeEditor(Item personItem) {
		this.personItem = personItem;
		editorForm = new Form();
		editorForm.setFormFieldFactory(this);
		editorForm.setBuffered(true);
		editorForm.setImmediate(true);
		// editorForm.setItemDataSource(personItem, Arrays.asList("firstName",
		// "lastName", "phoneNumber", "street", "city", "zipCode",
		// "department"));
		editorForm.setItemDataSource(personItem,
				Arrays.asList("date", "qtyByHour", "amountBy24Hours"));

		saveButton = new Button("Save", this);
		cancelButton = new Button("Cancel", this);

		editorForm.getFooter().addComponent(saveButton);
		editorForm.getFooter().addComponent(cancelButton);
		setSizeUndefined();
		setContent(editorForm);
		setCaption(buildCaption());
	}

	/**
	 * @return the caption of the editor window
	 */
	private String buildCaption() {
		return String.format("%s %s", personItem.getItemProperty("date")
				.getValue(), personItem.getItemProperty("amountBy24Hours")
				.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.
	 * ClickEvent)
	 */
	// @Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == saveButton) {
			editorForm.commit();
			fireEvent(new DailyGaragePaymentSavedEvent(this, personItem));
		} else if (event.getButton() == cancelButton) {
			editorForm.discard();
		}
		close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.FormFieldFactory#createField(com.vaadin.data.Item,
	 * java.lang.Object, com.vaadin.ui.Component)
	 */
	// @Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field field = DefaultFieldFactory.get().createField(item, propertyId,
				uiContext);

		if (field instanceof TextField) {
			((TextField) field).setNullRepresentation("");
		}

		if (String.valueOf(propertyId).equals("date")) {
			PopupDateField dateField = new PopupDateField();
			dateField.setResolution(PopupDateField.RESOLUTION_DAY);
			dateField.setConverter(new DateToCalendarConverter());
			field = dateField;
			field.setCaption("Fecha");
		}

		field.addValidator(new BeanValidator(DailyGaragePayment.class,
				propertyId.toString()));

		return field;
	}

	public void addListener(DailyGaragePaymentSavedListener listener) {
		try {
			Method method = DailyGaragePaymentSavedListener.class
					.getDeclaredMethod("dailyGaragePaymentSaved",
							new Class[] { DailyGaragePaymentSavedEvent.class });
			addListener(DailyGaragePaymentSavedEvent.class, listener, method);
		} catch (final java.lang.NoSuchMethodException e) {
			// This should never happen
			throw new java.lang.RuntimeException(
					"Internal error, editor saved method not found");
		}
	}

	public void removeListener(DailyGaragePaymentSavedListener listener) {
		removeListener(DailyGaragePaymentSavedEvent.class, listener);
	}

	public static class DailyGaragePaymentSavedEvent extends Component.Event {

		private Item savedItem;

		public DailyGaragePaymentSavedEvent(Component source, Item savedItem) {
			super(source);
			this.savedItem = savedItem;
		}

		public Item getSavedItem() {
			return savedItem;
		}
	}

	public interface DailyGaragePaymentSavedListener extends Serializable {
		public void dailyGaragePaymentSaved(DailyGaragePaymentSavedEvent event);
	}

	private static class DateToCalendarConverter implements
			Converter<Date, Calendar> {

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

}
