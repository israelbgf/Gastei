package israelbgf.gastei.mobile.presenters;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase.Presenter;
import israelbgf.gastei.mobile.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ListMonthlyExpensesUsecasePresenter implements Presenter {

    DecimalFormat CURRENCY_FORMATTER = new DecimalFormat("$#.##");

    private List<ExpenseEntity> itens = new ArrayList<>();
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
        itens.addAll(struct.expenses);
        adapter.notifyDataSetChanged();
    }

    private class ExpenseAdapter extends ArrayAdapter<ExpenseEntity> {

        public ExpenseAdapter(Context context, List<ExpenseEntity> itens) {
            super(context, -1, itens);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null)
                convertView = inflater.inflate(R.layout.expense_item, parent, false);

            ExpenseEntity expense = getItem(position);
            TextView dayOfWeekView = (TextView) convertView.findViewById(R.id.day_of_week);
            TextView amountView = (TextView) convertView.findViewById(R.id.amount);
            TextView placeView = (TextView) convertView.findViewById(R.id.place);
            TextView cityView = (TextView) convertView.findViewById(R.id.city);

            if(isFirstExpenseOfTheDay(expense)){
                dayOfWeekView.setVisibility(View.VISIBLE);
                dayOfWeekView.setText(dayOfWeek(expense.getDate()));
            }
            amountView.setText(CURRENCY_FORMATTER.format(expense.getAmount()));
            placeView.setText(expense.getPlace());
            cityView.setText("CITY");

            return convertView;
        }

        private boolean isFirstExpenseOfTheDay(ExpenseEntity expense) {
            return true;
        }

        private String dayOfWeek(Date date) {
            return new SimpleDateFormat("EEEE MM", Locale.US).format(date);
        }
    }
}
