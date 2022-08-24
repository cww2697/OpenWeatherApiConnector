package net.cwmediagroup.dataobjects;

public class LocationObject {

    private int locationId;
    private String locationLat;
    private String locationLong;
    private String locationCity;
    private String locationState;
    private String locationCountry;

    public LocationObject(int locationId, String locationLat, String locationLong, String locationCity, String locationCountry) {
        this.locationId = locationId;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.locationCity = locationCity;
        this.locationCountry = locationCountry;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(String locationLong) {
        this.locationLong = locationLong;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationState() {
        return locationState;
    }

    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    public String getLocationCountry() {
        return locationCountry;
    }

    public void setLocationCountry(String locationCountry) {
        this.locationCountry = locationCountry;
    }
}
