package user.hotelgrand.interfaces;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface SessionTableInterface {
    void createTable (SQLiteDatabase datab);
    Cursor selectManagerSession1(SQLiteDatabase datab);
    Cursor selectManagerSession1(SQLiteDatabase datab, int id);
    Cursor selectManagerSession2(SQLiteDatabase datab);
    Cursor selectManagerSession2(SQLiteDatabase datab, int id);
    }
