package com.example.spamcallsblocker;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.MODIFY_PHONE_STATE;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_PHONE_STATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    PrefsManager prefs;
    EditText myNumber;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        prefs = new PrefsManager(this);

        myNumber = findViewById(R.id.editTextNumber);

        myNumber.setText(prefs.getString("number", ""));

        if (getApplicationContext().checkSelfPermission( READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{READ_PHONE_STATE},1);
        }

        if (getApplicationContext().checkSelfPermission( CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{CALL_PHONE},2);
        }

        if (getApplicationContext().checkSelfPermission( READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{READ_CALL_LOG},3);
        }

        if (getApplicationContext().checkSelfPermission( MODIFY_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{MODIFY_PHONE_STATE},3);
        }



        }





    public void startService(View view){


        ComponentName component = new ComponentName(this, CallReceiver.class);

        int status = this.getPackageManager().getComponentEnabledSetting(component);
        if(status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || status == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT ){
            showToast("Is already started");
        } else if(status == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            this.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED , PackageManager.DONT_KILL_APP);
            showToast("Started");
        }



//
//        Intent intent = new Intent(this, DialerService.class);
       prefs.setString("number", myNumber.getText().toString());
//
//
//
//        startService(intent);
    }

    public void stopService(View view){

//        Intent serviceIntent = new Intent(this, DialerService.class);
//        stopService(serviceIntent);

        ComponentName component = new ComponentName(this, CallReceiver.class);

        int status = this.getPackageManager().getComponentEnabledSetting(component);
        if(status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || status == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT ) {
            this.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED , PackageManager.DONT_KILL_APP);
            showToast("Stopped");
        } else if(status == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            this.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED , PackageManager.DONT_KILL_APP);
            showToast("Already stopped");
        }



    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}