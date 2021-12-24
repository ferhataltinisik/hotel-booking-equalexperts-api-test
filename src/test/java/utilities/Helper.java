package utilities;

import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import pojo.Booking;
import pojo.Bookingdates;

import java.util.Locale;
import java.util.Map;

public class Helper implements TypeRegistryConfigurer {
    public Locale locale() {
        return Locale.ENGLISH;
    }

    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        Bookingdates bookingdates = new Bookingdates();
        typeRegistry.defineDataTableType(new DataTableType(Booking.class,
                        (Map<String, String> row) -> {
                            String firstname = row.get("firstname");
                            String lastname = row.get("lastname");
                            int totalprice = Integer.parseInt(row.get("totalprice"));
                            boolean depositpaid = Boolean.parseBoolean(row.get("depositpaid"));
                            bookingdates.setCheckin(row.get("checkin"));
                            bookingdates.setCheckout(row.get("checkin"));
                            return new Booking(firstname, lastname, totalprice, depositpaid, bookingdates);
                        }
                )
        );
    }
}
