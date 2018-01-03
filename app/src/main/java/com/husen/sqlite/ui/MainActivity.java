package com.husen.sqlite.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.husen.sqlite.R;
import com.husen.sqlite.helper.DatabaseHelper;
import com.husen.sqlite.helper.Session;
import com.husen.sqlite.model.User;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtUserPassword;
    private Button btnLogin;
    private TextView txtRegister;
    private String email, password;
    private Context context;
    private DatabaseHelper databaseHelper;
    private User user;
    private Gson gson;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        user = new User();
        session = new Session(context);
        gson = new GsonBuilder().setLenient().create();
        databaseHelper = new DatabaseHelper(context);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtUserPassword = (EditText) findViewById(R.id.edtUserPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        if (session.loggedin()){
            startActivity(new Intent(context, AddEmployeeActivity.class));
            finish();
        }
        actionListener();



        Log.e("record", String.valueOf(databaseHelper.checkDataAvalible()));
    }


    private void actionListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void checkUser() {
        email = edtEmail.getText().toString().trim();
        password = edtUserPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "ENTER EMAIL", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "ENTER PASSWORD", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.checkUser(email, password)){
            databaseHelper.updateUser(1);
            session.setLoggedin(true);
            Intent i = new Intent(context, AddEmployeeActivity.class);
            user = databaseHelper.getUser(email);
            session.setUserData(user);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }else {
            Toast.makeText(context, "Please check Email and Password", Toast.LENGTH_SHORT).show();
        }
    }
}
