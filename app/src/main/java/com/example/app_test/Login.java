package com.example.app_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ConstraintLayout firstFrame = findViewById(R.id.firstFr);
        ConstraintLayout secondFrame = findViewById(R.id.secondFr);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        firstFrame.setVisibility(View.GONE);
                        secondFrame.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 2000);

        onClickSkip();

        onClickLogin();
    }

    private void onClickSkip() {
        TextView skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip.setVisibility(View.GONE);
                ConstraintLayout loginBox = findViewById(R.id.loginbox);
                loginBox.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onClickLogin(){
        ConstraintLayout loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}
