package israelbgf.gastei.core.entity;

import israelbgf.gastei.core.entities.Expense;
import israelbgf.gastei.core.entities.SMSExpenseParser;
import org.junit.Test;

import static israelbgf.gastei.core.utils.DateUtils.date;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SMSExpenseParserShould {

    @Test
    public void parseExpenseFromKabum(){
        String SMS = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 9999 EM 08/01/2016 02:56. VALOR DE $ 1.534,80 NO(A) KABUM                    LIMEIRA";

        Expense expense = new SMSExpenseParser().parseExpenseFrom(SMS);

        assertThat(expense.getAmount(), equalTo(1534.8));
        assertThat(expense.getDate(), equalTo(date(2016, 1, 8, 2, 56)));
        assertThat(expense.getPlace(), equalTo("KABUM LIMEIRA"));
    }

    @Test
    public void parseExpenseFromHavan(){
        String SMS = "BRADESCO CARTOES: COMPRA APROVADA NO CARTAO FINAL 9999 EM 22/08/2015 00:17 NO VALOR DE $ 303,45 EM 2 X NO(A) HAVAN                    JOINVILLE.";

        Expense expense = new SMSExpenseParser().parseExpenseFrom(SMS);

        assertThat(expense.getAmount(), equalTo(303.45));
        assertThat(expense.getDate(), equalTo(date(2015, 8, 22, 0, 17)));
        assertThat(expense.getPlace(), equalTo("HAVAN JOINVILLE"));
    }

}
