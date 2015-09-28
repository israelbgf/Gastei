package israelbgf.gastei.core.utils;

import israelbgf.gastei.core.entities.ExpenseEntity;

import java.util.Date;

import static israelbgf.gastei.core.utils.DateUtils.createDate;

public class ExpenseFactory {

    public static final Date GIVEN_DATE = createDate(2015, 9, 26, 13, 46);
    public static final String GIVEN_PLACE = "Whathever Place";

    public static ExpenseEntity expense(float amount, Date date){
        return new ExpenseEntity(
                amount,
                GIVEN_PLACE,
                date);

    }

    public static ExpenseEntity sampleExpense(){
        return new ExpenseEntity(
                156.47,
                "GIASSI SUPERMERCADOS LOJAJOINVILLE",
                createDate(2015, 9, 26, 13, 46));
    }

}
