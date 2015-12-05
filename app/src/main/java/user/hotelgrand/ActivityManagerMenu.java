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

public class ActivityManagerMenu extends ActionBarActivity implements View.OnClickListener, Utils {

    private EditText etNameDishManagerMenu, etDescDishManagerMenu, etPriceDishManagerMenu;
    private Spinner spinner;
    private ListView lvManagerMenu;
    private ArrayList<Map<String, Object>> data;

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private ContentValues cv;

    private String dish, description;
    private int id_category, price;

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
                    addData();
                    Toast.makeText(getApplicationContext(), "Успішно додано", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.bShowManagerMenu:
                    showData();
                    Toast.makeText(getApplicationContext(), "Список оновлено", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.bDeleteManagerMenu:
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
        if (etNameDishManagerMenu.getText().length() != 0)
            etNameDishManagerMenu.setText("");
        if (etDescDishManagerMenu.getText().length() != 0)
            etDescDishManagerMenu.setText("");
        if (etPriceDishManagerMenu.getText().length() != 0)
            etPriceDishManagerMenu.setText("");
        if (cv != null)
            cv.clear();
    }

    @Override
    public void initVars() {
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

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        data = new ArrayList<>();
    }

    @Override
    public void getValues() {
        dish = etNameDishManagerMenu.getText().toString();
        description = etDescDishManagerMenu.getText().toString();
        price = Integer.valueOf(etPriceDishManagerMenu.getText().toString());
        id_category = spinner.getSelectedItemPosition() + 1;
    }

    @Override
    public void addData() {
        cv = new ContentValues();
        getValues();
        dbHelper.addToDatabase(dish, description, price, id_category, db, cv);
        cleanFields();
    }

    @Override
    public void showData() {
        int dishMenuColIndex, descMenuColIndex, priceMenuColIndex, categoryMenuColIndex;
        Cursor cursor = dbHelper.selectManagerDishes(db);
        data.clear();
        dishMenuColIndex = cursor.getColumnIndex(dbHelper.MENU_COLUMN_DISH);
        descMenuColIndex = cursor.getColumnIndex(dbHelper.MENU_COLUMN_DESC);
        priceMenuColIndex = cursor.getColumnIndex(dbHelper.MENU_COLUMN_PRICE);
        categoryMenuColIndex = cursor.getColumnIndex(dbHelper.CATEGORY_COLUMN_NAME);

        String[] from = {dbHelper.CATEGORY_COLUMN_NAME, dbHelper.MENU_COLUMN_DISH,
                dbHelper.MENU_COLUMN_DESC, dbHelper.MENU_COLUMN_PRICE};
        int[] to = {R.id.tvCategoryItemManagerMenu, R.id.tvDishItemManagerMenu,
                R.id.tvDescriptionItemManagerMenu, R.id.tvPriceItemManagerMenu};
        MySimpleAdapter sAdapter = new MySimpleAdapter(this, data, R.layout.item_manager_menu, from, to);
        lvManagerMenu.setAdapter(sAdapter);

        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> m = new HashMap<>();
                m.put(dbHelper.CATEGORY_COLUMN_NAME, cursor.getString(categoryMenuColIndex));
                m.put(dbHelper.MENU_COLUMN_DISH, cursor.getString(dishMenuColIndex));
                m.put(dbHelper.MENU_COLUMN_DESC, cursor.getString(descMenuColIndex));
                m.put(dbHelper.MENU_COLUMN_PRICE, cursor.getInt(priceMenuColIndex));
                data.add(m);
            } while (cursor.moveToNext());
        } else
            Toast.makeText(getApplicationContext(), "немає записів", Toast.LENGTH_SHORT).show();
        if (!cursor.isClosed())
            cursor.close();
    }

    @Override
    public void deleteData() {
        SparseBooleanArray sbArray = lvManagerMenu.getCheckedItemPositions();
        for (int i = 0;  i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)) {
                Log.d("myLogs", "" + key);
                HashMap<String, Object> itemHashMap = (HashMap<String, Object>) lvManagerMenu.getItemAtPosition(key);
                dish = itemHashMap.get(dbHelper.MENU_COLUMN_DISH).toString();
                dbHelper.deleteFromDatabase(db, dbHelper.DATABASE_TABLE_MENU,
                        dbHelper.MENU_COLUMN_DISH, dish);
            }
        }
    }
}
