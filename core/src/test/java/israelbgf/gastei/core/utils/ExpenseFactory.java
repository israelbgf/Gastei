package israelbgf.gastei.core.utils;

import israelbgf.gastei.core.entities.Expense;

import static israelbgf.gastei.core.utils.DateUtils.createDate;

public class ExpenseFactory {

    public static Expense sampleExpense(){
        return new Expense(
                156.47,
                "GIASSI SUPERMERCADOS LOJAJOINVILLE",
                createDate(2015, 9, 26, 13, 46));
    }

}
