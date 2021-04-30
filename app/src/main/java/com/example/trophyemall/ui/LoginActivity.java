package com.example.trophyemall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trophyemall.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "Login for TrophyEmAll";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
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
    /*
    public void sendEmailVerificationWithContinueUrl() {

        String url = "http://www.example.com/verify?uid=" + user.getUid();
        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl(url)
                .setIOSBundleId("com.example.ios")
                .setAndroidPackageName("com.example.trophyemall", false, null)
                .build();

        user.sendEmailVerification(actionCodeSettings)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                    }
                });
    }
    */
}