package israelbgf.gastei.mobile.presenters;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.usecases.ReceiveSMSUsecase;
import israelbgf.gastei.mobile.actvities.ExpenseManagementActivity;

public class ReceiveSMSPresenter implements ReceiveSMSUsecase.Presenter {
    private Context context;

    public ReceiveSMSPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void presentInvalidSMSContent(String invalidSmsContent) {
        Toast.makeText(context, "Problems!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void presentNewExpenseAdded(ExpenseEntity expenseAdded) {
        Notification.Builder notificationBuilder =
                new Notification.Builder(context)
                        .setContentTitle("There is a new expense!")
                        .setContentText("A new expense with the value of " + expenseAdded.getAmount());
        Intent resultIntent = new Intent(context, ExpenseManagementActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ExpenseManagementActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent((int) System.currentTimeMillis(), PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

}
