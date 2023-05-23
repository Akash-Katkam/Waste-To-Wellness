package com.example.hackathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class wifiClient extends AppCompatActivity {

    String ipAddress,userName;
    int remPort;
    Socket clientSocket;
    EditText chatBox;
    DataInputStream fromNetwork;
    DataOutputStream toNetwork;
    ListView clientView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> listItems=new ArrayList<String>();
    Ringtone msgTone;
    Vibrator msgVibrator;
    boolean conStatus=true;
    boolean activeApp;
    Notification.Builder nBuilder;
    NotificationManager nManager;
    @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_wifi_client);
            chatBox=(EditText)findViewById(R.id.clientMsg);
            clientView=(ListView)findViewById(R.id.clientChat);
            Uri msgNotify= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            msgTone=RingtoneManager.getRingtone(this,msgNotify);
            msgVibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
            nBuilder=new Notification.Builder(this);
            nManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            activeApp=true;
            arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
            clientView.setAdapter(arrayAdapter);
            Bundle getDetails=getIntent().getExtras();
            String joinInfo=getDetails.getString("joinDetails");
            int breakData=joinInfo.indexOf("\n");
            if(breakData!=-1)
            {
                ipAddress=joinInfo.substring(0,breakData);
                remPort=Integer.valueOf(joinInfo.substring(breakData+1,breakData+5));
                userName=joinInfo.substring(breakData+6,joinInfo.length());
            }
            Thread recThread=new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        clientSocket=new Socket(ipAddress,remPort);
                        fromNetwork=new DataInputStream(clientSocket.getInputStream());
                        toNetwork=new DataOutputStream(clientSocket.getOutputStream());
                        while(true)
                        {
                            Message rawMsg=Message.obtain();
                            rawMsg.obj=fromNetwork.readUTF();
                            mHandler.sendMessage(rawMsg);
                        }
                    }
                    catch(Exception e){}
                }
            });recThread.start();
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
            try
            {
                if(chatBox.getText().toString().equals(""))
                {
                    Toast.makeText(this, "Type a message to send!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    listItems.add(userName + ": " + chatBox.getText().toString());
                    arrayAdapter.notifyDataSetChanged();
                    toNetwork.writeUTF(userName + ": " + chatBox.getText().toString());
                    chatBox.setText("");
                }
            }
            catch(Exception e){}
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
                        Toast.makeText(wifiClient.this, showMsg.obj.toString(), Toast.LENGTH_LONG).show();
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
    }