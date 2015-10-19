package israelbgf.gastei.mobile;

import android.app.Application;
import android.test.ApplicationTestCase;
import io.realm.Realm;
import io.realm.RealmResults;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.utils.DateUtils;
import israelbgf.gastei.mobile.gateways.ExpenseGatewayRealm;
import israelbgf.gastei.mobile.gateways.realm.ExpenseRealm;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExpenseGatewayRealmShould extends ApplicationTestCase<Application> {

    private Realm realm;
    private ExpenseGatewayRealm gateway;

    public ExpenseGatewayRealmShould() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        realm.clear(ExpenseRealm.class);
        realm.commitTransaction();
        gateway = new ExpenseGatewayRealm(realm);
    }

    public void testPersistenceInDatabase() {
        ExpenseEntity expense = new ExpenseEntity(UUID.randomUUID().toString(), 20, "Giassi", DateUtils.date(2015, 12), true);

        gateway.save(expense);

        RealmResults<ExpenseRealm> results = realm.where(ExpenseRealm.class).findAll();
        assertEquals(1, results.size());
        ExpenseRealm storedExpense = results.get(0);
        assertEquals(expense.getId(), storedExpense.getId());
        assertEquals(expense.getAmount(), storedExpense.getAmount());
        assertEquals(expense.getDate().getTime(), storedExpense.getDate().getTime(), 1000);
        assertEquals(expense.getPlace(), storedExpense.getPlace());
        assertEquals(expense.isShared(), storedExpense.isShared());

    }

    public void testRetrievalByMonth() {
        Date january = DateUtils.date(2015, 1);
        Date februrary = DateUtils.date(2015, 2);
        ExpenseEntity giassiExpense = new ExpenseEntity(UUID.randomUUID().toString(), 10, "Giassi", january, true);
        ExpenseEntity wallmartExpense = new ExpenseEntity(UUID.randomUUID().toString(), 20, "Wallmart", februrary, true);
        gateway.save(giassiExpense);
        gateway.save(wallmartExpense);

        List<ExpenseEntity> expenses = gateway.retrieveByMonth(2015, 1);

        assertEquals(1, expenses.size());
        ExpenseEntity storedExpense = expenses.get(0);
        assertEquals(giassiExpense.getId(), storedExpense.getId());
        assertEquals(giassiExpense.getAmount(), storedExpense.getAmount());
        assertEquals(giassiExpense.getPlace(), storedExpense.getPlace());
        assertEquals(giassiExpense.getDate().getTime(), storedExpense.getDate().getTime(), 1000);
        assertEquals(giassiExpense.isShared(), storedExpense.isShared());

    }

    public void testMarkAsShared() {
        Date january = DateUtils.date(2015, 1);
        ExpenseEntity giassiExpense = new ExpenseEntity(UUID.randomUUID().toString(), 10, "Giassi", january, false);
        gateway.save(giassiExpense);

        gateway.markExpenseAsShared(giassiExpense.getId());

        ExpenseRealm storedExpense = realm.where(ExpenseRealm.class).findFirst();
        assertTrue(storedExpense.isShared());
    }

}
