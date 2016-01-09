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
        BetterCursor cursor = database.query(EXPENSE_TABLE, ALL_COLUMNS, restriction, null, null, DATE + " desc",
                firstDayOf(year, month).getTime(),
                lastDayOf(year, month).getTime());

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
        database.update(EXPENSE_TABLE, new BetterContentValues().with(SHARED, true), "_id = ?", existingExpenseId);
    }

    @Override
    public boolean contains(Expense candidate) {
        BetterCursor cursor = database.query(EXPENSE_TABLE, projection(_ID),
                "place = ? and amount = ? and date = ?",
                candidate.getPlace(), candidate.getAmount(), candidate.getDate().getTime());
        return cursor.moveToNext();
    }

    private String[] projection(String... projection) {
        return projection;
    }

}