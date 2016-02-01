package israelbgf.gastei.mobile.factories;

import android.content.Context;
import android.widget.Toast;
import israelbgf.gastei.core.usecases.MarkExpenseAsShared;

public class MarkExpenseAsSharedFactory {

    public static MarkExpenseAsShared make(final Context context) {
        return new MarkExpenseAsShared(ExpenseGatewaySQLiteFactory.make(context), new MarkExpenseAsShared.Presenter(){

            @Override
            public void presentExpenseShared() {
                Toast.makeText(context, "FOI SHAREADO", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
