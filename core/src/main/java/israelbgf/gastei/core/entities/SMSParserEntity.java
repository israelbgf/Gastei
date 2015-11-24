package israelbgf.gastei.core.entities;

import israelbgf.gastei.core.utils.IDGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class SMSParserEntity {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());

    private IDGenerator idGenerator;

    public SMSParserEntity(IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public ExpenseEntity parseExpenseFrom(String message) {
        Pattern pattern = Pattern.compile(".*EM\\s(.*)\\$\\s(.*)\\sNO\\(A\\)\\s(.*)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String date = matcher.group(1);
            String amount = matcher.group(2).replace(",", ".");
            String local = matcher.group(3);

            try {
                return new ExpenseEntity(idGenerator.generate(), parseDouble(amount), local, formatter.parse(date), false);
            } catch (ParseException e) {
                throw new InvalidSMSException();
            }
        } else {
            throw new InvalidSMSException();
        }
    }


    public static class InvalidSMSException extends RuntimeException {

    }
}
