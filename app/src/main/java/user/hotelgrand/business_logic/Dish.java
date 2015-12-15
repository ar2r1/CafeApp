package user.hotelgrand.business_logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import user.hotelgrand.MySimpleAdapter;
import user.hotelgrand.R;
import user.hotelgrand.database.DBConnection;
import user.hotelgrand.database.DishDBTable;
import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.DatabaseFunctionInterface;

public class Dish implements DatabaseFunctionInterface, DatabaseConstantsInterface {

    private DishDBTable dishTable;
    private SQLiteDatabase db;
    private DBConnection connection;

    public ArrayList<Map<String, Object>> data;

    public Dish () {}

    public void onCreate(Context context){
        Log.d(MY_LOGS_TAG, "Call Dish -> onCreate(Context)");

        dishTable = new DishDBTable();
        connection = new DBConnection();
        db = connection.openConnection(db, context);

        Log.d(MY_LOGS_TAG, "End Dish -> onCreate(Context)");
    }

    public void onDestroy() {
        Log.d(MY_LOGS_TAG, "Dish Call -> onDestroy()");

        connection.closeConnection(db);

        Log.d(MY_LOGS_TAG, "Dish End -> onDestroy()");
    }

    @Override
    public void addData(ContentValues cv) {
        Log.d(MY_LOGS_TAG, "Call Dish -> addData()");

        dishTable.insertToDatabase(db, cv);

        Log.d(MY_LOGS_TAG, "End Dish -> addData()");
    }

    @Override
    public void deleteData(String dish) {
        Log.d(MY_LOGS_TAG, "Dish Call -> deleteData()");

        dishTable.deleteFromDatabase(db, dish);

        Log.d(MY_LOGS_TAG, "Dish End -> deleteData()");
    }

    @Override
    public void showData(ListView lv, Context context) {
        Log.d(MY_LOGS_TAG, "Call Dish -> showData()");

        int dishMenuColIndex, descMenuColIndex, priceMenuColIndex, categoryMenuColIndex;
        Cursor cursor = dishTable.selectFromDatabase(db);
        data = new ArrayList<>();
        dishMenuColIndex = cursor.getColumnIndex(MENU_COLUMN_DISH);
        descMenuColIndex = cursor.getColumnIndex(MENU_COLUMN_DESC);
        priceMenuColIndex = cursor.getColumnIndex(MENU_COLUMN_PRICE);
        categoryMenuColIndex = cursor.getColumnIndex(CATEGORY_COLUMN_NAME);

        String[] from = new String[]{CATEGORY_COLUMN_NAME, MENU_COLUMN_DISH,
                MENU_COLUMN_DESC, MENU_COLUMN_PRICE};
        int[] to = {R.id.tvCategoryItemManagerMenu, R.id.tvDishItemManagerMenu,
                R.id.tvDescriptionItemManagerMenu, R.id.tvPriceItemManagerMenu};
        MySimpleAdapter sAdapter = new MySimpleAdapter(context, data, R.layout.item_manager_menu, from, to);
        lv.setAdapter(sAdapter);

        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> m = new HashMap<>();
                m.put(CATEGORY_COLUMN_NAME, cursor.getString(categoryMenuColIndex));
                m.put(MENU_COLUMN_DISH, cursor.getString(dishMenuColIndex));
                m.put(MENU_COLUMN_DESC, cursor.getString(descMenuColIndex));
                m.put(MENU_COLUMN_PRICE, cursor.getInt(priceMenuColIndex));
                data.add(m);
            } while (cursor.moveToNext());
        } else
            Toast.makeText(context, "немає записів", Toast.LENGTH_SHORT).show();
        if (!cursor.isClosed())
            cursor.close();

        Log.d(MY_LOGS_TAG, "End Dish -> showData()");
    }

}
