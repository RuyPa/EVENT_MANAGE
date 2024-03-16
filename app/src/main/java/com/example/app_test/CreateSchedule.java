package com.example.app_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Calendar;

public class CreateSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_schedule);

        onClickBack();

        onClickCreate();

        hideCreateButton();

        onClickMore();

        onClickDeleteButton();

        onClickYes();

        onClickCalendarIcon();

        onClickAnyWhere();

        onClickAnyWhereInCalendar();

        onClickUpdate();
    }

    public void onClickBack(){
        ImageView backEventIcon = findViewById(R.id.backiconcreateschedule);
        backEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToScheduleManage();
            }
        });
    }

    private void backToScheduleManage() {
        Intent intent = new Intent(CreateSchedule.this, ScheduleManage.class);
        startActivity(intent);
    }

    private void onClickCreate(){
        ConstraintLayout createButton = findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateSchedule.this, ScheduleManage.class);
                intent.putExtra("newSchedule", true);
                startActivity(intent);
            }
        });
    }

    private void hideCreateButton(){
        boolean extra = getIntent().getBooleanExtra("hideCreateButton", false);
        if(extra){
            ConstraintLayout constraintLayout = findViewById(R.id.create_button);
            constraintLayout.setVisibility(View.GONE);
        }
    }

    private void onClickMore(){
        ImageView moreBtn = findViewById(R.id.moreButton);
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
                hideSchedule();
            }

            private void hideSchedule() {
                Intent intent = new Intent(CreateSchedule.this, ScheduleManage.class);
                intent.putExtra("hideSchedule", true);
                startActivity(intent);
            }
        });
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

    private void onClickAnyWhere(){
        ConstraintLayout anywhere = findViewById(R.id.anywhere);
        anywhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCalendar();
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
                backToScheduleManage();
            }
        });
    }
}
