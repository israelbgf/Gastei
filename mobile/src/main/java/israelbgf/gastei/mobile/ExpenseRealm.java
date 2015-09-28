package israelbgf.gastei.mobile;

import io.realm.RealmObject;

import java.util.Date;

public class ExpenseRealm extends RealmObject {

    private double amount;
    private Date date;
    private String local;

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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
