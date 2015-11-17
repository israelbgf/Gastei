package israelbgf.gastei.mobile.factories;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase;
import israelbgf.gastei.mobile.actvities.ExpenseManagementActivity;
import israelbgf.gastei.mobile.gateways.ExpenseGatewayRealm;
import israelbgf.gastei.mobile.presenters.ListMonthlyExpensesUsecasePresenter;

public class ListMonthlyExpensesUsecaseFactory {
    public static ListMonthlyExpensesUsecase make(ExpenseManagementActivity activity) {

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(activity)
//                .schemaVersion(4)
//                .migration(new Migration())
//                .deleteRealmIfMigrationNeeded()
                .build();

        return new ListMonthlyExpensesUsecase(
                new ExpenseGatewayRealm(Realm.getInstance(realmConfiguration)),
//                new ExpenseGateway() {
//                    @Override
//                    public void save(ExpenseEntity expense) {
//
//                    }
//
//                    @Override
//                    public List<ExpenseEntity> retrieveByMonth(int year, int month) {
//                        List<ExpenseEntity> expenses = new ArrayList<>();
//                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 3), false));
//                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 23), false));
//                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 23), false));
//                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 24), false));
//                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 24), false));
//                        expenses.add(new ExpenseEntity("1", 5d, "MARLENE", DateUtils.date(2015, 10, 25), false));
//                        return expenses;
//                    }
//
//                    @Override
//                    public void markExpenseAsShared(String existingExpenseId) {
//
//                    }
//                },
                new ListMonthlyExpensesUsecasePresenter(activity));
    }
}
