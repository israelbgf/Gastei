package israelbgf.gastei.mobile.actvities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Locale;

public class ExpenseActivity extends Activity {

    final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    Expense expense;

    EditText amount, place, date;
    Switch shared;
    Button save, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        findViews();
        setupViews();
    }

    void findViews() {
        amount = (EditText) findViewById(R.id.amount);
        place = (EditText) findViewById(R.id.place);
        date = (EditText) findViewById(R.id.date);
        shared = (Switch) findViewById(R.id.shared);
        save = (Button) findViewById(R.id.save);
        delete = (Button) findViewById(R.id.delete);
    }

    private void setupViews() {
        expense = (Expense) getIntent().getSerializableExtra("expense");
        if (expense == null) {
            expense = new Expense();
            save.setText("Create");
            date.setText(dateFormatter.format(new Date()));
            delete.setVisibility(View.GONE);
        } else {
            save.setText("Save");
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

    public void delete(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Deleting Expense")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ExpenseGatewaySQLite gateway = ExpenseGatewaySQLiteFactory.make(ExpenseActivity.this);
                        gateway.delete(expense.getId());
                        Toast.makeText(ExpenseActivity.this, "Deleted! :)", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
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
