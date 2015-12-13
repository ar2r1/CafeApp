package user.hotelgrand;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import user.hotelgrand.business_logic.History;
import user.hotelgrand.database.DBHelper;
import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.Utils;


public class ActivityManagerHistory extends ActionBarActivity implements Utils, DatabaseConstantsInterface {

    private ListView lvHistory;
    private History h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_history);

        initVars();
        h.showData(lvHistory, getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        h.closeConnection();
        finish();
    }

    @Override
    public void cleanFields() {

    }

    @Override
    public void initVars() {
        Log.d(MY_LOGS_TAG, "Call ActManHis -> initVars()");

        h = new History();
        h.onCreate(getApplicationContext());
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        lvHistory.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> itemHashMap = (HashMap<String, Object>) parent.getItemAtPosition(position);
                int idSessionItem = Integer.valueOf(itemHashMap.get(SESSION_COLUMN_ID).toString());

                Intent intent = new Intent(getApplicationContext(), ActivityManagerSession.class);
                intent.putExtra(SESSION_COLUMN_ID, idSessionItem);
                startActivity(intent);
                finish();
            }
        });

        Log.d(MY_LOGS_TAG, "End ActManHis -> initVars()");
    }

    @Override
    public ContentValues getValues() {
        return null;
    }
}
