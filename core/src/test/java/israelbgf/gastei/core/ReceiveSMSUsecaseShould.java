package israelbgf.gastei.core;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.ReceiveSMSUsecase;
import org.junit.Test;

import static israelbgf.gastei.core.utils.DateUtils.createDate;
import static org.mockito.Mockito.*;

public class ReceiveSMSUsecaseShould {

    private static final String VALID_NUMBER = "2788";
    public static final String SMS_CONTENT = "BRADESCO CARTOES:COMPRA APROVADA NO CARTAO FINAL 9999 EM " +
            "26/09/2015 13:46. VALOR DE $ 156,47 NO(A) GIASSI SUPERMERCADOS LOJAJOINVILLE";
    public static final String INVALID_SMS_CONTENT = "MENSAGEM ERRADA";

    @Test
    public void storeExpenseWhenParseValidSMS() {
        ExpenseGateway gateway = mock(ExpenseGateway.class);
        ReceiveSMSUsecase.Presenter presenter = mock(ReceiveSMSUsecase.Presenter.class);
        ReceiveSMSUsecase usecase = new ReceiveSMSUsecase(gateway, presenter);

        usecase.execute(VALID_NUMBER, SMS_CONTENT);

        Expense expectedExpense = new Expense(
                156.47,
                "GIASSI SUPERMERCADOS LOJAJOINVILLE",
                createDate(2015, 9, 26, 13, 46));

        verify(presenter).presentNewExpenseAdded(expectedExpense);
        verify(gateway).save(expectedExpense);
        verifyNoMoreInteractions(gateway, presenter);
    }

    @Test
    public void notifyErrorWhenParseInvalidSMS() {
        ExpenseGateway gateway = mock(ExpenseGateway.class);
        ReceiveSMSUsecase.Presenter presenter = mock(ReceiveSMSUsecase.Presenter.class);
        ReceiveSMSUsecase usecase = new ReceiveSMSUsecase(gateway, presenter);

        usecase.execute(VALID_NUMBER, INVALID_SMS_CONTENT);

        verify(presenter).presentInvalidSMSContent(INVALID_SMS_CONTENT);
        verifyNoMoreInteractions(gateway, presenter);
    }


}
