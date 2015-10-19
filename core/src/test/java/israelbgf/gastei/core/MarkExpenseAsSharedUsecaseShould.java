package israelbgf.gastei.core;

import israelbgf.gastei.core.gateways.ExpenseGateway;
import org.junit.After;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MarkExpenseAsSharedUsecaseShould {

    private static final String EXISTING_EXPENSE_ID = "ID";

    final ExpenseGateway gateway = mock(ExpenseGateway.class);
    final MarkExpenseAsSharedUsecase.Presenter presenter = mock(MarkExpenseAsSharedUsecase.Presenter.class);
    MarkExpenseAsSharedUsecase usecase = new MarkExpenseAsSharedUsecase(gateway, presenter);

    @Test
    public void markExistingExpenseAsShared() {
        usecase.mark(EXISTING_EXPENSE_ID);
        verify(gateway).markExpenseAsShared(EXISTING_EXPENSE_ID);
        verify(presenter).presentExpenseShared();
    }

    @After
    public void after() {
        verifyNoMoreInteractions(gateway, presenter);
    }



}
