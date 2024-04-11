package com.example.app_test;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dto.EventDto;

public class CreateEventDetail extends AppCompatActivity{


    TextView title ;
    ImageView eventImage ;
    TextView eventName;
    TextView startDateTime;
    TextView endDateTime ;
    TextView type ;
    TextView location ;
    TextView address;
    TextView city;
    TextView description ;
    TextView eventVideo ;
    TextView websiteLink ;
    Calendar calendar1;
    Calendar calendar2;
    ConstraintLayout cateBox;
    ConstraintLayout createBox ;
    ConstraintLayout updateThumbnaibox;
    CalendarView calendarView;
    ConstraintLayout calendarLayout;
    ConstraintLayout getUpdateThumbnaibox;
    ImageView getImageView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_detail);

        onClickCalendar();

        getInforFromCalendar(calendar1, 6);

        insertImage();

        onClickAnyWhere();

        try {
            createEvent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        hideDraftBoxAndShowUpdate();
//
//        onClickBack();
//
//        onClickCreate();
//
//        onClickUpdateButton();
//
//        checkSeeDetail();
    }

    private void insertImage() {
        getUpdateThumbnaibox = findViewById(R.id.updateThumbnailbox);
        getImageView5 = findViewById(R.id.imageView5);

        getUpdateThumbnaibox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(CreateEventDetail.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Uri uri = data.getData();
//        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.border_top);
//        getImageView5.setMaxWidth(350);
//        getImageView5.setMaxHeight(175);
//        getImageView5.setBackground(drawable);
//        getImageView5.setImageURI(uri);
//    }

    private void onClickCalendar() {
        ImageView calendarIcon = findViewById(R.id.calendar1);
        calendarLayout = findViewById(R.id.calendarr);

        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getInforFromCalendar(Calendar calendar, int calendarId) {
        calendarView = findViewById(R.id.calendarView);
        calendar1 = Calendar.getInstance();

        setDate(calendar1);
        getDate();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(CreateEventDetail.this, dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                if(calendarId == 6){
                    startDateTime = findViewById(R.id.textView6);
                } else {
                    startDateTime = findViewById(R.id.textView7);
                }
                startDateTime.setText(year+"-"+month+"-"+dayOfMonth);
                calendarLayout.setVisibility(View.GONE);
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

    private void onClickAnyWhere(){
        ConstraintLayout anywhere = findViewById(R.id.anywhere);
        anywhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarLayout.setVisibility(View.GONE);
            }
        });
    }

    private void createEvent() throws Exception {

        title = findViewById(R.id.textView3);
        title.setText("Create Event");

        TextInputEditText eventNameEdit = findViewById(R.id.textView5);
        String eventName = eventNameEdit.getText().toString();

        enumTypeRegistration();

        provinceData();
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

    private void provinceData() throws Exception {

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextViewCity);

        autoCompleteTextView.setInputType(InputType.TYPE_NULL);

        String[] options = progressProvinceData();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.showDropDown();
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position).toString();
                Toast.makeText(CreateEventDetail.this, item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String[] progressProvinceData() throws Exception {
        String exampleRequest = FileUtils.readFileToString(new File("com/example/app_test/province.json"), StandardCharsets.UTF_8);
        Log.i("Joke", exampleRequest);
        String[] array = {exampleRequest};
        return array;
    }

    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    private void enumTypeRegistration() {
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        autoCompleteTextView.setInputType(InputType.TYPE_NULL);

        String[] options = new String[]{"indoor", "outdoor"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.showDropDown();
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                Intent intent = new Intent(CreateEventDetail.this, MainActivity.class);
                intent.putExtra("showEvent", true);
                startActivity(intent);
            }
        });
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
