package israelbgf.gastei.mobile.gateways.sqlite;

import android.database.sqlite.SQLiteDatabase;
import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;

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
        long id;

        if (expense.getId() == null) {
            id = database.insert(EXPENSE_TABLE, new BetterContentValues()
                    .with(AMOUNT, expense.getAmount())
                    .with(PLACE, expense.getPlace())
                    .with(DATE, expense.getDate())
                    .with(SHARED, expense.isShared()));
        } else {
            id = database.update(EXPENSE_TABLE, new BetterContentValues()
                    .with(AMOUNT, expense.getAmount())
                    .with(PLACE, expense.getPlace())
                    .with(DATE, expense.getDate())
                    .with(SHARED, expense.isShared()), "_ID = ?", expense.getId());
        }

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
    public void toggleSharedStatus(long existingExpenseId) {
        database.execDML("update expense set shared = case when shared = 1 then 0 else 1 end where _id = ?", new Long[]{existingExpenseId});
    }

    @Override
    public boolean contains(Expense candidate) {
        BetterCursor cursor = database.query(EXPENSE_TABLE, projection(_ID),
                "place = ? and amount = ? and date = ?",
                candidate.getPlace(), candidate.getAmount(), candidate.getDate().getTime());
        return cursor.moveToNext();
    }

    @Override
    public void delete(Long id) {
        database.delete(EXPENSE_TABLE, "_ID = ?", id);
    }

    private String[] projection(String... projection) {
        return projection;
    }


}
