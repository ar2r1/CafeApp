package user.hotelgrand.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import user.hotelgrand.interfaces.DatabaseConstantsInterface;
import user.hotelgrand.interfaces.DishTableInterface;

public class DishDBTable implements DishTableInterface, DatabaseConstantsInterface {

    public DishDBTable () {}
    
    @Override
    public void createTable(SQLiteDatabase db) {
        Log.d(MY_LOGS_TAG, "DishDBTable Call -> createTable()");

        db.execSQL("create table " + DATABASE_TABLE_CATEGORY + " (" +
                CATEGORY_COLUMN_ID + " integer primary key autoincrement, " +
                CATEGORY_COLUMN_NAME + " text);");

        db.execSQL("create table " + DATABASE_TABLE_MENU + " ( " +
                MENU_COLUMN_ID + " integer primary key autoincrement, " +
                MENU_COLUMN_DISH + " text, " +
                MENU_COLUMN_DESC + " text, " +
                MENU_COLUMN_PRICE + "  integer, " +
                CATEGORY_COLUMN_ID + " integer);");
        firstInsertData(db);

        Log.d(MY_LOGS_TAG, "DishDBTable End -> createTable()");
    }

    @Override
    public void deleteFromDatabase(SQLiteDatabase datab, String table, String i, String d) {
        Log.d(MY_LOGS_TAG, "DishDBTable Call -> deleteFromDatabase()");

        String query = "delete from " + table + " where " + i + " like '" + d + "';";
        datab.execSQL(query);

        Log.d(MY_LOGS_TAG, "DishDBTable End -> deleteFromDatabase()");
    }

    @Override
    public void firstInsertData(SQLiteDatabase db) {
        Log.d(MY_LOGS_TAG, "DishDBTable Call -> firstInsert()");

        db.execSQL("insert into category (id_category, name)values (1, 'Перші страви'); ");
        db.execSQL("insert into category (id_category, name)values (2, 'Другі страви'); ");
        db.execSQL("insert into category (id_category, name)values (3, 'Салати'); " );
        db.execSQL("insert into category (id_category, name)values (4, 'Гарніри'); " );
        db.execSQL("insert into category (id_category, name)values (5, 'Десерти'); " );
        db.execSQL("insert into category (id_category, name)values (6, 'Комплексні обіди'); " );
        db.execSQL("insert into category (id_category, name)values (7, 'Гарячі напої'); " );
        db.execSQL("insert into category (id_category, name)values (8, 'Соки'); " );
        db.execSQL("insert into category (id_category, name)values (9, 'Негазована вода'); " );
        db.execSQL("insert into category (id_category, name)values (10, 'Газована вода'); " );
        db.execSQL("insert into category (id_category, name)values (11, 'Пиво'); " );
        db.execSQL("insert into category (id_category, name)values (12, 'Вино'); ");
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (1, 1, 'Борщ український', '300г', 10); ");
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (2, 1, 'Суп грибний', '300г', 13); " );
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (3, 1, 'Бульйон з пельменями', '300г/600г', 10); " );
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (4, 1, 'Хліб', '1 кус', 1); " );
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (5, 2, 'Відбивна', '(свинина) 100г', 20); " );
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (6, 2, 'Відбивна', '(куряча) 100г', 18); " );
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (7, 3, 'Осінь', 'буряк, морква, капуста, помідори,майонез, горошок', 15); ");
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (8, 3, 'Особливий', 'перець, цибуля, ковбаса, яйце, сир', 16); " );
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (9, 4, 'Картопля фрі', '100г', 12); " );
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (10, 4, 'Картопляне пюре', '200г', 10); " );
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (11, 5, 'Блінчики з сиром', '(2 шт) 150г', 16); " );
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (12, 5, 'Блінчики з яблуками', '(2 шт) 150г', 16); " );
        db.execSQL("insert into menu (id_menu, id_category, dish, description, price) " +
                "values (13, 6, 'Комплекс 1', 'Борщ, пюре, відбивна, салат капустяний, сік, хліб', 44); ");

        Log.d(MY_LOGS_TAG, "UserDBTable End -> firstInsert()");
    }

    public Cursor getGroupDish (SQLiteDatabase db) {
        Log.d(MY_LOGS_TAG, "DishDBTable Call -> getGroupDish()");

        String q = "select " + CATEGORY_COLUMN_NAME + " from " + DATABASE_TABLE_CATEGORY + ";";
        Cursor c = db.rawQuery(q, null);

        Log.d(MY_LOGS_TAG, "DishDBTable End -> getGroupDish()");
        return c;
    }

    @Override
    public void insertToDatabase(SQLiteDatabase db, ContentValues conv) {
        Log.d(MY_LOGS_TAG, "DishDBTable Call -> insertToDatabase()");

        db.insert(DATABASE_TABLE_MENU, null, conv);

        Log.d(MY_LOGS_TAG, "DishDBTable End -> insertToDatabase()");
    }

    @Override
    public Cursor selectFromDatabase(SQLiteDatabase db){
        Log.d(MY_LOGS_TAG, "DishDBTable Call -> selectFromDatabase()");

        String query = "select " + DATABASE_TABLE_CATEGORY + "." + CATEGORY_COLUMN_NAME + " as name, " +
                DATABASE_TABLE_MENU + "." + MENU_COLUMN_DISH + " as dish, " +
                DATABASE_TABLE_MENU + "." + MENU_COLUMN_DESC + " as description, " +
                DATABASE_TABLE_MENU + "." + MENU_COLUMN_PRICE + " as price from " +
                DATABASE_TABLE_MENU + ", " + DATABASE_TABLE_CATEGORY + " where " +
                DATABASE_TABLE_MENU + "." + CATEGORY_COLUMN_ID + " = " +
                DATABASE_TABLE_CATEGORY + "." + CATEGORY_COLUMN_ID + ";";
        Cursor c = db.rawQuery(query, null);

        Log.d(MY_LOGS_TAG, "DishDBTable End -> selectFromDatabase()");
        return c;
    }

    @Override
    public Cursor selectFromDatabase(SQLiteDatabase db, String str) {
        Log.d(MY_LOGS_TAG, "DishDBTable Call -> selectFromDatabase()");

        String query = "select " + DATABASE_TABLE_MENU + "." + MENU_COLUMN_DISH + ", " +
                DATABASE_TABLE_MENU + "." + MENU_COLUMN_DESC + ", " +
                DATABASE_TABLE_MENU + "." + MENU_COLUMN_PRICE + " from " + DATABASE_TABLE_MENU +
                " where " + CATEGORY_COLUMN_ID + " in " +
                "(select " + CATEGORY_COLUMN_ID + " from " + DATABASE_TABLE_CATEGORY +
                " where " + CATEGORY_COLUMN_NAME + " like '" + str + "'); ";
        Cursor c = db.rawQuery(query, null);

        Log.d(MY_LOGS_TAG, "DishDBTable End -> selectFromDatabase()");
        return c;
    }

}
