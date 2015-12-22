package user.hotelgrand.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import user.hotelgrand.interfaces.ConstantsInterface;
import user.hotelgrand.interfaces.UserTableInterface;

public class UserDBTable implements UserTableInterface, ConstantsInterface {

    public UserDBTable () {}

    @Override
    public Cursor authorizationUser (SQLiteDatabase db, String l) {
        Log.d(MY_LOGS_TAG, "UserDBTable Call -> authorizationUser()");
        String q = "select " + USER_COLUMN_ID + ", " + USER_COLUMN_LOGIN + ", " + USER_COLUMN_PASSWORD +
                " from " + DATABASE_TABLE_USER + " where " + USER_COLUMN_LOGIN + " like '" + l + "';";
        Cursor c = db.rawQuery(q, null);
        Log.d(MY_LOGS_TAG, "UserDBTable End -> authorizationUser()");
        return c;
    }

    @Override
    public void createTable(SQLiteDatabase db) {
        Log.d(MY_LOGS_TAG, "UserDBTable Call -> createTable()");

        db.execSQL("create table " + DATABASE_TABLE_USER + " (" +
                USER_COLUMN_ID + " integer primary key autoincrement, " +
                USER_COLUMN_FNAME + " text,                                                                            " +
                USER_COLUMN_SNAME + " text, " +
                USER_COLUMN_LOGIN + " login text, " +
                USER_COLUMN_PASSWORD + " text);");
        firstInsertData(db);

        Log.d(MY_LOGS_TAG, "UserDBTable End -> createTable()");
    }

    @Override
    public void deleteFromDatabase(SQLiteDatabase datab, String table, String loginField, String login) {
        Log.d(MY_LOGS_TAG, "UserDBTable Call -> deleteFromDatabase()");

        String query = "delete from " + table + " where " + loginField + " like '" + login + "';";
        datab.execSQL(query);

        Log.d(MY_LOGS_TAG, "UserDBTable End -> deleteFromDatabase()");
    }

    public void firstInsertData(SQLiteDatabase db) {
        Log.d(MY_LOGS_TAG, "UserDBTable Call -> firstInsert()");

        db.execSQL("insert into user (id_user, first_name, second_name, login, password) " +
                "values (1, 'Сторожук', 'Артур', 'ar2r1', 'celtic'); ");
        db.execSQL("insert into user (id_user, first_name, second_name, login, password) " +
                "values (2, 'Остапчук', 'Володимир', 'ostapchuk', 'valoda'); " );
        db.execSQL("insert into user (id_user, first_name, second_name, login, password) " +
                "values (3, 'Деркач', 'Христина', 'kristi', 'swystunca'); ");

        Log.d(MY_LOGS_TAG, "UserDBTable End -> firstInsert()");
    }

    @Override
    public void insertToDatabase(SQLiteDatabase db, ContentValues cv) {
        Log.d(MY_LOGS_TAG, "Call UserDBTable -> insertToDatabase()");

        db.insert(DATABASE_TABLE_USER, null, cv);

        Log.d(MY_LOGS_TAG, "End UserDBTable -> insertToDatabase()");
    }

    @Override
    public Cursor selectFromDatabase(SQLiteDatabase db) {
        Log.d(MY_LOGS_TAG, "Call UserDBTable -> selectFromDatabase()");

        String query = "select * from " + DATABASE_TABLE_USER + ";";
        Cursor c = db.rawQuery(query, null);

        Log.d(MY_LOGS_TAG, "End UserDBTable -> selectFromDatabase()");
        return c;
    }

}
