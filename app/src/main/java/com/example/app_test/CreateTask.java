package com.example.app_test;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import api.ApiService;
import api.BaseAPI;
import dto.ScheduleDto;
import dto.TaskDto;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateTask extends AppCompatActivity {

    private Integer eventId;
    private Integer taskId;
    private TaskDto taskDto;
    private TextInputEditText titleText;
    private TextInputEditText startDateText;
    private TextInputEditText startTimeText;
    private TextInputEditText desText;
    private Retrofit retrofit = BaseAPI.getRetrofitInstance();
    private List<TextInputEditText> textInputEditTextList = new ArrayList<>();

    private ImageView calendarIcon;
    private ConstraintLayout calendarLayout;
    private Calendar calendar1;
    private CalendarView calendarView;
    private MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    private ConstraintLayout updateBox ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);
        taskId = getIntent().getIntExtra("taskId", 0);
        eventId = getIntent().getIntExtra("eventId", 0);

        searchComponentById();

        onClickBack();

        onClickCalendar1();

        getInforFromCalendar(calendar1);

        setStartTime();

        onClickCreate();

    }

    private static void fixDelay() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS) // Adjust the timeout duration as needed
                .readTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    private void searchComponentById() {

        titleText = findViewById(R.id.textView23);
        startDateText = findViewById(R.id.calendartime);
        startTimeText = findViewById(R.id.timetext);;
        desText = findViewById(R.id.destext);
    }

    private void onClickCreate() {
        ConstraintLayout updateButton = findViewById(R.id.create_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildRequest();
            }
        });
    }

    private void buildRequest() {

        String name = titleText.getText().toString();
        String startDate = startDateText.getText().toString();

        if(startTimeText.getText() == null){
            builder.addFormDataPart("startTime", "");
        } else {
            String startTime = startTimeText.getText().toString();
            builder.addFormDataPart("startTime", startTime);
        }

        if(desText.getText() == null){
            builder.addFormDataPart("des", "");
        } else {
            String des = desText.getText().toString();
            builder.addFormDataPart("des", des);
        }

        builder.addFormDataPart("name", name);
        builder.addFormDataPart("startDate", startDate);
        builder.addFormDataPart("checked", String.valueOf(0));
        builder.addFormDataPart("eventDto.id", String.valueOf(eventId));

        callAPI();
    }

    private void callAPI() {
        fixDelay();
        Retrofit retrofit = BaseAPI.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.createTask(builder.build());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateTask.this);
                builder.setMessage("Tạo nhiệm vụ thành công!")
                        .setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                backToTaskManage();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
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

    private void backToTaskManage() {
        Intent intent = new Intent(CreateTask.this, TaskManage.class);
        intent.putExtra("eventId", eventId);
        startActivity(intent);
    }

    private void onClickCalendar1() {
        calendarIcon = findViewById(R.id.calendar1);
        calendarLayout = findViewById(R.id.calendarr);

        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getInforFromCalendar(Calendar calendar) {
        calendarView = findViewById(R.id.calendarView);
        calendar1 = Calendar.getInstance();

        setDate(calendar1);
        getDate();

        handleDateChange();
    }

    void getDate(){
        long date = calendarView.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
        calendar1.setTimeInMillis(date);
        String selected_date = simpleDateFormat.format(calendar1.getTime());
//        Toast.makeText(this, selected_date, Toast.LENGTH_SHORT).show();

    }

    void setDate(Calendar calendar){

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        calendar1.set(Calendar.YEAR, year);
        calendar1.set(Calendar.MONTH, month);
        calendar1.set(Calendar.DAY_OF_MONTH, day);
        long milli = calendar1.getTimeInMillis();
        calendarView.setDate(milli);
    }

    private void handleDateChange() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(CreateTask.this, dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                startDateText.setText(year+"-"+month+"-"+dayOfMonth);
                calendarLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setStartTime() {

        ImageView selectDateTimeButton = findViewById(R.id.timeicon);
        selectDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });
    }

    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Cập nhật TextView với ngày và giờ được chọn
                startTimeText.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }
}
