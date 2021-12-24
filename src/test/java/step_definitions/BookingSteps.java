package step_definitions;

import com.github.javafaker.Faker;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import pojo.Booking;
import pojo.Bookingdates;
import pojo.Root;
import utilities.BaseUtils;
import utilities.HttpMethodUtils;

import java.util.List;
import java.util.Map;


public class BookingSteps {
    Response response = null;
    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    Integer totalPrice = faker.number().numberBetween(10, 100);
    boolean deposit = true;
    int bookingId = 0;
    String checkInDate = null;
    String checkOutDate = null;


    @Given("i call authentication end-pint and create token")
    public void iCallAuthenticationEndPintAndCreateToken() {
        //Assumed that the call pass the authentication
    }

    @When("i post customer information {string} {string}")
    public void i_post_customer_information(String checkInDate, String checkOutDate) {
        this.checkOutDate = checkOutDate;
        this.checkInDate = checkInDate;
        Booking booking = new Booking();
        Bookingdates bookingDate = new Bookingdates();
        bookingDate.setCheckin(checkInDate);
        bookingDate.setCheckout(checkOutDate);
        booking.setFirstname(firstName);
        booking.setLastname(lastName);
        booking.setDepositpaid(deposit);
        booking.setTotalprice(totalPrice);
        booking.setBookingdates(bookingDate);
        response = HttpMethodUtils.createPost(booking);
        bookingId = response.jsonPath().get("bookingid");

    }
    @Then("the customer is listed on booking list")
    public void the_customer_is_listed_on_booking_list() {
        int bookingId = response.jsonPath().get("bookingid");
        List<Root> bookings = HttpMethodUtils.getBookingList().jsonPath().getJsonObject("bookingid" );
        Assert.assertTrue(bookings.contains(bookingId));
    }

    @And("verify booking details")
    public void verifyBookingDetails() {
        response = HttpMethodUtils.getBookingDetails(bookingId);
        BaseUtils.assertEquals(firstName, response.jsonPath().get("firstname"));
        BaseUtils.assertEquals(lastName, response.jsonPath().get("lastname"));
        BaseUtils.assertEquals(totalPrice, response.jsonPath().get("totalprice"));
        BaseUtils.assertTrue(response.jsonPath().get("depositpaid"));
        BaseUtils.assertEquals(checkInDate, response.jsonPath().get("bookingdates.checkin"));
        BaseUtils.assertEquals(checkOutDate, response.jsonPath().get("bookingdates.checkout"));
    }


    @When("i post customer information")
    public void iPostCustomerInformation(DataTable dataTable) {
        List<Map<String, String>> bookingInfo = dataTable.asMaps(String.class, String.class);
        Booking booking = new Booking();
        Bookingdates bookingDate = new Bookingdates();

        this.firstName = bookingInfo.get(0).get("FirstName");
        this.lastName = bookingInfo.get(0).get("LastName");
        this.deposit = Boolean.parseBoolean(bookingInfo.get(0).get("Deposit"));
        this.totalPrice = Integer.parseInt(bookingInfo.get(0).get("Price"));
        this.checkInDate = bookingInfo.get(0).get("CheckInDate");
        this.checkOutDate = bookingInfo.get(0).get("CheckOutDate");

        booking.setFirstname(firstName);
        booking.setLastname(lastName);
        booking.setDepositpaid(deposit);
        booking.setTotalprice(totalPrice);
        bookingDate.setCheckin(checkInDate);
        bookingDate.setCheckout(checkOutDate);
        booking.setBookingdates(bookingDate);
        response = HttpMethodUtils.createPost(booking);
        bookingId=  response.jsonPath().get("bookingid");
    }

    @When("i delete a booking")
    public void iDeleteABooking() {
        HttpMethodUtils.deleteBooking(bookingId);
    }

    @Then("the booking is not available in the booking list")
    public void theBookingIsNotAvailableInTheBookingList() {
        List<Root> bookings = response.jsonPath().getJsonObject("bookingid" );
        Assert.assertFalse(bookings.contains(bookingId));
    }


}