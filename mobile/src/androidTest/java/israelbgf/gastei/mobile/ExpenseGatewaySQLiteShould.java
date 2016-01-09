package israelbgf.gastei.mobile;

import android.app.Application;
import android.test.ApplicationTestCase;
import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.mobile.gateways.sqlite.ExpenseGatewaySQLite;
import israelbgf.gastei.mobile.gateways.sqlite.BetterCursor;
import israelbgf.gastei.mobile.gateways.sqlite.DatabaseConnection;
import israelbgf.gastei.mobile.gateways.sqlite.BetterSQLiteDatabase;
import israelbgf.gastei.mobile.gateways.sqlite.ExpenseTableDefinition;

import java.util.Date;
import java.util.List;

import static israelbgf.gastei.core.utils.DateUtils.date;
import static israelbgf.gastei.mobile.gateways.sqlite.ExpenseTableDefinition.*;

public class ExpenseGatewaySQLiteShould extends ApplicationTestCase<Application> {

    private ExpenseGatewaySQLite gateway;
    private BetterSQLiteDatabase database;

    public ExpenseGatewaySQLiteShould() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        getContext().deleteDatabase(DatabaseConnection.DATABASE_NAME);
        DatabaseConnection connection = new DatabaseConnection(getContext());
        database = new BetterSQLiteDatabase(connection.getWritableDatabase());
        gateway = new ExpenseGatewaySQLite(database);
    }

    public void testPersistenceInDatabase() {
        Expense expense = new Expense(20.57, "Giassi", date(2015, 12), true);

        gateway.save(expense);

        BetterCursor cursor = database.query(EXPENSE_TABLE, ExpenseTableDefinition.ALL_COLUMNS);
        cursor.moveToFirst();

        assertEquals(expense.getId().longValue(), cursor.getLong(ExpenseTableDefinition._ID));
        assertEquals(expense.getAmount(), cursor.getDouble(AMOUNT));
        assertEquals(expense.getDate(), cursor.getDate(DATE));
        assertEquals(expense.getPlace(), cursor.getString(PLACE));
        assertEquals(expense.isShared(), cursor.getBoolean(ExpenseTableDefinition.SHARED));

    }

    public void testRetrievalByMonth() {
        Expense giassiExpense = new Expense(10, "Giassi", date(2015, 1), true);
        Expense otherExpense = new Expense(20, "This guy shouldnt bre retrivied", date(2015, 2), true);

        createExpense(giassiExpense);
        createExpense(otherExpense);

        List<Expense> expenses = gateway.retrieveByMonth(2015, 1);

        assertEquals(1, expenses.size());
        Expense storedExpense = expenses.get(0);
        assertNotNull(storedExpense.getId());
        assertEquals(giassiExpense.getAmount(), storedExpense.getAmount());
        assertEquals(giassiExpense.getPlace(), storedExpense.getPlace());
        assertEquals(giassiExpense.getDate().getTime(), storedExpense.getDate().getTime(), 1000);
        assertEquals(giassiExpense.isShared(), storedExpense.isShared());

    }

    public void testMarkAsShared() {
        Date january = date(2015, 1);
        Expense giassiExpense = new Expense(10, "Giassi", january, false);
        long id = createExpense(giassiExpense);

        gateway.markExpenseAsShared(String.valueOf(id));

        BetterCursor cursor = database.query(EXPENSE_TABLE, ExpenseTableDefinition.ALL_COLUMNS);
        cursor.moveToFirst();
        assertTrue(cursor.getBoolean(SHARED));
    }

    private long createExpense(Expense giassiExpense) {
        return database.insert(EXPENSE_TABLE, new BetterContentValues()
                .with(AMOUNT, giassiExpense.getAmount())
                .with(PLACE, giassiExpense.getPlace())
                .with(DATE, giassiExpense.getDate())
                .with(SHARED, giassiExpense.isShared()));
    }

    public void testContainsWhenHaveSamePlaceDateAndAmount(){
        Expense giassiExpense = new Expense(10, "Giassi", date(2015, 1), false);
        gateway.save(giassiExpense);

        assertTrue(gateway.contains(giassiExpense));
    }

    public void testDoesNotContainWhenPlaceIsDifferent(){
        Expense giassiExpense = new Expense(10, "Giassi", date(2015, 1), false);
        Expense angeloniExpense = new Expense(10, "Angeloni", date(2015, 1), false);
        gateway.save(giassiExpense);

        assertFalse(gateway.contains(angeloniExpense));
    }

    public void testDoesNotContainWhenDateIsDifferent(){
        Expense januaryExpense = new Expense(10, "Giassi", date(2015, 1), false);
        Expense februraryExpense = new Expense(10, "Giassi", date(2015, 2), false);
        gateway.save(januaryExpense);

        assertFalse(gateway.contains(februraryExpense));
    }

    public void testDoesNotContainWhenAmountIsDifferent(){
        Expense tenBucksExpense = new Expense(10, "Giassi", date(2015, 1), false);
        Expense twentyBucksExpense = new Expense(20, "Giassi", date(2015, 2), false);
        gateway.save(tenBucksExpense);

        assertFalse(gateway.contains(twentyBucksExpense));
    }


}
