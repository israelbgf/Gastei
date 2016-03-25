package israelbgf.gastei.core.usecases;


import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.GenerateMonthOverviewReport.Presenter;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static israelbgf.gastei.core.values.Month.month;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class GenerateMonthOverviewReportShould {

    @Mock
    Presenter presenter;
    @Mock
    ExpenseGateway gateway;

    GenerateMonthOverviewReport usecase;

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Before
    public void setUp() {
        usecase = new GenerateMonthOverviewReport(gateway, presenter);
    }

    @Test
    public void emptyReportWhenThereArentExpenses() {
        context.checking(new Expectations() {{
            allowing(gateway).retrieveBy(month(2016, 1));
            will(returnValue(emptyList()));

            oneOf(presenter).display(emptyOverviewReport());
        }});

        usecase.generateFor(month(2016, 1));
    }

    @Test
    public void amountOfSinglePlaceWhenThereAreOnlyOneExpense() {
        context.checking(new Expectations() {{
            allowing(gateway).retrieveBy(month(2016, 1));
            will(returnValue(asList(new Expense(10, "Place"))));

            oneOf(presenter).display(overviewReportWith("Place", 10));
        }});

        usecase.generateFor(month(2016, 1));
    }

    @Test
    public void sumAmountOfSinglePlaceWhenThereAreTwoExpenses() {
        context.checking(new Expectations() {{
            allowing(gateway).retrieveBy(month(2016, 1));
            will(returnValue(asList(
                    new Expense(10, "Place"),
                    new Expense(20, "Place")
            )));

            oneOf(presenter).display(overviewReportWith("Place", 30));
        }});

        usecase.generateFor(month(2016, 1));
    }

    @Test
    public void sumAmountOfMultiplaPlaces() {
        context.checking(new Expectations() {{
            allowing(gateway).retrieveBy(month(2016, 1));
            will(returnValue(asList(
                    new Expense(2, "Place"),
                    new Expense(3, "Place"),
                    new Expense(20, "Another Place"),
                    new Expense(30, "Another Place")
            )));

            oneOf(presenter).display(overviewReportWith(
                    "Place", 5,
                    "Another Place", 50
            ));
        }});

        usecase.generateFor(month(2016, 1));
    }

    private Presenter.MonthOverviewReport overviewReportWith(String firstPlace, double firstPlaceAmount,
                                                                               String secondPlace, double secondPlaceAmount) {

        Presenter.MonthOverviewReport report = new Presenter.MonthOverviewReport();
        report.expensesByPlace.put(firstPlace, firstPlaceAmount);
        report.expensesByPlace.put(secondPlace, secondPlaceAmount);
        report.totalAmount = firstPlaceAmount + secondPlaceAmount;
        return report;

    }

    private Presenter.MonthOverviewReport overviewReportWith(String place, double amount) {
        Presenter.MonthOverviewReport report = new Presenter.MonthOverviewReport();
        report.expensesByPlace.put(place, amount);
        report.totalAmount = amount;
        return report;
    }

    private Presenter.MonthOverviewReport emptyOverviewReport() {
        return new Presenter.MonthOverviewReport();
    }

}
