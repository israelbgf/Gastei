package israelbgf.gastei.mobile.gateways;

import io.realm.Realm;
import io.realm.RealmResults;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.mobile.gateways.realm.ExpenseRealm;

import java.util.ArrayList;
import java.util.List;

import static israelbgf.gastei.core.utils.DateUtils.firstDayOf;
import static israelbgf.gastei.core.utils.DateUtils.lastDayOf;

public class ExpenseGatewayRealm implements ExpenseGateway {

    private Realm realm;

    public ExpenseGatewayRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void save(ExpenseEntity expense) {
        realm.beginTransaction();
        ExpenseRealm expenseRealm = realm.createObject(ExpenseRealm.class);
        expenseRealm.setAmount(expense.getAmount());
        expenseRealm.setDate(expense.getDate());
        expenseRealm.setPlace(expense.getPlace());
        realm.commitTransaction();
    }

    @Override
    public List<ExpenseEntity> retrieveByMonth(int year, int month) {
        RealmResults<ExpenseRealm> results = realm.where(ExpenseRealm.class)
                .between("date", firstDayOf(year, month), lastDayOf(year, month))
                .findAll();

        List<ExpenseEntity> expenses = new ArrayList<>();
        for (ExpenseRealm expenseRealm : results) {
            expenses.add(new ExpenseEntity(expenseRealm.getAmount(), expenseRealm.getPlace(), expenseRealm.getDate()));
        }

        return expenses;
    }

}
