package org.torraca.garage.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.torraca.garage.model.DailyGaragePayment;
import org.torraca.garage.model.Employee;
import org.torraca.garage.model.Expense;
import org.torraca.garage.model.Garage;
import org.torraca.garage.model.MonthlyGaragePayment;
import org.torraca.garage.web.DailyGaragePaymentEditor.DailyGaragePaymentSavedEvent;
import org.torraca.garage.web.DailyGaragePaymentEditor.DailyGaragePaymentSavedListener;
import org.torraca.garage.web.components.mainTabs.DailyGaragePaymentsTab;
import org.torraca.garage.web.components.mainTabs.EmployeesTab;
import org.torraca.garage.web.components.mainTabs.ExpensesTab;
import org.torraca.garage.web.components.mainTabs.GaragesTab;
import org.torraca.garage.web.components.mainTabs.MonthlyGaragePaymentsTab;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

	public static final String PERSISTENCE_UNIT = "garageManager";

	private JPAContainer<DailyGaragePayment> dailyGaragePayments;
	private JPAContainer<MonthlyGaragePayment> monthlyGaragePayments;
	private JPAContainer<Garage> garages;
	private JPAContainer<Employee> employees;

	private Button newDailyPaymentButton;
	private Button newGarage;

	@Override
	protected void init(VaadinRequest request) {
		dataGenerator();
		
		Page.getCurrent().setTitle("Adminstrador de Cocheras");

		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		Label mainLabel = new Label("Administrador de Cocheras");
		mainLabel.setStyleName(Reindeer.LABEL_H1);
		layout.addComponent(mainLabel);

		TabSheet tabs = new TabSheet();
		tabs.setSizeFull();

		tabs.addTab(new DailyGaragePaymentsTab(), "Ingresos Diarios");
		tabs.addTab(new MonthlyGaragePaymentsTab(), "Ingresos Mensuales");
//		tabs.addComponent(buildMonthlyGaragePaymentTab());
//		tabs.addComponent(buildGarageTab());
		tabs.addTab(new EmployeesTab(), "Empleados");
//		tabs.addComponent(buildExpensesTab());
		tabs.addTab(new ExpensesTab(), "Gastos");
		tabs.addTab(new GaragesTab(), "Cocheras");

		layout.addComponent(tabs);

	}

	private static void dataGenerator() {
		EntityManager em = Persistence.createEntityManagerFactory(
				MyVaadinUI.PERSISTENCE_UNIT).createEntityManager();
		em.getTransaction().begin();

		Random randomGenerator = new Random(new Date().getTime());

		ArrayList<Garage> garageArray = new ArrayList<Garage>();

		for (int i = 0; i < 100; i++) {

			int year = randomGenerator.nextInt(2014 - 2000) + 2000;

			int month = randomGenerator.nextInt(Calendar.DECEMBER) + 1;

			GregorianCalendar gc = new GregorianCalendar(year, month, 1);

			int day = randomGenerator.nextInt(gc
					.getActualMaximum(gc.DAY_OF_MONTH)) + 1;

			gc.set(year, month, day);

			DailyGaragePayment dailyGaragePayment = new DailyGaragePayment(gc);
			dailyGaragePayment.setQtyBy24Hours(Long.valueOf(randomGenerator
					.nextInt(100)));
			dailyGaragePayment.setQtyBy8Hours(Long.valueOf(randomGenerator
					.nextInt(100)));
			dailyGaragePayment.setQtyByHour(Long.valueOf(randomGenerator
					.nextInt(100)));

			dailyGaragePayment.setAmountBy24Hours(randomGenerator.nextDouble());
			dailyGaragePayment.setAmountBy8Hours(randomGenerator.nextDouble());
			dailyGaragePayment.setAmountByHour(randomGenerator.nextDouble());

			dailyGaragePayment.setWithBill(randomGenerator.nextBoolean());

			em.persist(dailyGaragePayment);
		}

		for (int i = 0; i < 50; i++) {
			Garage garage = new Garage();

			garage.setNumber((long) (i + 1));
			garage.setPrice((double) 199);
			garage.setFloor(randomGenerator.nextInt(10) + 1);

			garageArray.add(garage);

			em.persist(garage);
		}

		for (int i = 1; i < 6; i++) {
			Employee employee = new Employee("Nombre " + i, "Apellido " + i);

			em.persist(employee);
		}

		for (int i = 0; i < 100; i++) {
			MonthlyGaragePayment monthlyGaragePayment = new MonthlyGaragePayment();
			monthlyGaragePayment.setGarage(garageArray.get(randomGenerator
					.nextInt(garageArray.size())));
			monthlyGaragePayment.setMonth(randomGenerator.nextInt(12));
			monthlyGaragePayment.setMoney(randomGenerator.nextDouble());

			em.persist(monthlyGaragePayment);

		}

		for (int i = 1; i < 11; i++) {

			int year = randomGenerator.nextInt(2014 - 2000) + 2000;

			int month = randomGenerator.nextInt(Calendar.DECEMBER) + 1;

			GregorianCalendar gc = new GregorianCalendar(year, month, 1);

			int day = randomGenerator.nextInt(gc
					.getActualMaximum(gc.DAY_OF_MONTH)) + 1;

			gc.set(year, month, day);

			Expense expense = new Expense(gc, randomGenerator.nextDouble(),
					"Detalle del gasto " + i);

			em.persist(expense);
		}

		em.getTransaction().commit();

	}

	private Table garagesTable;

	private Layout buildGarageTab() {
		final VerticalLayout l = new VerticalLayout();
		l.setCaption("Cocheras");
		l.setMargin(true);
		l.setSpacing(true);
		l.setImmediate(true);

		HorizontalLayout floorsLayout = new HorizontalLayout();
		floorsLayout.setWidth("100%");
		floorsLayout.setMargin(new MarginInfo(false, true, false, true));

		garages = JPAContainerFactory.make(Garage.class,
				MyVaadinUI.PERSISTENCE_UNIT);

		for (int i = 1; i < 11; i++) {
			final int floorParam = i;
			Button button = new Button(" [ Piso " + i + " ] ");
			button.setStyleName(Reindeer.BUTTON_LINK);
			button.addClickListener(new Button.ClickListener() {
				// @Override
				public void buttonClick(ClickEvent event) {

					garages = getGarages(floorParam);

					garagesTable.setContainerDataSource(garages);

				}
			});

			floorsLayout.addComponent(button);
		}

		l.addComponent(floorsLayout);

		garagesTable = createTable(garages, new String[] { "Precio", "Piso",
				"Número Cochera" }, null);

		l.addComponent(garagesTable);

		return l;
	}

	private Layout buildExpensesTab() {
		VerticalLayout l = new VerticalLayout();
		l.setCaption("Gastos");
		l.setMargin(true);
		l.setSpacing(true);

		JPAContainer<Expense> expenses;

		expenses = JPAContainerFactory.make(Expense.class,
				MyVaadinUI.PERSISTENCE_UNIT);

		// Table expensesTable = createTable(expenses, new String[] { "Precio",
		// "Número Cochera" }, null);
		Table expensesTable = createTable(expenses, null, null);

		l.addComponent(expensesTable);

		return l;
	}

	private Layout buildEmployeeTab() {
		VerticalLayout l = new VerticalLayout();
		l.setCaption("Empleados");
		l.setMargin(true);
		l.setSpacing(true);
		l.setImmediate(true);

		employees = JPAContainerFactory.make(Employee.class,
				MyVaadinUI.PERSISTENCE_UNIT);

		Table employeesTable = createTable(employees, new String[] {
				"Apellido", "Nombre" }, null);

		l.addComponent(employeesTable);

		return l;
	}

	private Layout buildMonthlyGaragePaymentTab() {
		VerticalLayout l = new VerticalLayout();
		l.setCaption("Ingresos Mensuales");
		l.setMargin(true);
		l.setSpacing(true);

		monthlyGaragePayments = JPAContainerFactory.make(
				MonthlyGaragePayment.class, MyVaadinUI.PERSISTENCE_UNIT);

		Table monthlyGaragePaymentsTable = createTable(monthlyGaragePayments,
				null, null);

		l.addComponent(monthlyGaragePaymentsTable);

		return l;
	}

	private Layout buildDateFields(String tabCaption) {
		VerticalLayout l = new VerticalLayout();
		l.setCaption(tabCaption);
		l.setMargin(true);
		l.setSpacing(true);

		l.addComponent(new Label(
				"Date fields don't currently have any additional style names, but here you can see how they behave with the different background colors."));

		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		// hl.setMargin(true, false, false, false);
		hl.setMargin(new MarginInfo(true, false, false, false));
		l.addComponent(hl);

		DateField df = new DateField();
		df.setValue(new Date());
		df.setResolution(DateField.RESOLUTION_MIN);
		hl.addComponent(df);

		df = new InlineDateField();
		df.setValue(new Date());
		df.setResolution(DateField.RESOLUTION_DAY);
		hl.addComponent(df);

		df = new InlineDateField();
		df.setValue(new Date());
		df.setResolution(DateField.RESOLUTION_YEAR);
		hl.addComponent(df);

		return l;
	}

	private JPAContainer<Garage> getGarages(final int floor) {
		garages.getEntityProvider().setQueryModifierDelegate(
				new DefaultQueryModifierDelegate() {
					@Override
					public void filtersWillBeAdded(
							CriteriaBuilder criteriaBuilder,
							CriteriaQuery<?> query, List<Predicate> predicates) {
						Root<?> fromGarage = query.getRoots().iterator().next();

						// Add a "WHERE age > 116" expression
						Path<Integer> floorPath = fromGarage
								.<Integer> get("floor");
						predicates.add(criteriaBuilder.equal(floorPath, floor));
					}
				});

		return garages;
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
