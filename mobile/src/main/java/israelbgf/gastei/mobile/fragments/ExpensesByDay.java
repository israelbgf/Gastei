package israelbgf.gastei.mobile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import israelbgf.gastei.core.usecases.ImportNewExpenses;
import israelbgf.gastei.core.usecases.ListMonthlyExpenses;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.factories.ImportNewExpensesFactory;
import israelbgf.gastei.mobile.factories.ListMonthlyExpensesFactory;
import israelbgf.gastei.mobile.widgets.SwipeableRelativeLayout;

import java.util.Date;

import static israelbgf.gastei.core.utils.DateUtils.monthOf;
import static israelbgf.gastei.core.utils.DateUtils.yearOf;

public class ExpensesByDay extends Fragment implements SwipeableRelativeLayout.SwipeListener {

    TextView currentDate;

    ListMonthlyExpenses listMonthlyUsecase;
    ImportNewExpenses importNewExpensesUsecase;

    int chosenYear = yearOf(new Date());
    int chosenMonth = monthOf(new Date());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMonthlyUsecase = ListMonthlyExpensesFactory.make(getActivity());
        importNewExpensesUsecase = ImportNewExpensesFactory.make(getActivity());
    }



    @Override
    public void onStart() {
        super.onStart();
        listMonthlyExpenses();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_expense_management, menu);
//        MenuItem item = menu.findItem(R.id.date_picker);
//        currentDate = (TextView) item.getActionView().findViewById(R.id.date_picker_button);
//        updateDateOnActionBar();
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_add:
//                startActivity(new Intent(this, ExpenseActivity.class));
//                return true;
//            case R.id.action_refresh:
//                listMonthlyExpenses();
//                return true;
//            case R.id.action_reimport:
//                importNewExpensesUsecase.importExisting(messagesFromPhone());
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

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
//        currentDate.setText(formattedDate(chosenYear, chosenMonth));
    }

    private void nextMonth() {
        if (chosenMonth > 12) {
            chosenYear++;
            chosenMonth = 1;
        } else {
            chosenMonth++;
        }
    }

    private void previousMonth() {
        if (chosenMonth < 1) {
            chosenYear--;
            chosenMonth = 12;
        } else {
            chosenMonth--;
        }
    }

    private void listMonthlyExpenses() {
        listMonthlyUsecase.list(chosenYear, chosenMonth);

    }


    public ExpensesByDay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.expense_list, container, false);

        ((SwipeableRelativeLayout) rootView.findViewById(R.id.main_layout)).setOnSwipeListener(this);


        return rootView;
    }

}
