package user.hotelgrand;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ActivityManagerUser extends ActionBarActivity implements View.OnClickListener, Utils {

    private EditText etFirstNameManagerUser, etSecondNameManagerUser, etLoginManagerUser,
            etPasswordManagerUser;
    private ListView lvManagerUser;
    private ArrayList<Map<String, Object>> data;

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private ContentValues cv;
    private String firstName, secondName, login, password;


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
                addData();
                Toast.makeText(this, "Успішно додано", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bShowManagerUser:
                showData();
                Toast.makeText(getApplicationContext(), "Список оновлено", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bDeleteManagerUser:
                deleteData();
                Toast.makeText(getApplicationContext(), "Успішно видалено", Toast.LENGTH_SHORT).show();
                break;
        }
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
        if (cv != null)
            cv.clear();
    }

    @Override
    public void initVars() {
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

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        data = new ArrayList<>();
    }

    @Override
    public void getValues() {
        firstName = etFirstNameManagerUser.getText().toString();
        secondName = etSecondNameManagerUser.getText().toString();
        login = etLoginManagerUser.getText().toString();
        password = etPasswordManagerUser.getText().toString();
    }

    @Override
    public void addData() {
        cv = new ContentValues();
        getValues();
        dbHelper.addToDatabase(firstName, secondName, login, password, db, cv);
        cleanFields();
    }

    @Override
    public void showData() {

        int fnameUserColIndex, snameUserColIndex, loginUserColIndex, passwordUserColIndex;
        Cursor cursor = dbHelper.selectManagerUser(db);
        data = new ArrayList<>();
        fnameUserColIndex = cursor.getColumnIndex(dbHelper.USER_COLUMN_FNAME);
        snameUserColIndex = cursor.getColumnIndex(dbHelper.USER_COLUMN_SNAME);
        loginUserColIndex = cursor.getColumnIndex(dbHelper.USER_COLUMN_LOGIN);
        passwordUserColIndex = cursor.getColumnIndex(dbHelper.USER_COLUMN_PASSWORD);

        String[] from = {dbHelper.USER_COLUMN_FNAME, dbHelper.USER_COLUMN_SNAME,
                dbHelper.USER_COLUMN_LOGIN, dbHelper.USER_COLUMN_PASSWORD};
        int[] to = {R.id.tvFirstNameManagerUser, R.id.tvSecondNameManagerUser,
                R.id.tvLoginManagerUser, R.id.tvPasswordManagerUser};
        MySimpleAdapter sAdapter = new MySimpleAdapter(this, data, R.layout.item_manager_user, from, to);
        lvManagerUser.setAdapter(sAdapter);

        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> m = new HashMap<>();
                m.put(dbHelper.USER_COLUMN_FNAME, cursor.getString(fnameUserColIndex));
                m.put(dbHelper.USER_COLUMN_SNAME, cursor.getString(snameUserColIndex));
                m.put(dbHelper.USER_COLUMN_LOGIN, cursor.getString(loginUserColIndex));
                m.put(dbHelper.USER_COLUMN_PASSWORD, cursor.getString(passwordUserColIndex));
                data.add(m);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed())
            cursor.close();
    }

    @Override
    public void deleteData() {
        SparseBooleanArray sbArray = lvManagerUser.getCheckedItemPositions();
        for (int i = 0;  i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)) {
                HashMap<String, Object> itemHashMap = (HashMap<String, Object>) lvManagerUser.getItemAtPosition(key);
                login = itemHashMap.get(dbHelper.USER_COLUMN_LOGIN).toString();
                dbHelper.deleteFromDatabase(db, dbHelper.DATABASE_TABLE_USER,
                        dbHelper.USER_COLUMN_LOGIN, login);
            }
        }
    }
}
