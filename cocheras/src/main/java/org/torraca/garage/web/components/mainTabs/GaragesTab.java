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

import org.torraca.garage.model.Garage;
import org.torraca.garage.web.MyVaadinUI;
import org.torraca.garage.web.components.CustomTable;
import org.torraca.garage.web.components.H2;
import org.torraca.garage.web.components.Ruler;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.themes.Reindeer;

public class GaragesTab extends CustomComponent {

	private JPAContainer<Garage> garages;

	private Table garagesTable;

	public GaragesTab() {
		final VerticalLayout l = new VerticalLayout();
		l.setMargin(true);
		l.setSpacing(true);
		l.setImmediate(true);

		H2 title = new H2("Cocheras");

		l.addComponent(title);
		l.addComponent(new Ruler());

		AbstractSelect floorSelect = new ComboBox("Seleccione el piso");
		floorSelect.addItem(0);
		floorSelect.setItemCaption(0, "Todos");
		floorSelect.setValue(0);

		for (int i = 1; i < 11; i++) {
			floorSelect.addItem(i);
			floorSelect.setItemCaption(i, "Piso  " + i);
		}

		floorSelect.setNullSelectionAllowed(false);
		floorSelect.setImmediate(true);

		floorSelect.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(final ValueChangeEvent event) {
				final String valueString = String.valueOf(event.getProperty()
						.getValue());
				valueString.replace("Piso ", "");

				int newFloor;

				if (valueString.equals("Todos")) {
					newFloor = 0;
				} else {
					newFloor = Integer.valueOf(valueString);
				}

				garages = getGarages(newFloor);
				Object[] columns = garagesTable.getVisibleColumns();
				garagesTable.setContainerDataSource(garages);
				garagesTable.setVisibleColumns(columns);

				Notification.show("Value changed:", valueString,
						Type.TRAY_NOTIFICATION);
			}
		});

		l.addComponent(floorSelect);

		garages = JPAContainerFactory.make(Garage.class,
				MyVaadinUI.PERSISTENCE_UNIT);
		
//		HorizontalLayout floorsLayout = new HorizontalLayout();
//		floorsLayout.setWidth("100%");
//		floorsLayout.setMargin(new MarginInfo(false, true, false, true));
//
//		for (int i = 1; i < 11; i++) {
//			final int floorParam = i;
//			Button button = new Button(" [ Piso " + i + " ] ");
//			button.setStyleName(Reindeer.BUTTON_LINK);
//			button.addClickListener(new Button.ClickListener() {
//				// @Override
//				public void buttonClick(ClickEvent event) {
//
//					garages = getGarages(floorParam);
//					Object[] columns = garagesTable.getVisibleColumns();
//					garagesTable.setContainerDataSource(garages);
//					garagesTable.setVisibleColumns(columns);
//
//				}
//			});
//
//			floorsLayout.addComponent(button);
//		}
//
//		l.addComponent(floorsLayout);

		garagesTable = new CustomTable(garages, new String[] { "floor",
				"number", "price" }, new String[] { "Piso", "Número Cochera",
				"Precio" }, null);

		l.addComponent(garagesTable);

		setCompositionRoot(l);
	}

	private JPAContainer<Garage> getGarages(final int floor) {

		if (floor != 0) {
			System.out.println("FLoor es " + floor);
			garages.getEntityProvider().setQueryModifierDelegate(
					new DefaultQueryModifierDelegate() {
						@Override
						public void filtersWillBeAdded(
								CriteriaBuilder criteriaBuilder,
								CriteriaQuery<?> query,
								List<Predicate> predicates) {
							Root<?> fromGarage = query.getRoots().iterator()
									.next();

							// Add a "WHERE age > 116" expression
							Path<Integer> floorPath = fromGarage
									.<Integer> get("floor");
							predicates.add(criteriaBuilder.equal(floorPath,
									floor));
						}
					});
		} else {
			System.out.println("MUESTRO TODOS");
			garages = JPAContainerFactory.make(Garage.class,
					MyVaadinUI.PERSISTENCE_UNIT);
		}

		return garages;
	}

}
