package com.example.app_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CreateDraft extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_draft);

        onClickBack();

        onClickDetail();
    }

    public void onClickBack(){
        ImageView backEventIcon = findViewById(R.id.backiconcreateschedule);
        backEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateDraft.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void onClickDetail() {
        ConstraintLayout createDetail = findViewById(R.id.create_buttonn);
        createDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateDraft.this, CreateDetail.class);
                startActivity(intent);
            }
        });
    }
}
