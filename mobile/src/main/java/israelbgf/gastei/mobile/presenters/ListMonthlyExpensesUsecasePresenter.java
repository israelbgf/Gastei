package israelbgf.gastei.mobile.presenters;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase.Presenter;
import israelbgf.gastei.mobile.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ListMonthlyExpensesUsecasePresenter implements Presenter {

    DecimalFormat CURRENCY_FORMATTER = new DecimalFormat("$#.##");

    private List<Map.Entry<Integer, List<ExpenseEntity>>> itens = new ArrayList<>();
    private ExpenseAdapter adapter;
    private ListActivity activity;

    public ListMonthlyExpensesUsecasePresenter(ListActivity activity) {
        this.activity = activity;
        this.adapter = new ExpenseAdapter(activity, itens);
        this.activity.setListAdapter(adapter);
    }

    @Override
    public void presentExpenses(Struct struct) {
        itens.clear();
        itens.addAll(struct.dailyExpenses.entrySet());
        adapter.notifyDataSetChanged();
    }

    private class ExpenseAdapter extends ArrayAdapter<Map.Entry<Integer, List<ExpenseEntity>>> {

        public ExpenseAdapter(Context context, List<Map.Entry<Integer, List<ExpenseEntity>>> itens) {
            super(context, -1, itens);
        }

        @Override
        public View getView(int position, View expenseView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Map.Entry<Integer, List<ExpenseEntity>> expenseGroup = getItem(position);
            Boolean first = true;
            LinearLayout groupView = new LinearLayout(activity);
            for (ExpenseEntity expense : expenseGroup.getValue()) {
                expenseView = inflater.inflate(R.layout.expense_item, groupView, false);

                TextView dayOfWeekView = (TextView) expenseView.findViewById(R.id.day_of_week);
                TextView amountView = (TextView) expenseView.findViewById(R.id.amount);
                TextView placeView = (TextView) expenseView.findViewById(R.id.place);
                TextView cityView = (TextView) expenseView.findViewById(R.id.city);

                if (first){
                    dayOfWeekView.setVisibility(View.VISIBLE);
                    dayOfWeekView.setText(dayOfWeek(expense.getDate()));
                } else {
                    dayOfWeekView.setVisibility(View.GONE);
                }
                amountView.setText(CURRENCY_FORMATTER.format(expense.getAmount()));
                placeView.setText(expense.getPlace());
                cityView.setText("CITY");

                groupView.addView(expenseView);
                first = false;
            }

            return groupView;
        }

        private String dayOfWeek(Date date) {
            return new SimpleDateFormat("EEEE MM", Locale.US).format(date);
        }
    }
}
