package unal.edu.co.bicicrash.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import unal.edu.co.bicicrash.R;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SING_IN = 0;
    private FirebaseAuth auth;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        intent = new Intent(this, MainActivity.class);
        if(auth.getCurrentUser() != null){
            //user esta logeado
            Log.d("AUTH", auth.getCurrentUser().getEmail());
            startActivity(intent);
            finish();

        }else{
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(
                            Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())

                    ).build(), RC_SING_IN);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SING_IN){
            if(resultCode == RESULT_OK){
                //user logged in
                Log.d("AUTH", auth.getCurrentUser().getEmail());


                startActivity(intent);

            }else{
                Log.d("AUTH", "NO ESTA AUTENTICADO");

            }
        }
    }

}
