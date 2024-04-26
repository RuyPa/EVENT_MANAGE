package dto;

import java.util.Date;

public class TaskDto {

    private Integer id;
    private String name;
    private Date startDate;
    private String startTime;
    private String des;
    private Integer checked;

    public TaskDto() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getDes() {
        return des;
    }

    public Integer getChecked() {
        return checked;
    }

    public TaskDto(Integer id, String name, Date startDate, String startTime, String des, Integer checked) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.des = des;
        this.checked = checked;
    }
}
