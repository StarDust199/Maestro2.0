package com.example.maestro20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editLogin, editPassword, editEmail;
    Button regButton;
    Spinner roleSpinner;
    String item;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editLogin=findViewById(R.id.login);
        editPassword=findViewById(R.id.password);
        editEmail=findViewById(R.id.email);
        regButton=findViewById(R.id.button_register);
        roleSpinner = findViewById(R.id.role_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        TextView txtView = findViewById(R.id.textView);
        txtView.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });

        regButton.setOnClickListener(view -> {
            database=FirebaseDatabase.getInstance();
            reference= database.getReference("users");
            String login=editLogin.getText().toString();
            String email=editEmail.getText().toString();
            String password=editPassword.getText().toString();


            HelperClass helperClass=new HelperClass(login,password,email,item);
            reference.child(login).setValue(helperClass);
            Toast.makeText(RegisterActivity.this, "Konto zostało utworzone!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item=roleSpinner.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}