package israelbgf.gastei.mobile.factories;

import android.content.Context;
import israelbgf.gastei.mobile.gateways.sqlite.BetterSQLiteDatabase;
import israelbgf.gastei.mobile.gateways.sqlite.DatabaseConnection;
import israelbgf.gastei.mobile.gateways.sqlite.ExpenseGatewaySQLite;

public class ExpenseGatewaySQLiteFactory {
    public static ExpenseGatewaySQLite make(Context context) {
        return new ExpenseGatewaySQLite(new BetterSQLiteDatabase(new DatabaseConnection(context).getWritableDatabase()));
    }
}
