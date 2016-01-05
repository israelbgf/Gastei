package israelbgf.gastei.mobile;

import android.content.ContentValues;

import java.util.Date;

public class BetterContentValues {

    private ContentValues contentValues;

    public BetterContentValues(){
        contentValues = new ContentValues();
    }

    public BetterContentValues with(String columnName, int value) {
        contentValues.put(columnName, value);
        return this;
    }

    public BetterContentValues with(String columnName, String value) {
        contentValues.put(columnName, value);
        return this;
    }

    public BetterContentValues with(String columnName, Double value) {
        contentValues.put(columnName, value);
        return this;
    }

    public BetterContentValues with(String columnName, Date value) {
        contentValues.put(columnName, value.getTime());
        return this;
    }

    public BetterContentValues with(String columnName, boolean value) {
        contentValues.put(columnName, value);
        return this;
    }

    public ContentValues getContentValues() {
        return contentValues;
    }
}
