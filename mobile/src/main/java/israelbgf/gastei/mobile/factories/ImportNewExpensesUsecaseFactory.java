package israelbgf.gastei.mobile.factories;

import android.content.Context;
import io.realm.Realm;
import israelbgf.gastei.core.usecases.ImportNewExpensesUsecase;
import israelbgf.gastei.core.utils.IDGenerator;
import israelbgf.gastei.mobile.gateways.ExpenseGatewayRealm;
import israelbgf.gastei.mobile.presenters.ImportNewExpensesPresenter;

public class ImportNewExpensesUsecaseFactory {
    public static ImportNewExpensesUsecase make(Context context) {
        return new ImportNewExpensesUsecase(
                new IDGenerator(),
                new ExpenseGatewayRealm(Realm.getInstance(context)),
                new ImportNewExpensesPresenter(context)
        );
    }
}
