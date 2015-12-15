package user.hotelgrand;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import user.hotelgrand.business_logic.Dish;
import user.hotelgrand.database.DBHelper;
import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.Utils;

public class ActivityManagerMenu extends ActionBarActivity implements View.OnClickListener, Utils, DatabaseConstantsInterface {

    private EditText etNameDishManagerMenu, etDescDishManagerMenu, etPriceDishManagerMenu;
    private Spinner spinner;
    private ListView lvManagerMenu;

    private String dish, description;
    private int id_category, price;
    private Dish d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_menu);

        initVars();
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bAddDishManagerMenu:
                    Log.d(MY_LOGS_TAG, "Call ActManMenu -> add()");

                    ContentValues contentValues = getValues();
                    d.addData(contentValues);
                    cleanFields();
                    Toast.makeText(getApplicationContext(), "Успішно додано", Toast.LENGTH_SHORT).show();

                    Log.d(MY_LOGS_TAG, "End ActManMenu -> add()");
                    break;

                case R.id.bShowManagerMenu:
                    Log.d(MY_LOGS_TAG, "Call ActManMenu -> show()");

                    d.showData(lvManagerMenu, getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Список оновлено", Toast.LENGTH_SHORT).show();

                    Log.d(MY_LOGS_TAG, "End ActManMenu -> show()");
                    break;

                case R.id.bDeleteManagerMenu:
                    Log.d(MY_LOGS_TAG, "Call ActManMenu -> delete()");

                    detectFieldToDelete();
                    Toast.makeText(getApplicationContext(), "Успішно видалено", Toast.LENGTH_SHORT).show();

                    Log.d(MY_LOGS_TAG, "End ActManMenu -> delete()");
                    break;
            }
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cleanFields();
        d.onDestroy();
        finish();
    }

    @Override
    public void cleanFields() {
        if (etNameDishManagerMenu.getText().length() != 0)
            etNameDishManagerMenu.setText("");
        if (etDescDishManagerMenu.getText().length() != 0)
            etDescDishManagerMenu.setText("");
        if (etPriceDishManagerMenu.getText().length() != 0)
            etPriceDishManagerMenu.setText("");
    }

    @Override
    public void initVars() {
        Log.d(MY_LOGS_TAG, "Call ActManMenu -> initVars()");

        Button bAdd = (Button) findViewById(R.id.bAddDishManagerMenu);
        bAdd.setOnClickListener(this);
        Button bShow = (Button) findViewById(R.id.bShowManagerMenu);
        bShow.setOnClickListener(this);
        Button bDelete = (Button) findViewById(R.id.bDeleteManagerMenu);
        bDelete.setOnClickListener(this);

        etNameDishManagerMenu = (EditText) findViewById(R.id.etNameDishManagerMenu);
        etDescDishManagerMenu = (EditText) findViewById(R.id.etDescDishManagerMenu);
        etPriceDishManagerMenu = (EditText) findViewById(R.id.etPriceDishManagerMenu);

        spinner = (Spinner) findViewById(R.id.spinCategoryManagerDish);
        ArrayAdapter<CharSequence> adapterResources = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterResources);

        lvManagerMenu = (ListView) findViewById(R.id.lvManagerMenu);
        lvManagerMenu.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        d = new Dish();
        d.onCreate(this);
        Log.d(MY_LOGS_TAG, "End ActManMenu -> initVars()");
    }

    @Override
    public ContentValues getValues() {
        Log.d(MY_LOGS_TAG, "Call ActManMenu -> getValues()");

        ContentValues cv = new ContentValues();
        dish = etNameDishManagerMenu.getText().toString();
        description = etDescDishManagerMenu.getText().toString();
        price = Integer.valueOf(etPriceDishManagerMenu.getText().toString());
        id_category = spinner.getSelectedItemPosition() + 1;

        cv.put(MENU_COLUMN_DISH, dish);
        cv.put(MENU_COLUMN_DESC, description);
        cv.put(MENU_COLUMN_PRICE, price);
        cv.put(CATEGORY_COLUMN_ID, id_category);

        Log.d(MY_LOGS_TAG, "End ActManMenu -> initVars()");
        return cv;
    }

    private void detectFieldToDelete () {
        SparseBooleanArray sbArray = lvManagerMenu.getCheckedItemPositions();
        for (int i = 0;  i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)) {
                HashMap<String, Object> itemHashMap = (HashMap<String, Object>) lvManagerMenu.getItemAtPosition(key);
                dish = itemHashMap.get(MENU_COLUMN_DISH).toString();
                d.deleteData(dish);
            }
        }
    }

}
