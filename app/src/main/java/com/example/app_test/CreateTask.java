package com.example.app_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CreateTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        onClickBack();

        onClickCreate();

        hideCreateBtn();

        onClickCalendarIcon();

        onClickAnyWhereInCalendar();

        onClickUpdate();
    }

    public void onClickBack(){
        ImageView backEventIcon = findViewById(R.id.backiconcreateschedule);
        backEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToTaskManage();
            }
        });
    }

    public void onClickCreate(){
        ConstraintLayout createBtn = findViewById(R.id.create_button);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTask.this, TaskManage.class);
                intent.putExtra("showNewTask", true);
                startActivity(intent);
            }
        });
    }

    private void hideCreateBtn(){
        boolean extra = getIntent().getBooleanExtra("hideCreateBtn", false);
        if(extra){
            ConstraintLayout createBtn = findViewById(R.id.createBox);
            createBtn.setVisibility(View.GONE);
        }
    }

    private void onClickCalendarIcon(){
        ImageView calendarIcon = findViewById(R.id.calendar1);
        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout calendarLayout = findViewById(R.id.calendarr);
                calendarLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideCalendar() {
        ConstraintLayout calendarLayout = findViewById(R.id.calendarr);
        calendarLayout.setVisibility(View.GONE);
    }

    private void onClickAnyWhereInCalendar(){
        ConstraintLayout calendarView = findViewById(R.id.calendarView);
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCalendar();
                ConstraintLayout updateBox = findViewById(R.id.updatebox);
                updateBox.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onClickUpdate(){
        ConstraintLayout updateBtn = findViewById(R.id.updatebutton);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToTaskManage();
            }
        });
    }

    private void backToTaskManage() {
        Intent intent = new Intent(CreateTask.this, TaskManage.class);
        startActivity(intent);
    }
}
