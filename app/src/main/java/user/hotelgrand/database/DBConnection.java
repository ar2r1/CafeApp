package user.hotelgrand.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import user.hotelgrand.interfaces.ConstantsInterface;

public class DBConnection implements ConstantsInterface {

    public DBHelper dbHelper;

    public DBConnection (){}

     public SQLiteDatabase openConnection(SQLiteDatabase db, Context context) {
         Log.d(MY_LOGS_TAG, "DBConnection Call -> openConnection()");

         dbHelper = new DBHelper(context);
         db = dbHelper.getWritableDatabase();

         Log.d(MY_LOGS_TAG, "DBConnection Call -> openConnection()");
         return db;
     }
    public void closeConnection (SQLiteDatabase db) {
        Log.d(MY_LOGS_TAG, "DBConnection Call -> closeConnection()");

        if (db != null)
            db.close();
        if (dbHelper != null)
            dbHelper.close();

        Log.d(MY_LOGS_TAG, "DBConnection End -> closeConnection()");
    }
}
