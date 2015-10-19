package israelbgf.gastei.mobile.presenters;

import android.app.ListActivity;
import android.widget.ArrayAdapter;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase.Presenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListMonthlyExpensesUsecasePresenter implements Presenter {

    private List<String> itens = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListActivity activity;

    public ListMonthlyExpensesUsecasePresenter(ListActivity activity) {
        this.activity = activity;
        this.adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, itens);
        this.activity.setListAdapter(adapter);
    }

    @Override
    public void presentExpenses(Struct struct) {
        itens.clear();
        for (ExpenseEntity expense : struct.expenses) {
            itens.add(formatted(expense));
        }
        adapter.notifyDataSetChanged();
    }

    private String formatted(ExpenseEntity expense) {
        String formattedDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(expense.getDate());
        return String.format("%s - %s R$%s", formattedDate, expense.getPlace(), expense.getAmount());
    }
}
