package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class RegisterExpenseFromSMSUsecase {

    public static final String BRADESCO_SMS_NUMBER = "27888";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private final ExpenseGateway gateway;
    private Presenter presenter;

    public RegisterExpenseFromSMSUsecase(ExpenseGateway gateway, Presenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void receive(String phoneNumber, String smsContent) {
        if(isNotBradescoSMSNumber(phoneNumber))
            return;

        try {
            ExpenseEntity newExpense = parseExpenseFrom(smsContent);
            gateway.save(newExpense);
            presenter.presentNewExpenseAdded(newExpense);
        } catch (InvalidSMSException e) {
            presenter.presentInvalidSMSContent(smsContent);
        }
    }

    private boolean isNotBradescoSMSNumber(String phoneNumber) {
        return !phoneNumber.equals(BRADESCO_SMS_NUMBER);
    }

    private ExpenseEntity parseExpenseFrom(String message) {
        Pattern pattern = Pattern.compile(".*EM\\s(.*)\\$\\s(.*)\\sNO\\(A\\)\\s(.*)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String date = matcher.group(1);
            String amount = matcher.group(2).replace(",", ".");
            String local = matcher.group(3);

            try {
                return new ExpenseEntity(parseDouble(amount), local, formatter.parse(date));
            } catch (ParseException e) {
                throw new InvalidSMSException();
            }
        } else {
            throw new InvalidSMSException();
        }
    }

    private static class InvalidSMSException extends RuntimeException {

    }

    public interface Presenter {

        void presentInvalidSMSContent(String invalidSmsContent);

        void presentNewExpenseAdded(ExpenseEntity expenseAdded);
    }
}
