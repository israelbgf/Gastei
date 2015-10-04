package israelbgf.gastei.mobile.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import israelbgf.gastei.mobile.factories.RegisterExpenseFromSMSUsecaseFactory;

public class SmsReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] rawMessages = (Object[]) bundle.get("pdus");
            parseSmsMessages(context, rawMessages);
        }
    }

    private void parseSmsMessages(Context context, Object[] rawMessages) {
        for (Object rawMessage : rawMessages) {
            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) rawMessage);
            String phoneNumber = currentMessage.getDisplayOriginatingAddress();
            String message = currentMessage.getDisplayMessageBody();

            RegisterExpenseFromSMSUsecaseFactory.make(context).receive(phoneNumber, message);
        }
    }
}

