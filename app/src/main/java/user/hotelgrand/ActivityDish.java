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


public class ActivityDish extends ActionBarActivity implements Utils {

    private final String DISH_ITEM = "dishItem";
    private final String DESC_ITEM = "descriptionItem";
    private final String PRICE_ITEM = "priceItem";

    private ListView lvListDish;
    private ArrayList<Map<String, Object>> data;

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        initVars();
        showData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cleanFields();
        if (db != null)
            db.close();
        if (dbHelper != null)
            dbHelper.close();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void cleanFields() {

    }

    @Override
    public void initVars() {
        lvListDish = (ListView) findViewById(R.id.lvDish);
        lvListDish.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvListDish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> itemHashMap = (HashMap<String, Object>) parent.getItemAtPosition(position);
                String dishItem = itemHashMap.get("dish").toString();
                String descriptionItem = itemHashMap.get("description").toString();
                int priceItem = Integer.valueOf(itemHashMap.get("price").toString());

                Intent intent = new Intent();
                intent.putExtra(DISH_ITEM, dishItem);
                intent.putExtra(DESC_ITEM, descriptionItem);
                intent.putExtra(PRICE_ITEM, priceItem);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        data = new ArrayList<>();
    }

    @Override
    public void showData() {
        int dishColIndex, descColIndex, priceColIndex;
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        Cursor cursor = dbHelper.selectDishes(db, category);

        dishColIndex = cursor.getColumnIndex(dbHelper.MENU_COLUMN_DISH);
        descColIndex = cursor.getColumnIndex(dbHelper.MENU_COLUMN_DESC);
        priceColIndex = cursor.getColumnIndex(dbHelper.MENU_COLUMN_PRICE);
        String[] from = {dbHelper.MENU_COLUMN_DISH, dbHelper.MENU_COLUMN_DESC,
                dbHelper.MENU_COLUMN_PRICE};
        int[] to = {R.id.tvItemName, R.id.tvItemDescription, R.id.tvItemPrice};
        MySimpleAdapter sAdapter = new MySimpleAdapter(this, data, R.layout.itemdish, from, to);
        lvListDish.setAdapter(sAdapter);

        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> m = new HashMap<>();
                m.put(dbHelper.MENU_COLUMN_DISH, cursor.getString(dishColIndex));
                m.put(dbHelper.MENU_COLUMN_DESC, cursor.getString(descColIndex));
                m.put(dbHelper.MENU_COLUMN_PRICE, cursor.getInt(priceColIndex));
                data.add(m);
            } while (cursor.moveToNext());
        } else
            Toast.makeText(getApplicationContext(), "немає записів", Toast.LENGTH_SHORT).show();
        if (!cursor.isClosed())
            cursor.close();
    }

}
