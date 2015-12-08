package user.hotelgrand.interfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface UserTableInterface {

    Cursor authorizationUser(SQLiteDatabase db, String l);

    void createTable(SQLiteDatabase db);

    void deleteFromDatabase(SQLiteDatabase datab, String table, String loginField, String login);

    void insertToDatabase(SQLiteDatabase db, ContentValues cv);

    Cursor selectFromDatabase(SQLiteDatabase db);
}
