package user.hotelgrand.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.HistoryTableInterface;

public class HistoryDBTable implements HistoryTableInterface, DatabaseConstantsInterface {

    @Override
    public void createTable (SQLiteDatabase datab){
        datab.execSQL("create table " + DATABASE_TABLE_HISTORY + " (" +
                HISTORY_COLUMN_ID + " integer primary key autoincrement, " +
                SESSION_COLUMN_ID + " integer, " +
                HISTORY_COLUMN_NAME + " text, " +
                HISTORY_COLUMN_COST + " integer, " +
                HISTORY_COLUMN_DATE + " text); ");
    }

    @Override
    public Cursor selectManagerHistory(SQLiteDatabase datab) {
        String q = "select * from " + DATABASE_TABLE_HISTORY + ";";
        Cursor c = datab.rawQuery(q, null);
        return c;
    }
}
