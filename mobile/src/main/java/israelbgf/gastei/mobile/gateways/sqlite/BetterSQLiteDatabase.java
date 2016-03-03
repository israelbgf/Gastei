package israelbgf.gastei.mobile.gateways.sqlite;

import android.database.sqlite.SQLiteDatabase;

public class BetterSQLiteDatabase {
    private SQLiteDatabase database;

    public BetterSQLiteDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public long insert(String tableName, BetterContentValues values) {
        return database.insert(tableName, null, values.getContentValues());
    }

    public long update(String tableName, BetterContentValues values, String restriction, Object... parameters){
        return database.update(tableName, values.getContentValues(), restriction, restrictionParameters(parameters));

    }

    public BetterCursor query(String tableName, String[] projection) {
        return new BetterCursor(database.query(tableName, projection, null, null, null, null, null));
    }

    public BetterCursor query(String tableName, String[] projection, String restriction, Object... parameters) {
        return new BetterCursor(database.query(tableName, projection, restriction, restrictionParameters(parameters), null, null, null));
    }

    public BetterCursor query(String tableName, String[] projection, String restriction, String groupBy, String having, String orderBy, Object... parameters) {
        return new BetterCursor(database.query(tableName, projection, restriction, restrictionParameters(parameters), groupBy, having, orderBy));
    }

    public void execDML(String dml, Object[] parameters){
        database.execSQL(dml, parameters);
    }

    private String[] restrictionParameters(Object[] parameters) {
        String[] processedParameters = new String[parameters.length];
        for(int i = 0; i < parameters.length; i++)
            processedParameters[i] = parameters[i].toString();
        return processedParameters;
    }
}
