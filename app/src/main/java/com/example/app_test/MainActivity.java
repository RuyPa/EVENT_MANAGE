package com.example.app_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout parentLayout;
    private ConstraintLayout constraintLayout;

    private ConstraintLayout confirmLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showEvent();

        onClickCreateDraft();

        onClickSchedule();

        onClickTask();

        onClickMore();

        onClickDelete();

        onClickNo();

        onClickYes();

        onClickSeeDetail();

        onClickEdit();

        onClickMorePtit();
    }

    private void onClickCreateDraft() {
        ImageView addEventIcon = findViewById(R.id.add_event_icon);
        addEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateDraft.class);
                startActivity(intent);
            }
        });
    }

    private void onClickSchedule() {
        ImageView addEventIcon = findViewById(R.id.schedule);
        addEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScheduleManage.class);
                startActivity(intent);
            }
        });
    }

    private void onClickTask() {
        ImageView addEventIcon = findViewById(R.id.task);
        addEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskManage.class);
                startActivity(intent);
            }
        });
    }

    private void onClickMore() {
        ImageView moreIcon = findViewById(R.id.moreIcon);
        moreIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showMoreLayout();
            }

            private void showMoreLayout() {
                constraintLayout = findViewById(R.id.more);
                if (constraintLayout.getVisibility() == View.GONE) {
                    constraintLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void onClickMorePtit(){

        ImageView morePtit = findViewById(R.id.moreptit);
        morePtit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showMoreLayout();
            }

            private void showMoreLayout() {
                constraintLayout = findViewById(R.id.more);
                if (constraintLayout.getVisibility() == View.GONE) {
                    constraintLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void onClickDelete() {

        ConstraintLayout deleteButton = findViewById(R.id.deletebutton);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showConfirmLayout();
            }

            private void showConfirmLayout() {
                ConstraintLayout confirmLayout = findViewById(R.id.confirm);
                if (confirmLayout.getVisibility() == View.GONE) {
                    hideMoreLayout();
                    confirmLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void hideMoreLayout(){
        ConstraintLayout moreLayout = findViewById(R.id.more);
        if(moreLayout.getVisibility() == View.VISIBLE){
            moreLayout.setVisibility(View.GONE);
        }
    }


    private void onClickNo() {

        ConstraintLayout noBox = findViewById(R.id.nobox);
        noBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideConfirmLayout();
            }
        });
    }

    private void hideConfirmLayout() {
        ConstraintLayout confirmLayout = findViewById(R.id.confirm);
        if (confirmLayout.getVisibility() == View.VISIBLE) {
            confirmLayout.setVisibility(View.GONE);
        }
    }

    private void onClickYes(){

        ConstraintLayout yesBox = findViewById(R.id.yesbox);
        yesBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideEvent();
            }

            private void hideEvent() {
                ConstraintLayout hideEvent = findViewById(R.id.ptit_tour);
                if(hideEvent.getVisibility() == View.VISIBLE){
                    hideConfirmLayout();
                    hideEvent.setVisibility(View.GONE);
                }
            }
        });
    }

    private void onClickSeeDetail(){

        ConstraintLayout seeDetailLayout = findViewById(R.id.ptit_tour);
        seeDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeDetailEvent();
            }
        });
    }

    private void seeDetailEvent() {
        Intent intent = new Intent(MainActivity.this, CreateDetail.class);
        intent.putExtra("backToHome", true);
        startActivity(intent);
    }

    private void showEvent(){

        boolean showEvent = getIntent().getBooleanExtra("showEvent", false);
        if (showEvent) {
            ConstraintLayout ptitEvent = findViewById(R.id.ptit_tour);
            if(ptitEvent.getVisibility() == View.GONE){
                ptitEvent.setVisibility(View.VISIBLE);
            }
        }
    }

    private void onClickEdit(){
        ConstraintLayout editButton = findViewById(R.id.editbutton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateDetail.class);
                intent.putExtra("hideDraftBox", true);
                startActivity(intent);
            }
        });
    }
}