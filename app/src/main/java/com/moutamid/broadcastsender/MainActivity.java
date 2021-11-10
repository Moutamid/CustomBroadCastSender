package com.moutamid.broadcastsender;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
    }

    String currentBtnType;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void BtnCLick(View v) {

        Button button = (Button) v;

        currentBtnType = button.getTag().toString();

        if (checkSelfPermission("edu.uic.cs478.fall2021.project3") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{"edu.uic.cs478.fall2021.project3"}, 1);
        } else {

            sendBroadcast(currentBtnType);

            /*try {
                Intent intent = new Intent();
                intent.setAction("edu.uic.cs478.fall2021.project3");
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                    *//*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "No such activity", Toast.LENGTH_SHORT).show();
                        }
                    });*//*
            }*/
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:

                sendBroadcast(currentBtnType);

                /*try {
                    Intent intent = new Intent();
                    intent.setAction("edu.uic.cs478.fall2021.project3");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    *//*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "No such activity", Toast.LENGTH_SHORT).show();
                        }
                    });*//*
                }*/
        }
    }

    String RESTAURANT = "restaurant";
    String ATTRACTION = "attraction";

    String CUSTOM_INTENT_ACTION = "custom_intent_action";
    String CUSTOM_EXTRA_TEXT = "custom_extra_text";

    public void sendBroadcast(String value) {

        Toast.makeText(MainActivity.this, "This is a toast!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent("com.moutamid.broadcastsender." + CUSTOM_INTENT_ACTION);
        intent.putExtra("com.moutamid.broadcastsender." + CUSTOM_EXTRA_TEXT, value);
        sendBroadcast(intent);

        try {
            Intent intent1 = getPackageManager().getLaunchIntentForPackage("com.moutamid.broadcastreceiver");
            startActivity(intent1);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "sendBroadcast: ERROR: "+e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}