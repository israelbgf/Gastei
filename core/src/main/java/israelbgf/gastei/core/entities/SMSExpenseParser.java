package israelbgf.gastei.core.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class SMSExpenseParser {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());

    public Expense parseExpenseFrom(String message) {
        Pattern pattern = Pattern.compile(".*EM\\s(.*)\\$\\s(.*)\\sNO\\(A\\)\\s(.*)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String date = matcher.group(1);
            String amount = matcher.group(2).replace(",", ".");
            String local = matcher.group(3);

            try {
                return new Expense(parseDouble(amount), local, formatter.parse(date), false);
            } catch (ParseException e) {
                throw new InvalidSMSException(message);
            }
        } else {
            throw new InvalidSMSException(message);
        }
    }


    public static class InvalidSMSException extends RuntimeException {

        public InvalidSMSException(String message) {
            super(message);
        }
    }
}
