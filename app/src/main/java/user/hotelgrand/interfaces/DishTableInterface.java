package user.hotelgrand.interfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface DishTableInterface {

    void createTable(SQLiteDatabase datab);

    void firstInsertData(SQLiteDatabase db);

    Cursor selectFromDatabase(SQLiteDatabase db);

    Cursor selectFromDatabase(SQLiteDatabase datab, String str);

    void insertToDatabase(SQLiteDatabase db, ContentValues conv);

    void deleteFromDatabase(SQLiteDatabase datab, String table, String i, String d);
}
