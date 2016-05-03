package israelbgf.gastei.mobile.actvities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import israelbgf.gastei.core.usecases.ListMonthlyExpenses;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.actvities.ExpenseActivity;
import israelbgf.gastei.mobile.actvities.MainActivity;
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
        MainActivity activity = (MainActivity) getActivity();

        currentDate = (MonthPicker) activity.findViewById(R.id.month_picker);
        currentDate.setOnMonthChangeListener(new MonthPicker.OnMonthChangeListener() {
            @Override
            public void onChange(int currentYear, int currentMonth) {
                listMonthlyUsecase.list(currentYear, currentMonth);
            }
        });

        View floatingButton = rootView.findViewById(R.id.floating_button);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ExpenseActivity.class));
            }
        });

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
