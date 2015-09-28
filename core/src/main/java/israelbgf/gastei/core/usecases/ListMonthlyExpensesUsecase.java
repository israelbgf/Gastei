package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;

import java.util.Date;
import java.util.List;

public class ListMonthlyExpensesUsecase {
    private final ExpenseGateway gateway;
    private final Presenter presenter;

    public ListMonthlyExpensesUsecase(ExpenseGateway gateway, Presenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void list(Date monthRestriction) {
        List<ExpenseEntity> expenses = gateway.retrieveByMonth(monthRestriction);
        presenter.presentExpenses(expenses, totalAmount(expenses));
    }

    private double totalAmount(List<ExpenseEntity> expenses) {
        float totalAmount = 0;
        for(ExpenseEntity expense: expenses){
            totalAmount += expense.getAmount();
        }
        return totalAmount;
    }

    public interface Presenter {
        void presentExpenses(List<ExpenseEntity> expenses, double totalAmount);
    }
}
