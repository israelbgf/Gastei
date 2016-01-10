package israelbgf.gastei.mobile.presenters;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import israelbgf.gastei.core.usecases.ImportNewExpenses;

import static android.widget.Toast.LENGTH_LONG;
import static israelbgf.gastei.core.usecases.RegisterExpenseFromSMS.BRADESCO_SMS_NUMBER;
import static java.lang.String.format;

public class ImportNewExpensesPresenter implements ImportNewExpenses.Presenter {
    private Context context;

    public ImportNewExpensesPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void parsingProblem(String invalidSMS) {
        Log.e("ParsingProblem", invalidSMS);
        Toast.makeText(context, format("Invalid SMS: " + invalidSMS, BRADESCO_SMS_NUMBER), LENGTH_LONG).show();

    }

    @Override
    public void nothingToImport() {
        Toast.makeText(context,
                format("No expenses to be imported (SMS Number: %s)", BRADESCO_SMS_NUMBER), LENGTH_LONG).show();
    }

    @Override
    public void imported(int quantity) {
        Toast.makeText(context,
                format("Imported %s SMSs", quantity), LENGTH_LONG).show();
    }
}
