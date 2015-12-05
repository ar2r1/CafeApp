package user.hotelgrand.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.HistoryTableInterface;

public class History implements HistoryTableInterface, DatabaseConstantsInterface {

    public Cursor selectManagerHistory(SQLiteDatabase datab) {
        String q = "select * from " + DATABASE_TABLE_HISTORY + ";";
        Cursor c = datab.rawQuery(q, null);
        return c;
    }
}
