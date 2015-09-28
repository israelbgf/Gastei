package israelbgf.gastei.mobile;

import android.app.Application;
import android.test.ApplicationTestCase;
import io.realm.Realm;
import io.realm.RealmResults;
import israelbgf.gastei.core.entities.Expense;

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
        Expense expense = new Expense(20, "Giassi", createDate(2015, 12));

        gateway.save(expense);

        RealmResults<ExpenseRealm> results = realm.where(ExpenseRealm.class).findAll();
        assertEquals(1, results.size());
        ExpenseRealm storedExpense = results.get(0);
        assertEquals(expense.amount, storedExpense.getAmount());
        assertEquals(expense.date.getTime(), storedExpense.getDate().getTime(), 1000);
        assertEquals(expense.local, storedExpense.getLocal());

    }

}
