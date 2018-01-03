package com.husen.sqlite.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.husen.sqlite.R;
import com.husen.sqlite.helper.DatabaseHelper;
import com.husen.sqlite.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUserName;
    private EditText edtUserPassword;
    private EditText edtEmail;
    private Button btnRegister;
    private String strUserName, strPassword, strEmail;
    private DatabaseHelper databaseHelper;
    private Context context;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        databaseHelper = new DatabaseHelper(context);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtUserPassword = (EditText) findViewById(R.id.edtUserPassword);
        edtUserName = (EditText) findViewById(R.id.edtUserName);

        actionListener();


    }

    private void actionListener() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        strUserName = edtUserName.getText().toString().trim();
        strPassword = edtUserPassword.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();

        if (TextUtils.isEmpty(strUserName)) {
            Toast.makeText(context, "ENTER USERNAME", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(strPassword)) {
            Toast.makeText(context, "ENTER PASSWORD", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(strEmail)) {
            Toast.makeText(context, "ENTER EMAIL", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.isEmailExist(strEmail)){
            Toast.makeText(context, "EMAIL ALREADY EXIST", Toast.LENGTH_SHORT).show();
            return;
        }

        user = new User();
        user.setUserEmail(strEmail);
        user.setUserName(strUserName);
        user.setUserPassword(strPassword);
        databaseHelper.addUser(user);

        Toast.makeText(context, "Register Successfully", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, MainActivity.class);
        startActivity(i);
        finish();


    }

}
