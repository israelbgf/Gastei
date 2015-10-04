package israelbgf.gastei.mobile.presenters;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.usecases.RegisterExpenseFromSMSUsecase;
import israelbgf.gastei.mobile.actvities.ExpenseManagementActivity;

public class RegisterExpenseFromSMSPresenter implements RegisterExpenseFromSMSUsecase.Presenter {
    private Context context;

    public RegisterExpenseFromSMSPresenter(Context context) {
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
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("There is a new expense!")
                        .setContentText("A new expense with the value of " + expenseAdded.getAmount());
        Intent intent = new Intent(context, ExpenseManagementActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(intent);

//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

}
