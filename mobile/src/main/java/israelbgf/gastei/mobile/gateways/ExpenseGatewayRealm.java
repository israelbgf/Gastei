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
        expenseRealm.setId(expense.getId());
        expenseRealm.setAmount(expense.getAmount());
        expenseRealm.setDate(expense.getDate());
        expenseRealm.setPlace(expense.getPlace());
        expenseRealm.setShared(expense.isShared());
        realm.commitTransaction();
    }

    @Override
    public List<ExpenseEntity> retrieveByMonth(int year, int month) {
        RealmResults<ExpenseRealm> results = realm.where(ExpenseRealm.class)
                .between("date", firstDayOf(year, month), lastDayOf(year, month))
                .findAll();

        List<ExpenseEntity> expenses = new ArrayList<>();
        for (ExpenseRealm expenseRealm : results) {
            expenses.add(new ExpenseEntity(expenseRealm.getId(), expenseRealm.getAmount(), expenseRealm.getPlace(), expenseRealm.getDate(), expenseRealm.isShared()));
        }

        return expenses;
    }

    @Override
    public void markExpenseAsShared(String existingExpenseId) {
        ExpenseRealm expense = realm.where(ExpenseRealm.class)
            .equalTo("id", existingExpenseId)
            .findFirst();

        realm.beginTransaction();
        expense.setShared(true);
        realm.commitTransaction();
    }

    @Override
    public boolean contains(ExpenseEntity candidate) {
        return realm.where(ExpenseRealm.class)
                .equalTo("place", candidate.getPlace())
                .equalTo("amount", candidate.getAmount())
                .equalTo("date", candidate.getDate())
                .count() > 0;
    }

}
