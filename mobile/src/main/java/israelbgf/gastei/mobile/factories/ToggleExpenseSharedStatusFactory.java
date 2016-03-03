package israelbgf.gastei.mobile.factories;

import android.content.Context;
import israelbgf.gastei.core.usecases.ToggleExpenseSharedStatus;

public class ToggleExpenseSharedStatusFactory {

    public static ToggleExpenseSharedStatus make(final Context context) {
        return make(context, new ToggleExpenseSharedStatus.Presenter() {
            @Override
            public void presentUpdatedExpense() {}
        });
    }

    public static ToggleExpenseSharedStatus make(final Context context, ToggleExpenseSharedStatus.Presenter presenter) {
        return new ToggleExpenseSharedStatus(ExpenseGatewaySQLiteFactory.make(context), presenter);
    }
}
