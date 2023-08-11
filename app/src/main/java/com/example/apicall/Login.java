package com.example.apicall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText mail;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
     mail = findViewById(R.id.email);
     btn = findViewById(R.id.get);

     btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String umail= mail.getText().toString();
             Intent intent = new Intent(Login.this,MainActivity.class);
             intent.putExtra("mail",umail);
             startActivity(intent);
             finish();
         }
     });
    }
}