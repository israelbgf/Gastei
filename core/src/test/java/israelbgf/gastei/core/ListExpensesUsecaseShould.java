package israelbgf.gastei.core;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.ListExpensesUsecase;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static israelbgf.gastei.core.utils.ExpenseFactory.sampleExpense;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class ListExpensesUsecaseShould {

    ExpenseGateway gateway = mock(ExpenseGateway.class);
    ListExpensesUsecase.Presenter presenter = mock(ListExpensesUsecase.Presenter.class);
    ListExpensesUsecase usecase = new ListExpensesUsecase(gateway, presenter);

    @Test
    public void listAllWhenThereAreExpensesStored() {
        List<Expense> expenses = asList(sampleExpense());
        when(gateway.listAll()).thenReturn(expenses);

        usecase.execute();

        verify(presenter).presentExpenses(expenses);
    }

    @After
    public void after(){
        verifyNoMoreInteractions(presenter);
    }


}
