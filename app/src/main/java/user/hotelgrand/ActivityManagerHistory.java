package user.hotelgrand;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import user.hotelgrand.database.DBHelper;
import user.hotelgrand.interfaces.Utils;


public class ActivityManagerHistory extends ActionBarActivity implements Utils {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    private ListView lvHistory;
    private ArrayList<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_history);

        initVars();
        showData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cleanFields();
        if (db.isOpen())
            db.close();
        if (dbHelper != null)
            dbHelper.close();
        finish();
    }

    @Override
    public void initVars() {
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        lvHistory.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> itemHashMap = (HashMap<String, Object>) parent.getItemAtPosition(position);
                int idSessionItem = Integer.valueOf(itemHashMap.get(dbHelper.SESSION_COLUMN_ID).toString());

                Intent intent = new Intent(getApplicationContext(), ActivityManagerSession.class);
                intent.putExtra(dbHelper.SESSION_COLUMN_ID, idSessionItem);
                startActivity(intent);
                finish();
            }
        });

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        data = new ArrayList<>();
    }

    @Override
    public void showData() {
        Cursor cursor = dbHelper.selectManagerHistory(db);
        int idHistoryColIndex = cursor.getColumnIndex(dbHelper.HISTORY_COLUMN_ID);
        int idSessionColIndex = cursor.getColumnIndex(dbHelper.SESSION_COLUMN_ID);
        int nameHistoryColIndex = cursor.getColumnIndex(dbHelper.HISTORY_COLUMN_NAME);
        int costHistoryColIndex = cursor.getColumnIndex(dbHelper.HISTORY_COLUMN_COST);
        int dateHistoryColIndex = cursor.getColumnIndex(dbHelper.HISTORY_COLUMN_DATE);

        String[] from = {dbHelper.HISTORY_COLUMN_ID, dbHelper.SESSION_COLUMN_ID,
                dbHelper.HISTORY_COLUMN_NAME, dbHelper.HISTORY_COLUMN_COST, dbHelper.HISTORY_COLUMN_DATE};
        int[] to = {R.id.tvIdItemHistory, R.id.tvSessionItemHistory, R.id.tvNameItemHistory,
                R.id.tvPriceItemHistory, R.id.tvDateItemHistory};
        lvHistory.setAdapter(new MySimpleAdapter(this, data, R.layout.item_manager_history, from, to));

        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> m = new HashMap<>();
                m.put(dbHelper.HISTORY_COLUMN_ID, cursor.getInt(idHistoryColIndex));
                m.put(dbHelper.SESSION_COLUMN_ID, cursor.getInt(idSessionColIndex));
                m.put(dbHelper.HISTORY_COLUMN_NAME, cursor.getString(nameHistoryColIndex));
                m.put(dbHelper.HISTORY_COLUMN_COST, cursor.getInt(costHistoryColIndex));
                m.put(dbHelper.HISTORY_COLUMN_DATE, cursor.getString(dateHistoryColIndex));
                data.add(m);
            } while (cursor.moveToNext());
        } else
            Toast.makeText(getApplicationContext(), "немає записів", Toast.LENGTH_SHORT).show();
        if (!cursor.isClosed())
            cursor.close();
    }

    @Override
    public void cleanFields() {

    }

    @Override
    public void getValues() {

    }

    @Override
    public void addData() {

    }

    @Override
    public void deleteData() {

    }
}
