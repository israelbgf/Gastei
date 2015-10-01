package israelbgf.gastei.core;

import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase;
import org.junit.After;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static israelbgf.gastei.core.utils.DateUtils.date;
import static israelbgf.gastei.core.utils.ExpenseFactory.expense;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class ListMonthlyExpensesUsecaseShould {

    ExpenseGateway gateway = mock(ExpenseGateway.class);
    ListMonthlyExpensesUsecase.Presenter presenter = mock(ListMonthlyExpensesUsecase.Presenter.class);
    ListMonthlyExpensesUsecase usecase = new ListMonthlyExpensesUsecase(gateway, presenter);

    @Test
    public void listAllWithTheirTotalWhenExpensesExistsForGivenMonth() {
        Date january = date(2015, 1);
        ExpenseEntity firstJanuaryExpense = expense(10, january);
        ExpenseEntity secondJanuaryExpense = expense(20, january);
        List<ExpenseEntity> expenses = asList(firstJanuaryExpense, secondJanuaryExpense);
        when(gateway.retrieveByMonth(2015, 1)).thenReturn(expenses);

        usecase.list(2015, 1);

        verify(presenter).presentExpenses(expenses,
                firstJanuaryExpense.getAmount() + secondJanuaryExpense.getAmount());
    }


    @After
    public void after() {
        verifyNoMoreInteractions(presenter);
    }


}
