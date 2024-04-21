package adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.app_test.R;
import com.example.app_test.ScheduleDetail;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dto.BasicEventDto;
import dto.EventDto;
import dto.ScheduleDto;

public class CustomBaseListScheduleAdapter extends BaseAdapter {

    private final Integer eventId;
    private Context context;
    private LayoutInflater layoutInflater;
    private Activity activity;
    private List<Integer> idList;
    private List<String> nameList;
    private List<Date> startDateList;
    private List<Date> endDateList;
    private List<String> startTimeList;
    private List<String> endTimeList;
    private List<String> locationList;
    private List<ScheduleDto> scheduleDtos;
    private HashMap<String, Integer> weekHashMap = new HashMap<>();
    private HashMap<Date, Integer> scheduleOfDate = new HashMap<>();


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

    public CustomBaseListScheduleAdapter(Activity activity, List<ScheduleDto> scheduleDtos, Integer eventId) {
        this.scheduleDtos = scheduleDtos;
        this.eventId = eventId;
        this.idList = scheduleDtos.stream()
                .map(ScheduleDto::getId)
                .collect(Collectors.toList());
        this.nameList = scheduleDtos.stream()
                .map(ScheduleDto::getName)
                .collect(Collectors.toList());
        this.startDateList = scheduleDtos.stream()
                .map(ScheduleDto::getStartDate)
                .collect(Collectors.toList());
        this.endDateList = scheduleDtos.stream()
                .map(ScheduleDto::getEndDate)
                .collect(Collectors.toList());
        this.startTimeList = scheduleDtos.stream()
                .map(ScheduleDto::getStartTime)
                .collect(Collectors.toList());
        this.endTimeList = scheduleDtos.stream()
                .map(ScheduleDto::getEndTime)
                .collect(Collectors.toList());
        this.locationList = scheduleDtos.stream()
                .map(ScheduleDto::getLocation)
                .collect(Collectors.toList());
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.layoutInflater = LayoutInflater.from(context);
        this.weekHashMap = convertWeek();
        this.scheduleOfDate = convertSheduleDate();
    }

    private HashMap<Date, Integer> convertSheduleDate() {
        for (Date startDate : startDateList) {
            if (startDate != null)
                scheduleOfDate.put(startDate, 0);
        }
        for (Date endDate : endDateList) {
            if (endDate != null && !scheduleOfDate.containsKey(endDate))
                scheduleOfDate.put(endDate, 0);
        }
        return  scheduleOfDate;
    }

    private HashMap<String, Integer> convertWeek() {
        for (ScheduleDto dto : scheduleDtos) {
            Date getStartDate = dto.getStartDate();
            Date getEndDate = dto.getEndDate();
            String result = "";
            if (getStartDate != null) {
                result = covertDateToWeek(getStartDate);
            } else {
                result = covertDateToWeek(getEndDate);
            }
            if (!weekHashMap.containsKey(result)) {
                weekHashMap.put(result, 0);
            }
        }
        return weekHashMap;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Date startDate = startDateList.get(position);
        Date endDate = endDateList.get(position);
        String startTime = startTimeList.get(position);
        String endTime = endTimeList.get(position);

        convertView = layoutInflater.inflate(R.layout.activity_list_schedule, null);

        TextView nameText = (TextView) convertView.findViewById(R.id.thamgiatext);
        nameText.setText(nameList.get(position));

        TextView timeText = (TextView) convertView.findViewById(R.id.gioptich);
        timeText.setText(checkTime(startTime, endTime));

        TextView locationText = (TextView) convertView.findViewById(R.id.textView25);
        locationText.setText(locationList.get(position));

        onClickSeeDetail(convertView, position);

        ConstraintLayout dateBox = (ConstraintLayout) convertView.findViewById(R.id.thungaythang);
        TextView weekBox = (TextView) convertView.findViewById(R.id.weekbox);
        ImageView lineBox = (ImageView) convertView.findViewById(R.id.imageView8);
        TextView day = (TextView) convertView.findViewById(R.id.textView20);
        TextView date = (TextView) convertView.findViewById(R.id.textView21);

        checkFirstDayOfWeek(position, weekBox, startDate, day, date, dateBox);
        checkFirstScheduleOfDay(startDate, endDate, dateBox, weekBox, day, date);
        checkLastScheduleOfDay(position, startDate, endDate, lineBox);

        onClickSeeDetail(convertView, position);

        return convertView;
    }

