package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.entities.SMSParserEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.utils.IDGenerator;

public class RegisterExpenseFromSMSUsecase {

    public static final String BRADESCO_SMS_NUMBER = "27888";
    private final ExpenseGateway gateway;
    private final IDGenerator idGenerator;
    private Presenter presenter;

    public RegisterExpenseFromSMSUsecase(ExpenseGateway gateway, Presenter presenter, IDGenerator idGenerator) {
        this.gateway = gateway;
        this.presenter = presenter;
        this.idGenerator = idGenerator;
    }

    public void receive(String phoneNumber, String smsContent) {
        if(isNotBradescoSMSNumber(phoneNumber))
            return;

        try {
            ExpenseEntity newExpense = new SMSParserEntity(idGenerator).parseExpenseFrom(smsContent);
            gateway.save(newExpense);
            presenter.presentNewExpenseAdded(newExpense);
        } catch (SMSParserEntity.InvalidSMSException e) {
            presenter.presentInvalidSMSContent(smsContent);
        }
    }

    private boolean isNotBradescoSMSNumber(String phoneNumber) {
        return !phoneNumber.equals(BRADESCO_SMS_NUMBER);
    }


    public interface Presenter {

        void presentInvalidSMSContent(String invalidSmsContent);

        void presentNewExpenseAdded(ExpenseEntity expenseAdded);
    }
}
