package israelbgf.gastei.mobile.factories;

import android.app.ListActivity;
import io.realm.Realm;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase;
import israelbgf.gastei.core.utils.DateUtils;
import israelbgf.gastei.mobile.gateways.ExpenseGatewayRealm;
import israelbgf.gastei.mobile.presenters.ListMonthlyExpensesUsecasePresenter;

import java.util.ArrayList;
import java.util.List;

public class ListMonthlyExpensesUsecaseFactory {
    public static ListMonthlyExpensesUsecase make(ListActivity listActivity) {
        return new ListMonthlyExpensesUsecase(
//                new ExpenseGatewayRealm(Realm.getInstance(listActivity.getApplicationContext())),
                new ExpenseGateway() {
                    @Override
                    public void save(ExpenseEntity expense) {

                    }

                    @Override
                    public List<ExpenseEntity> retrieveByMonth(int year, int month) {
                        List<ExpenseEntity> expenses = new ArrayList<>();
                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 3), false));
                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 23), false));
                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 23), false));
                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 24), false));
                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 24), false));
                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 25), false));
                        return expenses;
                    }

                    @Override
                    public void markExpenseAsShared(String existingExpenseId) {

                    }
                },
        new ListMonthlyExpensesUsecasePresenter(listActivity));
    }
}
