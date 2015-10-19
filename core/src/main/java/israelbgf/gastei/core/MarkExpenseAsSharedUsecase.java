package israelbgf.gastei.core;

import israelbgf.gastei.core.gateways.ExpenseGateway;

public class MarkExpenseAsSharedUsecase {
    private final ExpenseGateway gateway;
    private final Presenter presenter;

    public MarkExpenseAsSharedUsecase(ExpenseGateway gateway, Presenter presenter) {
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
