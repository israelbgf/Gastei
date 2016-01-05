package israelbgf.gastei.mobile.factories;

import android.content.Context;
import io.realm.Realm;
import israelbgf.gastei.core.usecases.RegisterExpenseFromSMSUsecase;
import israelbgf.gastei.core.utils.IDGenerator;
import israelbgf.gastei.mobile.gateways.realm.ExpenseGatewayRealm;
import israelbgf.gastei.mobile.presenters.RegisterExpenseFromSMSPresenter;

public class RegisterExpenseFromSMSUsecaseFactory {
    public static RegisterExpenseFromSMSUsecase make(Context context) {
        return new RegisterExpenseFromSMSUsecase(
                new ExpenseGatewayRealm(Realm.getInstance(context)),
                new RegisterExpenseFromSMSPresenter(context), new IDGenerator()
        );
    }
}
