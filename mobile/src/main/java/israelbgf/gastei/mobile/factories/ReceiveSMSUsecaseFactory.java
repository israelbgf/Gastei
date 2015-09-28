package israelbgf.gastei.mobile.factories;

import android.content.Context;
import io.realm.Realm;
import israelbgf.gastei.core.usecases.ReceiveSMSUsecase;
import israelbgf.gastei.mobile.gateways.ExpenseGatewayRealm;
import israelbgf.gastei.mobile.presenters.ReceiveSMSPresenter;

public class ReceiveSMSUsecaseFactory {
    public static ReceiveSMSUsecase make(Context context) {
        return new ReceiveSMSUsecase(
                new ExpenseGatewayRealm(Realm.getInstance(context)),
                new ReceiveSMSPresenter(context)
        );
    }
}
