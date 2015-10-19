package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;

import java.util.List;

public class ListMonthlyExpensesUsecase {
    private final ExpenseGateway gateway;
    private final Presenter presenter;

    public ListMonthlyExpensesUsecase(ExpenseGateway gateway, Presenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void list(int year, int month) {
        List<ExpenseEntity> expenses = gateway.retrieveByMonth(year, month);
        presenter.presentExpenses(new Presenter.Struct(expenses, totalAmount(expenses), totalShared(expenses)));
    }

    private double totalAmount(List<ExpenseEntity> expenses) {
        float totalAmount = 0;
        for (ExpenseEntity expense : expenses) {
            totalAmount += expense.getAmount();
        }
        return totalAmount;
    }

    private double totalShared(List<ExpenseEntity> expenses) {
        float totalAmount = 0;
        for (ExpenseEntity expense : expenses) {
            if (expense.isShared())
                totalAmount += expense.getAmount();
        }
        return totalAmount;
    }

    public interface Presenter {

        void presentExpenses(Struct struct);

        class Struct {
            public final List<ExpenseEntity> expenses;
            public final double totalAmount;
            public final double sharedAmount;

            public Struct(List<ExpenseEntity> expenses, double totalAmount, double sharedAmount) {
                this.expenses = expenses;
                this.totalAmount = totalAmount;
                this.sharedAmount = sharedAmount;
            }
        }
    }
}
