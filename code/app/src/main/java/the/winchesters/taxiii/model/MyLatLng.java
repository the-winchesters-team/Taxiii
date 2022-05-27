package the.winchesters.taxiii.model;

import com.google.android.gms.maps.model.LatLng;

public class MyLatLng {
    Double latitude;
    Double longitude;

    public MyLatLng(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public MyLatLng() {
    }
    public MyLatLng(LatLng latLng) {
        longitude = latLng.longitude;
        latitude = latLng.latitude;
    }
}
