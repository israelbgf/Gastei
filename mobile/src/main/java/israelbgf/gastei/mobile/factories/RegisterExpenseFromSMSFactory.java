package israelbgf.gastei.mobile.factories;

import android.content.Context;
import israelbgf.gastei.core.usecases.RegisterExpenseFromSMS;
import israelbgf.gastei.mobile.gateways.sqlite.BetterSQLiteDatabase;
import israelbgf.gastei.mobile.gateways.sqlite.DatabaseConnection;
import israelbgf.gastei.mobile.gateways.sqlite.ExpenseGatewaySQLite;
import israelbgf.gastei.mobile.presenters.RegisterExpenseFromSMSPresenter;

public class RegisterExpenseFromSMSFactory {
    public static RegisterExpenseFromSMS make(Context context) {
        return new RegisterExpenseFromSMS(
                new ExpenseGatewaySQLite(new BetterSQLiteDatabase(new DatabaseConnection(context).getWritableDatabase())),
                new RegisterExpenseFromSMSPresenter(context)
        );
    }
}
