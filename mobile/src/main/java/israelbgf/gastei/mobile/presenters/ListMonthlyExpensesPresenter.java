package israelbgf.gastei.mobile.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.*;
import android.widget.TextView;
import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.usecases.ListMonthlyExpenses.Presenter;
import israelbgf.gastei.core.usecases.ToggleExpenseSharedStatus;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.actvities.ExpenseActivity;
import israelbgf.gastei.mobile.actvities.ExpenseManagementActivity;
import israelbgf.gastei.mobile.factories.ToggleExpenseSharedStatusFactory;
import israelbgf.gastei.mobile.presenters.sectionedview.SectionedRecyclerViewAdapter;
import israelbgf.gastei.mobile.presenters.sectionedview.SectionedRecyclerViewAdapter.Section;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ListMonthlyExpensesPresenter implements Presenter {

    private static NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(Locale.US);
    private static DateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private ExpenseManagementActivity activity;

    public ListMonthlyExpensesPresenter(ExpenseManagementActivity activity) {
        this.activity = activity;
    }

    @Override
    public void presentExpenses(Struct struct) {
        setupRecyclerView(createSectionedExpenseAdapter(struct));
        setupFooter(struct);
    }

    private void setupRecyclerView(SectionedRecyclerViewAdapter adapter) {
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void setupFooter(Struct struct) {
        TextView totalAmount = (TextView) activity.findViewById(R.id.total_amount);
        totalAmount.setText(CURRENCY_FORMATTER.format(struct.totalAmount));

        updateTotalShared(struct.sharedAmount);
    }

    private void updateTotalShared(double amount) {
        TextView totalShared = (TextView) activity.findViewById(R.id.total_shared);
        totalShared.setText(CURRENCY_FORMATTER.format(amount / 2.0));
    }

    private SectionedRecyclerViewAdapter createSectionedExpenseAdapter(Struct struct) {
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(activity, struct.dailyExpenses.values());
        SectionedRecyclerViewAdapter sectionedAdapter = new SectionedRecyclerViewAdapter(
                activity,
                R.layout.expense_section,
                R.id.section_text, expenseAdapter);
        sectionedAdapter.setSections(asAGroupOf(struct.dailyExpenses));
        return sectionedAdapter;
    }

    private List<Section> asAGroupOf(LinkedHashMap<Integer, List<Expense>> dailyExpenses) {
        List<Section> sections = new ArrayList<>();
        if (!dailyExpenses.isEmpty()) {
            LinkedList<List<Expense>> dailyExpensesGroups = new LinkedList<>(dailyExpenses.values());
            Expense firstExpenseOfTheMonth = dailyExpensesGroups.getFirst().get(0);
            sections.add(new Section(0, dayOfWeek(firstExpenseOfTheMonth.getDate())));

            boolean firstDay = true;
            int sectionPosition = 0;
            for (Map.Entry<Integer, List<Expense>> entry : dailyExpenses.entrySet()) {
                if (firstDay) {
                    sectionPosition += entry.getValue().size();
                    firstDay = false;
                } else {
                    sections.add(new Section(sectionPosition, dayOfWeek(entry.getValue().get(0).getDate())));
                    sectionPosition += entry.getValue().size();
                }
            }
        }
        return sections;
    }

    private String dayOfWeek(Date date) {
        return new SimpleDateFormat("EEEE dd", Locale.US).format(date);
    }

    public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder> {

        private final Context context;
        private List<Expense> expenses = new ArrayList<>();

        public ExpenseAdapter(Context context, Collection<List<Expense>> expensesGroup) {
            this.context = context;
            for (List<Expense> expenses : expensesGroup) {
                this.expenses.addAll(expenses);
            }
        }

        @Override
        public ExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(context).inflate(R.layout.expense_item, parent, false);
            return new ExpenseHolder(view);
        }

        @Override
        public void onBindViewHolder(final ExpenseHolder holder, final int position) {
            final Expense selectedExpense = expenses.get(position);

            holder.place.setText(selectedExpense.getPlace());
            holder.amount.setText(CURRENCY_FORMATTER.format(selectedExpense.getAmount()));
            holder.details.setText(buildDetails(position));

            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ExpenseActivity.class);
                    intent.putExtra("expense", selectedExpense);
                    context.startActivity(intent);
                }
            });

            holder.item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ToggleExpenseSharedStatus expenseMarker = ToggleExpenseSharedStatusFactory.make(context, new ToggleExpenseSharedStatus.Presenter() {
                        @Override
                        public void presentUpdatedExpense() {
                            Expense expense = expenses.get(position);
                            expense.setShared(!expense.isShared());
                            holder.details.setText(buildDetails(position));

                            double totalSharedAmount = 0;
                            for (Expense item : expenses) {
                                if (item.isShared()) {
                                    totalSharedAmount += item.getAmount();
                                }
                            }

                            updateTotalShared(totalSharedAmount);
                        }
                    });

                    expenseMarker.toggle(selectedExpense.getId());
                    return true;
                }
            });
        }

        public Spanned buildDetails(int position) {
            String time = "at " + TIME_FORMATTER.format(expenses.get(position).getDate());
            String shared = expenses.get(position).isShared() ? " <b>(shared)</b>" : "";
            return Html.fromHtml(time + shared);
        }

        @Override
        public int getItemCount() {
            return expenses.size();
        }

        public class ExpenseHolder extends RecyclerView.ViewHolder {
            public final ViewGroup item;
            public final TextView amount;
            public final TextView place;
            public final TextView details;

            public ExpenseHolder(View view) {
                super(view);
                item = (ViewGroup) view;
                amount = (TextView) view.findViewById(R.id.amount);
                place = (TextView) view.findViewById(R.id.place);
                details = (TextView) view.findViewById(R.id.details);
            }
        }
    }

}
