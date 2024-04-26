package adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.app_test.R;
import com.example.app_test.TaskManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import api.ApiService;
import api.BaseAPI;
import dto.TaskDto;
import dto.TaskDto;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomBaseListTaskAdapter extends BaseAdapter {

    private final Integer eventId;
    private Context context;
    private LayoutInflater layoutInflater;
    private Activity activity;
    private List<Integer> idList;
    private List<String> nameList;
    private List<Date> startDateList;
    private List<String> startTimeList;
    private List<Integer> checkedList;
    private List<TaskDto> taskDtos;
    private HashMap<String, Integer> weekHashMap = new HashMap<>();
    private HashMap<Date, List<Integer>> taskOfDate = new HashMap<>();
    private ConstraintLayout bottomFrame;
    private ConstraintLayout headFrame;
    private int count = 0;

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public CustomBaseListTaskAdapter(Activity activity, List<TaskDto> taskDtos, Integer eventId) {

        this.taskDtos = taskDtos;
        this.eventId = eventId;
        this.idList = taskDtos.stream()
                .map(TaskDto::getId)
                .collect(Collectors.toList());
        this.nameList = taskDtos.stream()
                .map(TaskDto::getName)
                .collect(Collectors.toList());
        this.startDateList = taskDtos.stream()
                .map(TaskDto::getStartDate)
                .collect(Collectors.toList());
        this.startTimeList = taskDtos.stream()
                .map(TaskDto::getStartTime)
                .collect(Collectors.toList());
        this.checkedList = taskDtos.stream()
                .map(TaskDto::getChecked)
                .collect(Collectors.toList());
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.layoutInflater = LayoutInflater.from(context);
//        this.weekHashMap = convertWeek();
        this.taskOfDate = convertSheduleDate(taskDtos);
    }

    private HashMap<Date, List<Integer>> convertSheduleDate(List<TaskDto> taskDtos) {
        HashMap<Date, List<Integer>> scheduleMap = new HashMap<>();
        taskDtos.forEach(taskDto -> {
            Date startDate = taskDto.getStartDate();
            Integer taskId = taskDto.getId();
            scheduleMap.computeIfAbsent(startDate, k -> new ArrayList<>()).add(taskId);
        });
        return scheduleMap;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date startDate = startDateList.get(position);
        String startTime = startTimeList.get(position);

        convertView = layoutInflater.inflate(R.layout.activity_list_task, null);

        TextView nameText = (TextView) convertView.findViewById(R.id.name);
        nameText.setText(nameList.get(position));

        TextView timeText = (TextView) convertView.findViewById(R.id.time);
        timeText.setText(startTimeList.get(position));

        hideComponent(convertView);

        addOrHide(convertView, position);

        showCheckedBox(convertView, position);

        hideCheckedBox(convertView, position);

//        onClickSeeDetail(convertView, position);
//
//        ConstraintLayout dateBox = (ConstraintLayout) convertView.findViewById(R.id.thungaythang);
//        TextView weekBox = (TextView) convertView.findViewById(R.id.weekbox);
//        ImageView lineBox = (ImageView) convertView.findViewById(R.id.imageView8);
//        TextView day = (TextView) convertView.findViewById(R.id.textView20);
//        TextView date = (TextView) convertView.findViewById(R.id.textView21);
//
//        checkFirstDayOfWeek(position, weekBox, startDate, day, date, dateBox);
//        checkFirstScheduleOfDay(startDate, endDate, dateBox, weekBox, day, date);
//        checkLastScheduleOfDay(position, startDate, endDate, lineBox);
//
//        onClickSeeDetail(convertView, position);

        return convertView;
    }

    private void hideCheckedBox(View convertView, int position) {
        ImageView checkedBox = (ImageView) convertView.findViewById(R.id.checkbox);

        checkedBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUpdateCheckedAPI(0, position, convertView);
            }
        });
    }

    private void showCheckedBox(View convertView, int position) {
        ImageView square = (ImageView) convertView.findViewById(R.id.square);
        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUpdateCheckedAPI(1, position, convertView);
            }
        });
    }


    private void callUpdateCheckedAPI(int done, int position, View convertView) {
        fixDelay();

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("id", String.valueOf(taskDtos.get(position).getId()));
        builder.addFormDataPart("checked", String.valueOf(done));

        Retrofit retrofit = BaseAPI.getRetrofitInstance();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.updateChecked(builder.build());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("Joke", "onResponse: " +String.valueOf(done));
                if (done == 1) {
                    showChecked(convertView);
                } else {
                    hideChecked(convertView);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Joke", "Error fetching joke: " + t.getMessage());
            }
        });
    }

    private void hideChecked(View convertView) {

        ImageView checkBox = (ImageView) convertView.findViewById(R.id.checkbox);
        ImageView square = (ImageView) convertView.findViewById(R.id.square);

        square.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.GONE);
    }

    private void showChecked(View convertView) {

        ImageView checkBox = (ImageView) convertView.findViewById(R.id.checkbox);
        ImageView square = (ImageView) convertView.findViewById(R.id.square);

        square.setVisibility(View.GONE);
        checkBox.setVisibility(View.VISIBLE);
    }

    private void fixDelay() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS) // Adjust the timeout duration as needed
                .readTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    private void addOrHide(View convertView, int position) {

        if (checkedList.get(position) != null && checkedList.get(position) == 1) {
            showChecked(convertView);
        }

        List<Integer> check = taskOfDate.get(taskDtos.get(position).getStartDate());
        int id = taskDtos.get(position).getId();
        int firstPosition = -1;
        for (int i = 0; i < check.size(); i++) {
            if (check.get(i) == id) {
                firstPosition = i;
                break;
            }
        }

        if (firstPosition == 0) {

            Log.i("Joke color", "addOrHide: " + position);
            if (count % 3 == 0)
                headFrame.setBackgroundResource(R.drawable.orange_border_top);
            if (count % 3 == 1)
                headFrame.setBackgroundResource(R.drawable.blue_border_top);
            if (count % 3 == 2)
                headFrame.setBackgroundResource(R.drawable.red_border_top);
            headFrame.setVisibility(View.VISIBLE);
            TextView startTime = convertView.findViewById(R.id.startTime);
            startTime.setText(converDate(taskDtos.get(position).getStartDate()));

            count++;


        }

        if (firstPosition == check.size() - 1) {
            bottomFrame.setVisibility(View.VISIBLE);
        }


    }

    private String converDate(Date startDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(startDate);
    }

    private void hideComponent(View convertView) {
        headFrame = convertView.findViewById(R.id.orangerborder1);
        bottomFrame = convertView.findViewById(R.id.botomframe);

        headFrame.setVisibility(View.GONE);
        bottomFrame.setVisibility(View.GONE);
    }


}
