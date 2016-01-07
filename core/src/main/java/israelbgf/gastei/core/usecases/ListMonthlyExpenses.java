package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;

import java.util.*;

import static israelbgf.gastei.core.utils.DateUtils.dayOf;
import static java.util.Arrays.asList;

public class ListMonthlyExpenses {
    private final ExpenseGateway gateway;
    private final Presenter presenter;

    public ListMonthlyExpenses(ExpenseGateway gateway, Presenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void list(int year, int month) {
        List<Expense> expenses = gateway.retrieveByMonth(year, month);
        presenter.presentExpenses(new Presenter.Struct(
                        groupExpensesByMonthDay(expenses),
                        totalAmount(expenses),
                        totalShared(expenses))
        );
    }

    private LinkedHashMap<Integer, List<Expense>> groupExpensesByMonthDay(List<Expense> expenses) {
        LinkedHashMap<Integer, List<Expense>> dailyExpenses = new LinkedHashMap<>();
        if (!expenses.isEmpty())
            for (Expense expense : expenses) {
                int dayOfTheMonth = dayOf(expense.getDate());
                if (dailyExpenses.containsKey(dayOfTheMonth))
                    dailyExpenses.get(dayOfTheMonth).add(expense);
                else
                    dailyExpenses.put(dayOfTheMonth, new ArrayList<>(asList(expense)));
            }
        return dailyExpenses;
    }

    private double totalAmount(List<Expense> expenses) {
        float totalAmount = 0;
        for (Expense expense : expenses) {
            totalAmount += expense.getAmount();
        }
        return totalAmount;
    }

    private double totalShared(List<Expense> expenses) {
        float totalAmount = 0;
        for (Expense expense : expenses) {
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
            public LinkedHashMap<Integer, List<Expense>> dailyExpenses;

            public Struct(LinkedHashMap<Integer, List<Expense>> dailyExpenses, double totalAmount, double sharedAmount) {
                this.dailyExpenses = dailyExpenses;
                this.totalAmount = totalAmount;
                this.sharedAmount = sharedAmount;
            }
        }
    }
}
