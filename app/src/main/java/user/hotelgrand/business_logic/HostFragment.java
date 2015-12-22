package user.hotelgrand.business_logic;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class HostFragment extends Fragment{
    private static final int REQUEST_WEIGHT = 1;
    private static final int REQUEST_ANOTHER_ONE = 2;
    public void openWeightPicker() {
        DialogFragment fragment = new AuthorizationDialog();
        fragment.setTargetFragment(this, REQUEST_WEIGHT);
        fragment.show(getFragmentManager(), fragment.getClass().getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_WEIGHT:
                    int weight = data.getIntExtra("home", -1);
                    Log.d("myLogs", "" + weight);
                    break;
                case REQUEST_ANOTHER_ONE:
                    //...
                    break;
                //обработка других requestCode
            }
            //updateUI();
        }
    }

}
