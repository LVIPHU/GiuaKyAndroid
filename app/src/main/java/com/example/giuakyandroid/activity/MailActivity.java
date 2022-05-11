package com.example.giuakyandroid.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.giuakyandroid.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MailActivity extends AppCompatActivity {
    EditText edtTo, edtSubject, edtMessage;
    Button btnSend, btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail);
        setControl();
        setEvent();
    }
    private void setControl() {
        edtTo = findViewById(R.id.edtTo);
        edtSubject = findViewById(R.id.edtSubject);
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        btnReturn= findViewById(R.id.btnMReturn);
    }

    private void setEvent() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("mailto:")
                        .buildUpon()
                        .appendQueryParameter("to", edtTo.getText().toString())
                        .appendQueryParameter("subject", edtSubject.getText().toString())
                        .appendQueryParameter("body", edtMessage.getText().toString())
                        .build();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(Intent.createChooser(emailIntent, "Title"));
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });
    }
}