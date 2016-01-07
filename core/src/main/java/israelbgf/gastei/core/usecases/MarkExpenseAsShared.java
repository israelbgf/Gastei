package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.gateways.ExpenseGateway;

public class MarkExpenseAsShared {
    private final ExpenseGateway gateway;
    private final Presenter presenter;

    public MarkExpenseAsShared(ExpenseGateway gateway, Presenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void mark(String existingExpenseId) {
        gateway.markExpenseAsShared(existingExpenseId);
        presenter.presentExpenseShared();
    }

    public class Presenter {
        public void presentExpenseShared() {

        }
    }
}
