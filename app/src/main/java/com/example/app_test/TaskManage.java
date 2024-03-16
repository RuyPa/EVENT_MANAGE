package com.example.app_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TaskManage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_manage);

        onClickMore();

        onClickDeleteButton();

        onClickYes();

        onClickBack();

        onclickCreate();

        showNewTask();

        onClickDetailTask();
    }

    private void onclickCreate() {
        ImageView addEventIcon = findViewById(R.id.creatTask);
        addEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskManage.this, CreateTask.class);
                startActivity(intent);
            }
        });
    }

    public void onClickBack(){
        ImageView backEventIcon = findViewById(R.id.backiconcreateschedule);
        backEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskManage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showNewTask(){
        boolean extra = getIntent().getBooleanExtra("showNewTask", false);
        if(extra){
            ConstraintLayout deleteBox = findViewById(R.id.cleanBox);
            deleteBox.setVisibility(View.VISIBLE);
        }
    }

    private void onClickDetailTask(){
        ConstraintLayout cleanBox = findViewById(R.id.cleanBox);
        cleanBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskManage.this, CreateTask.class);
                intent.putExtra("hideCreateBtn", true);
                startActivity(intent);
            }
        });
    }

    private void onClickMore(){
        ImageView moreBtn = findViewById(R.id.cleanMoreBtn);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout moreLayout = findViewById(R.id.more);
                moreLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onClickDeleteButton(){
        ConstraintLayout deleteBtn = findViewById(R.id.deletebutton);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout confirmLayout = findViewById(R.id.confirm);
                ConstraintLayout moreLayout = findViewById(R.id.more);

                confirmLayout.setVisibility(View.VISIBLE);
                moreLayout.setVisibility(View.GONE);
            }
        });
    }

    private void onClickYes(){
        ConstraintLayout yesBox = findViewById(R.id.yesbox);
        yesBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideTask();
            }

            private void hideTask() {
                ConstraintLayout confirmLayout = findViewById(R.id.confirm);
                confirmLayout.setVisibility(View.GONE);
                ConstraintLayout cleanBox = findViewById(R.id.cleanBox);
                cleanBox.setVisibility(View.GONE);
            }
        });
    }
}
