package israelbgf.gastei.core.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.Double.parseDouble;

public class SMSExpenseParser {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());

    public Expense parseExpenseFrom(String smsContent) {
        return new Expense(
                parseAmount(smsContent),
                parsePlace(smsContent),
                parseDate(smsContent)
        );
    }

    private double parseAmount(String smsContent) {
        try {
            int amountStart = smsContent.indexOf("$") + 2;
            int amountEnd = smsContent.indexOf(" ", amountStart);
            String amount = smsContent.substring(amountStart, amountEnd);
            amount = amount.replace(".", "").replace(",", ".");
            return parseDouble(amount);
        } catch (Exception e) {
            throw new InvalidSMSException(smsContent, e);
        }
    }

    private String parsePlace(String smsContent) {
        int placeStart = smsContent.indexOf("NO(A)") + 5;
        String place = smsContent.substring(placeStart);
        place = removeDuplicatedWhiteSpaces(place);
        place = removeFinishingPeriod(place);
        return place.trim();
    }

    private String removeFinishingPeriod(String place) {
        if(place.charAt(place.length() - 1) == '.')
            place = place.substring(0, place.length() - 1);
        return place;
    }

    private String removeDuplicatedWhiteSpaces(String place) {
        place = place.replaceAll("\\s+", " ");
        return place;
    }

    private Date parseDate(String smsContent) {
        int dateStart = smsContent.indexOf("EM ") + 3;
        int dateEnd = dateStart + 16;
        try {
            return formatter.parse(smsContent.substring(dateStart, dateEnd));
        } catch (Exception e) {
            throw new InvalidSMSException(smsContent, e);
        }
    }

    public static class InvalidSMSException extends RuntimeException {

        private final String smsContent;

        public InvalidSMSException(String smsContent, Exception e) {
            super(e);
            this.smsContent = smsContent;
        }

        @Override
        public String toString() {
            return smsContent + "\n>>> " + super.toString();
        }
    }

}
