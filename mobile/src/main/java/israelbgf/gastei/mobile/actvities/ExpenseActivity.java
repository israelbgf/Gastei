package israelbgf.gastei.mobile.actvities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import israelbgf.gastei.mobile.R;

public class ExpenseActivity extends Activity{

    EditText amount;
    EditText place;
    EditText date;
    Switch shared;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense);
        createFields();
    }

    void createFields() {
        amount = (EditText) findViewById(R.id.amount);
        place = (EditText) findViewById(R.id.place);
        date = (EditText) findViewById(R.id.date);
        shared = (Switch) findViewById(R.id.shared);
        save = (Button) findViewById(R.id.save);
    }

    public void save(View v) {
        System.out.println(amount.getText());
        System.out.println(place.getText());
        System.out.println(date.getText());
        System.out.println(shared.isChecked());
    }

}
