package user.hotelgrand.interfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.ListView;

public interface DatabaseFunctionInterface {
    public void addData(ContentValues cv);

    public void deleteData(String field);

    public void showData(ListView lv, Context context);
}
