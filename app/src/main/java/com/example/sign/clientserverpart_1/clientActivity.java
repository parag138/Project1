package com.example.sign.clientserverpart_1;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class clientActivity extends AppCompatActivity {
    EditText etsend = null;
    EditText etreceive = null;
    Button b1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        etsend = (EditText) findViewById(R.id.sendString);
        etreceive = (EditText) findViewById(R.id.receiveString);
        b1 = (Button) findViewById(R.id.connect);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Socket s = null;
                String response = "";
                try {
                    InetSocketAddress sa = new InetSocketAddress("192.168.0.115", 8888);
                    s = new Socket();
                    s.connect(sa,2000);
                    OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
                    osw.write(etsend.getText().toString() + "\n");
                    osw.flush();
                    InputStreamReader isr = new InputStreamReader(s.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    response = br.readLine();
                    etreceive.append(response + "\n");
                    s.close();
                } catch (IOException ioe) {
                    Log.e("Code Error", ioe.getMessage());
                    etreceive.setText("Couldn’t connect with server");
                }
            }
        });
    }
}
