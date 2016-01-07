package israelbgf.gastei.core.entities;

import java.util.Date;
import java.util.Objects;

public class Expense {
    private Long id;
    private final double amount;
    private final String place;
    private final Date date;
    private boolean shared;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public String getPlace() {
        return place;
    }

    public Date getDate() {
        return date;
    }

    public boolean isShared() {
        return shared;
    }

    public Expense(Long id, double amount, String place, Date date, boolean shared) {
        this.id = id;
        this.amount = amount;
        this.place = place;
        this.date = date;
        this.shared = shared;
    }

    public Expense(double amount, String place, Date date, boolean shared) {
        this(null, amount, place, date, shared);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", place='" + place + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense that = (Expense) o;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(id, that.id) &&
                Objects.equals(place, that.place) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, place, date);
    }

}
