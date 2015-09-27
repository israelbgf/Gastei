package israelbgf.gastei.core.utils;

import israelbgf.gastei.core.entities.Expense;

import java.util.Date;

import static israelbgf.gastei.core.utils.DateUtils.createDate;

public class ExpenseFactory {

    public static final Date GIVEN_DATE = createDate(2015, 9, 26, 13, 46);
    public static final String GIVEN_PLACE = "Whathever Place";

    public static Expense expense(float amount, Date date){
        return new Expense(
                amount,
                GIVEN_PLACE,
                date);

    }

    public static Expense sampleExpense(){
        return new Expense(
                156.47,
                "GIASSI SUPERMERCADOS LOJAJOINVILLE",
                createDate(2015, 9, 26, 13, 46));
    }

}
