package israelbgf.gastei.mobile.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import israelbgf.gastei.mobile.factories.ToggleExpenseSharedStatusFactory;

public class MarkAsSharedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = intent.getIntExtra("NOTIFICATION_ID", 0);
        notificationManager.cancel(notificationId);

        long expenseId = intent.getLongExtra("EXPENSE_ID", 0);
        ToggleExpenseSharedStatusFactory.make(context).toggle(expenseId);
    }

}
