package israelbgf.gastei.mobile.gateways.sqlite;

import android.provider.BaseColumns;

public interface ExpenseTableDefinition extends BaseColumns{

    String EXPENSE_TABLE = "expense";

    String AMOUNT = "amount";
    String DATE = "date";
    String PLACE = "place";
    String SHARED = "shared";

    String[] ALL_COLUMNS = {_ID, AMOUNT, DATE, PLACE, SHARED};

}
