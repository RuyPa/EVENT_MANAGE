package com.example.app_test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import api.ApiService;
import api.BaseAPI;
import avtInterface.OnOptionSelectedListener;
import constant.CreateEventConst;
import dto.CategoryDto;
import dto.EventCategoryDto;
import dto.EventDto;
import dto.UserDto;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateEvent extends AppCompatActivity {

    TextView title ;
    TextInputEditText eventName;
    TextView startDate;
    TextView endDate;
    TextView startTimeText;
    TextView endTime ;
    String type ;
    TextInputEditText location ;
    TextInputEditText address;
    AutoCompleteTextView city;
    TextInputEditText description ;
    TextInputEditText eventVideo ;
    TextInputEditText websiteLink ;
    Calendar calendar1;
    Calendar calendar2;
    ConstraintLayout createBox ;
    ConstraintLayout updateThumbnaibox;
    CalendarView calendarView;
    ConstraintLayout calendarLayout;
    CalendarView calendarView2;
    ConstraintLayout calendarLayout2;
    ConstraintLayout getUpdateThumbnaibox;
    ImageView getImageView5;
    List<CategoryDto> categoryDtoEdits = new ArrayList<>();
    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    List<CategoryDto> categoryDtos = new ArrayList<>();
    EventDto eventUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_event_detail);

        getEventFromExtra();
        
        hideDraftBoxAndShowUpdate();

        findAllElementById();

        setInforDefault();

        callAPIGetAllCate();

        workWithDateTime();

        insertImage();

        onClickAnyWhere();

        createEvent();

        onClickBack();

        onClickUpdateButton();
    }

    private void setInforDefault() {

        title.setText("Update Event");
        eventName.setText(eventUpdate.getName());
        location.setText(eventUpdate.getLocation());
        address.setText(eventUpdate.getAddress());
        address.setText(eventUpdate.getAddress());
        eventVideo.setText(eventUpdate.getEventVideo());
        websiteLink.setText(eventUpdate.getWebsiteLink());
        description.setText(eventUpdate.getDes());
        city.setText(eventUpdate.getCity());
        startDate.setText(convertDate(eventUpdate.getStartDate()));
        endDate.setText(convertDate(eventUpdate.getEndDate()));
        endTime.setText(eventUpdate.getEndTime());
        startTimeText.setText(eventUpdate.getStartTime());
        Glide.with(this)
                .load(eventUpdate.getImgUrl())
                .into(getImageView5);
    }

    private void findAllElementById() {
        title = findViewById(R.id.textView3);
        eventName = findViewById(R.id.textView5);
        location = findViewById(R.id.textView11);
        address = findViewById(R.id.textView12);
        description = findViewById(R.id.textView14);
        eventVideo = findViewById(R.id.textView18);
        websiteLink = findViewById(R.id.textView17);
        city = findViewById(R.id.autoCompleteTextViewCity);
        startDate = findViewById(R.id.textView6);
        endDate = findViewById(R.id.textView7);
        endTime = findViewById(R.id.endtimetextview);
        startTimeText = findViewById(R.id.starttimetextview);
        getImageView5 = findViewById(R.id.imageView5);
    }

    private String convertDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private void getEventFromExtra() {
        eventUpdate = (EventDto) getIntent().getSerializableExtra("eventDto");
    }

    private void workWithDateTime() {
        onClickCalendar1();

        getInforFromCalendar(calendar1);

        onClickCalendar2();

        getInforFromCalendar2(calendar2);

        setStartTime();

        setEndTime();
    }

    private void setStartTime() {

        ImageView selectDateTimeButton = findViewById(R.id.time1);
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


        ImageView selectDateTimeButton = findViewById(R.id.time2);
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
                endTime.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void setCate() {

        LinearLayout checkboxContainer = findViewById(R.id.checkboxContainer);
        Log.i("Joke", String.valueOf(categoryDtos.size()));
        for (CategoryDto categoryDto: categoryDtos) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(categoryDto.getName());

            boolean isChecked = eventUpdate.getCategories().stream()
                            .anyMatch(dto -> dto.getCategoryDto().getName().equals(categoryDto.getName()));
            if(isChecked){
                checkBox.setChecked(isChecked);
                categoryDtoEdits.add(new CategoryDto(categoryDto.getId()));
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        categoryDtoEdits.add(new CategoryDto(categoryDto.getId()));
                    } else {
                        categoryDtoEdits.removeIf(dto -> dto.getId().equals(categoryDto.getId()));
                    }
                    Log.i("Joke", String.valueOf(categoryDtoEdits.size()));
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16);
            checkBox.setLayoutParams(params);
            checkboxContainer.addView(checkBox);
        }
    }

    private void callAPIGetAllCate() {

        fixDelay();

        Retrofit retrofit = BaseAPI.getRetrofitInstance();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<CategoryDto>> call = apiService.getAllCate();
        call.enqueue(new Callback<List<CategoryDto>>() {
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                categoryDtos.addAll(response.body());
                setCate();
            }

            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable t) {
                Log.e("Joke", "Error fetching joke: " + t.getMessage());
            }
        });
    }

    private void insertImage() {
        getUpdateThumbnaibox = findViewById(R.id.updateThumbnailbox);
        getUpdateThumbnaibox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(UpdateEvent.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            getImageView5.setImageURI(uri);
            File imageFile = new File(uri.getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            builder.addFormDataPart("file", imageFile.getName(), requestBody);
        }
    }

    private boolean isStartDateTimeSelected = true;

    private void onClickCalendar1() {
        ImageView calendarIcon = findViewById(R.id.calendarr1);
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

    private void handleDateChange() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(UpdateEvent.this, dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                TextView dateTimeTextView;
                if (isStartDateTimeSelected) {
                    startDate.setText(year+"-"+month+"-"+dayOfMonth);
                } else {
                    endDate.setText(year+"-"+month+"-"+dayOfMonth);
                }
                calendarLayout.setVisibility(View.GONE);
                isStartDateTimeSelected = !isStartDateTimeSelected;
            }
        });
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
        ImageView calendarIcon = findViewById(R.id.calendarr2);
        calendarLayout2 = findViewById(R.id.calendarr);

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


    private void onClickAnyWhere(){
        ConstraintLayout anywhere = findViewById(R.id.anywhere);
        anywhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarLayout.setVisibility(View.GONE);
            }
        });
    }

    private void createEvent() {

        provinceData();
        final String[] eventType = {""};
        onRadioClickView(new OnOptionSelectedListener() {
            @Override
            public void onOptionSelected(String selectedOption) {
                eventType[0] = selectedOption;
                type = eventType[0];
            }
        });
    }

    public void onRadioClickView(OnOptionSelectedListener listener){
        RadioGroup radioGroup = findViewById(R.id.wherefill);
        final RadioButton indoorRadioButton = findViewById(R.id.indoor);
        final RadioButton outdoorRadioButton = findViewById(R.id.outdoor);

        if (eventUpdate.getRegistrationType().equals("Trong nhà")) {
            indoorRadioButton.setChecked(true);
            type = "Trong nhà";
        } else{
            outdoorRadioButton.setChecked(true);
            type = "Ngoài trời";
        }

        final String[] selectedOption = {""};
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.indoor) {
                    listener.onOptionSelected("Trong nhà");
                } else if (checkedId == R.id.outdoor) {
                    listener.onOptionSelected("Ngoài trời");
                }
            }
        });
    }

    private void provinceData() {

        city.setInputType(InputType.TYPE_NULL);

        String[] options = CreateEventConst.provinces;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        city.setAdapter(adapter);

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city.showDropDown();
            }
        });
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position).toString();
                Toast.makeText(UpdateEvent.this, item, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onClickBack() {
        ImageView backEventIcon = findViewById(R.id.backtodraft);
        backEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateEvent.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private static void fixDelay() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS) // Adjust the timeout duration as needed
                .readTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    private void hideDraftBoxAndShowUpdate() {
        ConstraintLayout createBox = findViewById(R.id.createBox);
        createBox.setVisibility(View.GONE);
        showUpdateBox();
    }

    private void showUpdateBox() {
        ConstraintLayout updateBox = findViewById(R.id.updatebox);
        updateBox.setVisibility(View.VISIBLE);
    }

    private void onClickUpdateButton(){
        ConstraintLayout updateButton = findViewById(R.id.updatebutton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String name = eventName.getText().toString();
                String startTime = startTimeText.getText().toString();
                String endTimeText = endTime.getText().toString();
                String startDateText = startDate.getText().toString();
                String endDateText = endDate.getText().toString();
                String eventTypeText = type;
                String eventLocationText = location.getText().toString();
                String eventAddressText = address.getText().toString();
                String cityText = city.getText().toString();
                String des = description.getText().toString();
                String linkVid = eventVideo.getText().toString();
                String linkWeb = websiteLink.getText().toString();
                UserDto userDto = new UserDto(1);

                builder.addFormDataPart("id", String.valueOf(eventUpdate.getId()));
                builder.addFormDataPart("imgUrl", String.valueOf(eventUpdate.getImgUrl()));
                builder.addFormDataPart("name", name);
                builder.addFormDataPart("startDate", startDateText);
                builder.addFormDataPart("endDate", endDateText);

                builder.addFormDataPart("startTime", startTime);
                builder.addFormDataPart("endTime", endTimeText);
                builder.addFormDataPart("location", eventLocationText);
                builder.addFormDataPart("address", eventAddressText);
                builder.addFormDataPart("city", cityText);
                builder.addFormDataPart("des", des);

                builder.addFormDataPart("eventVideo", linkVid);
                builder.addFormDataPart("websiteLink", linkWeb);
                builder.addFormDataPart("registrationType", eventTypeText);
                builder.addFormDataPart("userDto.id", String.valueOf(userDto.getId()));

                for(int i = 0; i< categoryDtoEdits.size(); i++){
                    builder.addFormDataPart("categories[" + String.valueOf(i) + "].categoryDto.id"
                            , String.valueOf(categoryDtoEdits.get(i).getId()));
                }

                callAPIUpdate(builder.build());
            }
        });
    }

    private void callAPIUpdate(RequestBody requestBody) {
        Log.i("Joke", "onClick: " +"update");

        fixDelay();

        Retrofit retrofit = BaseAPI.getRetrofitInstance();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.updateEvent(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateEvent.this);
                builder.setMessage("Cập nhật thành công!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                backToEventManage();
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

    private void backToEventManage() {
        Intent intent = new Intent(UpdateEvent.this, MainActivity.class);
        startActivity(intent);
    }
}

