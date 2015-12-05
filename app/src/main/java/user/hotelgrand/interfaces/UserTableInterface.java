package user.hotelgrand.interfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface UserTableInterface {
    void createTable(SQLiteDatabase db);
    void deleteFromDatabase(SQLiteDatabase datab, String table, String i, String d);
    void insertToDatabase(String f, String s, String l, String p,
                                 SQLiteDatabase db, ContentValues cv);
    Cursor selectFromDatabase(SQLiteDatabase db);
}
