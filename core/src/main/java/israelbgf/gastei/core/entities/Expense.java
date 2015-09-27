package israelbgf.gastei.core.entities;

import java.util.Date;
import java.util.Objects;

public class Expense {
    private final double amount;
    private final String local;
    private Date date;

    public Expense(double amount, String local, Date date) {
        this.amount = amount;
        this.local = local;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "amount=" + amount +
                ", local='" + local + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(amount, expense.amount) &&
                Objects.equals(local, expense.local);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, local);
    }
}
