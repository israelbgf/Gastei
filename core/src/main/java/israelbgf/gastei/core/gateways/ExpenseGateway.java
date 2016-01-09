package israelbgf.gastei.core.gateways;

import israelbgf.gastei.core.entities.Expense;

import java.util.List;

public interface ExpenseGateway {
    void save(Expense expense);

    List<Expense> retrieveByMonth(int year, int month);

    void markExpenseAsShared(long existingExpenseId);

    boolean contains(Expense candidate);

}
