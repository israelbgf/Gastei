package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.gateways.ExpenseGateway;
import org.junit.After;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ToggleExpenseSharedStatusShould {

    private static final long EXISTING_EXPENSE_ID = 1;

    final ExpenseGateway gateway = mock(ExpenseGateway.class);
    final ToggleExpenseSharedStatus.Presenter presenter = mock(ToggleExpenseSharedStatus.Presenter.class);
    ToggleExpenseSharedStatus usecase = new ToggleExpenseSharedStatus(gateway, presenter);

    @Test
    public void toggleSharedStatusOfExistingExpense() {
        usecase.toggle(EXISTING_EXPENSE_ID);
        verify(gateway).toggleSharedStatus(EXISTING_EXPENSE_ID);
        verify(presenter).presentUpdatedExpense();
    }

    @After
    public void after() {
        verifyNoMoreInteractions(gateway, presenter);
    }



}
