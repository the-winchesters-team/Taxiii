package the.winchesters.taxiii.model;

import com.google.android.gms.maps.model.LatLng;

public class TaxiDriver extends User {
    private LatLng location;
    public TaxiDriver(String username, String firstName, String lastName, String phoneNumber) {
        super(username, firstName, lastName, phoneNumber);
    }

    public TaxiDriver(String username, String firstName, String lastName, String phoneNumber, LatLng location) {
        super(username, firstName, lastName, phoneNumber);
        this.location = location;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
