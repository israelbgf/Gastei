package israelbgf.gastei.core.gateways;

import israelbgf.gastei.core.entities.ExpenseEntity;

import java.util.List;

public interface ExpenseGateway {
    void save(ExpenseEntity expense);

    List<ExpenseEntity> retrieveByMonth(int year, int month);

    void markExpenseAsShared(String existingExpenseId);

    boolean contains(ExpenseEntity candidate);
}
