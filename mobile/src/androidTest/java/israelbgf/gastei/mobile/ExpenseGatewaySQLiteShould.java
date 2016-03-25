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
import static israelbgf.gastei.core.values.Month.month;
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

    public void testUpdateInDatabase() {
        Expense expense = new Expense(20.57, "Giassi", date(2015, 12), true);
        gateway.save(expense);
        expense.setAmount(10.0);
        expense.setPlace("Angeloni");
        expense.setDate(date(2015, 11));
        expense.setShared(false);
        gateway.save(expense);

        BetterCursor cursor = database.query(EXPENSE_TABLE, ExpenseTableDefinition.ALL_COLUMNS);
        cursor.moveToLast();

        assertEquals(1, cursor.getCount());
        assertEquals(expense.getId().longValue(), cursor.getLong(ExpenseTableDefinition._ID));
        assertEquals(expense.getAmount(), cursor.getDouble(AMOUNT));
        assertEquals(expense.getDate(), cursor.getDate(DATE));
        assertEquals(expense.getPlace(), cursor.getString(PLACE));
        assertEquals(expense.isShared(), cursor.getBoolean(ExpenseTableDefinition.SHARED));
    }

    public void testDeleteExpense() {
        Expense expense = new Expense(20.57, "Giassi", date(2015, 12), true);
        gateway.save(expense);
        Expense otherExpense = new Expense(20.57, "Giassi", date(2015, 12), true);
        gateway.save(otherExpense);

        gateway.delete(expense.getId());
        BetterCursor cursor = database.query(EXPENSE_TABLE, ExpenseTableDefinition.ALL_COLUMNS);
        cursor.moveToFirst();

        assertEquals(1, cursor.getCount());
        assertEquals(otherExpense.getId().longValue(), cursor.getLong(ExpenseTableDefinition._ID));

    }

    public void testRetrievalByMonth() {
        Expense giassiExpense = new Expense(10, "Giassi", date(2015, 1), true);
        Expense otherExpense = new Expense(20, "This guy shouldnt bre retrivied", date(2015, 2), true);

        gateway.save(giassiExpense);
        gateway.save(otherExpense);

        List<Expense> expenses = gateway.retrieveByMonth(2015, 1);

        assertEquals(1, expenses.size());
        Expense storedExpense = expenses.get(0);
        assertNotNull(storedExpense.getId());
        assertEquals(giassiExpense.getAmount(), storedExpense.getAmount());
        assertEquals(giassiExpense.getPlace(), storedExpense.getPlace());
        assertEquals(giassiExpense.getDate().getTime(), storedExpense.getDate().getTime(), 1000);
        assertEquals(giassiExpense.isShared(), storedExpense.isShared());

    }

    public void testOverridedRetrievalByMonth() {
        Expense giassiExpense = new Expense(10, "Giassi", date(2015, 1), true);
        Expense otherExpense = new Expense(20, "This guy shouldnt bre retrivied", date(2015, 2), true);

        gateway.save(giassiExpense);
        gateway.save(otherExpense);

        List<Expense> expenses = gateway.retrieveBy(month(2015, 1));

        assertEquals(1, expenses.size());
        Expense storedExpense = expenses.get(0);
        assertNotNull(storedExpense.getId());
        assertEquals(giassiExpense.getAmount(), storedExpense.getAmount());
        assertEquals(giassiExpense.getPlace(), storedExpense.getPlace());
        assertEquals(giassiExpense.getDate().getTime(), storedExpense.getDate().getTime(), 1000);
        assertEquals(giassiExpense.isShared(), storedExpense.isShared());

    }

    public void testRetrievalByMonthOrderedByMostRecent(){
        Expense recent = new Expense(10, "Should be the first", date(2015, 1, 2), false);
        Expense oldest = new Expense(20, "Should be the second", date(2015, 1, 1), false);
        gateway.save(oldest);
        gateway.save(recent);

        List<Expense> expenses = gateway.retrieveByMonth(2015, 1);

        assertEquals(recent.getId(), expenses.get(0).getId());
        assertEquals(oldest.getId(), expenses.get(1).getId());
    }


    public void testMakeItSharedWhenItsNot() {
        Date january = date(2015, 1);
        Expense expenseToBeAffected = new Expense(10, "Giassi", january, false);
        Expense otherExpense = new Expense(10, "Angeloni", january, false);
        gateway.save(expenseToBeAffected);
        gateway.save(otherExpense);

        gateway.toggleSharedStatus(expenseToBeAffected.getId());

        BetterCursor cursor = database.query(EXPENSE_TABLE, ExpenseTableDefinition.ALL_COLUMNS, "shared = 1 and _id = ?", expenseToBeAffected.getId());
        assertEquals(1, cursor.getCount());
    }

    public void testMakeItUnsharedWhenItIs() {
        Date january = date(2015, 1);
        Expense expenseToBeAffected = new Expense(10, "Giassi", january, true);
        Expense otherExpense = new Expense(10, "Angeloni", january, true);
        gateway.save(expenseToBeAffected);
        gateway.save(otherExpense);

        gateway.toggleSharedStatus(expenseToBeAffected.getId());

        BetterCursor cursor = database.query(EXPENSE_TABLE, ExpenseTableDefinition.ALL_COLUMNS, "shared = 0 and _id = ?", expenseToBeAffected.getId());
        assertEquals(1, cursor.getCount());
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
