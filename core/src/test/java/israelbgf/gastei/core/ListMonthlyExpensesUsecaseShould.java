package israelbgf.gastei.core;

import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase.Presenter;
import org.junit.After;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static israelbgf.gastei.core.utils.DateUtils.date;
import static israelbgf.gastei.core.utils.ExpenseFactory.expense;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class ListMonthlyExpensesUsecaseShould {

    ExpenseGateway gateway = mock(ExpenseGateway.class);
    PresenterSpy presenter = new PresenterSpy();
    ListMonthlyExpensesUsecase usecase = new ListMonthlyExpensesUsecase(gateway, presenter);

    @Test
    public void listAllWithTheirTotalWhenExpensesExistsForGivenMonth() {
        Date january = date(2015, 1);
        ExpenseEntity firstJanuaryExpense = expense(10, january);
        ExpenseEntity secondJanuaryExpense = expense(20, january);
        List<ExpenseEntity> expenses = asList(firstJanuaryExpense, secondJanuaryExpense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        assertThat(presenter.spiedStruct.expenses, equalTo(expenses));
        assertThat(presenter.spiedStruct.totalAmount, equalTo(firstJanuaryExpense.getAmount() + secondJanuaryExpense.getAmount()));
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
