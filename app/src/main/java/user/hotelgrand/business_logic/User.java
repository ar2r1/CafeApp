package user.hotelgrand.business_logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import user.hotelgrand.MySimpleAdapter;
import user.hotelgrand.R;
import user.hotelgrand.database.DBConnection;
import user.hotelgrand.database.DBHelper;
import user.hotelgrand.database.UserDBTable;
import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.DatabaseFunctionInterface;

public class User implements DatabaseConstantsInterface, DatabaseFunctionInterface{

    private UserDBTable userTable;
    private SQLiteDatabase db;
    private DBConnection connection;
    public ArrayList<Map<String, Object>> data;

    public User() {    }

    public void onCreate(Context context){
        Log.d(MY_LOGS_TAG, "Call User -> onCreate(Context)");

        userTable = new UserDBTable();
        connection = new DBConnection();
        db = connection.openConnection(db, context);

        Log.d(MY_LOGS_TAG, "End User -> onCreate(Context)");
    }

    public void onDestroy() {
        Log.d(MY_LOGS_TAG, "Dish Call -> onDestroy()");

        connection.closeConnection(db);

        Log.d(MY_LOGS_TAG, "Dish End -> onDestroy()");
    }

    public void showData(ListView lv, Context context) {
        Log.d(MY_LOGS_TAG, "Call User -> showData(ListView, Context)");

        int fnameUserColIndex, snameUserColIndex, loginUserColIndex, passwordUserColIndex;
        Cursor cursor = userTable.selectFromDatabase(db);
        data = new ArrayList<>();
        fnameUserColIndex = cursor.getColumnIndex(USER_COLUMN_FNAME);
        snameUserColIndex = cursor.getColumnIndex(USER_COLUMN_SNAME);
        loginUserColIndex = cursor.getColumnIndex(USER_COLUMN_LOGIN);
        passwordUserColIndex = cursor.getColumnIndex(USER_COLUMN_PASSWORD);

        String[] from = new String[]{USER_COLUMN_FNAME, USER_COLUMN_SNAME,
                USER_COLUMN_LOGIN, USER_COLUMN_PASSWORD};
        int[] to = {R.id.tvFirstNameManagerUser, R.id.tvSecondNameManagerUser,
                R.id.tvLoginManagerUser, R.id.tvPasswordManagerUser};
        MySimpleAdapter sAdapter = new MySimpleAdapter(context, data, R.layout.item_manager_user,
                from, to);
        lv.setAdapter(sAdapter);

        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> m = new HashMap<>();
                m.put(USER_COLUMN_FNAME, cursor.getString(fnameUserColIndex));
                m.put(USER_COLUMN_SNAME, cursor.getString(snameUserColIndex));
                m.put(USER_COLUMN_LOGIN, cursor.getString(loginUserColIndex));
                m.put(USER_COLUMN_PASSWORD, cursor.getString(passwordUserColIndex));
                data.add(m);
            } while (cursor.moveToNext());
        } else
            Toast.makeText(context, "немає записів", Toast.LENGTH_SHORT).show();
        if (!cursor.isClosed())
            cursor.close();

        Log.d(MY_LOGS_TAG, "End User -> showData(ListView, Context)");
    }

    @Override
    public void addData(ContentValues cv) {
        Log.d(MY_LOGS_TAG, "Call User -> addData()");

        userTable.insertToDatabase(db, cv);

        Log.d(MY_LOGS_TAG, "End User -> addData()");
    }

    @Override
    public void deleteData(String login) {
        Log.d(MY_LOGS_TAG, "Call User -> deleteData()");

        userTable.deleteFromDatabase(db, DATABASE_TABLE_USER,
                USER_COLUMN_LOGIN, login);

        Log.d(MY_LOGS_TAG, "End User -> deleteData()");

    }
}
