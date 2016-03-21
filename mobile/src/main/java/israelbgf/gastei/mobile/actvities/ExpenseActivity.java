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

    EditText amount;
    EditText place;
    EditText date;
    Switch shared;
    Button save;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense);
        setupFields();
    }

    void setupFields() {
        amount = (EditText) findViewById(R.id.amount);
        place = (EditText) findViewById(R.id.place);
        date = (EditText) findViewById(R.id.date);
        shared = (Switch) findViewById(R.id.shared);
        save = (Button) findViewById(R.id.save);

        date.setText(dateFormatter.format(new Date()));
    }

    public void save(View v) {
        try {
            Expense expense = parseExpense();
            ExpenseGatewaySQLite gateway = ExpenseGatewaySQLiteFactory.make(this);
            gateway.save(expense);
            Toast.makeText(this, "Created! :)", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
        }
    }

    private Expense parseExpense() {
        boolean hasErrors = false;
        Expense expense = new Expense();
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

        return expense;
    }

}
