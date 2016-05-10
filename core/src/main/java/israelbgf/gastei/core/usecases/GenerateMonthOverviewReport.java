package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.values.Month;

import java.util.*;

public class GenerateMonthOverviewReport {

    private final ExpenseGateway gateway;
    private final Presenter presenter;

    public GenerateMonthOverviewReport(ExpenseGateway gateway, Presenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void generateFor(Month forMonth) {
        Map<String, Double> expensesByPlace = new HashMap<>();
        double totalAmount = 0;
        for (Expense expense : gateway.retrieveBy(forMonth)) {
            String place = expense.getPlace();
            Double amountSoFarForThisPlace = expensesByPlace.get(place);

            if (amountSoFarForThisPlace == null)
                expensesByPlace.put(place, expense.getAmount());
            else
                expensesByPlace.put(place, amountSoFarForThisPlace + expense.getAmount());
            totalAmount += expense.getAmount();
        }
        presenter.display(totalAmount, buildOrderedListOf(expensesByPlace));
    }

    private List<Presenter.ReportItem> buildOrderedListOf(Map<String, Double> expensesByPlace) {
        List<Presenter.ReportItem> orderedItems = new ArrayList<>();
        for (Map.Entry<String, Double> entry : expensesByPlace.entrySet()) {
            orderedItems.add(new Presenter.ReportItem(entry.getKey(), entry.getValue()));
        }

        Collections.sort(orderedItems, new Comparator<Presenter.ReportItem>() {
            @Override
            public int compare(Presenter.ReportItem o1, Presenter.ReportItem o2) {
                return o2.amount.compareTo(o1.amount);
            }
        });

        return orderedItems;
    }

    public interface Presenter {

        void display(double totalAmount, List<ReportItem> reportItems);

        class ReportItem {
            public String place;
            public Double amount;

            public ReportItem(String place, Double amount) {
                this.place = place;
                this.amount = amount;
            }

            @Override
            public String toString() {
                return "ReportItem{" +
                        "place='" + place + '\'' +
                        ", amount=" + amount +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ReportItem that = (ReportItem) o;
                return Objects.equals(place, that.place) &&
                        Objects.equals(amount, that.amount);
            }

            @Override
            public int hashCode() {
                return Objects.hash(place, amount);
            }
        }

    }
}
