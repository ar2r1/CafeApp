package user.hotelgrand;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Date;

import user.hotelgrand.database.DBHelper;
import user.hotelgrand.interfaces.Utils;


public class ActivityMain extends ActionBarActivity implements Utils {

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private int idSession, income;
    private String beginSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVars();
        Cursor cursorSession = dbHelper.getIdSession(db);
        cursorSession.moveToFirst();
        int idSessionColIndex = cursorSession.getColumnIndex("max");
        idSession = cursorSession.getInt(idSessionColIndex) + 1;
        beginSession = (String) DateFormat.format("kk:mm:ss dd-MM-yyyy", new Date());
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                income = income + intent.getIntExtra("mustPay", 1);
                Toast.makeText(getApplicationContext(), "Надійшло " + income,
                        Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) Toast.makeText(getApplicationContext(),
                    "Операція відмінена", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cleanFields();
        finish();
    }

    @Override
    public void cleanFields() {
        if (db.isOpen())
            db.close();
        if (dbHelper != null)
            dbHelper.close();
    }

    @Override
    public void initVars() {
        ImageButton ibRestaurant = (ImageButton) findViewById(R.id.ibRestaurant);
        ibRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRestaurant = new Intent(getApplicationContext(), ActivityRestaurant.class);
                iRestaurant.putExtra("id_session", idSession);
                startActivityForResult(iRestaurant, 1);
            }
        });
        Button bEndSession = (Button) findViewById(R.id.bEndSession);
        bEndSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endSession();
            }
        });

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        income = 0;
    }

    private void endSession() {
        ContentValues cv = new ContentValues();
        Intent intent = getIntent();
        int id_user1 = intent.getIntExtra(dbHelper.SESSION_COLUMN_ID_USER1, 0);
        int id_user2 = intent.getIntExtra(dbHelper.SESSION_COLUMN_ID_USER2, 0);
        String endSession = (String) DateFormat.format("kk:mm:ss dd-MM-yyyy", new Date());

        cv.put(dbHelper.SESSION_COLUMN_ID, idSession);
        cv.put(dbHelper.SESSION_COLUMN_DATE_BEGIN, beginSession);
        cv.put(dbHelper.SESSION_COLUMN_DATE_END, endSession);
        cv.put(dbHelper.SESSION_COLUMN_ID_USER1, id_user1);
        cv.put(dbHelper.SESSION_COLUMN_ID_USER2, id_user2);
        cv.put(dbHelper.SESSION_COLUMN_INCOME, income);
        db.insert(dbHelper.DATABASE_TABLE_SESSION, null, cv);
        Toast.makeText(getApplicationContext(), "Зміна закінчена. Прибуток " + income, Toast.LENGTH_LONG);
        cv.clear();
        cleanFields();
        finish();
    }

    @Override
    public void addData() {

    }

    @Override
    public void deleteData() {

    }

    @Override
    public void showData() {

    }

    @Override
    public void getValues() {

    }
}
