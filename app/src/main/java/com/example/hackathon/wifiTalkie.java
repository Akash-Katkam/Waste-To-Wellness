package com.example.hackathon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class wifiTalkie extends AppCompatActivity {

    Intent openActivity;
    AlertDialog.Builder abtDev;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_talkie);
        abtDev=new AlertDialog.Builder(this);
    }

    public void click(View view)
    {
        if(view.getId()==R.id.btHost)
        {
            openActivity=new Intent(this,hostService.class);
            startActivity(openActivity);
        }
        else
        {
            openActivity=new Intent(this,joinService.class);
            startActivity(openActivity);
        }
    }

//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem menuItem)
//    {
//        abtDev.setTitle("About Developer")
//                .setMessage("Developed By: Er. Raj Virk\nContact: +91-7696788553\nEmail: aarsoftronix@gmail.com")
//                .setPositiveButton("OK",new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface dialog, int which) {}
//                })
//                .show();
//        return true;
//    }
}