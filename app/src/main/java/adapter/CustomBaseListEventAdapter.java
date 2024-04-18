package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.app_test.CreateDetail;
import com.example.app_test.CreateEventDetail;
import com.example.app_test.MainActivity;
import com.example.app_test.R;
import com.example.app_test.ScheduleManage;
import com.example.app_test.TaskManage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import api.ApiService;
import api.BaseAPI;
import dto.BasicEventDto;
import dto.EventDto;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomBaseListEventAdapter extends BaseAdapter {

    private Context context;
    private List<String> eventName;
    private List<String> eventTime;
    private List<String> eventLocation;
    private List<Date> startDate;
    private List<String> eventImage;
    private List<Integer> eventId;
    private LayoutInflater layoutInflater;
    private Activity activity;
    private Dialog dialog;
    private Dialog dialogConfirm;
    private Dialog dialogSuccess;

    public CustomBaseListEventAdapter(Activity activity, List<BasicEventDto> eventDtoList){
        this.eventName = eventDtoList.stream()
                .map(BasicEventDto::getName)
                .collect(Collectors.toList());
        this.eventTime = eventDtoList.stream()
                .map(BasicEventDto::getStartTime)
                .collect(Collectors.toList());
        this.eventImage = eventDtoList.stream()
                .map(BasicEventDto::getImgUrl)
                .collect(Collectors.toList());
        this.eventId = eventDtoList.stream()
                .map(BasicEventDto:: getId)
                .collect(Collectors.toList());
        this.startDate = eventDtoList.stream()
                .map(BasicEventDto::getStartDate)
                .collect(Collectors.toList());
        this.eventLocation = eventDtoList.stream()
                .map(BasicEventDto::getLocation)
                .collect(Collectors.toList());
        this.activity = activity;
        this.context= activity.getApplicationContext();
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

        TextView idEventTime = (TextView) convertView.findViewById(R.id.datetimetext);
        idEventTime.setText(convertData(eventTime.get(position), startDate.get(position)));

        TextView eventLocationText = (TextView) convertView.findViewById(R.id.locationtext);
        eventLocationText.setText(eventLocation.get(position));

        ImageView imageView = convertView.findViewById(R.id.imageView5);
        Glide.with(context)
                .load(eventImage.get(position))
                .into(imageView);

        clickToSeeDetail(convertView, position);


        clickScheduleIcon(convertView);

        clickTaskIcon(convertView);

        onClickMore(convertView, position);
//
//        onClickDelete(convertView, position);
//
//        onClickNo(convertView);
//
//        onClickYes(convertView, position);

        return convertView;
    }

    private String convertData(String startTime, Date startDate) {
        String[] component = startDate.toString().split(" ");
        String date = component[0] + ", "
                + component[1] + " " + component[2] +
                " " + component[5] + " at " + startTime;
        return date;
    }

    private void clickToSeeDetail(View convertView, int position) {
        ConstraintLayout eventLayout = convertView.findViewById(R.id.all_event_layout);
        eventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(300, TimeUnit.SECONDS) // Adjust the timeout duration as needed
                        .readTimeout(300, TimeUnit.SECONDS)
                        .build();

                Retrofit retrofit = BaseAPI.getRetrofitInstance();

                ApiService apiService = retrofit.create(ApiService.class);
                Call<EventDto> call = apiService.getEventById(eventId.get(position));
                call.enqueue(new Callback<EventDto>() {
                    @Override
                    public void onResponse(Call<EventDto> call, Response<EventDto> response) {
                        EventDto eventDto = response.body();
                        Intent intent = new Intent(context, CreateDetail.class);
                        intent.putExtra("eventDto", eventDto);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<EventDto> call, Throwable t) {
                        Log.e("Joke", "Error fetching joke: " + t.getMessage());
                    }
                });
            }
        });
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

    private void onClickMore(View convertView, Integer position) {
        LinearLayout moreButton = convertView.findViewById(R.id.moreArea);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showDeleteDialog();
                    }

                    private void showDeleteDialog() {
                        dialog = new Dialog(activity);
                        dialog.setContentView(R.layout.event_more_action);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawable(Drawable.createFromPath("drawable/card_shape.xml"));
                        dialog.show();

                        ConstraintLayout deleteButton = dialog.findViewById(R.id.deletebutton);
                        deleteButton.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                showConfirmLayout(convertView, position);
                            }

                            private void showConfirmLayout(View convertView, Integer position) {
                                dialogConfirm = new Dialog(activity);
                                dialogConfirm.setContentView(R.layout.confirm_delete_action);
                                dialogConfirm.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialogConfirm.getWindow().setBackgroundDrawable(Drawable.createFromPath("drawable/card_shape.xml"));
                                dialog.dismiss();
                                dialogConfirm.show();

                                ConstraintLayout noBox = dialogConfirm.findViewById(R.id.nobox);
                                noBox.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogConfirm.dismiss();
                                    }
                                });


                                ConstraintLayout yesBox = dialogConfirm.findViewById(R.id.yesbox);
                                yesBox.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogConfirm.dismiss();
                                        callDeleteEventApi(position);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }


    private void callDeleteEventApi(Integer position) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS) // Adjust the timeout duration as needed
                .readTimeout(300, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = BaseAPI.getRetrofitInstance();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.deleteEvent(eventId.get(position));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialogSuccess = new Dialog(activity);
                dialogSuccess.setContentView(R.layout.success);
                dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogSuccess.getWindow().setBackgroundDrawable(Drawable.createFromPath("drawable/card_shape.xml"));
                dialogSuccess.show();

                ConstraintLayout yesBoxx = dialogSuccess.findViewById(R.id.yesbox);
                yesBoxx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        backToEventManage();
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Joke", "Error fetching joke: " + t.getMessage());
            }
        });
    }

    private void backToEventManage() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
