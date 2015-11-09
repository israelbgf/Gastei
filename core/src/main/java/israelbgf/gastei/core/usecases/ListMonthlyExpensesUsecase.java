package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;

import java.util.*;

import static israelbgf.gastei.core.utils.DateUtils.dayOfTheMonth;
import static java.util.Arrays.asList;

public class ListMonthlyExpensesUsecase {
    private final ExpenseGateway gateway;
    private final Presenter presenter;

    public ListMonthlyExpensesUsecase(ExpenseGateway gateway, Presenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void list(int year, int month) {
        List<ExpenseEntity> expenses = gateway.retrieveByMonth(year, month);
        presenter.presentExpenses(new Presenter.Struct(
                        groupExpensesByMonthDay(expenses),
                        totalAmount(expenses),
                        totalShared(expenses))
        );
    }

    private LinkedHashMap<Integer, List<ExpenseEntity>> groupExpensesByMonthDay(List<ExpenseEntity> expenses) {
        LinkedHashMap<Integer, List<ExpenseEntity>> dailyExpenses = new LinkedHashMap<>();
        if (!expenses.isEmpty())
            for (ExpenseEntity expense : expenses) {
                int dayOfTheMonth = dayOfTheMonth(expense.getDate());
                if (dailyExpenses.containsKey(dayOfTheMonth))
                    dailyExpenses.get(dayOfTheMonth).add(expense);
                else
                    dailyExpenses.put(dayOfTheMonth, new ArrayList<>(asList(expense)));
            }
        return dailyExpenses;
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
            public final double totalAmount;
            public final double sharedAmount;
            public LinkedHashMap<Integer, List<ExpenseEntity>> dailyExpenses;

            public Struct(LinkedHashMap<Integer, List<ExpenseEntity>> dailyExpenses, double totalAmount, double sharedAmount) {
                this.dailyExpenses = dailyExpenses;
                this.totalAmount = totalAmount;
                this.sharedAmount = sharedAmount;
            }
        }
    }
}
