package the.winchesters.taxiii.model;

import com.google.android.gms.maps.model.LatLng;

public class TaxiDriver extends User {
    private MyLatLng location;
    public TaxiDriver(String username, String firstName, String lastName, String phoneNumber) {
        super(username, firstName, lastName, phoneNumber);
    }

    public TaxiDriver() {

    }

    public TaxiDriver(String username, String firstName, String lastName, String phoneNumber, MyLatLng location) {
        super(username, firstName, lastName, phoneNumber);
        this.location = location;
    }

    public MyLatLng getLocation() {
        return location;
    }

    public void setLocation(MyLatLng location) {
        this.location = location;
    }
}
