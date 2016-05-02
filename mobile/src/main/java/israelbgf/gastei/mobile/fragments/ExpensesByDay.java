package israelbgf.gastei.mobile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import israelbgf.gastei.core.usecases.ListMonthlyExpenses;
import israelbgf.gastei.mobile.R;
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

        View rootView = inflater.inflate(R.layout.expense_list, container, false);

        MainActivity activity = (MainActivity) getActivity();
        currentDate = (MonthPicker) activity.findViewById(R.id.month_picker);
        currentDate.setOnMonthChangeListener(new MonthPicker.OnMonthChangeListener() {
            @Override
            public void onChange(int currentMonth, int currentYear) {
                listMonthlyUsecase.list(currentMonth, currentYear);
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
