package user.hotelgrand.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import user.hotelgrand.database.DishDBTable;
import user.hotelgrand.database.HistoryDBTable;
import user.hotelgrand.database.SessionDBTable;
import user.hotelgrand.database.UserDBTable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DishDBTable dish = new DishDBTable();
        dish.createTable(db);
        dish.firstInsertData(db);

        UserDBTable user = new UserDBTable();
        user.createTable(db);
        user.firstInsertData(db);

        SessionDBTable session = new SessionDBTable();
        session.createTable(db);

        HistoryDBTable history = new HistoryDBTable();
        history.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {    }
}
