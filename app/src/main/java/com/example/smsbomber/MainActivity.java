package com.example.smsbomber;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smsbomber.smsBbomber.R;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;

    private static final String LOG_TAG = "AndroidExample";

    private EditText editPhoneNumber;
    private EditText editMessage;

    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editPhoneNumber = (EditText) this.findViewById(R.id.edit_phoneNumber);
        this.editMessage = (EditText) this.findViewById(R.id.edit_message);

        this.btnSend = (Button) this.findViewById(R.id.button_send);

        this.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissionAndSendSMS();
            }
        });
    }


    private void askPermissionAndSendSMS() {

        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) {

            int sendSmsPermisson = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSION_REQUEST_CODE_SEND_SMS
                );
                return;
            }
        }
        this.sendSMS_by_smsManager();
    }


    private void sendSMS_by_smsManager()  {

        String phoneNumber = this.editPhoneNumber.getText().toString();
        String message = this.editMessage.getText().toString();
        for (int i=0; i<5;i++) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber,
                        null,
                        message,
                        null,
                        null);

                Log.i( LOG_TAG,"Votre SMS est envoyé ! ");
                Toast.makeText(getApplicationContext(),"Votre SMS a éé envoyé 5 fois !",
                        Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Log.e( LOG_TAG,"L'envoi du sms a échoué...", ex);
                Toast.makeText(getApplicationContext(),"L'envoi du sms a échoué.. " + ex.getMessage(),
                        Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE_SEND_SMS: {


                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i( LOG_TAG,"Permission OK!");
                    Toast.makeText(this, "Permission OK!", Toast.LENGTH_LONG).show();

                    this.sendSMS_by_smsManager();
                }
                else {
                    Log.i( LOG_TAG,"Permission refusée!");
                    Toast.makeText(this, "Permission refusée!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSION_REQUEST_CODE_SEND_SMS) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Action OK", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action annulée", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action échouée", Toast.LENGTH_LONG).show();
            }
        }
    }
}