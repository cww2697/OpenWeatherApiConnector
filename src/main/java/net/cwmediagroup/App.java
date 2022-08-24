package net.cwmediagroup;

import net.cwmediagroup.dataobjects.LocationObject;
import net.cwmediagroup.services.LocationService;
import net.cwmediagroup.services.WeatherService;

import java.util.ArrayList;

public class App {

    private static final LocationService locationService = new LocationService();
    private static final WeatherService weatherService = new WeatherService();

    public static void main(String[] args) {

        ArrayList<LocationObject> locations = locationService.getLocations();
        boolean insertComplete = weatherService.insertWeatherValues(locations);
        if (!insertComplete) {
            System.exit(2);
        }

    }
}
