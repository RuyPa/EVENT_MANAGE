package dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BasicEventDto implements Serializable {

    Integer id;
    String name;
    Date startDate;
    String startTime;
    String imgUrl;

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

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public BasicEventDto(Integer id, String name, Date startDate, String startTime, String imgUrl) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.imgUrl = imgUrl;
    }

    public BasicEventDto() {
    }
}
