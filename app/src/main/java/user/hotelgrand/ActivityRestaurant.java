package user.hotelgrand;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityRestaurant extends ActionBarActivity implements View.OnClickListener, Utils {

    private ListView lvOrderList;
    private ArrayList<Map<String, Object>> data;

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        initVars();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] from = {"dish", "price"};
        int[] to = {R.id.tvOrderItemName, R.id.tvOrderItemPrice};
        MySimpleAdapter sAdapter = new MySimpleAdapter(this, data, R.layout.itemorder, from, to);
        lvOrderList.setAdapter(sAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bFirstDishes:
                openMenuDish("Перші страви");
                break;
            case R.id.bSecondDishes:
                openMenuDish("Другі страви");
                break;
            case R.id.bSalat:
                openMenuDish("Салати");
                break;
            case R.id.bGarnishes:
                openMenuDish("Гарніри");
                break;
            case R.id.bDessert:
                openMenuDish("Десерти");
                break;
            case R.id.bComplex:
                openMenuDish("Комплексні обіди");
                break;
            case R.id.bHotDrink:
                openMenuDish("Гарячі напої");
                break;
            case R.id.bJuice:
                openMenuDish("Соки");
                break;
            case R.id.bWaterNogaz:
                openMenuDish("Вода негазована");
                break;
            case R.id.bWaterGaz:
                openMenuDish("Вода газована");
                break;
            case R.id.bBeer:
                openMenuDish("Пиво");
                break;
            case R.id.bWine:
                /*openMenuDish("Вино");
                break;*/
                Intent iExpListView = new Intent(getApplicationContext(), ActivityExpListView.class);
                startActivity(iExpListView);
                break;
            case R.id.bConfirm:
                confirmOrder();
                finish();
                break;
        }
    }

    private void openMenuDish (String category){
        Intent intentDish = new Intent(this, ActivityDish.class);
        intentDish.putExtra("category", category);
        startActivityForResult(intentDish, 1);
    }

    private void confirmOrder () {
        int mustPay = 0;
        ContentValues cv = new ContentValues();
        Intent intent = getIntent();
        int idSession = intent.getIntExtra(dbHelper.SESSION_COLUMN_ID, 0);
        db = dbHelper.getWritableDatabase();
        SparseBooleanArray sbArray = lvOrderList.getCheckedItemPositions();
        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)) {
                HashMap<String, Object> itemHashMap =
                        (HashMap<String, Object>) lvOrderList.getItemAtPosition(key);
                String nameItem = itemHashMap.get("dish").toString();
                int costItem = Integer.valueOf(itemHashMap.get("price").toString());
                mustPay += costItem;
                String curentDate = (String) DateFormat.format("kk:mm:ss dd-MM-yyyy", new Date());

                cv.put(dbHelper.SESSION_COLUMN_ID, idSession);
                cv.put(dbHelper.HISTORY_COLUMN_NAME, nameItem);
                cv.put(dbHelper.HISTORY_COLUMN_COST, costItem);
                cv.put(dbHelper.HISTORY_COLUMN_DATE, curentDate);
                db.insert(dbHelper.DATABASE_TABLE_HISTORY, null, cv);
            }
        }
        Toast.makeText(this, "До оплати " + mustPay + " грн.", Toast.LENGTH_LONG).show();
        Intent result = new Intent();
        result.putExtra("mustPay", mustPay);
        setResult(RESULT_OK, result);
        cv.clear();
        cleanFields();
    }

    private void addDataToList (int resCode, Intent i, ArrayList<Map<String, Object>> list){
        if (resCode == RESULT_OK) {
            HashMap<String, Object> m = new HashMap<>();
            m.put("dish", i.getStringExtra("dishItem"));
            m.put("price", i.getIntExtra("priceItem", 0));
            list.add(m);
        } else
            Toast.makeText(this, "BAD", Toast.LENGTH_SHORT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
            switch (requestCode) {
                case 1:
                    addDataToList(resultCode, intent, data);
                    break;
                case 2:
                    addDataToList(resultCode, intent, data);
                    break;
                case 3:
                    addDataToList(resultCode, intent, data);
                    break;
                case 4:
                    addDataToList(resultCode, intent, data);
                    break;
                case 5:
                    addDataToList(resultCode, intent, data);
                    break;
                case 6:
                    addDataToList(resultCode, intent, data);
                    break;
                case 7:
                    addDataToList(resultCode, intent, data);
                    break;
                case 8:
                    addDataToList(resultCode, intent, data);
                    break;
                case 9:
                    addDataToList(resultCode, intent, data);
                    break;
                case 10:
                    addDataToList(resultCode, intent, data);
                    break;
                case 11:
                    addDataToList(resultCode, intent, data);
                    break;
                case 12:
                    addDataToList(resultCode, intent, data);
                    break;
            }
        }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void cleanFields() {
        if (db != null)
            db.close();
        if (dbHelper != null)
            dbHelper.close();
    }

    @Override
    public void initVars() {
        Button bFirstDishes = (Button) findViewById(R.id.bFirstDishes);
        bFirstDishes.setOnClickListener(this);
        Button bSecondDishes = (Button) findViewById(R.id.bSecondDishes);
        bSecondDishes.setOnClickListener(this);
        Button bSalat = (Button) findViewById(R.id.bSalat);
        bSalat.setOnClickListener(this);
        Button bGarnishes = (Button) findViewById(R.id.bGarnishes);
        bGarnishes.setOnClickListener(this);
        Button bDessert = (Button) findViewById(R.id.bDessert);
        bDessert.setOnClickListener(this);
        Button bComplex = (Button) findViewById(R.id.bComplex);
        bComplex.setOnClickListener(this);
        Button bHotDrink = (Button) findViewById(R.id.bHotDrink);
        bHotDrink.setOnClickListener(this);
        Button bJuice = (Button) findViewById(R.id.bJuice);
        bJuice.setOnClickListener(this);
        Button bWaterNogaz = (Button) findViewById(R.id.bWaterNogaz);
        bWaterNogaz.setOnClickListener(this);
        Button bWaterGaz = (Button) findViewById(R.id.bWaterGaz);
        bWaterGaz.setOnClickListener(this);
        Button bBeer = (Button) findViewById(R.id.bBeer);
        bBeer.setOnClickListener(this);
        Button bWine = (Button) findViewById(R.id.bWine);
        bWine.setOnClickListener(this);
        Button bConfirm = (Button) findViewById(R.id.bConfirm);
        bConfirm.setOnClickListener(this);

        lvOrderList = (ListView) findViewById(R.id.lvOrderList);
        lvOrderList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        dbHelper = new DBHelper(this);
        data = new ArrayList<>();
    }

    @Override
    public void getValues() {

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
}