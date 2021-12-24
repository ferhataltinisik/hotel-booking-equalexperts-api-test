package utilities;

import io.restassured.response.Response;
import org.json.JSONArray;
import pojo.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpMethodUtils extends BaseUtils{


    public static Response createPost(Booking booking) {
        String url = ConfigurationReader.get("baseUri") + ConfigurationReader.get("booking_end_point");
        String payload = createJSONPayload(booking).toString();
        return sendRequest( BaseUtils.POST, url, payload);
    }

    public static Response getBookingList() {
        String url = ConfigurationReader.get("baseUri") + ConfigurationReader.get("booking_end_point");
        return sendRequest( BaseUtils.GET, url, null);
    }

    public static Response getBookingDetails(int bookingId) {
        String url = ConfigurationReader.get("baseUri") + ConfigurationReader.get("booking_end_point") + bookingId;
        System.out.println("urlll " + url);
        return sendRequest( BaseUtils.GET, url, null);
    }

    public static Response deleteBooking(int bookingId) {
        String url = ConfigurationReader.get("baseUri") + ConfigurationReader.get("booking_end_point") + bookingId;
        return sendRequest( BaseUtils.DELETE, url, null);
    }


}
