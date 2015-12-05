package user.hotelgrand;

import android.content.Context;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MySimpleAdapter extends SimpleAdapter {
    public MySimpleAdapter(Context context,  List<? extends Map<String, ?>> data,
                           int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

    }
}
