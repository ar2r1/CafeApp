package user.hotelgrand.interfaces;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface HistoryTableInterface {
    public Cursor selectManagerHistory(SQLiteDatabase datab);
}
