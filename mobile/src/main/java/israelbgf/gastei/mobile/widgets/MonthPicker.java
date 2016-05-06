package israelbgf.gastei.mobile.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import israelbgf.gastei.core.values.Month;
import israelbgf.gastei.mobile.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static israelbgf.gastei.core.utils.DateUtils.monthOf;
import static israelbgf.gastei.core.utils.DateUtils.yearOf;

public class MonthPicker extends LinearLayout {


    private TextView currentYearView;
    private TextView currentMonthView;

    private OnMonthChangeListener onMonthChangeListener = new DummyOnMonthChangeListener();

    int currentYear = yearOf(new Date());
    int currentMonth = monthOf(new Date());

    public MonthPicker(Context context) {
        super(context);
        init();
    }

    public MonthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonthPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.widget_month_chooser, this);
        this.currentYearView = (TextView) findViewById(R.id.current_year);
        this.currentMonthView = (TextView) findViewById(R.id.current_month);

        ImageButton previousMonthButton = (ImageButton) findViewById(R.id.previous_month);
        previousMonthButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                previousMonth();
            }
        });

        ImageButton nextMonthButton = (ImageButton) findViewById(R.id.next_month);
        nextMonthButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonth();
            }
        });

    }

    public void nextMonth() {
        if (currentMonth >= 12) {
            currentYear++;
            currentMonth = 1;
        } else {
            currentMonth++;
        }
        updateWidget();
        onMonthChangeListener.onChange(new Month(currentYear, currentMonth));
    }

    public void previousMonth() {
        if (currentMonth <= 1) {
            currentYear--;
            currentMonth = 12;
        } else {
            currentMonth--;
        }
        updateWidget();
        onMonthChangeListener.onChange(new Month(currentYear, currentMonth));
    }

    public void setCurrentMonth(int year, int month) {
        this.currentYear = year;
        this.currentMonth = month;
        updateWidget();
        onMonthChangeListener.onChange(new Month(currentYear, currentMonth));
    }

    public void setOnMonthChangeListener(OnMonthChangeListener onMonthChangeListener){
        this.onMonthChangeListener = onMonthChangeListener;
    }

    private void updateWidget() {
        currentYearView.setText(String.valueOf(currentYear));
        currentMonthView.setText(getMonthName());

    }

    private String getMonthName() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, currentMonth - 1);
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
    }

    public Month getMonth(){
        return new Month(currentYear, currentMonth);
    }

    @Deprecated
    public int getCurrentYear() {
        return currentYear;
    }

    @Deprecated
    public int getCurrentMonth() {
        return currentMonth;
    }


    public interface OnMonthChangeListener {

        void onChange(Month newMonth);

    }

    private class DummyOnMonthChangeListener implements OnMonthChangeListener {

        @Override
        public void onChange(Month newMonth) {

        }
    }

}
