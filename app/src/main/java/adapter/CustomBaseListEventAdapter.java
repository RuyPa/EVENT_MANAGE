package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app_test.R;
import com.example.app_test.ScheduleManage;
import com.example.app_test.TaskManage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import dto.BasicEventDto;

public class CustomBaseListEventAdapter extends BaseAdapter {

    private Context context;
    private List<String> eventName;
    private List<String> eventTime;

    private List<String> eventImage;
    private LayoutInflater layoutInflater;

    public CustomBaseListEventAdapter(Context context, List<BasicEventDto> eventDtoList){
        this.eventName = eventDtoList.stream()
                                    .map(BasicEventDto::getName)
                                    .collect(Collectors.toList());
        this.eventTime = eventDtoList.stream()
                                    .map(BasicEventDto::getStartTime)
                                    .collect(Collectors.toList());
        this.eventImage = eventDtoList.stream()
                                    .map(BasicEventDto::getImgUrl)
                                    .collect(Collectors.toList());
        this.context= context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return eventName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.activity_list_event, null);

        TextView idEventName = (TextView) convertView.findViewById(R.id.event_name);
        idEventName.setText(eventName.get(position));

        TextView idEventTime = (TextView) convertView.findViewById(R.id.event_time);
        idEventTime.setText(eventTime.get(position));

        ImageView imageView = convertView.findViewById(R.id.event_image);
        Glide.with(context)
                .load(eventImage.get(position))
                .into(imageView);


        clickScheduleIcon(convertView);

        clickTaskIcon(convertView);

        return convertView;
    }

    private void clickTaskIcon(View convertView) {
        LinearLayout taskLinear = convertView.findViewById(R.id.task_linear);
        taskLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start a new activity
                Intent intent = new Intent(context, TaskManage.class);
                // You can also put extra data into the intent if needed
                // intent.putExtra("key", "value");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void clickScheduleIcon(View convertView) {
        LinearLayout scheduleIcon = convertView.findViewById(R.id.schedule_linear);
        scheduleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start a new activity
                Intent intent = new Intent(context, ScheduleManage.class);
                // You can also put extra data into the intent if needed
                // intent.putExtra("key", "value");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
}
