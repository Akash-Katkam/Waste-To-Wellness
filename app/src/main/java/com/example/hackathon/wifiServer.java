package com.example.hackathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class wifiServer extends AppCompatActivity {

   String userName;
    int serverPort;
    AlertDialog.Builder ipPort;
    Socket clientSocket;
    ServerSocket serverSocket;
    EditText chatBox;
    AlertDialog.Builder abtDev;

    DataInputStream fromNetwork;
    DataOutputStream toNetwork;

    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    ListView serverView;
    Ringtone msgTone;
    Vibrator msgVibrator;
    boolean conStatus=false;
    boolean activeApp;
    Notification.Builder nBuilder;
    NotificationManager nManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_server);
        chatBox=(EditText)findViewById(R.id.serverMsg);
        serverView=(ListView)findViewById(R.id.serverChat);
        abtDev=new AlertDialog.Builder(this);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        serverView.setAdapter(arrayAdapter);
        ipPort=new AlertDialog.Builder(this);
        Uri msgNotify= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        msgTone=RingtoneManager.getRingtone(this,msgNotify);
        msgVibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        nBuilder=new Notification.Builder(this);
        nManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        activeApp=true;
        WifiManager wifiManager=(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress= Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        Bundle getDetails=getIntent().getExtras();
        String hostInfo=getDetails.getString("hostDetails");
        int breakData=hostInfo.indexOf("\n");
        if(breakData!=-1)
        {
            serverPort=Integer.valueOf(hostInfo.substring(0,breakData));
            userName=hostInfo.substring(breakData+1,hostInfo.length());
        }
        try
        {
            serverSocket = new ServerSocket(serverPort);
            Toast.makeText(this, "Service started, waiting for friend!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Sorry. Unable to host chat service!", Toast.LENGTH_SHORT).show();
        }
        Thread thd=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    clientSocket = serverSocket.accept();
                    conStatus=true;
                    Message conMsg = Message.obtain();
                    conMsg.obj = "Friend has connected, start messaging.";
                    mHandler.sendMessage(conMsg);
                    try
                    {
                        toNetwork = new DataOutputStream(clientSocket.getOutputStream());
                        toNetwork.writeUTF("You are connected to "+userName+"'s chat service.");
                    } catch (Exception e) {}
                    while(true)
                    {
                        fromNetwork = new DataInputStream(clientSocket.getInputStream());
                        Message myMsg = Message.obtain();
                        myMsg.obj = fromNetwork.readUTF();
                        mHandler.sendMessage(myMsg);
                    }
                }
                catch(Exception e) {}
            }
        });thd.start();
        ipPort.setTitle("Kindly Proceed!")
                .setMessage("IP ADDRESS: "+ipAddress+"\n"+"USER ID: "+String.valueOf(serverPort))
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {}
                }).show();
    }

    protected void onResume()
    {
        super.onResume();
        activeApp=true;
    }

    protected void onStop()
    {
        super.onStop();
        activeApp=false;
    }

    public void click(View view)
    {
        if(chatBox.getText().toString().equals(""))
        {
            Toast.makeText(this, "Type a message to send!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            listItems.add(userName + ": " + chatBox.getText().toString());
            arrayAdapter.notifyDataSetChanged();
            Thread thd = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try {
                        toNetwork = new DataOutputStream(clientSocket.getOutputStream());
                        toNetwork.writeUTF(userName + ": " + chatBox.getText().toString());
                    } catch (Exception e) {}
                }
            });
            thd.start();
            chatBox.setText("");
        }
    }

    Handler mHandler=new Handler()
    {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void handleMessage(Message showMsg)
        {
            if(activeApp)
            {
                if(conStatus)
                {
                    Toast.makeText(wifiServer.this, showMsg.obj.toString(), Toast.LENGTH_LONG).show();
                    msgTone.play();
                    msgVibrator.vibrate(1000);
                    conStatus=false;
                }
                else
                {
                    listItems.add(showMsg.obj.toString());
                    arrayAdapter.notifyDataSetChanged();
                    msgTone.play();
                    msgVibrator.vibrate(1000);
                }
            }
            else
            {
                listItems.add(showMsg.obj.toString());
                arrayAdapter.notifyDataSetChanged();
                nBuilder.setContentTitle("Wi-Fi Talkie")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText(showMsg.obj.toString());
                nManager.notify(1,nBuilder.build());
                msgTone.play();
                msgVibrator.vibrate(1000);
            }
        }
    };

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        abtDev.setTitle("Have you Donated?")
                .setMessage("If yes, then register yourself in leaderboard")
                .setPositiveButton("Register",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     startActivity(new Intent(wifiServer.this,Leaderboard_register.class));
                    }
                })
                .show();
        return true;
    }
}
