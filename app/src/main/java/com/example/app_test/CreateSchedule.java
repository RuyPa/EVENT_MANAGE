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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import api.ApiService;
import api.BaseAPI;
import dto.ScheduleDto;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateSchedule extends AppCompatActivity {

    private ScheduleDto scheduleDto;
    private TextInputEditText titleText;
    private TextInputEditText startDateText;
    private TextInputEditText endDateText;
    private TextInputEditText startTimeText;
    private TextInputEditText endTimeText;
    private TextInputEditText locationText;
    private TextInputEditText desText;
    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    private Integer eventId;

    ImageView calendarIcon;
    ConstraintLayout calendarLayout;
    ConstraintLayout calendarLayout2;
    Calendar calendar1;
    Calendar calendar2;
    CalendarView calendarView;
    CalendarView calendarView2;
    private boolean isStartDateTimeSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_schedule);
        eventId = getIntent().getIntExtra("eventId", 0);
        Log.i("Joke eventId", "onCreate: " + eventId);

        findComponentById();


//        onClickBack();
//
        onClickCreate();

        onClickCalendar1();

        getInforFromCalendar(calendar1);

        onClickCalendar2();

        getInforFromCalendar2(calendar2);

        setStartTime();

        setEndTime();
//
//        hideCreateButton();
//
//        onClickMore();
//
//        onClickDeleteButton();
//
//        onClickYes();
//
//        onClickCalendarIcon();
//
//        onClickAnyWhere();
//
//        onClickAnyWhereInCalendar();
//
//        onClickUpdate();
    }

    private void onClickCreate(){
        ConstraintLayout createButton = findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildRequest();
            }
        });
    }

    private void buildRequest() {

        String name = titleText.getText().toString();
        String startDate = startDateText.getText().toString();
        String endDate = endDateText.getText().toString();
        String startTime = startTimeText.getText().toString();
        String endTime = endTimeText.getText().toString();
        String des = desText.getText().toString();
        String location = locationText.getText().toString();

        builder.addFormDataPart("name", name);
        builder.addFormDataPart("startTime", startTime);
        builder.addFormDataPart("endTime", endTime);
        builder.addFormDataPart("startDate", startDate);
        builder.addFormDataPart("endDate", endDate);
        builder.addFormDataPart("location", location);
        builder.addFormDataPart("des", des);
        builder.addFormDataPart("eventDto.id", String.valueOf(eventId));
        Log.i("Joke in4", "onCreate: " + startDate + " " + endDate + " " + startTime + " " + endTime);


        callAPI();
    }

    private void callAPI() {
        fixDelay();
        Retrofit retrofit = BaseAPI.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.createSchedule(builder.build());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateSchedule.this);
                builder.setMessage("Thành công!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                backToScheduleManage();
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

    private static void fixDelay() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS) // Adjust the timeout duration as needed
                .readTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    private void findComponentById() {
        titleText = findViewById(R.id.textView23);
        startDateText = findViewById(R.id.calendartime);
        endDateText = findViewById(R.id.calendartimee);
        startTimeText = findViewById(R.id.timetext);;
        endTimeText = findViewById(R.id.timetextt);;
        locationText = findViewById(R.id.locationtext);
        desText = findViewById(R.id.destext);

        calendarIcon = findViewById(R.id.calendarr1);
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
        intent.putExtra("eventId", eventId);
        startActivity(intent);
    }

    private void onClickCalendar1() {
        calendarIcon = findViewById(R.id.calendar1);
        calendarLayout = findViewById(R.id.calendarrBoxView);

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

    private void onClickCalendar2() {
        ImageView calendarIcon = findViewById(R.id.calendar11);
        calendarLayout2 = findViewById(R.id.calendarrBoxView);

        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarLayout2.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getInforFromCalendar2(Calendar calendar) {
        calendarView2 = findViewById(R.id.calendarView);
        calendar2 = Calendar.getInstance();

        setDate2(calendar2);
        getDate2();

        handleDateChange();
    }

    void getDate2(){
        long date = calendarView2.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
        calendar2.setTimeInMillis(date);
        String selected_date = simpleDateFormat.format(calendar2.getTime());
//        Toast.makeText(this, selected_date, Toast.LENGTH_SHORT).show();

    }

    void setDate2(Calendar calendar){

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        calendar2.set(Calendar.YEAR, year);
        calendar2.set(Calendar.MONTH, month);
        calendar2.set(Calendar.DAY_OF_MONTH, day);
        long milli = calendar2.getTimeInMillis();
        calendarView2.setDate(milli);
    }

    private void handleDateChange() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(CreateSchedule.this, dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                TextView dateTimeTextView;
                if (isStartDateTimeSelected) {
                    startDateText.setText(year+"-"+month+"-"+dayOfMonth);
                } else {
                    endDateText.setText(year+"-"+month+"-"+dayOfMonth);
                }
                calendarLayout.setVisibility(View.GONE);
                isStartDateTimeSelected = !isStartDateTimeSelected;
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

    private void setEndTime() {

        ImageView selectDateTimeButton = findViewById(R.id.timeiconn);
        selectDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker2();
            }
        });
    }

    private void showDateTimePicker2() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Cập nhật TextView với ngày và giờ được chọn
                endTimeText.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }


//
//    private void hideCreateButton(){
//        boolean extra = getIntent().getBooleanExtra("hideCreateButton", false);
//        if(extra){
//            ConstraintLayout constraintLayout = findViewById(R.id.create_button);
//            constraintLayout.setVisibility(View.GONE);
//        }
//    }
//
//    private void onClickMore(){
//        ImageView moreBtn = findViewById(R.id.moreButton);
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
//                hideSchedule();
//            }
//
//            private void hideSchedule() {
//                Intent intent = new Intent(CreateSchedule.this, ScheduleManage.class);
//                intent.putExtra("hideSchedule", true);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void onClickCalendarIcon(){
//        ImageView calendarIcon = findViewById(R.id.calendar1);
//        calendarIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ConstraintLayout calendarLayout = findViewById(R.id.calendarr);
//                calendarLayout.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    private void onClickAnyWhere(){
//        ConstraintLayout anywhere = findViewById(R.id.anywhere);
//        anywhere.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideCalendar();
//            }
//        });
//    }
//
//    private void hideCalendar() {
//        ConstraintLayout calendarLayout = findViewById(R.id.calendarr);
//        calendarLayout.setVisibility(View.GONE);
//    }
//
//    private void onClickAnyWhereInCalendar(){
//        ConstraintLayout calendarView = findViewById(R.id.calendarView);
//        calendarView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideCalendar();
//                ConstraintLayout updateBox = findViewById(R.id.updatebox);
//                updateBox.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    private void onClickUpdate(){
//        ConstraintLayout updateBtn = findViewById(R.id.updatebutton);
//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                backToScheduleManage();
//            }
//        });
//    }
}
