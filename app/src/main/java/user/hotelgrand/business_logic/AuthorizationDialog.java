package user.hotelgrand.business_logic;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import user.hotelgrand.ActivityStart;
import user.hotelgrand.R;
import user.hotelgrand.database.DBConnection;
import user.hotelgrand.interfaces.ConstantsInterface;

public class AuthorizationDialog extends DialogFragment implements ConstantsInterface {

    private Authorization auth;
    private EditText loginEditText, passwordEditText;
    private String login, password;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Авторизація");
        View v = inflater.inflate(R.layout.authorization_dialog, null);
        loginEditText = (EditText) v.findViewById(R.id.etLogin);
        passwordEditText = (EditText) v.findViewById(R.id.etPassword);
        v.findViewById(R.id.bConfirmAuthorization).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStart activity = (ActivityStart) getActivity();
                auth = new Authorization(activity);
                auth.onCreate();
                login = loginEditText.getText().toString();
                password = passwordEditText.getText().toString();
                int idUser = auth.equalsLogin(login, password);
                if (idUser == 0)
                    Toast.makeText(activity, "Невірний логін або пароль", Toast.LENGTH_SHORT).show();
                else if (idUser != 0) {
                    Toast.makeText(activity, "Авторизовано", Toast.LENGTH_SHORT).show();
                    activity.rememberIdUser(idUser);                }
                auth.onDestroy();

                dismiss();
            }
        });
        return v;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
    }

}