    private void checkLastScheduleOfDay(int position, Date startDate, Date endDate, ImageView lineBox) {
        if(position == nameList.size() - 1){
            lineBox.setVisibility(View.VISIBLE);
        } else {
                String currentDate = "";
                if(startDate != null) currentDate = startDate.toString().split(" ")[2];
                else currentDate = endDate.toString().split(" ")[2];
                String followingDate = "";
                if(startDateList.get(position + 1)!= null) followingDate = startDateList.get(position + 1).toString().split(" ")[2];
                else followingDate = endDateList.get(position + 1).toString().split(" ")[2];

            if(!currentDate.equals(followingDate)){
                    lineBox.setVisibility(View.VISIBLE);
                }
        }
    }

    private void checkFirstScheduleOfDay(Date startDate, Date endDate, ConstraintLayout dateBox, TextView weekBox, TextView day, TextView date) {
        if (weekBox.getVisibility() == View.GONE) {
            if(startDate != null){
                setDateAndDay(dateBox, weekBox, day, date, startDate);
            } else {
                setDateAndDay(dateBox, weekBox, day, date, endDate);
            }
        }
    }

    private String checkTime(String startTime, String endTime) {
        if(startTime != null && endTime != null){
            return "Từ " + startTime + " đến " + endTime;
        } else if (startTime == null) {
            return "Đến " + endTime;
        } else {
            return "Từ " + startTime;
        }
    }

    private void setDateAndDay(ConstraintLayout dateBox, TextView weekBox, TextView day, TextView date, Date startDate) {
        int value = scheduleOfDate.get(startDate);
        if(value == 0){
            scheduleOfDate.put(startDate, value + 1);
            day.setText(startDate.toString().split(" ")[0]);
            date.setText(startDate.toString().split(" ")[2]);
            dateBox.setVisibility(View.VISIBLE);
        }
    }

    private void checkFirstDayOfWeek(int position, TextView weekBox, Date startDate, TextView day, TextView date, ConstraintLayout dateBox) {
        if (startDate != null) {
            int value = weekHashMap.get(covertDateToWeek(startDate));
            if (value == 0) {
                weekHashMap.put(covertDateToWeek(startDate), value + 1);
                weekBox.setText(covertDateToWeek(startDateList.get(position)));

                day.setText(startDate.toString().split(" ")[0]);
                date.setText(startDate.toString().split(" ")[2]);
                int vl = scheduleOfDate.get(startDate);
                scheduleOfDate.put(startDate, value + 1);


                weekBox.setVisibility(View.VISIBLE);
                dateBox.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean checkFirstDateOfWeek(int position) {
        Date startDate = scheduleDtos.get(position).getStartDate();
        if (startDate != null) {
            logicWithDate(startDate);
        }
        return false;
    }

    private boolean logicWithDate(Date startDate) {
        String keyWeek = covertDateToWeek(startDate);
        if (weekHashMap.get(keyWeek) == 0) {
            return true;
        } else {
            int value = weekHashMap.get(keyWeek);
            weekHashMap.put(keyWeek, value + 1);
        }
        return false;
    }


    private String covertDateToWeek(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int first = calendar.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_WEEK) + 2;
        int last = first + 6;

        calendar.set(Calendar.DAY_OF_MONTH, first);
        Date firstDay = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, last);
        Date lastDay = calendar.getTime();

        String[] split1 = firstDay.toString().split(" ");
        String[] split2 = lastDay.toString().split(" ");
        return split1[2] + " - " + split2[2] + " " + split2[1];
    }

    private void onClickSeeDetail(View convertView, int position) {
        Integer scheduleId = idList.get(position);
        ConstraintLayout scheduleBox = (ConstraintLayout) convertView.findViewById(R.id.schedulebox);

        scheduleBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScheduleDetail.class);
                Log.i("Joke last", "onClick: "+ String.valueOf(idList.get(position)));
                intent.putExtra("scheduleId", idList.get(position));
                intent.putExtra("eventId", eventId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
}
