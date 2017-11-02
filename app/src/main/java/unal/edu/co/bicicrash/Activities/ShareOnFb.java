package unal.edu.co.bicicrash.Activities;

import android.app.Dialog;
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
import com.facebook.FacebookDialog;
import com.facebook.FacebookSdk;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import unal.edu.co.bicicrash.R;

public class ShareOnFb extends FragmentActivity {
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    Intent intentAfterFB=new Intent(this, MainActivity.class);

     ShareLinkContent content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
    //  setContentView(R.layout.activity_sahre_on_fb);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                publicar();
            }
        });
        thread.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //shareDialog.show(content);
                Log.d("Mensaje", "ENTRANDOOOO");
                //thread.interrupt();
            }
        }, 5000);
    }

    public  void publicar(){
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

                content = new ShareLinkContent.Builder()
                        .setQuote("Prueba BiciCrash")
                        .setContentUrl(Uri.parse("http://google.com"))
                        .build();

                FacebookSdk.getExecutor();
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
        finish();
    }

}
