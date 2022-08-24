package net.cwmediagroup.services;

import net.cwmediagroup.connectors.DatabaseConnector;
import net.cwmediagroup.dataobjects.LocationObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LocationService {
    private DatabaseConnector connector;
    private Connection conn;

    public LocationService() {
        this.connector = new DatabaseConnector();
        connector.connect();
        this.conn = connector.getConnection();
    }

    public ArrayList<LocationObject> getLocations() {
        try {
            ArrayList<LocationObject> locations = new ArrayList<LocationObject>();
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM location");
            while (rs.next()) {
                int locationId = rs.getInt("location_id");
                String locationLat = rs.getString("location_lat");
                String locationLong = rs.getString("location_long");
                String locationCity = rs.getString("city");
                String locationState = rs.getString("state");
                String locationCountry = rs.getString("country");

                LocationObject locationObject = new LocationObject(locationId, locationLat, locationLong, locationCity, locationCountry);
                if (locationState != null) {
                    locationObject.setLocationState(locationState);
                }
                locations.add(locationObject);
            }

            return locations;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<LocationObject>();
    }
}
