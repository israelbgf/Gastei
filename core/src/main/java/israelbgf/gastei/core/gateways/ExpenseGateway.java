package israelbgf.gastei.core.gateways;

import israelbgf.gastei.core.entities.ExpenseEntity;

import java.util.Date;
import java.util.List;

public interface ExpenseGateway {
    void save(ExpenseEntity expense);

    List<ExpenseEntity> retrieveByMonth(Date month);
}
