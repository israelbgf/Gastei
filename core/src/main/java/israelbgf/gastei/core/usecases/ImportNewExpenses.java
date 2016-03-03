package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.entities.SMSExpenseParser;
import israelbgf.gastei.core.gateways.ExpenseGateway;

import java.util.List;


public class ImportNewExpenses {
    private ExpenseGateway expenseGateway;
    private final Presenter presenter;

    public ImportNewExpenses(ExpenseGateway expenseGateway, Presenter presenter) {
        this.expenseGateway = expenseGateway;
        this.presenter = presenter;
    }

    public void importExisting(List<String> smssToParse) {
        int importedQuantity = 0;

        if(!smssToParse.isEmpty()){
            for(String sms : smssToParse){
                try {
                    Expense expense = parse(sms);
                    if(!expenseGateway.contains(expense)) {
                        expenseGateway.save(expense);
                        importedQuantity++;
                    }
                } catch (SMSExpenseParser.InvalidSMSException e) {
                    presenter.parsingProblem(e.getSmsContent());
                }
            }
        }

        if(importedQuantity == 0)
            presenter.nothingToImport();
        else
            presenter.imported(importedQuantity);
    }

    private Expense parse(String sms) {
        return new SMSExpenseParser().parseExpenseFrom(sms);
    }

    public interface Presenter {

        void parsingProblem(String invalidSMS);

        void nothingToImport();

        void imported(int quantity);
    }
}
