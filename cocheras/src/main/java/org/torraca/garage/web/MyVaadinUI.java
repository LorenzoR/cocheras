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
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
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
import org.torraca.garage.model.MonthlyGaragePayment.PaymentMethod;
import org.torraca.garage.model.MonthlyGaragePayment.PaymentType;
import org.torraca.garage.model.SalaryAdvance;
import org.torraca.garage.web.components.forms.DailyGaragePaymentEditor.DailyGaragePaymentSavedEvent;
import org.torraca.garage.web.components.forms.DailyGaragePaymentEditor.DailyGaragePaymentSavedListener;
import org.torraca.garage.web.components.mainTabs.AccountingTab;
import org.torraca.garage.web.components.mainTabs.DailyGaragePaymentsTab;
import org.torraca.garage.web.components.mainTabs.EmployeesTab;
import org.torraca.garage.web.components.mainTabs.ExpensesTab;
import org.torraca.garage.web.components.mainTabs.GaragesTab;
import org.torraca.garage.web.components.mainTabs.MonthlyGaragePaymentsTab;
import org.torraca.garage.web.components.mainTabs.SalaryAdvancesTab;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
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

	@PersistenceContext(unitName = "garageManager", type = PersistenceContextType.EXTENDED)
	private static EntityManager entityManager;

	@Override
	protected void init(VaadinRequest request) {
		dataGenerator();

		Page.getCurrent().setTitle("Adminstrador de Cocheras");

		final VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setWidth("1200px");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		setContent(mainLayout);

		final HorizontalLayout headerLayout = new HorizontalLayout();
		headerLayout.setMargin(false);
		headerLayout.setSpacing(false);
		headerLayout.setWidth("100%");

		Label mainLabel = new Label("Administrador de Cocheras");
		mainLabel.setWidth("70%");
		mainLabel.setStyleName(Reindeer.LABEL_H1);

		headerLayout.addComponent(mainLabel);

		VerticalLayout userPanelLayout = new VerticalLayout();
		userPanelLayout.setWidth("30%");
		Label userLabel = new Label("Bienvenido, usuario");
		userPanelLayout.addComponent(userLabel);
		userPanelLayout.setComponentAlignment(userLabel, Alignment.TOP_CENTER);

		Button logoutButton = new Button("Salir");
		logoutButton.setStyleName(Reindeer.BUTTON_SMALL);
		userPanelLayout.addComponent(logoutButton);
		userPanelLayout.setComponentAlignment(logoutButton,
				Alignment.MIDDLE_CENTER);

		headerLayout.addComponent(userPanelLayout);

		headerLayout.setComponentAlignment(userPanelLayout,
				Alignment.MIDDLE_RIGHT);

		mainLayout.addComponent(headerLayout);

		TabSheet tabs = new TabSheet();
		tabs.setSizeFull();

		tabs.addTab(new DailyGaragePaymentsTab(), "Ingresos Diarios");
		tabs.addTab(new MonthlyGaragePaymentsTab(), "Ingresos Mensuales");
		// tabs.addComponent(buildMonthlyGaragePaymentTab());
		// tabs.addComponent(buildGarageTab());
		tabs.addTab(new EmployeesTab(), "Empleados");
		// tabs.addComponent(buildExpensesTab());
		tabs.addTab(new ExpensesTab(), "Gastos");
		tabs.addTab(new GaragesTab(), "Cocheras");
		tabs.addTab(new SalaryAdvancesTab(), "Adelantos de Sueldo");
		tabs.addTab(new AccountingTab(), "Contabilidad");

		mainLayout.addComponent(tabs);

	}

	private static void dataGenerator() {
		entityManager = Persistence.createEntityManagerFactory(
				MyVaadinUI.PERSISTENCE_UNIT).createEntityManager();
		entityManager.getTransaction().begin();

		Random randomGenerator = new Random(new Date().getTime());

		ArrayList<Garage> garageArray = new ArrayList<Garage>();
		ArrayList<Employee> employeeArray = new ArrayList<Employee>();

		for (int i = 0; i < 50; i++) {
			Garage garage = new Garage();

			garage.setNumber((long) (i + 1));
			garage.setPrice((double) 199);
			garage.setFloor(randomGenerator.nextInt(10) + 1);
			garage.setSide((char) ('a' + randomGenerator.nextInt(20)));

			garageArray.add(garage);

			entityManager.persist(garage);
		}

		for (int i = 1; i < 6; i++) {
			Employee employee = new Employee("Nombre " + i, "Apellido " + i);
			employeeArray.add(employee);
			entityManager.persist(employee);
		}

		for (int i = 0; i < 20; i++) {

			DailyGaragePayment dailyGaragePayment = new DailyGaragePayment(
					randomDate());
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

			dailyGaragePayment.setRegisteredBy(employeeArray
					.get(randomGenerator.nextInt(employeeArray.size())));

			entityManager.persist(dailyGaragePayment);
		}

		for (int i = 0; i < 4; i++) {
			MonthlyGaragePayment monthlyGaragePayment = new MonthlyGaragePayment();
			monthlyGaragePayment.setGarage(garageArray.get(randomGenerator
					.nextInt(garageArray.size())));
			monthlyGaragePayment.setDate(randomMonthYear());
			monthlyGaragePayment
					.setMoney((double) randomGenerator.nextInt(100));

			monthlyGaragePayment.setWithBill(randomGenerator.nextBoolean());

			monthlyGaragePayment.setRegisteredBy(employeeArray
					.get(randomGenerator.nextInt(employeeArray.size())));

			monthlyGaragePayment
					.setPaymentMethod(PaymentMethod.values()[randomGenerator
							.nextInt(PaymentMethod.values().length)]);
			monthlyGaragePayment
					.setPaymentType(PaymentType.values()[randomGenerator
							.nextInt(PaymentType.values().length)]);

			if (monthlyGaragePayment.getPaymentType() == PaymentType.PRESENT) {
				monthlyGaragePayment.setQtyMonths(1);
			} else {
				monthlyGaragePayment
						.setQtyMonths(randomGenerator.nextInt(5) + 1);
			}

			entityManager.persist(monthlyGaragePayment);

		}

		for (int i = 1; i < 11; i++) {

			Expense expense = new Expense(randomDate(),
					randomGenerator.nextDouble(), "Detalle del gasto " + i);

			entityManager.persist(expense);
		}

		for (int i = 0; i < 10; i++) {
			SalaryAdvance salaryAdvance = new SalaryAdvance();
			salaryAdvance.setAmount(randomGenerator.nextDouble());
			salaryAdvance.setEmployee(employeeArray.get(randomGenerator
					.nextInt(employeeArray.size())));
			salaryAdvance.setDate(randomDate());

			entityManager.persist(salaryAdvance);
		}

		entityManager.getTransaction().commit();

	}

	private static Calendar randomDate() {
		Random randomGenerator = new Random(new Date().getTime());

		int year = randomGenerator.nextInt(2014 - 2000) + 2000;

		int month = randomGenerator.nextInt(Calendar.DECEMBER) + 1;

		GregorianCalendar gc = new GregorianCalendar(year, month, 1);

		int day = randomGenerator.nextInt(gc.getActualMaximum(gc.DAY_OF_MONTH)) + 1;

		gc.set(year, month, day);

		return gc;
	}

	private static Calendar randomMonthYear() {
		Random randomGenerator = new Random(new Date().getTime());

		int year = randomGenerator.nextInt(2014 - 2000) + 2000;

		int month = randomGenerator.nextInt(Calendar.DECEMBER) + 1;

		GregorianCalendar gc = new GregorianCalendar(year, month, 1);

		return gc;
	}
}
