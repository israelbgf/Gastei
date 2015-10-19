package israelbgf.gastei.core.utils;

import israelbgf.gastei.core.entities.ExpenseEntity;

import java.util.Date;
import java.util.UUID;

import static israelbgf.gastei.core.utils.DateUtils.date;

public class ExpenseFactory {

    public static final String GIVEN_PLACE = "Whathever Place";

    public static ExpenseEntity expense(float amount, Date date) {
        return new ExpenseEntity(
                UUID.randomUUID().toString(),
                amount,
                GIVEN_PLACE,
                date,
                false);

    }

    public static ExpenseEntity expense(float amount, Date date, boolean shared) {
        return new ExpenseEntity(
                UUID.randomUUID().toString(),
                amount,
                GIVEN_PLACE,
                date,
                shared);

    }

    public static ExpenseEntity sampleExpense() {
        return new ExpenseEntity(
                UUID.randomUUID().toString(), 156.47,
                "GIASSI SUPERMERCADOS LOJAJOINVILLE",
                date(2015, 9, 26, 13, 46),
                false);
    }

}
