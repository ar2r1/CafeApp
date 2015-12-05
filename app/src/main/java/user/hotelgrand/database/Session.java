package user.hotelgrand.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.SessionTableInterface;

public class Session implements SessionTableInterface, DatabaseConstantsInterface {

    @Override
    public Cursor selectManagerSession1(SQLiteDatabase datab) {
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
        return c;
    }

    @Override
    public Cursor selectManagerSession1(SQLiteDatabase datab, int id) {
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
        return c;
    }

    @Override
    public Cursor selectManagerSession2(SQLiteDatabase datab) {
        String q = "select " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_FNAME + " as firstnames, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_SNAME + " as secondnames " +
                " from " + DATABASE_TABLE_SESSION + " inner join " + DATABASE_TABLE_USER + " on " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_ID + " = " + DATABASE_TABLE_SESSION + "." +
                SESSION_COLUMN_ID_USER2 + ";";
        Cursor c = datab.rawQuery(q, null);
        return c;
    }

    @Override
    public Cursor selectManagerSession2(SQLiteDatabase datab, int id) {
        String q = "select " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_FNAME + " as firstnames, " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_SNAME + " as secondnames " +
                " from " + DATABASE_TABLE_SESSION + " inner join " + DATABASE_TABLE_USER + " on " +
                DATABASE_TABLE_USER + "." + USER_COLUMN_ID + " = " + DATABASE_TABLE_SESSION + "." +
                SESSION_COLUMN_ID_USER2 + " where " + SESSION_COLUMN_ID + " = " + id + ";";
        Cursor c = datab.rawQuery(q, null);
        return c;
    }

    public Cursor getIdSession (SQLiteDatabase datab) {
        String q = "select max(" + SESSION_COLUMN_ID + ") as max from (select * from " +
                DATABASE_TABLE_HISTORY + ");";
        Cursor c = datab.rawQuery(q, null);
        return c;
    }
}
