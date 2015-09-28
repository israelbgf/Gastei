package israelbgf.gastei.mobile;

import android.app.Application;
import android.test.ApplicationTestCase;
import io.realm.Realm;
import io.realm.RealmResults;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.mobile.gateways.ExpenseGatewayRealm;
import israelbgf.gastei.mobile.gateways.realm.ExpenseRealm;

import java.util.Date;
import java.util.List;

import static israelbgf.gastei.core.utils.DateUtils.createDate;

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

    public void testExpensePersistence() {
        ExpenseEntity expense = new ExpenseEntity(20, "Giassi", createDate(2015, 12));

        gateway.save(expense);

        RealmResults<ExpenseRealm> results = realm.where(ExpenseRealm.class).findAll();
        assertEquals(1, results.size());
        ExpenseRealm storedExpense = results.get(0);
        assertEquals(expense.getAmount(), storedExpense.getAmount());
        assertEquals(expense.getDate().getTime(), storedExpense.getDate().getTime(), 1000);
        assertEquals(expense.getPlace(), storedExpense.getPlace());

    }

    public void testRetrieveExpenseByMonth() {
        Date january = createDate(2015, 1);
        Date februrary = createDate(2015, 2);
        gateway.save(new ExpenseEntity(10, "Giassi", january));
        gateway.save(new ExpenseEntity(20, "Wallmart", februrary));

        List<ExpenseEntity> expenses = gateway.retrieveByMonth(january);

        assertEquals(1, expenses.size());
        ExpenseEntity storedExpense = expenses.get(0);
        assertEquals(10.0, storedExpense.getAmount());
        assertEquals("Giassi", storedExpense.getPlace());
        assertEquals(january.getTime(), storedExpense.getDate().getTime(), 1000);

    }

}
