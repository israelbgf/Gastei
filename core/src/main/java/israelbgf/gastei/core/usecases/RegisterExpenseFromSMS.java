package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.entities.SMSExpenseParser;
import israelbgf.gastei.core.gateways.ExpenseGateway;

public class RegisterExpenseFromSMS {

    public static final String BRADESCO_SMS_NUMBER = "27888";
    private final ExpenseGateway gateway;
    private Presenter presenter;

    public RegisterExpenseFromSMS(ExpenseGateway gateway, Presenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void receive(String phoneNumber, String smsContent) {
        if(isNotBradescoSMSNumber(phoneNumber))
            return;

        try {
            Expense newExpense = new SMSExpenseParser().parseExpenseFrom(smsContent);
            gateway.save(newExpense);
            presenter.presentNewExpenseAdded(newExpense);
        } catch (SMSExpenseParser.InvalidSMSException e) {
            presenter.presentInvalidSMSContent(smsContent);
        }
    }

    private boolean isNotBradescoSMSNumber(String phoneNumber) {
        return !phoneNumber.equals(BRADESCO_SMS_NUMBER);
    }


    public interface Presenter {

        void presentInvalidSMSContent(String invalidSmsContent);

        void presentNewExpenseAdded(Expense expenseAdded);
    }
}
