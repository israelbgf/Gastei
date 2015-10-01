package israelbgf.gastei.mobile.factories;

import android.app.ListActivity;
import io.realm.Realm;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase;
import israelbgf.gastei.mobile.gateways.ExpenseGatewayRealm;
import israelbgf.gastei.mobile.presenters.ListMonthlyExpensesUsecasePresenter;

public class ListMonthlyExpensesUsecaseFactory {
    public static ListMonthlyExpensesUsecase make(ListActivity listActivity) {
        return new ListMonthlyExpensesUsecase(
                new ExpenseGatewayRealm(Realm.getInstance(listActivity.getApplicationContext())),
                new ListMonthlyExpensesUsecasePresenter(listActivity));
    }
}
