package israelbgf.gastei.core;

import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase.Presenter;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.*;

import static israelbgf.gastei.core.utils.DateUtils.date;
import static israelbgf.gastei.core.utils.ExpenseFactory.expense;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListMonthlyExpensesUsecaseShould {

    ExpenseGateway gateway = mock(ExpenseGateway.class);
    PresenterSpy presenter = new PresenterSpy();
    ListMonthlyExpensesUsecase usecase = new ListMonthlyExpensesUsecase(gateway, presenter);

    @Test
    public void presentNoExpensesWhenEmptyMonth() {
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(Collections.<ExpenseEntity>emptyList());

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.dailyExpenses.values().size(), equalTo(0));
    }

    @Test
    public void presentSingleExpenseWhenThereIsOne() {
        Date whateverDate = date(2015, 1);
        ExpenseEntity expense = expense(10, whateverDate, false);
        List<ExpenseEntity> expenses = asList(expense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.dailyExpenses.get(1), equalTo(expenses));
    }

    @Test
    public void presentTwoGroupedExpensesWhenBothFromSameDay() {
        Date whateverDate = date(2015, 1);
        ExpenseEntity expense = expense(10, whateverDate, false);
        ExpenseEntity otherExpense = expense(10, whateverDate, false);
        List<ExpenseEntity> expenses = asList(expense, otherExpense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.dailyExpenses.get(1), equalTo(expenses));
    }

    @Test
    public void presentZeroAsSumWhenDoesNotExistSharedExpenses() {
        Date whateverDate = date(2015, 1);
        ExpenseEntity nonSharedExpense = expense(10, whateverDate, false);
        List<ExpenseEntity> expenses = asList(nonSharedExpense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.sharedAmount, equalTo(0.0));
    }

    @Test
    public void presentSharedExpenseAmountWhenThereIsOne() {
        Date whateverDate = date(2015, 1);
        ExpenseEntity nonSharedExpense = expense(10, whateverDate, false);
        ExpenseEntity sharedExpense = expense(10, whateverDate, true);
        List<ExpenseEntity> expenses = asList(nonSharedExpense, sharedExpense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.sharedAmount, equalTo(sharedExpense.getAmount()));
    }

    @Test
    public void presentSharedExpenseAmountWhenThereAreMany() {
        Date whateverDate = date(2015, 1);
        ExpenseEntity nonSharedExpense = expense(10, whateverDate, false);
        ExpenseEntity sharedExpense = expense(10, whateverDate, true);
        ExpenseEntity otherSharedExpense = expense(20, whateverDate, true);
        List<ExpenseEntity> expenses = asList(nonSharedExpense, sharedExpense, otherSharedExpense);
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
