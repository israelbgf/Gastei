package israelbgf.gastei.core;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase;
import org.junit.After;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static israelbgf.gastei.core.utils.DateUtils.createDate;
import static israelbgf.gastei.core.utils.ExpenseFactory.expense;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class ListMonthlyExpensesUsecaseShould {

    ExpenseGateway gateway = mock(ExpenseGateway.class);
    ListMonthlyExpensesUsecase.Presenter presenter = mock(ListMonthlyExpensesUsecase.Presenter.class);
    ListMonthlyExpensesUsecase usecase = new ListMonthlyExpensesUsecase(gateway, presenter);

    @Test
    public void listAllWithTheirTotalWhenExpensesExistsForGivenMonth() {
        Date january = createDate(2015, 1);
        Expense firstJanuaryExpense = expense(10, january);
        Expense secondJanuaryExpense = expense(20, january);
        List<Expense> expenses = asList(firstJanuaryExpense, secondJanuaryExpense);
        when(gateway.retrieveByMonth(january)).thenReturn(expenses);

        usecase.list(january);

        verify(presenter).presentExpenses(expenses, firstJanuaryExpense.amount + secondJanuaryExpense.amount);
    }


    @After
    public void after() {
        verifyNoMoreInteractions(presenter);
    }


}
