package israelbgf.gastei.mobile;

import android.app.Application;
import android.test.ApplicationTestCase;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.utils.DateUtils;
import israelbgf.gastei.mobile.gateways.sqlite.ExpenseGatewaySQLite;
import israelbgf.gastei.mobile.gateways.sqlite.BetterCursor;
import israelbgf.gastei.mobile.gateways.sqlite.DatabaseConnection;
import israelbgf.gastei.mobile.gateways.sqlite.BetterSQLiteDatabase;
import israelbgf.gastei.mobile.gateways.sqlite.ExpenseTableDefinition;

import java.util.Date;
import java.util.List;

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
        ExpenseEntity expense = new ExpenseEntity(20.57, "Giassi", DateUtils.date(2015, 12), true);

        gateway.save(expense);

        BetterCursor cursor = database.query(EXPENSE_TABLE, ExpenseTableDefinition.ALL_COLUMNS);
        cursor.moveToFirst();

        assertNotNull(expense.getId(), cursor.getLong(ExpenseTableDefinition._ID));
        assertEquals(expense.getAmount(), cursor.getDouble(AMOUNT));
        assertEquals(expense.getDate(), cursor.getDate(DATE));
        assertEquals(expense.getPlace(), cursor.getString(PLACE));
        assertEquals(expense.isShared(), cursor.getBoolean(ExpenseTableDefinition.SHARED));

    }

    public void testRetrievalByMonth() {
        Date january = DateUtils.date(2015, 1);
        Date februrary = DateUtils.date(2015, 2);
        ExpenseEntity giassiExpense = new ExpenseEntity(10, "Giassi", january, true);

        database.insert(EXPENSE_TABLE, new BetterContentValues()
                                                .with(AMOUNT, giassiExpense.getAmount())
                                                .with(PLACE, giassiExpense.getPlace())
                                                .with(DATE, giassiExpense.getDate())
                                                .with(SHARED, giassiExpense.isShared()));

        database.insert(EXPENSE_TABLE, new BetterContentValues()
                                                .with(AMOUNT, 20)
                                                .with(PLACE, "This guy shouldnt be retrivied")
                                                .with(DATE, februrary)
                                                .with(SHARED, true));


        List<ExpenseEntity> expenses = gateway.retrieveByMonth(2015, 1);

        assertEquals(1, expenses.size());
        ExpenseEntity storedExpense = expenses.get(0);
        assertNotNull(storedExpense.getId());
        assertEquals(giassiExpense.getAmount(), storedExpense.getAmount());
        assertEquals(giassiExpense.getPlace(), storedExpense.getPlace());
        assertEquals(giassiExpense.getDate().getTime(), storedExpense.getDate().getTime(), 1000);
        assertEquals(giassiExpense.isShared(), storedExpense.isShared());

    }
//
//    public void testMarkAsShared() {
//        Date january = DateUtils.date(2015, 1);
//        ExpenseEntity giassiExpense = new ExpenseEntity(UUID.randomUUID().toString(), 10, "Giassi", january, false);
//        gateway.save(giassiExpense);
//
//        gateway.markExpenseAsShared(giassiExpense.getId());
//
//        ExpenseRealm storedExpense = realm.where(ExpenseRealm.class).findFirst();
//        assertTrue(storedExpense.isShared());
//    }
//
//    public void testContainsWhenHaveSamePlaceDateAndAmount(){
//        Date january = DateUtils.date(2015, 1);
//        ExpenseEntity giassiExpense = new ExpenseEntity(UUID.randomUUID().toString(), 10, "Giassi", january, false);
//        gateway.save(giassiExpense);
//
//        assertTrue(gateway.contains(giassiExpense));
//    }
//
//    public void testDoesNotContainWhenPlaceIsDifferent(){
//        Date january = DateUtils.date(2015, 1);
//        ExpenseEntity giassiExpense = new ExpenseEntity(UUID.randomUUID().toString(), 10, "Giassi", january, false);
//        gateway.save(giassiExpense);
//
//        assertFalse(gateway.contains(new ExpenseEntity(UUID.randomUUID().toString(), 10, "Angeloni", january, false)));
//    }
//
//    public void testDoesNotContainWhenDateIsDifferent(){
//        Date january = DateUtils.date(2015, 1);
//        Date februrary = DateUtils.date(2015, 2);
//        ExpenseEntity giassiExpense = new ExpenseEntity(UUID.randomUUID().toString(), 10, "Giassi", january, false);
//        gateway.save(giassiExpense);
//
//        assertFalse(gateway.contains(new ExpenseEntity(UUID.randomUUID().toString(), 10, "Giassi", februrary, false)));
//    }
//
//    public void testDoesNotContainWhenAmountIsDifferent(){
//        Date january = DateUtils.date(2015, 1);
//        ExpenseEntity giassiExpense = new ExpenseEntity(UUID.randomUUID().toString(), 10, "Giassi", january, false);
//        gateway.save(giassiExpense);
//
//        assertFalse(gateway.contains(new ExpenseEntity(UUID.randomUUID().toString(), 20, "Giassi", january, false)));
//    }


}
