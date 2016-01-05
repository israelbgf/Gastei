package israelbgf.gastei.mobile.gateways.sqlite;

import android.database.sqlite.SQLiteDatabase;
import io.realm.Realm;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.mobile.BetterContentValues;
import israelbgf.gastei.mobile.gateways.realm.ExpenseRealm;
import israelbgf.gastei.mobile.gateways.sqlite.BetterCursor;
import israelbgf.gastei.mobile.gateways.sqlite.BetterSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static israelbgf.gastei.core.utils.DateUtils.firstDayOf;
import static israelbgf.gastei.core.utils.DateUtils.lastDayOf;
import static israelbgf.gastei.mobile.gateways.sqlite.ExpenseTableDefinition.*;

public class ExpenseGatewaySQLite implements ExpenseGateway {

    private Realm realm;
    private BetterSQLiteDatabase database;

    public ExpenseGatewaySQLite(SQLiteDatabase database) {
        this(new BetterSQLiteDatabase(database));
    }

    public ExpenseGatewaySQLite(BetterSQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void save(ExpenseEntity expense) {
        database.insert(EXPENSE_TABLE, new BetterContentValues()
                .with(AMOUNT, expense.getAmount())
                .with(PLACE, expense.getPlace())
                .with(DATE, expense.getDate())
                .with(SHARED, expense.isShared()));
    }

    @Override
    public List<ExpenseEntity> retrieveByMonth(int year, int month) {

        String restriction = "date >= ? and date <= ?";
        String[] restrictionParameters = {
                String.valueOf(firstDayOf(year, month).getTime()),
                String.valueOf(lastDayOf(year, month).getTime())
        };

        BetterCursor cursor = database.query(EXPENSE_TABLE, ALL_COLUMNS, restriction, restrictionParameters);

        List<ExpenseEntity> expenses = new ArrayList<>();
        while(cursor.moveToNext()){
            expenses.add(new ExpenseEntity(
                    cursor.getString(_ID),
                    cursor.getDouble(AMOUNT),
                    cursor.getString(PLACE),
                    cursor.getDate(DATE),
                    cursor.getBoolean(SHARED)));
        }

        return expenses;
    }

    @Override
    public void markExpenseAsShared(String existingExpenseId) {
        ExpenseRealm expense = realm.where(ExpenseRealm.class)
            .equalTo("id", existingExpenseId)
            .findFirst();

        realm.beginTransaction();
        expense.setShared(true);
        realm.commitTransaction();
    }

    @Override
    public boolean contains(ExpenseEntity candidate) {
        return realm.where(ExpenseRealm.class)
                .equalTo("place", candidate.getPlace())
                .equalTo("amount", candidate.getAmount())
                .equalTo("date", candidate.getDate())
                .count() > 0;
    }

}
