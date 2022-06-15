package com.jorgesys.sendfilewhatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jorgesys.sendfilewhatsapp.R;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "WhatsAppSendTxtFile";
    private String fileName = "Irina_IASI.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendWhatsApp();

    }

    private void sendWhatsApp(){
        try {
            File file = new File(getFilesDir(), fileName);
            file.createNewFile(); //Create file
            if(file.exists()){ //file exists?
                FileOutputStream out = new FileOutputStream(new File(getFilesDir(), fileName), true);
                out.write(("Buna sunt Jorgesys | 12345 67890").getBytes()); //Add info to file.
                out.close();
                Log.i(TAG, "add info to file: " + file.getCanonicalFile());
                try { // Send file via WhatsApp
                    Uri uriFile = FileProvider.getUriForFile(this, getPackageName(), file); //add <external-path name="files" path="files"/>
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    Log.i(TAG, "file.getAbsolutePath() : " + file.getAbsolutePath());
                    intent.putExtra(Intent.EXTRA_STREAM, uriFile);
                    intent.setPackage("com.whatsapp");
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Whatsapp is not installed.", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Can´t create file to send.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }

    }


}