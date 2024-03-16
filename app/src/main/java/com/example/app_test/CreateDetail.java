package com.example.app_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CreateDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_detail);

        hideDraftBoxAndShowUpdate();

        onClickBack();

        onClickCreate();

        onClickUpdateButton();

        checkSeeDetail();
    }

    private void checkSeeDetail() {
        boolean extra = getIntent().getBooleanExtra("backToHome", false);
        if(extra){
            ImageView backEventIcon = findViewById(R.id.backtodraft);
            backEventIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CreateDetail.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void onClickBack() {
        ImageView backEventIcon = findViewById(R.id.backtodraft);
        backEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateDetail.this, CreateDraft.class);
                startActivity(intent);
            }
        });
    }

    private void onClickCreate() {
        ConstraintLayout createButton = findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateDetail.this, MainActivity.class);
                intent.putExtra("showEvent", true);
                startActivity(intent);
            }
        });
    }

    private void hideDraftBoxAndShowUpdate() {

        boolean showEvent = getIntent().getBooleanExtra("hideDraftBox", false);
        if (showEvent) {
            ConstraintLayout createBox = findViewById(R.id.createBox);
            if (createBox.getVisibility() == View.VISIBLE) {
                createBox.setVisibility(View.GONE);
                showUpdateBox();
            }
        }
    }

    private void showUpdateBox() {
        ConstraintLayout updateBox = findViewById(R.id.updatebox);
        if (updateBox.getVisibility() == View.GONE) {
            updateBox.setVisibility(View.VISIBLE);
        }
    }

    private void onClickUpdateButton(){
        ConstraintLayout updateButton = findViewById(R.id.updatebutton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
