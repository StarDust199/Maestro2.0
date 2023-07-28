package com.example.maestro20;

import static android.content.ContentValues.TAG;

import static com.example.maestro20.HelperClass.role;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editLogin, editPassword, editEmail;
    Button regButton;
    Spinner roleSpinner;
    String item;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editLogin=findViewById(R.id.login);
        editPassword=findViewById(R.id.password);
        editEmail=findViewById(R.id.email);
        regButton=findViewById(R.id.button_register);
        roleSpinner = findViewById(R.id.role_spinner);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();
        TextView txtView = findViewById(R.id.textView);
        txtView.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });



        regButton.setOnClickListener(view -> {


            String login = editLogin.getText().toString();
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            if (TextUtils.isEmpty(login) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)) {

                Toast.makeText(RegisterActivity.this, "Pole nie może być puste", Toast.LENGTH_SHORT).show();
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {

                            Toast.makeText(RegisterActivity.this, "Konto zostało utworzone.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Tworzenie konta nie powiodło się.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
            writeNewUser(login, password, email, role);
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item=roleSpinner.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void writeNewUser(String login, String password, String email, String role) {
        HelperClass user = new HelperClass(login,password, email,role);

        mDatabase.child("users").child(login).setValue(user);
    }

}