package user.hotelgrand;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;

import user.hotelgrand.business_logic.User;
import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.Utils;


public class ActivityManagerUser extends ActionBarActivity implements View.OnClickListener, Utils, DatabaseConstantsInterface{

    private EditText etFirstNameManagerUser, etSecondNameManagerUser, etLoginManagerUser,
            etPasswordManagerUser;
    private ListView lvManagerUser;
    private String firstName, secondName, login, password;
    private User u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user);

        initVars();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bAddUserManagerUser:
                Log.d(MY_LOGS_TAG, "Call ActManUser -> addData()");
                ContentValues contentValues = getValues();
                u.addData(contentValues);
                cleanFields();
                Toast.makeText(this, "Успішно додано", Toast.LENGTH_SHORT).show();
                Log.d(MY_LOGS_TAG, "End ActManUser -> addData()");
                break;

            case R.id.bShowManagerUser:
                Log.d(MY_LOGS_TAG, "Call ActManUser -> showData()");
                u.showData(lvManagerUser, this);
                Toast.makeText(getApplicationContext(), "Список оновлено", Toast.LENGTH_SHORT).show();
                Log.d(MY_LOGS_TAG, "End ActManUser -> showData()");
                break;

            case R.id.bDeleteManagerUser:
                Log.d(MY_LOGS_TAG, "Call ActManUser -> deleteData()");
                detectFieldToDelete();
                Toast.makeText(getApplicationContext(), "Успішно видалено", Toast.LENGTH_SHORT).show();
                Log.d(MY_LOGS_TAG, "End ActManUser -> deleteData()");
                break;
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
        if (etFirstNameManagerUser .getText().length() != 0)
            etFirstNameManagerUser.setText("");
        if (etSecondNameManagerUser.getText().length() != 0)
            etSecondNameManagerUser.setText("");
        if (etLoginManagerUser.getText().length() != 0)
            etLoginManagerUser.setText("");
        if (etPasswordManagerUser.getText().length() != 0)
            etPasswordManagerUser.setText("");
        u.closeConnection();
    }

    @Override
    public void initVars() {
        Log.d(MY_LOGS_TAG, "Call ActManUser -> initVars()");

        Button bAdd = (Button) findViewById(R.id.bAddUserManagerUser);
        bAdd.setOnClickListener(this);
        Button bShow = (Button) findViewById(R.id.bShowManagerUser);
        bShow.setOnClickListener(this);
        Button bDelete = (Button) findViewById(R.id.bDeleteManagerUser);
        bDelete.setOnClickListener(this);

        etFirstNameManagerUser = (EditText) findViewById(R.id.etFirstNameManagerUser);
        etSecondNameManagerUser = (EditText) findViewById(R.id.etSecondNameManagerUser);
        etLoginManagerUser = (EditText) findViewById(R.id.etLoginManagerUser);
        etPasswordManagerUser = (EditText) findViewById(R.id.etPasswordMangerUser);

        lvManagerUser = (ListView) findViewById(R.id.lvUserManagerUser);
        lvManagerUser.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        u.onCreate(this);
        Log.d(MY_LOGS_TAG, "End ActManUser -> initVars()");
    }

    @Override
    public ContentValues getValues() {
        Log.d(MY_LOGS_TAG, "Call ActManUser -> getValues()");
        ContentValues cv = new ContentValues();
        firstName = etFirstNameManagerUser.getText().toString();
        secondName = etSecondNameManagerUser.getText().toString();
        login = etLoginManagerUser.getText().toString();
        password = etPasswordManagerUser.getText().toString();
        cv.put(USER_COLUMN_FNAME, firstName);
        cv.put(USER_COLUMN_SNAME, secondName);
        cv.put(USER_COLUMN_LOGIN, login);
        cv.put(USER_COLUMN_PASSWORD, password);

        Log.d(MY_LOGS_TAG, "End ActManUser -> getValues()");

        return cv;
    }

    private void detectFieldToDelete() {
        SparseBooleanArray sbArray = lvManagerUser.getCheckedItemPositions();
        for (int i = 0;  i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)) {
                HashMap<String, Object> itemHashMap = (HashMap<String, Object>) lvManagerUser.getItemAtPosition(key);
                login = itemHashMap.get(USER_COLUMN_LOGIN).toString();
                u.deleteData(login);
            }
        }
    }
}
