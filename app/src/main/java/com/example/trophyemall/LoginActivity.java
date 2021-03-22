package com.example.trophyemall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).build(), RC_SIGN_IN);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                String msg_error = "";
                if (response == null) {
                    msg_error = "Es necesario iniciar sesión para acceder a la aplicación";
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    msg_error = "Error de conexión";
                } else {
                    msg_error = "Error desconocido";
                }
                Toast.makeText(this, msg_error, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}