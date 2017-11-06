package unal.edu.co.bicicrash.business;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import unal.edu.co.bicicrash.Activities.GMailSender;
import unal.edu.co.bicicrash.Utils.BiciContact;

/**
 * Created by Daniel on 2/11/2017.
 */

public class MenssagerBusiness {

    public MenssagerBusiness() {
        super();

    }


    public static void sendMsn(ArrayList arrayPhoneContacts, String message, Context mContext) {
        Log.d("MSN","enviooos");

        StringBuilder numbersString = new StringBuilder();
        BiciContact biciContact;

        for (int i = 0; i < arrayPhoneContacts.size(); i++){
            biciContact = (BiciContact) arrayPhoneContacts.get(i);

            //la clase SmsManager permite enviar los mensajes SMS
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(biciContact.getNumber(), null, "Esta es una prueba Bicicrash -> " + message, null,null);
        }

    }
    public static void sendMailer(ArrayList arrayEmailContacts, String messageWarning) {
        StringBuilder emailString = new StringBuilder();
        BiciContact biciContact;
        for (int i = 0; i < arrayEmailContacts.size(); i++){
            biciContact = (BiciContact) arrayEmailContacts.get(i);
            emailString.append(biciContact.getNumber()+",");
        }

        try {
            Log.d("MAILER ","envios");
            Log.d("MAILER1 ",emailString.toString());

            //"dannysalazarm07@gmail.com, miatorresch@unal.edu.co, jalopezfa@unal.edu.co"
            GMailSender sender = new GMailSender("soportebicicrash@gmail.com", "bicicrash123");
            sender.sendMail("Prueba envio desde la app",
                    "Bueno, probando el envio automatico desde bicicrash -> "+messageWarning,
                    "soportebicicrash@gmail.com",
                    emailString.toString());

        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }


    }
    public static class  mailerTask extends AsyncTask<Void, Void, Boolean> {
        private ArrayList arrayEmailContacts;
        private String message;

        public mailerTask(ArrayList arrayEmailContacts, String m) {
            this.arrayEmailContacts = arrayEmailContacts;
            this.message = m;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            sendMailer(arrayEmailContacts, message);
            return true;
        }
    }


    public  static class  msnTask extends AsyncTask<Void, Void, Boolean> {
        private ArrayList arrayPhoneContacts;
        private String message;
        private Context context;


        public msnTask(ArrayList arrayPhoneContacts, String messageWarning, Context context) {
            this.arrayPhoneContacts = arrayPhoneContacts;
            this.message = messageWarning;
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            sendMsn(arrayPhoneContacts, message, context);
            return true;
        }
    }
}
