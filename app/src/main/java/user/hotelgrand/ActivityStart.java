package user.hotelgrand;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import user.hotelgrand.business_logic.Authorization;
import user.hotelgrand.business_logic.AuthorizationDialog;
import user.hotelgrand.interfaces.ConstantsInterface;
import user.hotelgrand.interfaces.Utils;
import user.hotelgrand.user_interface.restaurant_interface.ActivityMain;


public class ActivityStart extends ActionBarActivity implements ConstantsInterface {

    private int idFirstUser, idSecondUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initVars();
    }

    public void initVars() {
        Button bAuthorizationFirst = (Button) findViewById(R.id.bAuthorizationFirst);

        bAuthorizationFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment();
            }
        });
    }

    public void showDialogFragment () {
        DialogFragment dialogAuthorization = new AuthorizationDialog();
        FragmentManager manager = getFragmentManager();
        dialogAuthorization.show(manager, getClass().getName());
    }

    public void rememberIdUser (int id) {
        if (idFirstUser == 0) {
            idFirstUser = id;
            showDialogFragment();
        } else {
            if (idSecondUser == 0) {
                idSecondUser = id;

                Intent intentMainActivity = new Intent(this, ActivityMain.class);
                intentMainActivity.putExtra("idFirstUser", idFirstUser);
                intentMainActivity.putExtra("idSecondUser", idSecondUser);
                startActivity(intentMainActivity);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}