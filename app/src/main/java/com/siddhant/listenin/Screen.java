package com.siddhant.listenin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import static com.siddhant.listenin.Client.webSocketClient;

public class Screen extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        editText=findViewById(R.id.editText1);
        Thread thread=new Thread(new Client(Screen.this));
        thread.start();
    }

    public void send_SONG (View view)  {
        try {
            webSocketClient.send(new JSONObject().put("message",editText.getText()).toString());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }
}