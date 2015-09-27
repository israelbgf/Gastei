package israelbgf.gastei.core;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.ReceiveSMSUsecase;
import org.junit.Test;

import static israelbgf.gastei.core.utils.DateUtils.createDate;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ReceiveSMSUsecaseShould {

    private static final String VALID_NUMBER = "2788";
    public static final String SMS_CONTENT = "BRADESCO CARTOES:COMPRA APROVADA NO CARTAO FINAL 9999 EM " +
            "26/09/2015 13:46. VALOR DE $ 156,47 NO(A) GIASSI SUPERMERCADOS LOJAJOINVILLE";

    @Test
    public void parseAndStoreExpense() {
        ExpenseGateway gateway = mock(ExpenseGateway.class);
        ReceiveSMSUsecase usecase = new ReceiveSMSUsecase(gateway);

        usecase.execute(VALID_NUMBER, SMS_CONTENT);

        verify(gateway).save(new Expense(
                156.47,
                "GIASSI SUPERMERCADOS LOJAJOINVILLE",
                createDate(2015, 9, 26, 13, 46)));
    }


}
