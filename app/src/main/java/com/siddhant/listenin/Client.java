package com.siddhant.listenin;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

public class Client implements Runnable{
    Activity activity;
    static ArrayList<String> arrayList=new ArrayList<String>();
    static ArrayList<String> urllist=new ArrayList<String>();
    public Client(Activity activity) {
        this.activity = activity;
    }
    static WebSocketClient webSocketClient;
    @Override
    public void run()  {
        try {
            gg();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    void gg() throws Exception{
        //URL url=new URL("ws:okmusicbot.herokuapp.com/ws/bot");
        URI uri=new URI("ws://okmusicbot.herokuapp.com/ws/bot");
        webSocketClient=new WebSocketClient(uri,new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
               // System.out.println(handshakedata  +"  CONNECTED #############################################");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"CONNECTED",Toast.LENGTH_LONG).show();
                        arrayList.clear();
                        urllist.clear();
                    }
                });
            }

            @Override
            public void onMessage(String message) {
                //System.out.println(message +"  MESSAGE #############################################");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(activity,"Message Received",Toast.LENGTH_LONG).show();
                    }
                });
                try {
                    JSONObject jsonObject=new JSONObject(message);
                    JSONObject Jmessage=jsonObject.getJSONObject("message");
                    String url=Jmessage.getString("url" );
                    String title=Jmessage.getString("title" );
                    arrayList.add(title);
                    urllist.add(url);
                       if (urllist.size()==2) {
                           Intent intent = new Intent(activity, Songs.class);
                           activity.startActivity(intent);
                       }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onClose(int code, String reason, boolean remote)
            {
             //   System.out.println(reason +"  CLOSE#############################################"+code);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Thread thread=new Thread(new Client(activity));
                        thread.start();
                        Toast.makeText(activity,"CLOSED CONNECTION",Toast.LENGTH_LONG).show();


                    }
                });

            }

            @Override
            public void onError(final Exception ex) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,""+ex,Toast.LENGTH_LONG).show();
                    }
                });
                //System.out.println(ex+"  EXCEPTION #############################################");
            }
        };
        webSocketClient.connect();


    }
}
