package user.hotelgrand;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import user.hotelgrand.database.DBHelper;

public class ActivityExpListView extends ActionBarActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;

    ExpandableListView expListDish;
    ArrayList<String> childDataIt;
    ArrayList<ArrayList<String>> childDa;

    ArrayList<Map<String, Object>> groupData;
    ArrayList<Map<String, Object>> childDataItem;
    ArrayList<ArrayList<Map<String, Object>>> childData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_list_view);

        initVars();
    }

    private void initVars() {
        expListDish = (ExpandableListView) findViewById(R.id.expListDish);
        groupData = new ArrayList<>();
        childDataItem = new ArrayList<>();
        childData = new ArrayList<>();
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        showData();
    }

    private void showData () {

        createGroupDish(groupData);
        String groupFrom[] = new String[] {dbHelper.CATEGORY_COLUMN_NAME};
        int groupTo[] = new int[] {android.R.id.text1};

        /*Resources resources = getResources();
        String[] attributes = resources.getStringArray(R.array.category);
        for (int i = 0; i < attributes.length; i++) {
            childData.add(insertAttributes(childDataItem, attributes[i]));
        }*/
        childDataIt.add("child1");
        childDataIt.add("child2");
        childDa.add(childDataIt);

        childDataIt.add("child3");
        childDataIt.add("child4");
        childDataIt.add("child5");
        childDa.add(childDataIt);

        String childFrom[] = new String [] {dbHelper.MENU_COLUMN_DISH};
        int childTo[] = new int[] {android.R.id.text1};

        /*SimpleExpandableListAdapter sAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);*/

        //MyExpandableListAdapter sAdapter = new MyExpandableListAdapter(getApplicationContext(), groupData);
        //expListDish.setAdapter(sAdapter);
    }

    private ArrayList<Map<String, Object>> createGroupDish (ArrayList<Map<String, Object>> group) {
        Cursor c = dbHelper.getGroupDish(db);
        int nameCategory = c.getColumnIndex(dbHelper.CATEGORY_COLUMN_NAME);

        if (c.moveToFirst()) {
            do {
                Map<String, Object> m = new HashMap<>();
                m.put(dbHelper.CATEGORY_COLUMN_NAME, c.getString(nameCategory));
                group.add(m);
            } while (c.moveToNext());
        } else Toast.makeText(this, "CreateGroup cursor is empty", Toast.LENGTH_SHORT).show();
        return group;
    }

    private ArrayList<Map<String, Object>> insertAttributes (ArrayList<Map<String, Object>> childD, String attr) {
        Cursor cursor = dbHelper.selectDishes(db, attr);
        childD = new ArrayList<>();
        int dishColIndex = cursor.getColumnIndex(dbHelper.MENU_COLUMN_DISH);

        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> m = new HashMap<>();
                m.put(dbHelper.MENU_COLUMN_DISH, cursor.getString(dishColIndex));
                childD.add(m);
            } while (cursor.moveToNext());
        } else Toast.makeText(this, "insertAttr Cursor is empty", Toast.LENGTH_SHORT).show();

        return childD;
    }


}
