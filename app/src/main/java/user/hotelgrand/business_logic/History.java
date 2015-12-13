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
import user.hotelgrand.database.DBHelper;
import user.hotelgrand.database.HistoryDBTable;
import user.hotelgrand.database.UserDBTable;
import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.DatabaseFunctionInterface;

public class History implements DatabaseConstantsInterface, DatabaseFunctionInterface {

    private HistoryDBTable historyTable;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    public ArrayList<Map<String, Object>> data;

    public History () {}

    public void onCreate (Context context) {
        Log.d(MY_LOGS_TAG, "Call History -> onCreate()");

        historyTable = new HistoryDBTable();
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();

        Log.d(MY_LOGS_TAG, "End History -> onCreate()");
    }

    public void closeConnection(){
        Log.d(MY_LOGS_TAG, "Call History -> closeConnection()");

        if (db != null)
            db.close();
        if (dbHelper != null)
            dbHelper.close();

        Log.d(MY_LOGS_TAG, "End History -> closeConnection()");
    }

    public void showData (ListView lv, Context context) {
        Log.d(MY_LOGS_TAG, "Call History -> showData(ListView, Context)");

        Cursor cursor = historyTable.selectFromDatabase(db);
        data = new ArrayList<>();
        int idHistoryColIndex = cursor.getColumnIndex(HISTORY_COLUMN_ID);
        int idSessionColIndex = cursor.getColumnIndex(SESSION_COLUMN_ID);
        int nameHistoryColIndex = cursor.getColumnIndex(HISTORY_COLUMN_NAME);
        int costHistoryColIndex = cursor.getColumnIndex(HISTORY_COLUMN_COST);
        int dateHistoryColIndex = cursor.getColumnIndex(HISTORY_COLUMN_DATE);

        String[] from = {HISTORY_COLUMN_ID, SESSION_COLUMN_ID, HISTORY_COLUMN_NAME,
                HISTORY_COLUMN_COST, HISTORY_COLUMN_DATE};
        int[] to = {R.id.tvIdItemHistory, R.id.tvSessionItemHistory, R.id.tvNameItemHistory,
                R.id.tvPriceItemHistory, R.id.tvDateItemHistory};
        lv.setAdapter(new MySimpleAdapter(context, data, R.layout.item_manager_history, from, to));

        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> m = new HashMap<>();
                m.put(HISTORY_COLUMN_ID, cursor.getInt(idHistoryColIndex));
                m.put(SESSION_COLUMN_ID, cursor.getInt(idSessionColIndex));
                m.put(HISTORY_COLUMN_NAME, cursor.getString(nameHistoryColIndex));
                m.put(HISTORY_COLUMN_COST, cursor.getInt(costHistoryColIndex));
                m.put(HISTORY_COLUMN_DATE, cursor.getString(dateHistoryColIndex));
                data.add(m);
            } while (cursor.moveToNext());
        } else
            Toast.makeText(context, "немає записів", Toast.LENGTH_SHORT).show();
        if (!cursor.isClosed())
            cursor.close();

        Log.d(MY_LOGS_TAG, "End History -> showData(ListView, Context)");
    }

    @Override
    public void addData(ContentValues cv) {
        Log.d(MY_LOGS_TAG, "Call History -> addData()");

        historyTable.insertToDatabase(db, cv);

        Log.d(MY_LOGS_TAG, "End History -> addData()");
    }

    @Override
    public void deleteData(String login) {

    }
}
