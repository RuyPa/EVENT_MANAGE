package com.example.app_test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import dto.EventDto;
import dto.ScheduleDto;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScheduleDetail extends AppCompatActivity {
    private Integer eventId;
    private Integer scheduleId;
    private ScheduleDto scheduleDto;
    private TextInputEditText titleText;
    private TextInputEditText startDateText;
    private TextInputEditText endDateText;
    private TextInputEditText startTimeText;
    private TextInputEditText endTimeText;
    private TextInputEditText locationText;
    private TextInputEditText desText;
    private Dialog dialog;
    private Dialog confirmDialog;
    private Retrofit retrofit = BaseAPI.getRetrofitInstance();

    private List<TextInputEditText> textInputEditTextList = new ArrayList<>();


    ImageView calendarIcon;
    ConstraintLayout calendarLayout;
    ConstraintLayout calendarLayout2;
    Calendar calendar1;
    Calendar calendar2;
    CalendarView calendarView;
    CalendarView calendarView2;
    private boolean isStartDateTimeSelected = true;
    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    ConstraintLayout updateBox ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_detail);
        scheduleId = getIntent().getIntExtra("scheduleId", 0);
        eventId = getIntent().getIntExtra("eventId", 0);
        Log.i("Joke now", "onClick: "+ scheduleId);

//        hideAndShow();

        fetchData();

        onClickUpdate();

        onClickMore();

        onClickBack();

        onClickCalendar1();

        getInforFromCalendar(calendar1);

        onClickCalendar2();

        getInforFromCalendar2(calendar2);

        setStartTime();

        setEndTime();

    }

    private void onClickUpdate() {
        ConstraintLayout updateButton = findViewById(R.id.updatebutton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildRequest();
            }
        });
    }

    private void hideAndShow() {
        ConstraintLayout updateBox = findViewById(R.id.updatebox);
        updateBox.setVisibility(View.VISIBLE);
    }


    private void fetchData() {
        fixDelay();


        ApiService apiService = retrofit.create(ApiService.class);
        Call<ScheduleDto> call = apiService.getScheduleById(scheduleId);
        call.enqueue(new Callback<ScheduleDto>() {
            @Override
            public void onResponse(Call<ScheduleDto> call, Response<ScheduleDto> response) {
                scheduleDto = response.body();
                Log.i("Joke now", "onClick: "+ scheduleDto.getLocation());
                searchComponentById();
                fillData();
            }

            @Override
            public void onFailure(Call<ScheduleDto> call, Throwable t) {
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

    private void searchComponentById() {

        titleText = findViewById(R.id.textView23);
        titleText.setText("Schedule Detail");
        startDateText = findViewById(R.id.calendartime);
        endDateText = findViewById(R.id.calendartimee);
        startTimeText = findViewById(R.id.timetext);;
        endTimeText = findViewById(R.id.timetextt);;
        locationText = findViewById(R.id.locationText);
        desText = findViewById(R.id.desText);
        updateBox = findViewById(R.id.updatebox);

        addToListening();
    }

    private void addToListening() {
        textInputEditTextList.add(titleText);
        textInputEditTextList.add(startDateText);
        textInputEditTextList.add(endDateText);
        textInputEditTextList.add(startTimeText);
        textInputEditTextList.add(endTimeText);
        textInputEditTextList.add(locationText);
        textInputEditTextList.add(desText);

        listenOnclick();
    }

    private void listenOnclick() {
        for (TextInputEditText textInputEditText : textInputEditTextList) {
            textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    updateBox.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void fillData() {

        titleText.setText(scheduleDto.getName());
        locationText.setText(scheduleDto.getLocation());
        desText.setText(scheduleDto.getDes());
        startTimeText.setText(scheduleDto.getStartTime());
        startDateText.setText(convertDateInfor(scheduleDto.getStartDate()));

        if(scheduleDto.getEndTime()== null){
            ConstraintLayout endBox = findViewById(R.id.endBox);
            endBox.setVisibility(View.GONE);
        } else {
            endTimeText.setText(scheduleDto.getEndTime());
            endDateText.setText(convertDateInfor(scheduleDto.getEndDate()));
        }
    }

    private String convertDateInfor(Date startDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(startDate);
    }

    private void onClickMore() {
        ImageView moreButton = findViewById(R.id.moreButton);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

    }

    private void showDeleteDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.event_more_action);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(Drawable.createFromPath("drawable/card_shape.xml"));
        hideAnotherOption();
        dialog.show();

        onClickDelete();

    }

    private void hideAnotherOption() {
        ConstraintLayout option1 = dialog.findViewById(R.id.time11);
        ConstraintLayout option2 = dialog.findViewById(R.id.time21);
        ConstraintLayout option3 = dialog.findViewById(R.id.time31);

        option1.setVisibility(View.GONE);
        option2.setVisibility(View.GONE);
        option3.setVisibility(View.GONE);
    }


    private void onClickDelete() {
        ConstraintLayout deleteButton = dialog.findViewById(R.id.deletebutton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirm();
            }
        });
    }

    private void showConfirm() {
        dialog.dismiss();
        confirmDialog = new Dialog(this);
        confirmDialog.setContentView(R.layout.confirm_delete_action);
        confirmDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        confirmDialog.getWindow().setBackgroundDrawable(Drawable.createFromPath("drawable/card_shape.xml"));
        confirmDialog.show();

        onClickYes();
    }


    private void onClickYes() {
        ConstraintLayout yesButton = confirmDialog.findViewById(R.id.yesbox);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
                callDeleteAPI();
            }
        });

    }

    private void callDeleteAPI() {
        fixDelay();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.deleteSchedule(scheduleId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                updateBox.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleDetail.this);
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
                Log.e("Joke", "Error fetching joke: " + t.getMessage());
            }
        });
    }



//    ----------------------------------------------------------------------------------------------------------------------------------------

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
        builder.addFormDataPart("id", String.valueOf(scheduleId));
        Log.i("Joke in4", "onCreate: " + startDate + " " + endDate + " " + startTime + " " + endTime);


        callAPI();
    }

    private void callAPI() {
        fixDelay();
        Retrofit retrofit = BaseAPI.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.updateSchedule(builder.build());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleDetail.this);
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

//    private void findComponentById() {
//        titleText = findViewById(R.id.textView23);
//        startDateText = findViewById(R.id.calendartime);
//        endDateText = findViewById(R.id.calendartimee);
//        startTimeText = findViewById(R.id.timetext);;
//        endTimeText = findViewById(R.id.timetextt);;
//        locationText = findViewById(R.id.locationtext);
//        desText = findViewById(R.id.destext);
//
//        calendarIcon = findViewById(R.id.calendarr1);
//    }

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
        Intent intent = new Intent(ScheduleDetail.this, ScheduleManage.class);
        intent.putExtra("eventId", eventId);
        startActivity(intent);
    }

    private void onClickCalendar1() {
        calendarIcon = findViewById(R.id.calendar1);
        calendarLayout = findViewById(R.id.calendarViewBox);

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
        calendarLayout2 = findViewById(R.id.calendarViewBox);

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
                Toast.makeText(ScheduleDetail.this, dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
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
}
