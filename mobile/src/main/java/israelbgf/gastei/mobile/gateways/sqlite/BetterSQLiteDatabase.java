package israelbgf.gastei.mobile.gateways.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import israelbgf.gastei.mobile.BetterContentValues;

public class BetterSQLiteDatabase {
    private SQLiteDatabase database;

    public BetterSQLiteDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public void insert(String tableName, BetterContentValues values) {
        database.insert(tableName, null, values.getContentValues());
    }

    public BetterCursor query(String tableName, String[] projection) {
        return new BetterCursor(database.query(tableName, projection, null, null, null, null, null));
    }

    public BetterCursor query(String tableName, String[] projection, String restriction, String... restrictionParameters) {
        return new BetterCursor(database.query(tableName, projection, restriction, restrictionParameters, null, null, null));
    }
}
