package israelbgf.gastei.mobile.actvities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.factories.ExpenseGatewaySQLiteFactory;
import israelbgf.gastei.mobile.gateways.sqlite.ExpenseGatewaySQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseActivity extends Activity {

    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    Expense expense;

    EditText amount;
    EditText place;
    EditText date;
    Switch shared;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense);
        findViews();
        setupViews();
    }

    void findViews() {
        amount = (EditText) findViewById(R.id.amount);
        place = (EditText) findViewById(R.id.place);
        date = (EditText) findViewById(R.id.date);
        shared = (Switch) findViewById(R.id.shared);
        save = (Button) findViewById(R.id.save);
    }

    private void setupViews() {
        expense = (Expense) getIntent().getSerializableExtra("expense");
        if (expense == null) {
            expense = new Expense();
            date.setText(dateFormatter.format(new Date()));
        } else {
            amount.setText(String.valueOf(expense.getAmount()));
            place.setText(expense.getPlace());
            date.setText(dateFormatter.format(expense.getDate()));
            shared.setChecked(expense.isShared());
        }

    }

    public void save(View v) {
        try {
            fillExpenseFromForm();
            ExpenseGatewaySQLite gateway = ExpenseGatewaySQLiteFactory.make(this);
            gateway.save(expense);
            Toast.makeText(this, "Created! :)", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
        }
    }

    private void fillExpenseFromForm() {
        boolean hasErrors = false;
        try {
            expense.setAmount(Double.valueOf(amount.getText().toString()));
        } catch (Exception e) {
            amount.setError("Invalid amount.");
            hasErrors = true;
        }

        if (place.getText().toString().isEmpty()) {
            place.setError("Place is required.");
            hasErrors = true;
        } else
            expense.setPlace(place.getText().toString());

        try {
            expense.setDate(dateFormatter.parse(date.getText().toString()));
        } catch (ParseException e) {
            date.setError("Invalid date.");
            hasErrors = true;
        }

        expense.setShared(shared.isChecked());

        if (hasErrors)
            throw new RuntimeException();

    }

}
