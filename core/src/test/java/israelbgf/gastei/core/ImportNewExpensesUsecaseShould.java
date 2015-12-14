package israelbgf.gastei.core;

import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.gateways.ExpenseFromSMSGateway;
import israelbgf.gastei.core.usecases.ImportNewExpensesUsecase;
import israelbgf.gastei.core.utils.IDGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static israelbgf.gastei.core.utils.DateUtils.date;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class ImportNewExpensesUsecaseShould {

    IDGenerator idGenerator = mock(IDGenerator.class);
    ExpenseFromSMSGateway expenseFromSmsGateway = mock(ExpenseFromSMSGateway.class);
    ExpenseGateway expenseGateway = mock(ExpenseGateway.class);
    ImportNewExpensesUsecase.Presenter presenter = mock(ImportNewExpensesUsecase.Presenter.class);
    ImportNewExpensesUsecase usecase = new ImportNewExpensesUsecase(idGenerator, expenseGateway, expenseFromSmsGateway, presenter);

    @Before
    public void before(){
        when(idGenerator.generate()).thenReturn("ID");
    }

    @Test
    public void presentNoExpensesToImportWhenThereAreNothingToImport() {
        usecase.importExisting(Collections.<String>emptyList());

        verify(presenter).nothingToImport();
    }

    @Test
    public void importOneExpenseWhenThereAreOneToImport() {
        final int ONE_EXPENSE = 1;
        when(expenseGateway.contains(anyExpense())).thenReturn(false);

        usecase.importExisting(asList(newSMS("ANGELONI")));

        verify(expenseGateway).save(newEntity("ANGELONI"));
        verify(presenter).imported(ONE_EXPENSE);
    }

    @Test
    public void importTwoExpenseWhenThereAreTwoToImport() {
        final int TWO_EXPENSES = 2;

        when(expenseGateway.contains(anyExpense())).thenReturn(false);

        usecase.importExisting(asList(
            newSMS("ANGELONI"),
            newSMS("GIASSI")
        ));

        verify(expenseGateway).save(newEntity("ANGELONI"));
        verify(expenseGateway).save(newEntity("GIASSI"));
        verify(presenter).imported(TWO_EXPENSES);
    }

    @Test
    public void doNotMakeDuplicatesWhenAlreadyHaveAnGivenExpense() {
        when(expenseGateway.contains(newEntity("ANGELONI")))
                .thenReturn(true);

        usecase.importExisting(asList(newSMS("ANGELONI")));

        verify(expenseGateway, never()).save(anyExpense());
        verify(presenter).nothingToImport();
    }

    @After
    public void after() {
        verifyNoMoreInteractions(presenter);
    }

    private ExpenseEntity anyExpense() {
        return any(ExpenseEntity.class);
    }

    private String newSMS(String place) {
        return "BRADESCO CARTOES:COMPRA APROVADA NO CARTAO FINAL 9999 EM 25/10/2015 13:46. VALOR DE $ 156,47 NO(A) " + place;
    }

    private ExpenseEntity newEntity(String place) {
        return new ExpenseEntity("ID", 156.47, place, date(2015, 10, 25, 13, 46), false);
    }


}
