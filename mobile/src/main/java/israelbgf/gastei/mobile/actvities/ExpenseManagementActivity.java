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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListMonthlyExpensesUsecase listMonthlyUsecase = ListMonthlyExpensesUsecaseFactory.make(this);

        Date today = new Date();
        listMonthlyUsecase.list(yearOf(today), monthOf(today));
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
                Toast.makeText(this, "Refreshing data...", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_reimport:
                Toast.makeText(this, "Re-importing data from Bradesco SMSs", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        final Calendar calendar = Calendar.getInstance();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            this.calendar.set(year, month, day);

            ListMonthlyExpensesUsecase listMonthlyUsecase = ListMonthlyExpensesUsecaseFactory.make((ExpenseManagementActivity) getActivity());
            listMonthlyUsecase.list(year, month);
            TextView monthView = (TextView) getActivity().findViewById(R.id.date_picker_button);
            monthView.setText(getFormattedDate(year, month));
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
