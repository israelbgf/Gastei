package israelbgf.gastei.mobile.gateways.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnection extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gastei.db";

    public DatabaseConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ExpenseTableDefinition.EXPENSE_TABLE + "(" +
                ExpenseTableDefinition._ID + " INTEGER PRIMARY KEY, " +
                ExpenseTableDefinition.AMOUNT + " REAL, " +
                ExpenseTableDefinition.DATE + " INTEGER, " +
                ExpenseTableDefinition.PLACE + " TEXT, " +
                ExpenseTableDefinition.SHARED + " BOOLEAN)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
