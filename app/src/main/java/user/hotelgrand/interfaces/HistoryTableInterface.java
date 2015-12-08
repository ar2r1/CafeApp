package user.hotelgrand.interfaces;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface HistoryTableInterface {
    public void createTable (SQLiteDatabase datab);
    public Cursor selectManagerHistory(SQLiteDatabase datab);
}
