package israelbgf.gastei.mobile.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ShareExpenseReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "GO!!", Toast.LENGTH_LONG).show();
    }

}

