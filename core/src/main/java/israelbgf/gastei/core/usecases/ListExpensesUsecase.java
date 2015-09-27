package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;

import java.util.List;

public class ListExpensesUsecase {
    private final ExpenseGateway gateway;
    private final Presenter presenter;

    public ListExpensesUsecase(ExpenseGateway gateway, Presenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void execute() {
        presenter.presentExpenses(gateway.listAll());
    }

    public interface Presenter {
        void presentExpenses(List<Expense> expenses);
    }
}
