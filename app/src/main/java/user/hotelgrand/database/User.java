package user.hotelgrand.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.UserTableInterface;

public class User implements UserTableInterface, DatabaseConstantsInterface {

    public Cursor authorizationUser (SQLiteDatabase db, String l) {
        String q = "select " + USER_COLUMN_ID + ", " + USER_COLUMN_LOGIN + ", " + USER_COLUMN_PASSWORD +
                " from " + DATABASE_TABLE_USER + " where " + USER_COLUMN_LOGIN + " like '" + l + "';";
        Cursor c = db.rawQuery(q, null);
        return c;
    }

    @Override
    public void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + DATABASE_TABLE_USER +" (" +
                USER_COLUMN_ID + " integer primary key autoincrement, " +
                USER_COLUMN_FNAME + " text,  " +
                USER_COLUMN_SNAME + " text, " +
                USER_COLUMN_LOGIN + " login text, " +
                USER_COLUMN_PASSWORD + " text);");
    }

    @Override
    public void deleteFromDatabase(SQLiteDatabase datab, String table, String i, String d) {
        String query = "delete from " + table + " where " + i + " like '" + d + "';";
        datab.execSQL(query);
    }

    @Override
    public void insertToDatabase(String f, String s, String l, String p,
                                 SQLiteDatabase db, ContentValues cv) {
        cv.put(USER_COLUMN_FNAME, f);
        cv.put(USER_COLUMN_SNAME, s);
        cv.put(USER_COLUMN_LOGIN, l);
        cv.put(USER_COLUMN_PASSWORD, p);
        db.insert(DATABASE_TABLE_USER, null, cv);
        cv.clear();
    }

    private void insertFirstData (SQLiteDatabase db) {
        db.execSQL("insert into user (id_user, first_name, second_name, login, password) " +
                "values (1, 'Сторожук', 'Артур', 'ar2r1', 'celtic'); " );
        db.execSQL("insert into user (id_user, first_name, second_name, login, password) " +
                "values (2, 'Остапчук', 'Володимир', 'ostapchuk', 'valoda'); " );
        db.execSQL("insert into user (id_user, first_name, second_name, login, password) " +
                "values (3, 'Деркач', 'Христина', 'kristi', 'swystunca'); " );
    }

    @Override
    public Cursor selectFromDatabase(SQLiteDatabase db) {
        String query = "select * from " + DATABASE_TABLE_USER + ";";
        Cursor c = db.rawQuery(query, null);
        return c;
    }




}
