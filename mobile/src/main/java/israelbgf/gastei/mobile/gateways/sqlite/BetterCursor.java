package israelbgf.gastei.mobile.gateways.sqlite;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import java.util.Date;

public class BetterCursor implements Cursor{
    private Cursor cursor;

    public BetterCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public int getPosition() {
        return cursor.getPosition();
    }

    @Override
    public boolean move(int offset) {
        return cursor.move(offset);
    }

    @Override
    public boolean moveToPosition(int position) {
        return cursor.moveToPosition(position);
    }

    @Override
    public boolean moveToFirst() {
        return cursor.moveToFirst();
    }

    @Override
    public boolean moveToLast() {
        return cursor.moveToLast();
    }

    @Override
    public boolean moveToNext() {
        return cursor.moveToNext();
    }

    @Override
    public boolean moveToPrevious() {
        return cursor.moveToPrevious();
    }

    @Override
    public boolean isFirst() {
        return cursor.isFirst();
    }

    @Override
    public boolean isLast() {
        return cursor.isLast();
    }

    @Override
    public boolean isBeforeFirst() {
        return cursor.isBeforeFirst();
    }

    @Override
    public boolean isAfterLast() {
        return cursor.isAfterLast();
    }

    @Override
    public int getColumnIndex(String columnName) {
        return cursor.getColumnIndex(columnName);
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        return cursor.getColumnIndexOrThrow(columnName);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return cursor.getColumnName(columnIndex);
    }

    @Override
    public String[] getColumnNames() {
        return cursor.getColumnNames();
    }

    @Override
    public int getColumnCount() {
        return cursor.getColumnCount();
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        return cursor.getBlob(columnIndex);
    }

    @Override
    public String getString(int columnIndex) {
        return cursor.getString(columnIndex);
    }

    public String getString(String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        cursor.copyStringToBuffer(columnIndex, buffer);
    }

    @Override
    public short getShort(int columnIndex) {
        return cursor.getShort(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) {
        return cursor.getInt(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) {
        return cursor.getLong(columnIndex);
    }

    public long getLong(String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    @Override
    public float getFloat(int columnIndex) {
        return cursor.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) {
        return cursor.getDouble(columnIndex);
    }

    public double getDouble(String columnName) {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(columnName));
    }

    public Date getDate(String columnName){
        return new Date(cursor.getLong(cursor.getColumnIndexOrThrow(columnName)));
    }

    public boolean getBoolean(String columnName){
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName)) != 0;
    }

    @Override
    public int getType(int columnIndex) {
        return cursor.getType(columnIndex);
    }

    @Override
    public boolean isNull(int columnIndex) {
        return cursor.isNull(columnIndex);
    }

    @Override
    public void deactivate() {
        cursor.deactivate();
    }

    @Override
    public boolean requery() {
        return cursor.requery();
    }

    @Override
    public void close() {
        cursor.close();
    }

    @Override
    public boolean isClosed() {
        return cursor.isClosed();
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {
        cursor.registerContentObserver(observer);
    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {
        cursor.unregisterContentObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        cursor.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        cursor.unregisterDataSetObserver(observer);
    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {
        cursor.setNotificationUri(cr, uri);
    }

    @Override
    public Uri getNotificationUri() {
        return cursor.getNotificationUri();
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        return cursor.getWantsAllOnMoveCalls();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void setExtras(Bundle bundle) {
        cursor.setExtras(bundle);
    }

    @Override
    public Bundle getExtras() {
        return cursor.getExtras();
    }

    @Override
    public Bundle respond(Bundle extras) {
        return cursor.respond(extras);
    }
}
