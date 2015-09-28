package israelbgf.gastei.core.entities;

import java.util.Date;
import java.util.Objects;

public class ExpenseEntity {
    private final double amount;
    private final String place;
    private final Date date;

    public double getAmount() {
        return amount;
    }

    public String getPlace() {
        return place;
    }

    public Date getDate() {
        return date;
    }

    public ExpenseEntity(double amount, String place, Date date) {
        this.amount = amount;
        this.place = place;
        this.date = date;
    }

    @Override
    public String toString() {
        return "ExpenseEntity{" +
                "amount=" + amount +
                ", local='" + place + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseEntity expense = (ExpenseEntity) o;
        return Objects.equals(amount, expense.amount) &&
                Objects.equals(place, expense.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, place);
    }
}
