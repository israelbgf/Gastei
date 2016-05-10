package israelbgf.gastei.core.usecases;


import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.GenerateMonthOverviewReport.Presenter;
import israelbgf.gastei.core.usecases.GenerateMonthOverviewReport.Presenter.ReportItem;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;

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

            oneOf(presenter).display(0, Collections.<ReportItem>emptyList());
        }});

        usecase.generateFor(month(2016, 1));
    }

    @Test
    public void amountOfSinglePlaceWhenThereAreOnlyOneExpense() {
        context.checking(new Expectations() {{
            allowing(gateway).retrieveBy(month(2016, 1));
            will(returnValue(asList(new Expense(10, "Place"))));

            oneOf(presenter).display(10.0, asList(new ReportItem("Place", 10.0)));
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

            oneOf(presenter).display(30.0, asList(new ReportItem("Place", 30.0)));
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

            oneOf(presenter).display(55, asList(
                    new ReportItem("Another Place", 50.0),
                    new ReportItem("Place", 5.0)
            ));
        }});

        usecase.generateFor(month(2016, 1));
    }

    @Test
    public void descendingOrderOfAmounts() {
        context.checking(new Expectations() {{
            allowing(gateway).retrieveBy(month(2016, 1));
            will(returnValue(asList(
                    new Expense(10, "Last"),
                    new Expense(100, "First")
            )));

            oneOf(presenter).display(110, asList(
                    new ReportItem("First", 100.0),
                    new ReportItem("Last", 10.0)
            ));
        }});

        usecase.generateFor(month(2016, 1));
    }

}
