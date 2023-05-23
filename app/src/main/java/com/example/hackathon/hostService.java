package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class hostService extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Intent openServer;
    EditText localPort, serverName;
    SharedPreferences.Editor spEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_service);
        localPort=(EditText)findViewById(R.id.etPort);
        serverName=(EditText)findViewById(R.id.etName);
        sharedPreferences=getSharedPreferences("conInfo",MODE_PRIVATE);
        spEditor=sharedPreferences.edit();
        localPort.setText(sharedPreferences.getString("PORT",""));
        serverName.setText(sharedPreferences.getString("USER NAME",""));
        if(localPort.getText().toString().equals("")||
                serverName.getText().toString().equals(""))
        {
            Toast.makeText(this, "Provide all network and personal details", Toast.LENGTH_SHORT).show();
        }
    }

    public void click(View view) {
        if (localPort.getText().toString().equals("") || serverName.getText().toString().equals("")) {
            Toast.makeText(this, "Sorry! Provide all details to host chat service.", Toast.LENGTH_SHORT).show();
        } else {
            if (localPort.getText().length() == 4) {
                spEditor.putString("PORT", localPort.getText().toString());
                spEditor.putString("USER NAME", serverName.getText().toString());
                spEditor.commit();
                openServer = new Intent(this, wifiServer.class);
                openServer.putExtra("hostDetails", localPort.getText().toString() + "\n" + serverName.getText().toString());
                startActivity(openServer);
            } else {
                Toast.makeText(this, "Sorry! Port must be of four integers long!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}