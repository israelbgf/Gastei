package israelbgf.gastei.mobile.presenters;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.usecases.MarkExpenseAsShared;
import israelbgf.gastei.core.usecases.RegisterExpenseFromSMS;
import israelbgf.gastei.mobile.actvities.ExpenseManagementActivity;

public class RegisterExpenseFromSMSPresenter implements RegisterExpenseFromSMS.Presenter {
    private Context context;

    public RegisterExpenseFromSMSPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void presentInvalidSMSContent(String invalidSmsContent) {
        Toast.makeText(context, "Problems!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void presentNewExpenseAdded(Expense expenseAdded) {
        Notification.Builder notificationBuilder =
                new Notification.Builder(context)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("There is a new expense!")
                        .setPriority(Notification.PRIORITY_MAX)
                        .setWhen(0)
                        .addAction(android.R.drawable.ic_menu_send, "Share this one", createShareIntent(expenseAdded))
                        .setContentText("A new expense with the value of " + expenseAdded.getAmount());

        notificationBuilder.setContentIntent(createShowDetailsIntent());

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(generateRequestCode(), notificationBuilder.build());
    }

    private PendingIntent createShowDetailsIntent() {
        Intent intent = new Intent(context, ExpenseManagementActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(intent);
        return stackBuilder.getPendingIntent(generateRequestCode(), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent createShareIntent(Expense expense) {
        Intent intent = new Intent(context, ExpenseManagementActivity.class);
        intent.setAction(MarkExpenseAsShared.class.getName());
        intent.putExtra("EXPENSE_ID", expense.getId());

        return PendingIntent.getActivity(context, generateRequestCode(), intent,
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    private int generateRequestCode() {
        return ((Long)System.currentTimeMillis()).intValue();
    }

}
