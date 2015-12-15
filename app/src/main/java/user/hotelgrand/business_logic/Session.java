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
import user.hotelgrand.database.SessionDBTable;
import user.hotelgrand.database.UserDBTable;
import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.DatabaseFunctionInterface;

public class Session implements DatabaseConstantsInterface, DatabaseFunctionInterface {

    private SessionDBTable sessionTable;
    private SQLiteDatabase db;
    private DBConnection connection;
    public ArrayList<Map<String, Object>> data;

    public Session() {}

    public void onCreate (Context context){
        Log.d(MY_LOGS_TAG, "Session Call -> onCreate()");

        sessionTable = new SessionDBTable();
        connection = new DBConnection();
        db = connection.openConnection(db, context);

        Log.d(MY_LOGS_TAG, "Session End -> onCreate()");
    }

    public void onDestroy() {
        Log.d(MY_LOGS_TAG, "Session Call -> onDestroy()");

        connection.closeConnection(db);

        Log.d(MY_LOGS_TAG, "Session End -> onDestroy()");
    }

    public void showData(ListView lv, Context context) {
        Log.d(MY_LOGS_TAG, "Session Call -> showData()");

        Cursor cursor1 = sessionTable.selectFromDatabase1(db);
        Cursor cursor2 = sessionTable.selectFromDatabase2(db);

        int idSessionColIndex = cursor1.getColumnIndex(SESSION_COLUMN_ID);
        int date_beginColIndex = cursor1.getColumnIndex("begin");
        int date_endColIndex = cursor1.getColumnIndex("end");
        int user1FNameColIndex = cursor1.getColumnIndex("firstnamef");
        int user1SNameColIndex = cursor1.getColumnIndex("secondnamef");
        int user2FNameColIndex = cursor2.getColumnIndex("firstnames");
        int user2SNameColIndex = cursor2.getColumnIndex("secondnames");
        int incomeColIndex = cursor1.getColumnIndex(SESSION_COLUMN_INCOME);

        String[] from = {SESSION_COLUMN_ID, "begin", "end", "firstnamef", "secondnamef",
                "firstnames", "secondnames", SESSION_COLUMN_INCOME};
        int[] to = {R.id.tvItemIdManagerSession, R.id.tvItemDateBeginManagerSession,
                R.id.tvItemDateEndManagerSession, R.id.tvItemFisrtName1ManagerSession,
                R.id.tvItemSecondNameManagerSession, R.id.tvItemFisrtName2ManagerSession,
                R.id.tvItemSecondName2ManagerSession, R.id.tvItemIncomeManagerSession};
        lv.setAdapter(new MySimpleAdapter(context, data, R.layout.item_manager_session, from, to));

        if (cursor1.moveToFirst() && cursor2.moveToFirst()) {
            do {
                Map<String, Object> m = new HashMap<>();
                m.put(SESSION_COLUMN_ID, cursor1.getInt(idSessionColIndex));
                m.put("begin", cursor1.getString(date_beginColIndex));
                m.put("end", cursor1.getString(date_endColIndex));
                m.put("firstnamef", cursor1.getString(user1FNameColIndex));
                m.put("secondnamef", cursor1.getString(user1SNameColIndex));
                m.put("firstnames", cursor2.getString(user2FNameColIndex));
                m.put("secondnames", cursor2.getString(user2SNameColIndex));
                m.put(SESSION_COLUMN_INCOME, cursor1.getInt(incomeColIndex));
                data.add(m);
            } while (cursor1.moveToNext() && cursor2.moveToNext());
        } else
            Toast.makeText(context, "немає записів", Toast.LENGTH_SHORT).show();
        if (!cursor1.isClosed())
            cursor1.close();
        if(!cursor2.isClosed())
            cursor2.close();

        Log.d(MY_LOGS_TAG, "Session End -> showData()");
    }

    public void addData(ContentValues cv) {

    }

    @Override
    public void deleteData(String login) {

    }
}
