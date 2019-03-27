package com.example.weatherapp1;

public class DisplayItem {
    private String id;
    private String country;
    private String windSpeed;
    private String visibility;
    private String temperature;
    private String humidity;
    private String pressure;

    public DisplayItem() {
    }

    public DisplayItem(String id, String country, String windSpeed, String visibility, String temperature, String humidity, String pressure) {
        this.id = id;
        this.country = country;
        this.windSpeed = windSpeed;
        this.visibility = visibility;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "DisplayItem{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", visibility='" + visibility + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                '}';
    }
}
