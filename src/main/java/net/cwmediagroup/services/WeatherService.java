package net.cwmediagroup.services;

import net.cwmediagroup.connectors.DatabaseConnector;
import net.cwmediagroup.connectors.OpenWeatherApiConnector;
import net.cwmediagroup.dataobjects.LocationObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WeatherService {

    private final OpenWeatherApiConnector apiConnector = new OpenWeatherApiConnector();
    private final LocationService locationService = new LocationService();

    private DatabaseConnector connector;
    private Connection conn;

    public WeatherService() {
        this.connector = new DatabaseConnector();
        connector.connect();
        this.conn = connector.getConnection();
    }

    public boolean insertWeatherValues(ArrayList<LocationObject> locations) {

        try {
            ArrayList<String> insertValuesArray = new ArrayList<String>();

            for (LocationObject location : locations) {
                JSONObject jsonObject = apiConnector.getLocationData(location);
                String insertValue = this.buildValuesStatement(jsonObject, location.getLocationId());
                insertValuesArray.add(insertValue);
            }

            String fullInsertValueStatement = "";
            for (int i = 0; i < insertValuesArray.size(); i++) {
                fullInsertValueStatement = fullInsertValueStatement.concat(insertValuesArray.get(i));
                if (i != insertValuesArray.size() - 1) {
                    fullInsertValueStatement = fullInsertValueStatement.concat(",");
                }
            }

            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(this.buildInsertStatement(fullInsertValueStatement));
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String buildValuesStatement(JSONObject jsonObject, int locationId) {
        String values = "(";
        Float windSpeed;
        Float windGust;
        Float windDegree;
        JSONObject main = jsonObject.getJSONObject("main");
        JSONObject wind = jsonObject.getJSONObject("wind");
        JSONArray weatherArray = jsonObject.getJSONArray("weather");
        JSONObject weather = (JSONObject) weatherArray.get(0);

        float temp = main.getBigDecimal("temp").floatValue();
        float feelsLike = main.getBigDecimal("feels_like").floatValue();
        int pressure = main.getInt("pressure");
        int humidity = main.getInt("humidity");

        if (wind.has("speed")) {
            windSpeed = wind.getBigDecimal("speed").floatValue();
        } else {
            windSpeed = null;
        }

        if (wind.has("deg")) {
            windDegree = wind.getBigDecimal("deg").floatValue();
        } else {
            windDegree = null;
        }

        if (wind.has("gust")) {
            windGust = wind.getBigDecimal("gust").floatValue();
        } else {
            windGust = null;
        }

        int visibility = jsonObject.getInt("visibility");
        String weatherDesc = weather.getString("description");

        return values
                + temp + ','
                + feelsLike + ','
                + pressure + ','
                + humidity + ','
                + visibility + ','
                + windSpeed + ','
                + windDegree + ','
                + windGust + ','
                + "'" + weatherDesc + "'" + ','
                + locationId + ")";

    }

    private String buildInsertStatement(String fullInsertValueStatement) {
        return "INSERT INTO weather (temp, feels_like, pressure, humidity, visibility, wind_speed, wind_deg, wind_gust, weather_desc, location_id) VALUES " + fullInsertValueStatement + ";";
    }
}
