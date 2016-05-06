package israelbgf.gastei.mobile.actvities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import israelbgf.gastei.core.usecases.ListMonthlyExpenses;
import israelbgf.gastei.core.values.Month;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.actvities.ExpenseActivity;
import israelbgf.gastei.mobile.factories.ListMonthlyExpensesFactory;
import israelbgf.gastei.mobile.widgets.MonthPicker;

public class ExpensesByDay extends Fragment {

    ListMonthlyExpenses listMonthlyUsecase;
    MonthPicker currentDate;

    public ExpensesByDay() {
        super();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMonthlyUsecase = ListMonthlyExpensesFactory.make(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_expenses_by_day, container, false);

        currentDate = (MonthPicker) rootView.findViewById(R.id.month_picker);
        currentDate.setOnMonthChangeListener(new MonthPicker.OnMonthChangeListener() {
            @Override
            public void onChange(Month month) {
                listMonthlyUsecase.list(month.year, month.month);
            }
        });

        View floatingButton = rootView.findViewById(R.id.floating_button);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ExpenseActivity.class));
            }
        });

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.view_toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        currentDate.setCurrentMonth(
                currentDate.getCurrentYear(),
                currentDate.getCurrentMonth()
        );
    }

}
