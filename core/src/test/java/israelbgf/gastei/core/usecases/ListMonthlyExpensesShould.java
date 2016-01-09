package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.ListMonthlyExpenses;
import israelbgf.gastei.core.usecases.ListMonthlyExpenses.Presenter;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static israelbgf.gastei.core.utils.DateUtils.date;
import static israelbgf.gastei.core.utils.ExpenseFactory.expense;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListMonthlyExpensesShould {

    ExpenseGateway gateway = mock(ExpenseGateway.class);
    PresenterSpy presenter = new PresenterSpy();
    ListMonthlyExpenses usecase = new ListMonthlyExpenses(gateway, presenter);

    @Test
    public void presentNoExpensesWhenEmptyMonth() {
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(Collections.<Expense>emptyList());

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.dailyExpenses.values().size(), equalTo(0));
    }

    @Test
    public void presentSingleExpenseWhenThereIsOne() {
        Date date = date(2015, 1);
        Expense expense = expense(10, date, false);
        List<Expense> expenses = asList(expense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.dailyExpenses.get(1), equalTo(expenses));
    }

    @Test
    public void presentOneGroupOfExpensesWhenBothFromSameDay() {
        Date date = date(2015, 1);
        Expense expense = expense(10, date, false);
        Expense otherExpense = expense(10, date, false);
        List<Expense> expenses = asList(expense, otherExpense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.dailyExpenses.get(1), equalTo(expenses));
    }

    @Test
    public void presentTwoGroupsOfExpensesWhenBothFromDistinctDays() {
        int DAY_ONE = 1;
        int DAY_TWO = 2;
        Date date = date(2015, 1, DAY_ONE);
        Date otherDate = date(2015, 1, DAY_TWO);
        Expense expense = expense(10, date, false);
        Expense otherExpense = expense(10, otherDate, false);
        List<Expense> expenses = asList(expense, otherExpense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.dailyExpenses.get(DAY_ONE), equalTo(asList(expense)));
        assertThat(presenter.spiedStruct.dailyExpenses.get(DAY_TWO), equalTo(asList(otherExpense)));
    }

    @Test
    public void presentZeroAsSumWhenDoesNotExistSharedExpenses() {
        Date date = date(2015, 1);
        Expense nonSharedExpense = expense(10, date, false);
        List<Expense> expenses = asList(nonSharedExpense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.sharedAmount, equalTo(0.0));
    }

    @Test
    public void presentSharedExpenseAmountWhenThereIsOne() {
        Date date = date(2015, 1);
        Expense nonSharedExpense = expense(10, date, false);
        Expense sharedExpense = expense(10, date, true);
        List<Expense> expenses = asList(nonSharedExpense, sharedExpense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.sharedAmount, equalTo(sharedExpense.getAmount()));
    }

    @Test
    public void presentSharedExpenseAmountWhenThereAreMany() {
        Date date = date(2015, 1);
        Expense nonSharedExpense = expense(10, date, false);
        Expense sharedExpense = expense(10, date, true);
        Expense otherSharedExpense = expense(20, date, true);
        List<Expense> expenses = asList(nonSharedExpense, sharedExpense, otherSharedExpense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.sharedAmount, equalTo(sharedExpense.getAmount() + otherSharedExpense.getAmount()));
    }

    private static class PresenterSpy implements Presenter {

        private Struct spiedStruct;

        @Override
        public void presentExpenses(Struct spiedStruct) {
            this.spiedStruct = spiedStruct;
        }
    }

}
