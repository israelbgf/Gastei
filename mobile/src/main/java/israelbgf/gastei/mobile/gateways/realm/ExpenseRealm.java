package israelbgf.gastei.mobile.gateways.realm;

import io.realm.RealmObject;

import java.util.Date;

public class ExpenseRealm extends RealmObject {

    private double amount;
    private Date date;
    private String place;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
