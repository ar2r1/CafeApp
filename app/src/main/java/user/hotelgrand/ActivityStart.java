package user.hotelgrand;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ActivityStart extends ActionBarActivity implements Utils {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    EditText etFirstUserLogin, etFirstUserPassword, etSecondUserLogin, etSecondUserPassword;
    TextView tvFirstOk, tvSecondOk;
    String loginFUser, loginSUser, passFUSer, passSUser;
    private int idFirstUser, idSecondUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initVars();
    }

    @Override
    public void cleanFields() {
        if (etFirstUserPassword.getText().length() != 0)
            etFirstUserPassword.setText("");
        if (etSecondUserPassword.getText().length() != 0)
            etSecondUserPassword.setText("");
        if (tvFirstOk.getText().toString().equals("Ok"))
            tvFirstOk.setText("");
        if (tvSecondOk.getText().toString().equals(""))
            tvSecondOk.setText("");
    }
    private void closeDatabaseConect (Cursor c1, Cursor c2) {
        if (db.isOpen())
            db.close();
        if (dbHelper != null)
            dbHelper.close();
        if (!c1.isClosed())
            c1.close();
        if (!c2.isClosed())
            c2.close();
    }

    @Override
    public void initVars() {
        Button bCompleteChooseUser = (Button) findViewById(R.id.bCompleteChooseUser);
        bCompleteChooseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
                equalsLogin();
            }
        });
        Button bCallManager = (Button) findViewById(R.id.bCallManager);
        bCallManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCallManager = new Intent (getApplicationContext(), ActivityManager.class);
                startActivity(iCallManager);
            }
        });
        etFirstUserLogin = (EditText) findViewById(R.id.etFirstUserLogin);
        etFirstUserPassword = (EditText) findViewById(R.id.etFirstUserPassword);
        etSecondUserLogin = (EditText) findViewById(R.id.etSecondUserLogin);
        etSecondUserPassword = (EditText) findViewById(R.id.etSecondUserPassword);
        tvFirstOk = (TextView) findViewById(R.id.tvFirstOk);
        tvSecondOk = (TextView) findViewById(R.id.tvSecondOk);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void getValues(){
        loginFUser = etFirstUserLogin.getText().toString();
        passFUSer = etFirstUserPassword.getText().toString();
        loginSUser = etSecondUserLogin.getText().toString();
        passSUser = etSecondUserPassword.getText().toString();
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

    private void equalsLogin () {
        Cursor cursorFirstUser = dbHelper.authorizationUser(db, loginFUser);
        Cursor cursorSecondUser = dbHelper.authorizationUser(db, loginSUser);

        int idColIndex = cursorFirstUser.getColumnIndex(dbHelper.USER_COLUMN_ID);
        int loginColIndex = cursorFirstUser.getColumnIndex(dbHelper.USER_COLUMN_LOGIN);
        int passwordColIndex = cursorFirstUser.getColumnIndex(dbHelper.USER_COLUMN_PASSWORD);

        cleanFields();
        if (cursorFirstUser.moveToFirst()) {
            if (loginFUser.equals(cursorFirstUser.getString(loginColIndex))) {
                if (passFUSer.equals(cursorFirstUser.getString(passwordColIndex))) {
                    tvFirstOk.setText("Ok");
                    tvFirstOk.setTextColor(Color.GREEN);
                    idFirstUser = cursorFirstUser.getInt(idColIndex);
                } else {
                    tvFirstOk.setText("Bad");
                    tvFirstOk.setTextColor(Color.RED);
                }
            }
        }
        if (cursorSecondUser.moveToFirst()) {
            if (loginSUser.equals(cursorSecondUser.getString(loginColIndex))) {
                if (passSUser.equals(cursorSecondUser.getString(passwordColIndex))) {
                    tvSecondOk.setText("Ok");
                    tvSecondOk.setTextColor(Color.GREEN);
                    idSecondUser = cursorSecondUser.getInt(idColIndex);
                } else {
                    tvSecondOk.setText("Bad");
                    tvSecondOk.setTextColor(Color.RED);
                }
            }
        }
        if ("Ok".equals(tvFirstOk.getText().toString()) && "Ok".equals(tvSecondOk.getText().toString())) {
            Intent intent = new Intent(this, ActivityMain.class);
            intent.putExtra("id_user1", idFirstUser);
            intent.putExtra("id_user2", idSecondUser);
            startActivity(intent);
            closeDatabaseConect(cursorFirstUser, cursorSecondUser);
        }
        etFirstUserPassword.setText("");
        etSecondUserPassword.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cleanFields();
        if (db != null)
            db.close();
        if (dbHelper != null)
            dbHelper.close();
        finish();
    }
}