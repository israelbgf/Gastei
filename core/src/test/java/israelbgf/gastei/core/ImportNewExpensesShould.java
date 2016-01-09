package israelbgf.gastei.core;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.usecases.ImportNewExpenses;
import org.junit.After;
import org.junit.Test;

import java.util.Collections;

import static israelbgf.gastei.core.utils.DateUtils.date;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ImportNewExpensesShould {

    ExpenseGateway expenseGateway = mock(ExpenseGateway.class);
    ImportNewExpenses.Presenter presenter = mock(ImportNewExpenses.Presenter.class);
    ImportNewExpenses usecase = new ImportNewExpenses(expenseGateway, presenter);

    @Test
    public void presentNoExpensesToImportWhenThereAreNothingToImport() {
        usecase.importExisting(Collections.<String>emptyList());

        verify(presenter).nothingToImport();
    }

    @Test
    public void importOneExpenseWhenThereAreOneToImport() {
        final int ONE_EXPENSE = 1;
        when(expenseGateway.contains(anyExpense())).thenReturn(false);

        usecase.importExisting(asList(SMS("ANGELONI")));

        verify(expenseGateway).save(entity("ANGELONI"));
        verify(presenter).imported(ONE_EXPENSE);
    }

    @Test
    public void importTwoExpenseWhenThereAreTwoToImport() {
        final int TWO_EXPENSES = 2;

        when(expenseGateway.contains(anyExpense())).thenReturn(false);

        usecase.importExisting(asList(
            SMS("ANGELONI"),
            SMS("GIASSI")
        ));

        verify(expenseGateway).save(entity("ANGELONI"));
        verify(expenseGateway).save(entity("GIASSI"));
        verify(presenter).imported(TWO_EXPENSES);
    }

    @Test
    public void doNotMakeDuplicatesWhenAlreadyHaveAnGivenExpense() {
        when(expenseGateway.contains(entity("ANGELONI")))
                .thenReturn(true);

        usecase.importExisting(asList(SMS("ANGELONI")));

        verify(expenseGateway, never()).save(anyExpense());
        verify(presenter).nothingToImport();
    }

    @Test
    public void ignoreErrorsButStillKeepImporting() {
        when(expenseGateway.contains(anyExpense())).thenReturn(false);

        String firstInvalidSMS = invalidSMS("ERROR-1");
        String secondInvalidSMS = invalidSMS("ERROR-2");

        usecase.importExisting(asList(
                firstInvalidSMS,
                SMS("ANGELONI"),
                secondInvalidSMS));

        verify(expenseGateway).save(entity("ANGELONI"));
        verify(presenter).parsingProblem(firstInvalidSMS);
        verify(presenter).parsingProblem(secondInvalidSMS);
        verify(presenter).imported(1);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(presenter);
    }

    private Expense anyExpense() {
        return any(Expense.class);
    }

    private String invalidSMS(String smsContent) {
        return smsContent;
    }

    private String SMS(String place) {
        return "BRADESCO CARTOES:COMPRA APROVADA NO CARTAO FINAL 9999 EM 25/10/2015 13:46. VALOR DE $ 156,47 NO(A) " + place;
    }

    private Expense entity(String place) {
        return new Expense(156.47, place, date(2015, 10, 25, 13, 46), false);
    }


}
