package user.hotelgrand.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import user.hotelgrand.interfaces.ConstantsInterface;
import user.hotelgrand.interfaces.HistoryTableInterface;

public class HistoryDBTable implements HistoryTableInterface, ConstantsInterface {

    public HistoryDBTable () {}

    @Override
    public void createTable (SQLiteDatabase db){
        Log.d(MY_LOGS_TAG, "HistoryDBTable Call -> createTable()");

        db.execSQL("create table " + DATABASE_TABLE_HISTORY + " (" +
                HISTORY_COLUMN_ID + " integer primary key autoincrement, " +
                SESSION_COLUMN_ID + " integer, " +
                HISTORY_COLUMN_NAME + " text, " +
                HISTORY_COLUMN_COST + " integer, " +
                HISTORY_COLUMN_DATE + " text); ");

        Log.d(MY_LOGS_TAG, "HistoryDBTable End -> createTable()");
    }

    @Override
    public void insertToDatabase(SQLiteDatabase db, ContentValues cv) {
        Log.d(MY_LOGS_TAG, "HistoryDBTable Call -> insert()");

        db.insert(DATABASE_TABLE_HISTORY, null, cv);

        Log.d(MY_LOGS_TAG, "HistoryDBTable End -> insert()");
    }

    @Override
    public Cursor selectFromDatabase(SQLiteDatabase db) {
        Log.d(MY_LOGS_TAG, "HistoryDBTable Call -> selectFromDatabase()");

        String q = "select * from " + DATABASE_TABLE_HISTORY + ";";
        Cursor c = db.rawQuery(q, null);

        Log.d(MY_LOGS_TAG, "HistoryDBTable End -> selectFromDatabase()");
        return c;
    }

}
