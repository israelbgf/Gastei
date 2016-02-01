package israelbgf.gastei.mobile.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import israelbgf.gastei.mobile.factories.MarkExpenseAsSharedFactory;

public class MarkAsSharedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long expenseId = intent.getLongExtra("EXPENSE_ID", 0);
        MarkExpenseAsSharedFactory.make(context).mark(expenseId);
    }

}
