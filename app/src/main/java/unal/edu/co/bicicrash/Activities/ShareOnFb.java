package unal.edu.co.bicicrash.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import unal.edu.co.bicicrash.R;

public class ShareOnFb extends FragmentActivity {
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    Intent intent;
    //final Button button2 = (Button) findViewById(R.id.button2);



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        intent = new Intent(this, MainActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sahre_on_fb);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                findViewById(R.id.button2).performClick();
//            }
//        }, 5000);

        publicar();
    }

    public static void hola(){
        if(true){
        }
    }

    public void publicar(){
        //intent = new Intent(this, ShareOnFb.class);
        try {

            if (ShareDialog.canShow(ShareLinkContent.class)) {
//                ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                        //.setContentTitle("Ayudaaa me accidenté")
//                        .setContentDescription("estoy en ... : ----, esto es una prueba")
//                        //.setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//                        .setQuote("Hola bicicrash 2017")
//                        .build();
//                shareDialog.show(linkContent);
//                //startActivity(intent);

                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .setQuote("Prueba BiciCrash")
                        .build();
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

            }
        }catch (Exception e){
            Toast.makeText(this, "algo paso: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        startActivity(intent);
    }


}
