package com.husen.sqlite.ui;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.husen.sqlite.R;
import com.husen.sqlite.model.User;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    private Context context;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        user = new User();


        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent i = new Intent(context, MainActivity.class);
                startActivity(i);
            }

        }.start();
    }
}
