package unal.edu.co.bicicrash.business;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import unal.edu.co.bicicrash.Activities.GMailSender;

/**
 * Created by Daniel on 2/11/2017.
 */

public class MenssagerBusiness {
    public MenssagerBusiness() {
        super();
    }


    public static void sendMsn() {
        Log.d("MSN","enviooos");
    }
    public static void sendMailer() {

        try {

            Log.d("MAILER ","enviooos");


            GMailSender sender = new GMailSender("soportebicicrash@gmail.com", "bicicrash123");
            sender.sendMail("Prueba envio desde la app",
                    "Bueno, probando el envio automatico desde bicicrash",
                    "soportebicicrash@gmail.com",
                    "dannysalazarm07@gmail.com, miatorresch@unal.edu.co, jalopezfa@unal.edu.co");

        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }


    }
    public static class  mailerTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            sendMailer();
            return true;
        }
    }


    public  static class  msnTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            sendMsn();
            return null;
        }

    }
}
