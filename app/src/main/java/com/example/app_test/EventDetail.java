package com.example.app_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import dto.EventDto;

public class EventDetail extends AppCompatActivity {

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
    ImageView calendar1;
    ImageView calendar2;
    ConstraintLayout cateBox;
    ConstraintLayout createBox ;
    ConstraintLayout updateThumbnaibox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        EventDto eventDto = (EventDto) getIntent().getSerializableExtra("eventDto");
        if(eventDto != null){
            getEventDetaiFromLastView(eventDto);
        } else{
            createEvent();
        }

        hideDraftBoxAndShowUpdate();

        onClickBack();

        onClickCreate();

        onClickUpdateButton();

        checkSeeDetail();
    }

    private void createEvent() {
        findById();
        title.setText("Create Event");
        EditText eventNameEdit = findViewById(R.id.textView5);
    }

    private void getEventDetaiFromLastView(EventDto eventDto) {
        setInfor(eventDto);
    }

    private void setInfor(EventDto eventDto) {

        findById();
        title.setText("Event Detail");
        Glide.with(this)
                .load(eventDto.getImgUrl())
                .into(eventImage);
        eventName.setText(eventDto.getName());
        startDateTime.setText(eventDto.getStartDate() + " " + eventDto.getStartTime());
        endDateTime.setText(eventDto.getEndDate() + " " + eventDto.getEndTime());
        type.setText(eventDto.getRegistrationType());
        location.setText(eventDto.getLocation());
        address.setText(eventDto.getAddress());
        city.setText(eventDto.getCity());
        description.setText(eventDto.getDes());
        eventVideo.setText(eventDto.getEventVideo());
        websiteLink.setText(eventDto.getWebsiteLink());
        cateBox.setVisibility(View.GONE);
        calendar1.setVisibility(View.GONE);
        calendar2.setVisibility(View.GONE);
        createBox.setVisibility(View.GONE);
        updateThumbnaibox.setVisibility(View.GONE);
    }

    private void findById() {
        title = findViewById(R.id.textView3);
        eventImage = findViewById(R.id.imageView5);
        eventName= findViewById(R.id.textView5);
        startDateTime= findViewById(R.id.textView6);
        endDateTime =findViewById(R.id.textView7);
        type = findViewById(R.id.textView9);
        location = findViewById(R.id.textView11);
        address = findViewById(R.id.textView12);
        city = findViewById(R.id.textView13);
        description = findViewById(R.id.textView14);
        eventVideo = findViewById(R.id.textView18);
        websiteLink = findViewById(R.id.textView17);
        calendar1 = findViewById(R.id.calendar1);
        calendar2 = findViewById(R.id.calendar2);
        cateBox = findViewById(R.id.catebox);
        createBox = findViewById(R.id.createBox);
        updateThumbnaibox = findViewById(R.id.updateThumbnailbox);
    }

    private void checkSeeDetail() {
        boolean extra = getIntent().getBooleanExtra("backToHome", false);
        if(extra){
            ImageView backEventIcon = findViewById(R.id.backtodraft);
            backEventIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EventDetail.this, MainActivity.class);
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
                Intent intent = new Intent(EventDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickCreate() {
        ConstraintLayout createButton = findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetail.this, MainActivity.class);
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
                Intent intent = new Intent(EventDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
