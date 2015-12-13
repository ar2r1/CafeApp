package user.hotelgrand.interfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface HistoryTableInterface {

    void createTable (SQLiteDatabase db);

    void insertToDatabase(SQLiteDatabase db, ContentValues cv);

    Cursor selectFromDatabase(SQLiteDatabase db);

}
