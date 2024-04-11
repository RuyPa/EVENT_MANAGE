package dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
public class EventDto implements Serializable {

    private Integer id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String startTime;
    private String endTime;
    private String location;
    private String address;
    private String city;
    private String des;
    private String eventVideo;
    private String registrationType;
    private String websiteLink;
    private String imgUrl;
    private List<EventCategoryDto> categories;
    private UserDto userDto;

    public EventDto() {
    }

    public EventDto(Integer id, String name, Date startDate, Date endDate, String startTime, String endTime, String location, String address, String city, String des, String eventVideo, String registrationType, String websiteLink, String imgUrl, List<EventCategoryDto> categories, UserDto userDto) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.address = address;
        this.city = city;
        this.des = des;
        this.eventVideo = eventVideo;
        this.registrationType = registrationType;
        this.websiteLink = websiteLink;
        this.imgUrl = imgUrl;
        this.categories = categories;
        this.userDto = userDto;
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

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setEventVideo(String eventVideo) {
        this.eventVideo = eventVideo;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCategories(List<EventCategoryDto> categories) {
        this.categories = categories;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
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

    public Date getEndDate() {
        return endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getDes() {
        return des;
    }

    public String getEventVideo() {
        return eventVideo;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<EventCategoryDto> getCategories() {
        return categories;
    }

    public UserDto getUserDto() {
        return userDto;
    }
}
