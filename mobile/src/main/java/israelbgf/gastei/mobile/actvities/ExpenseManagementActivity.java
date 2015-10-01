package israelbgf.gastei.mobile.actvities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.factories.ListMonthlyExpensesUsecaseFactory;


public class ExpenseManagementActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListMonthlyExpensesUsecase listMonthlyUsecase = ListMonthlyExpensesUsecaseFactory.make(this);
        listMonthlyUsecase.list(2015, 10);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_management, menu);
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
}
