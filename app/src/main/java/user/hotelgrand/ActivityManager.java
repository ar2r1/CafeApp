package user.hotelgrand;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityManager extends ActionBarActivity implements View.OnClickListener {

    private Button bCallManagerMenu, bCallManagerHistory, bCallManagerUser, bCallManagerSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        bCallManagerMenu = (Button) findViewById(R.id.bCallManagerMenu);
        bCallManagerMenu.setOnClickListener(this);
        bCallManagerHistory = (Button) findViewById(R.id.bCallManagerHistory);
        bCallManagerHistory.setOnClickListener(this);
        bCallManagerUser = (Button) findViewById(R.id.bCallManagerUser);
        bCallManagerUser.setOnClickListener(this);
        bCallManagerSession = (Button) findViewById(R.id.bCallManagerHistory);
        bCallManagerSession.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bCallManagerMenu:
                Intent iCallMenu = new Intent(this, ActivityManagerMenu.class);
                startActivity(iCallMenu);
                break;
            case R.id.bCallManagerHistory:
                Intent iCallHistory = new Intent(this, ActivityManagerHistory.class);
                startActivity(iCallHistory);
                break;
            case R.id.bCallManagerSession:
                Intent iCallSession = new Intent(this, ActivityManagerSession.class);
                startActivity(iCallSession);
                break;
            case R.id.bCallManagerUser:
                Intent iCallUser = new Intent (this, ActivityManagerUser.class);
                startActivity(iCallUser);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
