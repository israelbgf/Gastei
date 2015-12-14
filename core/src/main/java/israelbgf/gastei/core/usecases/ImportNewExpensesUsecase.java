package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.entities.SMSParserEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.gateways.ExpenseFromSMSGateway;
import israelbgf.gastei.core.utils.IDGenerator;

import java.util.List;

import static israelbgf.gastei.core.utils.DateUtils.date;

public class ImportNewExpensesUsecase {
    private ExpenseGateway expenseGateway;
    private final ExpenseFromSMSGateway expenseFromSmsGateway;
    private final Presenter presenter;
    private IDGenerator idGenerator;

    public ImportNewExpensesUsecase(IDGenerator idGenerator, ExpenseGateway expenseGateway, ExpenseFromSMSGateway expenseFromSmsGateway, Presenter presenter) {
        this.idGenerator = idGenerator;
        this.expenseGateway = expenseGateway;
        this.expenseFromSmsGateway = expenseFromSmsGateway;
        this.presenter = presenter;
    }

    public void importExisting(List<String> smssToParse) {
        int importedQuantity = 0;

        if(!smssToParse.isEmpty()){
            for(String sms : smssToParse){
                ExpenseEntity expense = parse(sms);
                if(!expenseGateway.contains(expense)) {
                    expenseGateway.save(expense);
                    importedQuantity++;
                }
            }
        }

        if(importedQuantity == 0)
            presenter.nothingToImport();
        else
            presenter.imported(importedQuantity);
    }

    private ExpenseEntity parse(String sms) {
        return new SMSParserEntity(idGenerator).parseExpenseFrom(sms);
    }

    public interface Presenter {
        void nothingToImport();

        void imported(int quantity);
    }
}
