package israelbgf.gastei.core.entities;

import java.util.Date;
import java.util.Objects;

public class ExpenseEntity {
    private String id;
    private final double amount;
    private final String place;
    private final Date date;
    private boolean shared;

    public String getId() {
        return id;
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

    public ExpenseEntity(String id, double amount, String place, Date date, boolean shared) {
        this.id = id;
        this.amount = amount;
        this.place = place;
        this.date = date;
        this.shared = shared;
    }

    public ExpenseEntity(double amount, String place, Date date, boolean shared) {
        this(null, amount, place, date, shared);
    }

    @Override
    public String toString() {
        return "ExpenseEntity{" +
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
        ExpenseEntity that = (ExpenseEntity) o;
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
