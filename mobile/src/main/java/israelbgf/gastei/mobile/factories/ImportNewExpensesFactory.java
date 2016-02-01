package israelbgf.gastei.mobile.factories;

import android.content.Context;
import israelbgf.gastei.core.usecases.ImportNewExpenses;
import israelbgf.gastei.mobile.presenters.ImportNewExpensesPresenter;

public class ImportNewExpensesFactory {
    public static ImportNewExpenses make(Context context) {
        return new ImportNewExpenses(
                ExpenseGatewaySQLiteFactory.make(context),
                new ImportNewExpensesPresenter(context)
        );
    }
}
