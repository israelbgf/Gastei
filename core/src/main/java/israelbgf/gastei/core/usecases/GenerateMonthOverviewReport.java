package israelbgf.gastei.core.usecases;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.gateways.ExpenseGateway;
import israelbgf.gastei.core.values.Month;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GenerateMonthOverviewReport {

    private final ExpenseGateway gateway;
    private final Presenter presenter;

    public GenerateMonthOverviewReport(ExpenseGateway gateway, Presenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void generateFor(Month forMonth) {
        Presenter.MonthOverviewReport report = new Presenter.MonthOverviewReport();
        for (Expense expense : gateway.retrieveBy(forMonth)) {
            String place = expense.getPlace();
            Double amountSoFarForThisPlace = report.expensesByPlace.get(place);

            if (amountSoFarForThisPlace == null)
                report.expensesByPlace.put(place, expense.getAmount());
            else
                report.expensesByPlace.put(place, amountSoFarForThisPlace + expense.getAmount());
            report.totalAmount += expense.getAmount();
        }
        presenter.display(report);
    }

    public interface Presenter {
        void display(MonthOverviewReport report);

        class MonthOverviewReport {

            public double totalAmount = 0;
            public Map<String, Double> expensesByPlace = new HashMap<>();

            @Override
            public String toString() {
                return "MonthOverviewReport{" +
                        "totalAmount=" + totalAmount +
                        ", expensesByPlace=" + expensesByPlace +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                MonthOverviewReport that = (MonthOverviewReport) o;
                return Double.compare(that.totalAmount, totalAmount) == 0 &&
                        Objects.equals(expensesByPlace, that.expensesByPlace);
            }

            @Override
            public int hashCode() {
                return Objects.hash(totalAmount, expensesByPlace);
            }
        }

    }
}
