package user.hotelgrand;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import user.hotelgrand.database.DBHelper;
import user.hotelgrand.interfaces.Utils;

public class ActivityManagerSession extends ActionBarActivity implements Utils {

    private ListView lvSession;
    private ArrayList<Map<String, Object>> data;

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    @Override
    public void cleanFields() {
        if (db.isOpen())
            db.close();
        if (dbHelper != null)
            dbHelper.close();
    }

    public void initVars() {
        lvSession = (ListView) findViewById(R.id.lvManagerSession);
        lvSession.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        data = new ArrayList<>();
    }

    @Override
    public void getValues() {

    }

    @Override
    public void showData() {
        Cursor cursor1 = dbHelper.selectManagerSession1(db);
        Cursor cursor2 = dbHelper.selectManagerSession2(db);

        int idSessionColIndex = cursor1.getColumnIndex(dbHelper.SESSION_COLUMN_ID);
        int date_beginColIndex = cursor1.getColumnIndex("begin");
        int date_endColIndex = cursor1.getColumnIndex("end");
        int user1FNameColIndex = cursor1.getColumnIndex("firstnamef");
        int user1SNameColIndex = cursor1.getColumnIndex("secondnamef");
        int user2FNameColIndex = cursor2.getColumnIndex("firstnames");
        int user2SNameColIndex = cursor2.getColumnIndex("secondnames");
        int incomeColIndex = cursor1.getColumnIndex(dbHelper.SESSION_COLUMN_INCOME);

        String[] from = {dbHelper.SESSION_COLUMN_ID, "begin", "end", "firstnamef", "secondnamef",
                "firstnames", "secondnames", dbHelper.SESSION_COLUMN_INCOME};
        int[] to = {R.id.tvItemIdManagerSession, R.id.tvItemDateBeginManagerSession,
                R.id.tvItemDateEndManagerSession, R.id.tvItemFisrtName1ManagerSession,
                R.id.tvItemSecondNameManagerSession, R.id.tvItemFisrtName2ManagerSession,
                R.id.tvItemSecondName2ManagerSession, R.id.tvItemIncomeManagerSession};
        lvSession.setAdapter(new MySimpleAdapter(this, data, R.layout.item_manager_session, from, to));

        if (cursor1.moveToFirst() && cursor2.moveToFirst()) {
            do {
                Map<String, Object> m = new HashMap<>();
                m.put(dbHelper.SESSION_COLUMN_ID, cursor1.getInt(idSessionColIndex));
                m.put("begin", cursor1.getString(date_beginColIndex));
                m.put("end", cursor1.getString(date_endColIndex));
                m.put("firstnamef", cursor1.getString(user1FNameColIndex));
                m.put("secondnamef", cursor1.getString(user1SNameColIndex));
                m.put("firstnames", cursor2.getString(user2FNameColIndex));
                m.put("secondnames", cursor2.getString(user2SNameColIndex));
                m.put(dbHelper.SESSION_COLUMN_INCOME, cursor1.getInt(incomeColIndex));
                data.add(m);
            } while (cursor1.moveToNext() && cursor2.moveToNext());
        } else
            Toast.makeText(getApplicationContext(), "немає записів", Toast.LENGTH_SHORT).show();
        if (!cursor1.isClosed())
            cursor1.close();
        if(!cursor2.isClosed())
            cursor2.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_session);

        initVars();
        showData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cleanFields();
        finish();
    }

    @Override
    public void addData() {

    }

    @Override
    public void deleteData() {

    }
}

