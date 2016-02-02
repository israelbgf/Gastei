package israelbgf.gastei.mobile.factories;

import android.content.Context;
import israelbgf.gastei.core.usecases.MarkExpenseAsShared;

public class MarkExpenseAsSharedFactory {

    public static MarkExpenseAsShared make(final Context context) {
        return make(context, new MarkExpenseAsShared.Presenter() {
            @Override
            public void presentExpenseShared() {}
        });
    }

    public static MarkExpenseAsShared make(final Context context, MarkExpenseAsShared.Presenter presenter) {
        return new MarkExpenseAsShared(ExpenseGatewaySQLiteFactory.make(context), presenter);
    }
}
