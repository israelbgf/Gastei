package israelbgf.gastei.mobile.actvities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import israelbgf.gastei.core.usecases.GenerateMonthOverviewReport;
import israelbgf.gastei.core.values.Month;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.factories.ExpenseGatewaySQLiteFactory;
import israelbgf.gastei.mobile.widgets.MonthPicker;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExpensesByPlace extends Fragment implements GenerateMonthOverviewReport.Presenter {

    private static NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(Locale.US);

    private MostPaidPlacesAdapter adapter;
    private MonthPicker monthPicker;
    private GenerateMonthOverviewReport report;
    private TextView total;

    public ExpensesByPlace() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        report = new GenerateMonthOverviewReport(ExpenseGatewaySQLiteFactory.make(getContext()), this);
        adapter = new MostPaidPlacesAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_expenses_by_place, container, false);

        monthPicker = (MonthPicker) rootView.findViewById(R.id.month_picker);
        monthPicker.setOnMonthChangeListener(new MonthPicker.OnMonthChangeListener() {
            @Override
            public void onChange(Month month) {
                report.generateFor(month);
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        total = (TextView) rootView.findViewById(R.id.total_amount);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        report.generateFor(monthPicker.getMonth());
    }

    @Override
    public void display(MonthOverviewReport report) {
        total.setText(CURRENCY_FORMATTER.format(report.totalAmount));
        adapter.setItems(new LinkedList<>(report.expensesByPlace.entrySet()));
        adapter.notifyDataSetChanged();
    }

    public static class MostPaidPlacesAdapter extends
            RecyclerView.Adapter<MostPaidPlacesAdapter.ViewHolder> {

        private List<Map.Entry<String, Double>> items = new LinkedList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            return new ViewHolder(inflater.inflate(R.layout.view_placerank_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Map.Entry<String, Double> entry = items.get(position);
            holder.place.setText(entry.getKey());
            holder.amount.setText(CURRENCY_FORMATTER.format(entry.getValue()));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItems(LinkedList<Map.Entry<String, Double>> items) {
            this.items = items;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView place;
            public TextView amount;

            public ViewHolder(View itemView) {
                super(itemView);
                place = (TextView) itemView.findViewById(R.id.place);
                amount = (TextView) itemView.findViewById(R.id.total_amount);
            }
        }
    }

}
