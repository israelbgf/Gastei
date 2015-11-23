package israelbgf.gastei.mobile.actvities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.factories.ListMonthlyExpensesUsecaseFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static israelbgf.gastei.core.utils.DateUtils.monthOf;
import static israelbgf.gastei.core.utils.DateUtils.yearOf;


public class ExpenseManagementActivity extends Activity {

    ListMonthlyExpensesUsecase listMonthlyUsecase;

    int chosenYear = yearOf(new Date());
    int chosenMonth = monthOf(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMonthlyUsecase  = ListMonthlyExpensesUsecaseFactory.make(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listMonthlyExpenses();
    }

    private void listMonthlyExpenses() {
        listMonthlyUsecase.list(chosenYear, chosenMonth);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_management, menu);
        MenuItem item = menu.findItem(R.id.date_picker);
        TextView datePicker = (TextView) item.getActionView().findViewById(R.id.date_picker_button);

        final DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });

        Date today = new Date();
        String formattedDate = DatePickerFragment.getFormattedDate(yearOf(today), monthOf(today));
        datePicker.setText(formattedDate);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                listMonthlyExpenses();
                return true;
            case R.id.action_reimport:
                Toast.makeText(this, "Re-importing data from Bradesco SMSs", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            ExpenseManagementActivity activity = getExpenseManagementActivity();
            return new DatePickerDialog(activity, this, activity.chosenYear, activity.chosenMonth, 1);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            ExpenseManagementActivity activity = getExpenseManagementActivity();
            activity.chosenYear = year;
            activity.chosenMonth = month;

            activity.listMonthlyExpenses();

            TextView monthView = (TextView) getActivity().findViewById(R.id.date_picker_button);
            monthView.setText(getFormattedDate(activity.chosenYear, activity.chosenMonth));
        }

        private ExpenseManagementActivity getExpenseManagementActivity() {
            return (ExpenseManagementActivity) getActivity();
        }

        public static String getFormattedDate(int year, int month) {
            final Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);

            String monthDisplay = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
            return monthDisplay + "/" + year;
        }
    }

}
