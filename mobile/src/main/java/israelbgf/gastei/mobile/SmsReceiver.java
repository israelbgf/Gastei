package israelbgf.gastei.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] rawMessages = (Object[]) bundle.get("pdus");
            parseSmsMessages(rawMessages);
        }
    }

    private void parseSmsMessages(Object[] rawMessages) {
        for (Object rawMessage : rawMessages) {
            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) rawMessage);
            String phoneNumber = currentMessage.getDisplayOriginatingAddress();
            String message = currentMessage.getDisplayMessageBody();

            ReceiveSMSUsecaseFactory.make().receive(phoneNumber, message);
        }
    }
}

