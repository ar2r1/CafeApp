package user.hotelgrand.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.SessionTableInterface;

public class SessionDBTable implements SessionTableInterface, DatabaseConstantsInterface {

    @Override
    public void createTable (SQLiteDatabase datab){
        Log.d(MY_LOGS_TAG, "SessionDBTable Call -> createTable()");

        datab.execSQL("create table " + DATABASE_TABLE_SESSION + " (" +
                SESSION_COLUMN_ID + " integer primary key autoincrement, " +
                SESSION_COLUMN_DATE_BEGIN + " text, " +
                SESSION_COLUMN_DATE_END + " text, " +
                SESSION_COLUMN_ID_USER1 + " integer, " +
                SESSION_COLUMN_ID_USER2 + " integer, " +
                SESSION_COLUMN_INCOME + " integer);");

        Log.d(MY_LOGS_TAG, "SessionDBTable End -> createTable()");
    }

    @Override
    public Cursor selectFromDatabase1(SQLiteDatabase datab) {
        Log.d(MY_LOGS_TAG, "SessionDBTable Call -> select1()");

        String query = "select " + DATABASE_TABLE_SESSION + "." + SESSION_COLUMN_ID +
                " as " + SESSION_COLUMN_ID  +", " +
                DATABASE_TABLE_SESSION + "." + SESSION_COLUMN_DATE_BEGIN + " as begin, " +
                DATABASE_TABLE_SESSION + "." + SESSION_COLUMN_DATE_END + " as end, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_FNAME + " as firstnamef, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_SNAME + " as secondnamef, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_FNAME + " as firstnames, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_SNAME + " as secondnames, " +
                DATABASE_TABLE_SESSION + "." + SESSION_COLUMN_INCOME + " as income " +
                " from " + DATABASE_TABLE_SESSION + " inner join " + DATABASE_TABLE_USER +
                " on user.id_user = session.id_user1;";
        Cursor c = datab.rawQuery(query, null);

        Log.d(MY_LOGS_TAG, "SessionDBTable End -> select1()");

        return c;
    }

    @Override
    public Cursor selectFromDatabase1(SQLiteDatabase datab, int id) {
        Log.d(MY_LOGS_TAG, "SessionDBTable Call -> select1(id)");

        String query = "select " + DATABASE_TABLE_SESSION + "." + SESSION_COLUMN_ID +
                " as " + SESSION_COLUMN_ID  +", " +
                DATABASE_TABLE_SESSION + "." + SESSION_COLUMN_DATE_BEGIN + " as begin, " +
                DATABASE_TABLE_SESSION + "." + SESSION_COLUMN_DATE_END + " as end, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_FNAME + " as firstnamef, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_SNAME + " as secondnamef, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_FNAME + " as firstnames, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_SNAME + " as secondnames, " +
                DATABASE_TABLE_SESSION + "." + SESSION_COLUMN_INCOME + " as income " +
                " from " + DATABASE_TABLE_SESSION + " inner join " + DATABASE_TABLE_USER +
                " on user.id_user = session.id_user1 where " +
                SESSION_COLUMN_ID + " = " + id + ";";
        Cursor c = datab.rawQuery(query, null);

        Log.d(MY_LOGS_TAG, "SessionDBTable End -> select1(id)");
        return c;
    }

    @Override
    public Cursor selectFromDatabase2(SQLiteDatabase db) {
        Log.d(MY_LOGS_TAG, "SessionDBTable Call -> select2()");

        String q = "select " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_FNAME + " as firstnames, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_SNAME + " as secondnames " +
                " from " + DATABASE_TABLE_SESSION + " inner join " + DATABASE_TABLE_USER + " on " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_ID + " = " + DATABASE_TABLE_SESSION + "." +
                SESSION_COLUMN_ID_USER2 + ";";
        Cursor c = db.rawQuery(q, null);

        Log.d(MY_LOGS_TAG, "SessionDBTable End -> select2(id)");
        return c;
    }

    @Override
    public Cursor selectFromDatabase2(SQLiteDatabase datab, int id) {
        Log.d(MY_LOGS_TAG, "SessionDBTable Call -> select2(id)");

        String q = "select " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_FNAME + " as firstnames, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_SNAME + " as secondnames " +
                " from " + DATABASE_TABLE_SESSION + " inner join " + DATABASE_TABLE_USER + " on " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_ID + " = " + DATABASE_TABLE_SESSION + "." +
                SESSION_COLUMN_ID_USER2 + " where " + SESSION_COLUMN_ID + " = " + id + ";";
        Cursor c = datab.rawQuery(q, null);

        Log.d(MY_LOGS_TAG, "SessionDBTable End -> select2(id)");
        return c;
    }

    public Cursor getIdSession (SQLiteDatabase datab) {
        Log.d(MY_LOGS_TAG, "SessionDBTable Call -> getIdSession()");

        String q = "select max(" + SESSION_COLUMN_ID + ") as max from (select * from " +
                DATABASE_TABLE_HISTORY + ");";
        Cursor c = datab.rawQuery(q, null);

        Log.d(MY_LOGS_TAG, "SessionDBTable End -> getIdSession()");
        return c;
    }
}
