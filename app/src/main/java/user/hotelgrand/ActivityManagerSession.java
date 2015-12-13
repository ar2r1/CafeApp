package user.hotelgrand;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import user.hotelgrand.business_logic.Session;
import user.hotelgrand.database.DBHelper;
import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.Utils;

public class ActivityManagerSession extends ActionBarActivity implements Utils, DatabaseConstantsInterface {

    private ListView lvSession;
    private Session s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MY_LOGS_TAG, "ActManSession Call onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_session);

        initVars();
        s.showData(lvSession, getApplicationContext());

        Log.d(MY_LOGS_TAG, "ActManSession End -> onCreate()");
    }

    @Override
    public void cleanFields() {

    }

    @Override
    public void initVars() {
        Log.d(MY_LOGS_TAG, "ActManSession Call -> initVars()");

        lvSession = (ListView) findViewById(R.id.lvManagerSession);
        lvSession.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        s = new Session();
        s.onCreate(getApplicationContext());

        Log.d(MY_LOGS_TAG, "ActManSession End -> initVars()");
    }

    @Override
    public ContentValues getValues() {
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cleanFields();
        s.closeConnection();
        finish();
    }

}

