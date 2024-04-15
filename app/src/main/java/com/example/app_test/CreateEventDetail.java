package com.example.app_test;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import adapter.CustomBaseListEventAdapter;
import api.ApiService;
import api.BaseAPI;
import avtInterface.OnOptionSelectedListener;
import constant.CreateEventConst;
import dto.BasicEventDto;
import dto.CategoryDto;
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

public class CreateEventDetail extends AppCompatActivity{


    TextView title ;
    ImageView eventImage ;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_detail);

        setCate();

        onClickCalendar1();

        getInforFromCalendar(calendar1);

        onClickCalendar2();

        getInforFromCalendar2(calendar2);
        
        setStartTime();

        setEndTime();

        insertImage();

        onClickAnyWhere();

        createEvent();

//        hideDraftBoxAndShowUpdate();
//
//        onClickBack();
//
        onClickCreate();
//
//        onClickUpdateButton();
//
//        checkSeeDetail();
    }

    private void setStartTime() {

        startTimeText = findViewById(R.id.starttimetextview);
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

        endTime = findViewById(R.id.endtimetextview);
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
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categoryDtos.add(new CategoryDto(5,"kids"));
        categoryDtos.add(new CategoryDto(6, "virtual"));
        LinearLayout checkboxContainer = findViewById(R.id.checkboxContainer);
        for (CategoryDto categoryDto: categoryDtos) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(categoryDto.getName());
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
            params.setMargins(0, 0, 0, 16); // Thiết lập margin bottom là 16 pixels
            checkBox.setLayoutParams(params);
            checkboxContainer.addView(checkBox);
        }
    }

    private void insertImage() {
        getUpdateThumbnaibox = findViewById(R.id.updateThumbnailbox);
        getImageView5 = findViewById(R.id.imageView5);

        getUpdateThumbnaibox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(CreateEventDetail.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Uri uri = data.getData();
//        getImageView5.setImageURI(uri);
//
//        super.onActivityResult(requestCode, resultCode, data);
//        String imagePath = getPathFromUri(uri);
//        // Thêm đường dẫn ảnh vào builder
//        if (imagePath != null) {
//            File imageFile = new File(imagePath);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
//            builder.addFormDataPart("file", imageFile.getName(), requestBody);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            Log.i("Joke", "onActivityResult: " +uri.toString());
            Log.i("Joke", "onActivityResult: " +getPathFromUri(uri));
                getImageView5.setImageURI(uri);
                String imagePath = getPathFromUri(uri);

                    File imageFile = new File(uri.getPath());
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                    builder.addFormDataPart("file", imageFile.getName(), requestBody);
        }
    }

    private String getPathFromUri(Uri uri) {
        String path = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (columnIndex != -1) {
                path = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return path;
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
                Toast.makeText(CreateEventDetail.this, dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                TextView dateTimeTextView;
                if (isStartDateTimeSelected) {
                    startDate = findViewById(R.id.textView6);
                    startDate.setText(year+"-"+month+"-"+dayOfMonth);
                } else {
                    endDate = findViewById(R.id.textView7);
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

        title = findViewById(R.id.textView3);
        title.setText("Create Event");

        eventName = findViewById(R.id.textView5);
        location = findViewById(R.id.textView11);
        address = findViewById(R.id.textView12);

        provinceData();

        description = findViewById(R.id.textView14);

        eventVideo = findViewById(R.id.textView18);

        websiteLink = findViewById(R.id.textView17);

        final String[] eventType = {""};
        onRadioClickView(new OnOptionSelectedListener() {
            @Override
            public void onOptionSelected(String selectedOption) {
                eventType[0] = selectedOption;
                type = eventType[0];
            }
        });



//
//        EditText startDateTimeEdit = findViewById(R.id.textView6);
//        String startDateTime = startDateTimeEdit.getText().toString();
//
//        EditText endDateTimeEdit = findViewById(R.id.textView7);
//        String endDateTime = endDateTimeEdit.getText().toString();
//
//        EditText typeEdit = findViewById(R.id.textView9);
//        String type = typeEdit.getText().toString();
//
//        EditText locationEdit = findViewById(R.id.textView11);
//        String location = locationEdit.getText().toString();
//
//        EditText addressEdit = findViewById(R.id.textView12);
//        String address = addressEdit.getText().toString();
//
//        EditText cityEdit = findViewById(R.id.textView13);
//        String city = cityEdit.getText().toString();
//
//        EditText descriptionEdit = findViewById(R.id.textView14);
//        String description = descriptionEdit.getText().toString();
//
//        EditText eventVideoEdit = findViewById(R.id.textView18);
//        String eventVideo = eventVideoEdit.getText().toString();
//
//        EditText websiteEdit = findViewById(R.id.textView17);
//        String website = websiteEdit.getText().toString();

    }

    public void onRadioClickView(OnOptionSelectedListener listener){
        RadioGroup radioGroup = findViewById(R.id.wherefill);
        final RadioButton indoorRadioButton = findViewById(R.id.indoor);
        final RadioButton outdoorRadioButton = findViewById(R.id.outdoor);

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

        city = findViewById(R.id.autoCompleteTextViewCity);

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
                Toast.makeText(CreateEventDetail.this, item, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void checkSeeDetail() {
        boolean extra = getIntent().getBooleanExtra("backToHome", false);
        if(extra){
            ImageView backEventIcon = findViewById(R.id.backtodraft);
            backEventIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CreateEventDetail.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void onClickBack() {
        ImageView backEventIcon = findViewById(R.id.backtodraft);
        backEventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEventDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickCreate() {
        ConstraintLayout createButton = findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDto eventDto = new EventDto();

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

                callAPI(builder.build());
            }
        });
    }

    private void callAPI(RequestBody requestBody) {
        fixDelay();

        Retrofit retrofit = BaseAPI.getRetrofitInstance();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.createEvent(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody eventDtoList = response.body();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
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

    private void hideDraftBoxAndShowUpdate() {

        boolean showEvent = getIntent().getBooleanExtra("hideDraftBox", false);
        if (showEvent) {
            ConstraintLayout createBox = findViewById(R.id.createBox);
            if (createBox.getVisibility() == View.VISIBLE) {
                createBox.setVisibility(View.GONE);
                showUpdateBox();
            }
        }
    }

    private void showUpdateBox() {
        ConstraintLayout updateBox = findViewById(R.id.updatebox);
        if (updateBox.getVisibility() == View.GONE) {
            updateBox.setVisibility(View.VISIBLE);
        }
    }

    private void onClickUpdateButton(){
        ConstraintLayout updateButton = findViewById(R.id.updatebutton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEventDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
