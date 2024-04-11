package com.example.app_test;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import adapter.CustomBaseListEventAdapter;
import api.ApiService;
import api.BaseAPI;
import dto.BasicEventDto;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_manage);

        getEventsFromAPI();

        onClickCreateDetail();
    }

    private void getEventsFromAPI() {
        fixDelay();

        Retrofit retrofit = BaseAPI.getRetrofitInstance();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<BasicEventDto>> call = apiService.getBasicEventData();
        call.enqueue(new Callback<List<BasicEventDto>>() {
            @Override
            public void onResponse(Call<List<BasicEventDto>> call, Response<List<BasicEventDto>> response) {
                List<BasicEventDto> eventDtoList = response.body();
                Log.i("Joke done", response.body().toString());

                // Set adapter here inside onResponse
                ListView listView = findViewById(R.id.list_event_view);
                CustomBaseListEventAdapter adapter = new CustomBaseListEventAdapter(getApplicationContext(), eventDtoList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<BasicEventDto>> call, Throwable t) {
                Log.e("Joke", "Error fetching joke: " + t.getMessage());
            }
        });
    }

    private static void fixDelay() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS) // Adjust the timeout duration as needed
                .readTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    private void onClickCreateDetail() {
        ImageView addEventIcon = findViewById(R.id.add_event_icon);
        addEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateEventDetail.class);
                startActivity(intent);
            }
        });
    }
//
//    private void onClickSchedule() {
//        ImageView addEventIcon = findViewById(R.id.schedule);
//        addEventIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ScheduleManage.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void onClickTask() {
//        ImageView addEventIcon = findViewById(R.id.task);
//        addEventIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, TaskManage.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void onClickMore() {
//        ImageView moreIcon = findViewById(R.id.moreIcon);
//        moreIcon.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                showMoreLayout();
//            }
//
//            private void showMoreLayout() {
//                constraintLayout = findViewById(R.id.more);
//                if (constraintLayout.getVisibility() == View.GONE) {
//                    constraintLayout.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//    }
//
//    private void onClickMorePtit(){
//
//        ImageView morePtit = findViewById(R.id.moreptit);
//        morePtit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                showMoreLayout();
//            }
//
//            private void showMoreLayout() {
//                constraintLayout = findViewById(R.id.more);
//                if (constraintLayout.getVisibility() == View.GONE) {
//                    constraintLayout.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//    }
//
//    private void onClickDelete() {
//
//        ConstraintLayout deleteButton = findViewById(R.id.deletebutton);
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                showConfirmLayout();
//            }
//
//            private void showConfirmLayout() {
//                ConstraintLayout confirmLayout = findViewById(R.id.confirm);
//                if (confirmLayout.getVisibility() == View.GONE) {
//                    hideMoreLayout();
//                    confirmLayout.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//    }
//
//    private void hideMoreLayout(){
//        ConstraintLayout moreLayout = findViewById(R.id.more);
//        if(moreLayout.getVisibility() == View.VISIBLE){
//            moreLayout.setVisibility(View.GONE);
//        }
//    }
//
//
//    private void onClickNo() {
//
//        ConstraintLayout noBox = findViewById(R.id.nobox);
//        noBox.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                hideConfirmLayout();
//            }
//        });
//    }
//
//    private void hideConfirmLayout() {
//        ConstraintLayout confirmLayout = findViewById(R.id.confirm);
//        if (confirmLayout.getVisibility() == View.VISIBLE) {
//            confirmLayout.setVisibility(View.GONE);
//        }
//    }
//
//    private void onClickYes(){
//
//        ConstraintLayout yesBox = findViewById(R.id.yesbox);
//        yesBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideEvent();
//            }
//
//            private void hideEvent() {
//                ConstraintLayout hideEvent = findViewById(R.id.ptit_tour);
//                if(hideEvent.getVisibility() == View.VISIBLE){
//                    hideConfirmLayout();
//                    hideEvent.setVisibility(View.GONE);
//                }
//            }
//        });
//    }
//
//    private void onClickSeeDetail(){
//
//        ConstraintLayout seeDetailLayout = findViewById(R.id.ptit_tour);
//        seeDetailLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                seeDetailEvent();
//            }
//        });
//    }
//
//    private void seeDetailEvent() {
//        Intent intent = new Intent(MainActivity.this, CreateDetail.class);
//        intent.putExtra("backToHome", true);
//        startActivity(intent);
//    }
//
//    private void showEvent(){
//
//        boolean showEvent = getIntent().getBooleanExtra("showEvent", false);
//        if (showEvent) {
//            ConstraintLayout ptitEvent = findViewById(R.id.ptit_tour);
//            if(ptitEvent.getVisibility() == View.GONE){
//                ptitEvent.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    private void onClickEdit(){
//        ConstraintLayout editButton = findViewById(R.id.editbutton);
//        editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CreateDetail.class);
//                intent.putExtra("hideDraftBox", true);
//                startActivity(intent);
//            }
//        });
//    }
}