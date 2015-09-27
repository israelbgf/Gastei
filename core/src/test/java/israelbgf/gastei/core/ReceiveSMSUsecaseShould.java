package israelbgf.gastei.core;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.ReceiveSMSUsecase;
import org.junit.After;
import org.junit.Test;

import static israelbgf.gastei.core.utils.ExpenseFactory.sampleExpense;
import static org.mockito.Mockito.*;

public class ReceiveSMSUsecaseShould {

    private static final String INVALID_NUMBER = "9999";


    public static final String SMS_CONTENT = "BRADESCO CARTOES:COMPRA APROVADA NO CARTAO FINAL 9999 EM " +
            "26/09/2015 13:46. VALOR DE $ 156,47 NO(A) GIASSI SUPERMERCADOS LOJAJOINVILLE";
    public static final String INVALID_SMS_CONTENT = "MENSAGEM ERRADA";

    ExpenseGateway gateway = mock(ExpenseGateway.class);
    ReceiveSMSUsecase.Presenter presenter = mock(ReceiveSMSUsecase.Presenter.class);
    ReceiveSMSUsecase usecase = new ReceiveSMSUsecase(gateway, presenter);

    @Test
    public void storeExpenseWhenParseValidSMS() {
        usecase.receive(ReceiveSMSUsecase.BRADESCO_SMS_NUMBER, SMS_CONTENT);

        Expense expectedExpense = sampleExpense();
        verify(presenter).presentNewExpenseAdded(expectedExpense);
        verify(gateway).save(expectedExpense);
    }

    @Test
    public void notifyErrorWhenParseInvalidSMS() {
        usecase.receive(ReceiveSMSUsecase.BRADESCO_SMS_NUMBER, INVALID_SMS_CONTENT);

        verify(presenter).presentInvalidSMSContent(INVALID_SMS_CONTENT);
    }

    @Test
    public void ignoreWhenInvalidNumber() {
        usecase.receive(INVALID_NUMBER, null);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(gateway, presenter);
    }


}
