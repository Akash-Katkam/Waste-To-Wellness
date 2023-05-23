package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class joinService extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Intent openClient;
    EditText ipAddress, remotePort, clientName;
    SharedPreferences.Editor spEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_service);
        ipAddress=(EditText)findViewById(R.id.etAddress);
        remotePort=(EditText)findViewById(R.id.etPort);
        clientName=(EditText)findViewById(R.id.etName);
        sharedPreferences=getSharedPreferences("conInfo",MODE_PRIVATE);
        spEditor=sharedPreferences.edit();
        ipAddress.setText(sharedPreferences.getString("IP ADDRESS",""));
        remotePort.setText(sharedPreferences.getString("PORT",""));
        clientName.setText(sharedPreferences.getString("USER NAME",""));
        if(ipAddress.getText().toString().equals("")||remotePort.getText().toString().equals("")||
                clientName.getText().toString().equals(""))
        {
            Toast.makeText(this, "Provide all network and personal details", Toast.LENGTH_SHORT).show();
        }
    }

    public void click(View view)
    {
        if(ipAddress.getText().toString().equals("")||remotePort.getText().toString().equals("")||
                clientName.getText().toString().equals(""))
        {
            Toast.makeText(this, "Sorry! Provide all details to join chat service.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(remotePort.getText().length()==4)
            {
                spEditor.putString("IP ADDRESS", ipAddress.getText().toString());
                spEditor.putString("PORT", remotePort.getText().toString());
                spEditor.putString("USER NAME", clientName.getText().toString());
                spEditor.commit();
                openClient = new Intent(this, wifiClient.class);
                openClient.putExtra("joinDetails", ipAddress.getText().toString() + "\n" +
                        remotePort.getText().toString() + "\n" + clientName.getText().toString());
                startActivity(openClient);
            }
            else
            {
                Toast.makeText(this, "Sorry! Port must be of four integers long!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
