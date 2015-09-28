package israelbgf.gastei.mobile.factories;

import israelbgf.gastei.core.usecases.ReceiveSMSUsecase;

public class ReceiveSMSUsecaseFactory {
    public static ReceiveSMSUsecase make() {
        return new ReceiveSMSUsecase(null, null);
    }
}
