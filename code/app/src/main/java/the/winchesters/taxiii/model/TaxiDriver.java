package the.winchesters.taxiii.model;

import static the.winchesters.taxiii.model.Role.TAXI_DRIVER;

import com.google.android.gms.maps.model.LatLng;

public class TaxiDriver extends User {
    private MyLatLng location;
    public TaxiDriver(String username, String firstName, String lastName, String phoneNumber) {
        super(TAXI_DRIVER,username, firstName, lastName, phoneNumber);
    }

    public TaxiDriver() {

    }

    public TaxiDriver(String username, String firstName, String lastName, String phoneNumber, MyLatLng location) {
        super(TAXI_DRIVER,username, firstName, lastName, phoneNumber);
        this.location = location;
    }

    public MyLatLng getLocation() {
        return location;
    }

    public void setLocation(MyLatLng location) {
        this.location = location;
    }
}
