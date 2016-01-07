package israelbgf.gastei.mobile.factories;

import android.content.Context;
import israelbgf.gastei.core.usecases.ImportNewExpenses;
import israelbgf.gastei.mobile.gateways.sqlite.BetterSQLiteDatabase;
import israelbgf.gastei.mobile.gateways.sqlite.DatabaseConnection;
import israelbgf.gastei.mobile.gateways.sqlite.ExpenseGatewaySQLite;
import israelbgf.gastei.mobile.presenters.ImportNewExpensesPresenter;

public class ImportNewExpensesFactory {
    public static ImportNewExpenses make(Context context) {
        return new ImportNewExpenses(
                new ExpenseGatewaySQLite(new BetterSQLiteDatabase(new DatabaseConnection(context).getWritableDatabase())),
                new ImportNewExpensesPresenter(context)
        );
    }
}
