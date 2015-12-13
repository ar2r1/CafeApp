package user.hotelgrand.interfaces;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface SessionTableInterface {
    void createTable (SQLiteDatabase datab);
    Cursor selectFromDatabase1(SQLiteDatabase datab);
    Cursor selectFromDatabase1(SQLiteDatabase datab, int id);
    Cursor selectFromDatabase2(SQLiteDatabase datab);
    Cursor selectFromDatabase2(SQLiteDatabase datab, int id);
    }
