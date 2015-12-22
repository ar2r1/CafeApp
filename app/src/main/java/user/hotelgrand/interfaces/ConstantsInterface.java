package user.hotelgrand.interfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface ConstantsInterface {

    String MY_LOGS_TAG = "myLogs";

    String STATE_OK = "Користувач успішно авторизований";
    String STATE_WRONG = "Неправильні логін чи пароль";

    String DATABASE_TABLE_MENU = "menu";
    String MENU_COLUMN_ID = "id_menu";
    String MENU_COLUMN_DISH = "dish";
    String MENU_COLUMN_DESC = "description";
    String MENU_COLUMN_PRICE = "price";

    String DATABASE_TABLE_CATEGORY = "category";
    String CATEGORY_COLUMN_NAME = "name";
    String CATEGORY_COLUMN_ID = "id_category";

    String DATABASE_TABLE_USER = "user";
    String USER_COLUMN_ID = "id_user";
    String USER_COLUMN_FNAME = "first_name";
    String USER_COLUMN_SNAME = "second_name";
    String USER_COLUMN_LOGIN = "login";
    String USER_COLUMN_PASSWORD = "password";

    String DATABASE_TABLE_SESSION = "session";
    String SESSION_COLUMN_ID = "id_session";
    String SESSION_COLUMN_DATE_BEGIN = "date_begin";
    String SESSION_COLUMN_DATE_END = "date_end";
    String SESSION_COLUMN_ID_USER1 = "id_user1";
    String SESSION_COLUMN_ID_USER2 = "id_user2";
    String SESSION_COLUMN_INCOME = "income";

    String DATABASE_TABLE_HISTORY = "history";
    String HISTORY_COLUMN_ID = "id_history";
    String HISTORY_COLUMN_NAME = "name";
    String HISTORY_COLUMN_COST = "cost";
    String HISTORY_COLUMN_DATE = "date";
}
