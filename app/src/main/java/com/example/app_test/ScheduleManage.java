package com.example.app_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ScheduleManage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_manage);

        onClickBack();

        onClickDetail();

        onClickCreateSchedule();

        showNewSchedule();

        hideSchedule();
    }

    public void onClickBack(){
        ImageView backEventIcon = findViewById(R.id.backiconschedule);
        backEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleManage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickDetail(){
        ConstraintLayout constraintLayout = findViewById(R.id.newSchedule);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleManage.this, CreateSchedule.class);
                intent.putExtra("hideCreateButton", true);
                startActivity(intent);
            }
        });
    }

    public void onClickCreateSchedule(){
        ImageView constraintLayout = findViewById(R.id.creatTask);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ScheduleManage.this, CreateSchedule.class);
                startActivity(intent);
            }
        });
    }

    private void showNewSchedule(){
        boolean getExtra = getIntent().getBooleanExtra("newSchedule", false);
        if(getExtra){
            ConstraintLayout newSchedule = findViewById(R.id.newSchedule);
            newSchedule.setVisibility(View.VISIBLE);
        }
    }

    private void hideSchedule(){
        boolean extra = getIntent().getBooleanExtra("hideSchedule", false);
        if(extra){
            ConstraintLayout hideSchedule = findViewById(R.id.newSchedule);
            hideSchedule.setVisibility(View.GONE);
        }
    }

    private void onclickAnyWhereInCalendar(){
        ConstraintLayout calendar = findViewById(R.id.calendarr);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setVisibility(View.GONE);
                ConstraintLayout showUpdateBtn = findViewById(R.id.updatebutton);
            }
        });
    }
}
