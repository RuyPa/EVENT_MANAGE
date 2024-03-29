package com.example.app_test;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import adapter.CustomBaseListEventAdapter;

public class Test extends AppCompatActivity {

    String[] eventName = {"Event of Duy", "Meeting Supplier"};
    String[] eventTime = {"Tue, Feb 27 2024 at 11:55 am", "Wed, Feb 28 2024 at 11:55 am"};

    String[] eventImage = {"https://res.cloudinary.com/dkf74ju3o/image/upload/v1711306370/tthnqg_x917k9.png",
                            "https://res.cloudinary.com/dkf74ju3o/image/upload/v1711306373/meeting_qvcxm1.png"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


//        listView = (ListView)findViewById(R.id.list_view);
//        CustomBaseListEventAdapter adapter = new CustomBaseListEventAdapter(getApplicationContext(), eventName, eventTime, eventImage);
//        listView.setAdapter(adapter);



    }
}
