package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.gateways.ExpenseGateway;

public class ToggleExpenseSharedStatus {
    private final ExpenseGateway gateway;
    private final Presenter presenter;

    public ToggleExpenseSharedStatus(ExpenseGateway gateway, Presenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void toggle(long existingExpenseId) {
        gateway.toggleSharedStatus(existingExpenseId);
        presenter.presentUpdatedExpense();
    }

    public interface Presenter {
        void presentUpdatedExpense();
    }
}
