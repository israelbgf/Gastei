package israelbgf.gastei.mobile.actvities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import israelbgf.gastei.core.usecases.ImportNewExpenses;
import israelbgf.gastei.core.usecases.ListMonthlyExpenses;
import israelbgf.gastei.core.usecases.RegisterExpenseFromSMS;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.factories.ImportNewExpensesFactory;
import israelbgf.gastei.mobile.factories.ListMonthlyExpensesFactory;
import israelbgf.gastei.mobile.widgets.SwipeableRelativeLayout;

import java.util.*;

import static israelbgf.gastei.core.utils.DateUtils.monthOf;
import static israelbgf.gastei.core.utils.DateUtils.yearOf;


public class ExpenseManagementActivity extends Activity implements SwipeableRelativeLayout.SwipeListener {

    TextView currentDate;

    ListMonthlyExpenses listMonthlyUsecase;
    ImportNewExpenses importNewExpensesUsecase;

    int chosenYear = yearOf(new Date());
    int chosenMonth = monthOf(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupVisualEffectsForActivityTransitions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_list);
        ((SwipeableRelativeLayout)findViewById(R.id.main_layout)).setOnSwipeListener(this);

        listMonthlyUsecase  = ListMonthlyExpensesFactory.make(this);
        importNewExpensesUsecase = ImportNewExpensesFactory.make(this);
    }

    private void setupVisualEffectsForActivityTransitions() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
    }

    @Override
    protected void onStart() {
        super.onStart();
        listMonthlyExpenses();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_management, menu);
        MenuItem item = menu.findItem(R.id.date_picker);
        currentDate = (TextView) item.getActionView().findViewById(R.id.date_picker_button);
        updateDateOnActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(this, ExpenseActivity.class));
                return true;
            case R.id.action_refresh:
                listMonthlyExpenses();
                return true;
            case R.id.action_reimport:
                importNewExpensesUsecase.importExisting(messagesFromPhone());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSwipeRightToLeft() {
        nextMonth();
        updateDateOnActionBar();
        listMonthlyExpenses();
    }


    @Override
    public void onSwipeLeftToRight() {
        previousMonth();
        updateDateOnActionBar();
        listMonthlyExpenses();
    }

    private void updateDateOnActionBar() {
        currentDate.setText(formattedDate(chosenYear, chosenMonth));
    }

    private void nextMonth() {
        if(chosenMonth > 12){
            chosenYear++;
            chosenMonth = 1;
        }else{
            chosenMonth++;
        }
    }

    private void previousMonth() {
        if(chosenMonth < 1){
            chosenYear--;
            chosenMonth = 12;
        }else{
            chosenMonth--;
        }
    }

    private void listMonthlyExpenses() {
        listMonthlyUsecase.list(chosenYear, chosenMonth);

    }

    private List<String> messagesFromPhone() {
        String[] selection = new String[] { RegisterExpenseFromSMS.BRADESCO_SMS_NUMBER };
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://sms/inbox"), new String[] {"body"}, "address=?", selection, null);

        List<String> messages = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                messages.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return messages;
    }

    private static String formattedDate(int year, int month) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);

        String monthDisplay = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        return monthDisplay + "/" + year;
    }

}
