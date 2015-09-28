package israelbgf.gastei.mobile;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseGatewayRealm implements ExpenseGateway{

    private Realm realm;

    public ExpenseGatewayRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void save(Expense expense) {
        realm.beginTransaction();
        ExpenseRealm expenseRealm = realm.createObject(ExpenseRealm.class);
        expenseRealm.setAmount(expense.amount);
        expenseRealm.setDate(expense.date);
        expenseRealm.setLocal(expense.local);
        realm.commitTransaction();
    }

    @Override
    public List<Expense> retrieveByMonth(Date month) {
        RealmResults<ExpenseRealm> results = realm.where(ExpenseRealm.class).equalTo("date", month).findAll();

        List<Expense> expenses = new ArrayList<>();
        for(ExpenseRealm expenseRealm : results){
            expenses.add(new Expense(expenseRealm.getAmount(), expenseRealm.getLocal(), expenseRealm.getDate()));
        }

        return expenses;
    }
}
