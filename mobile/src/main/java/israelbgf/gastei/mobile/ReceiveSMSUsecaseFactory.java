package israelbgf.gastei.mobile;

import israelbgf.gastei.core.usecases.ReceiveSMSUsecase;

public class ReceiveSMSUsecaseFactory {
    public static ReceiveSMSUsecase make() {
        return new ReceiveSMSUsecase(null);
    }
}
