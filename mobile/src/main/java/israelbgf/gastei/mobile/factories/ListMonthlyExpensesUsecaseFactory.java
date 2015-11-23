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
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm realmInstance = Realm.getInstance(realmConfiguration);

        return new ListMonthlyExpensesUsecase(
                new ExpenseGatewayRealm(realmInstance),
                new ListMonthlyExpensesUsecasePresenter(activity));
    }
}
