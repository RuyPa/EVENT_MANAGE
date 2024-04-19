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

import adapter.CustomBaseListEventAdapter;
import adapter.CustomBaseListScheduleAdapter;
import api.ApiService;
import api.BaseAPI;
import dto.BasicEventDto;
import dto.EventDto;
import dto.ScheduleDto;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleManage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_manage);

        fetchDateFromAPI();

        onClickBack();

        onClickDetail();

        onClickCreateSchedule();

        showNewSchedule();

        hideSchedule();
    }

    private void fetchDateFromAPI() {

        Integer eventId = (Integer) getIntent().getIntExtra("eventId", 0);

        fixDelay();

        Retrofit retrofit = BaseAPI.getRetrofitInstance();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<ScheduleDto>> call = apiService.getScheduleByEventId(eventId);
        call.enqueue(new Callback<List<ScheduleDto>>() {
            @Override
            public void onResponse(Call<List<ScheduleDto>> call, Response<List<ScheduleDto>> response) {
                List<ScheduleDto> scheduleDtos = response.body();

                // Set adapter here inside onResponse
                ListView listView = findViewById(R.id.list_schedule_view);
//                CustomBaseListEventAdapter adapter = new CustomBaseListEventAdapter(getApplicationContext(), eventDtoList);
                CustomBaseListScheduleAdapter adapter = new CustomBaseListScheduleAdapter(ScheduleManage.this, scheduleDtos );
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ScheduleDto>> call, Throwable t) {
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
//        ConstraintLayout constraintLayout = findViewById(R.id.newSchedule);
//        constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ScheduleManage.this, CreateSchedule.class);
//                intent.putExtra("hideCreateButton", true);
//                startActivity(intent);
//            }
//        });
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
//        boolean getExtra = getIntent().getBooleanExtra("newSchedule", false);
//        if(getExtra){
//            ConstraintLayout newSchedule = findViewById(R.id.newSchedule);
//            newSchedule.setVisibility(View.VISIBLE);
//        }
    }

    private void hideSchedule(){
//        boolean extra = getIntent().getBooleanExtra("hideSchedule", false);
//        if(extra){
//            ConstraintLayout hideSchedule = findViewById(R.id.newSchedule);
//            hideSchedule.setVisibility(View.GONE);
//        }
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
