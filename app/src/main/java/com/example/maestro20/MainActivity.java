package com.example.maestro20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText txtLogin, txtPassword;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLogin=findViewById(R.id.login);
        txtPassword=findViewById(R.id.password);
        btn_login=findViewById(R.id.button_login);
        TextView txtView = findViewById(R.id.textView);
        txtView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}