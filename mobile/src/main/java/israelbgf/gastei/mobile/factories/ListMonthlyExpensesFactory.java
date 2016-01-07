package israelbgf.gastei.mobile.factories;

import israelbgf.gastei.core.usecases.ListMonthlyExpenses;
import israelbgf.gastei.mobile.actvities.ExpenseManagementActivity;
import israelbgf.gastei.mobile.gateways.sqlite.BetterSQLiteDatabase;
import israelbgf.gastei.mobile.gateways.sqlite.DatabaseConnection;
import israelbgf.gastei.mobile.gateways.sqlite.ExpenseGatewaySQLite;
import israelbgf.gastei.mobile.presenters.ListMonthlyExpensesPresenter;

public class ListMonthlyExpensesFactory {
    public static ListMonthlyExpenses make(ExpenseManagementActivity activity) {
        return new ListMonthlyExpenses(
                new ExpenseGatewaySQLite(new BetterSQLiteDatabase(new DatabaseConnection(activity.getApplicationContext()).getWritableDatabase())),
                new ListMonthlyExpensesPresenter(activity));
    }
}
