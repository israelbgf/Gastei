package israelbgf.gastei.core.values;

import java.util.Objects;

public class Month {
    public final int year;
    public final int month;

    public Month(int year, int month) {
        this.year = year;
        this.month = month;
    }


    public static Month month(int year, int month) {
        return new Month(year, month);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Month month1 = (Month) o;
        return year == month1.year &&
                month == month1.month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month);
    }

    @Override
    public String toString() {
        return String.format("Month{%s/%s}", year, month);
    }
}
