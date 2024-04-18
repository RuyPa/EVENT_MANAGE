package api;


import java.util.List;

import dto.BasicEventDto;
import dto.CategoryDto;
import dto.EventDto;
import dto.TestDto;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/v1/events/my-event?userId=1") // Specify the endpoint path (relative to the base URL)
    Call<List<BasicEventDto>> getBasicEventData();

    @GET("/api/v1/events")
    Call<EventDto> getEventById(@Query("id")Integer eventId);

    @POST("/api/v1/events")
    Call<ResponseBody> createEvent(@Body RequestBody requestBody);

    @DELETE("/api/v1/events")
    Call<ResponseBody> deleteEvent(@Query("id")Integer eventId);

    @GET("api/v1/categories")
    Call<List<CategoryDto>> getAllCate();
}
