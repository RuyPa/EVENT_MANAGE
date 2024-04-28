package com.example.app_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import adapter.CustomBaseListScheduleAdapter;
import adapter.CustomBaseListTaskAdapter;
import api.ApiService;
import api.BaseAPI;
import dto.TaskDto;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TaskManage extends AppCompatActivity {

    private Integer eventId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_manage);

        fetchDateFromAPI();

//        onClickMore();
//
//        onClickDeleteButton();
//
//        onClickYes();
//
        onClickBack();

        onclickCreate();
//
//        showNewTask();
//
//        onClickDetailTask();
    }

    private void fetchDateFromAPI() {
        eventId = (Integer) getIntent().getIntExtra("eventId", 0);
        Log.i("Joke eventId", "onCreate2: " + eventId);

        fixDelay();

        Retrofit retrofit = BaseAPI.getRetrofitInstance();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<TaskDto>> call = apiService.getTaskByEventId(eventId);
        call.enqueue(new Callback<List<TaskDto>>() {
            @Override
            public void onResponse(Call<List<TaskDto>> call, Response<List<TaskDto>> response) {
                List<TaskDto> taskDtos = response.body();

                // Set adapter here inside onResponse
                ListView listView = findViewById(R.id.list_task_view);
//                CustomBaseListEventAdapter adapter = new CustomBaseListEventAdapter(getApplicationContext(), eventDtoList);
                CustomBaseListTaskAdapter adapter = new CustomBaseListTaskAdapter(TaskManage.this, taskDtos, eventId );
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<TaskDto>> call, Throwable t) {
                Log.e("Joke", "Error fetching joke: " + t.getMessage());
            }
        });
    }

    private void fixDelay() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS) // Adjust the timeout duration as needed
                .readTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    private void onclickCreate() {
        ImageView addEventIcon = findViewById(R.id.creatTask);
        addEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskManage.this, CreateTask.class);
                intent.putExtra("eventId", eventId);
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
//
//    private void showNewTask(){
//        boolean extra = getIntent().getBooleanExtra("showNewTask", false);
//        if(extra){
//            ConstraintLayout deleteBox = findViewById(R.id.cleanBox);
//            deleteBox.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void onClickDetailTask(){
//        ConstraintLayout cleanBox = findViewById(R.id.cleanBox);
//        cleanBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TaskManage.this, CreateTask.class);
//                intent.putExtra("hideCreateBtn", true);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void onClickMore(){
//        ImageView moreBtn = findViewById(R.id.cleanMoreBtn);
//        moreBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ConstraintLayout moreLayout = findViewById(R.id.more);
//                moreLayout.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    private void onClickDeleteButton(){
//        ConstraintLayout deleteBtn = findViewById(R.id.deletebutton);
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ConstraintLayout confirmLayout = findViewById(R.id.confirm);
//                ConstraintLayout moreLayout = findViewById(R.id.more);
//
//                confirmLayout.setVisibility(View.VISIBLE);
//                moreLayout.setVisibility(View.GONE);
//            }
//        });
//    }
//
//    private void onClickYes(){
//        ConstraintLayout yesBox = findViewById(R.id.yesbox);
//        yesBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideTask();
//            }
//
//            private void hideTask() {
//                ConstraintLayout confirmLayout = findViewById(R.id.confirm);
//                confirmLayout.setVisibility(View.GONE);
//                ConstraintLayout cleanBox = findViewById(R.id.cleanBox);
//                cleanBox.setVisibility(View.GONE);
//            }
//        });
//    }
}
