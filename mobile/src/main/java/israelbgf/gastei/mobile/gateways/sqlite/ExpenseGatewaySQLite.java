package israelbgf.gastei.mobile.gateways.sqlite;

import android.database.sqlite.SQLiteDatabase;
import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.mobile.BetterContentValues;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static israelbgf.gastei.core.utils.DateUtils.firstDayOf;
import static israelbgf.gastei.core.utils.DateUtils.lastDayOf;
import static israelbgf.gastei.mobile.gateways.sqlite.ExpenseTableDefinition.*;

public class ExpenseGatewaySQLite implements ExpenseGateway {

    private BetterSQLiteDatabase database;

    public ExpenseGatewaySQLite(SQLiteDatabase database) {
        this(new BetterSQLiteDatabase(database));
    }

    public ExpenseGatewaySQLite(BetterSQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void save(Expense expense) {
        long id = database.insert(EXPENSE_TABLE, new BetterContentValues()
                .with(AMOUNT, expense.getAmount())
                .with(PLACE, expense.getPlace())
                .with(DATE, expense.getDate())
                .with(SHARED, expense.isShared()));

        expense.setId(id);
    }

    @Override
    public List<Expense> retrieveByMonth(int year, int month) {

        String restriction = "date >= ? and date <= ?";
        String[] restrictionParameters = {
                String.valueOf(firstDayOf(year, month).getTime()),
                String.valueOf(lastDayOf(year, month).getTime())
        };

        BetterCursor cursor = database.query(EXPENSE_TABLE, ALL_COLUMNS, restriction, restrictionParameters);

        List<Expense> expenses = new ArrayList<>();
        while(cursor.moveToNext()){
            expenses.add(new Expense(
                    cursor.getLong(_ID),
                    cursor.getDouble(AMOUNT),
                    cursor.getString(PLACE),
                    cursor.getDate(DATE),
                    cursor.getBoolean(SHARED)));
        }

        return expenses;
    }

    @Override
    public void markExpenseAsShared(String existingExpenseId) {
//        ExpenseRealm expense = realm.where(ExpenseRealm.class)
//            .equalTo("id", existingExpenseId)
//            .findFirst();
//
//        realm.beginTransaction();
//        expense.setShared(true);
//        realm.commitTransaction();
    }

    @Override
    public boolean contains(Expense candidate) {
//        return realm.where(ExpenseRealm.class)
//                .equalTo("place", candidate.getPlace())
//                .equalTo("amount", candidate.getAmount())
//                .equalTo("date", candidate.getDate())
//                .count() > 0;
        return false;
    }

}
