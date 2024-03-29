package api;


import java.util.List;

import dto.BasicEventDto;
import dto.EventDto;
import dto.TestDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/v1/events/my-event?userId=1") // Specify the endpoint path (relative to the base URL)
    Call<List<BasicEventDto>> getBasicEventData();

    @GET("/api/v1/events")
    Call<EventDto> getEventById(@Query("id")String eventId);
}
