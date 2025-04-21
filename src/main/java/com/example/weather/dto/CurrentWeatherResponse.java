package com.example.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherResponse {
    private Location location;
    private Current current;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public static class Location {
        private String name;
        private String region;
        private String country;
        private double lat;
        private double lon;
        @JsonProperty("tz_id")
        private String tzId;
        @JsonProperty("localtime_epoch")
        private long localtimeEpoch;
        private String localtime;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getTzId() {
            return tzId;
        }

        public void setTzId(String tzId) {
            this.tzId = tzId;
        }

        public long getLocaltimeEpoch() {
            return localtimeEpoch;
        }

        public void setLocaltimeEpoch(long localtimeEpoch) {
            this.localtimeEpoch = localtimeEpoch;
        }

        public String getLocaltime() {
            return localtime;
        }

        public void setLocaltime(String localtime) {
            this.localtime = localtime;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Current {
        @JsonProperty("last_updated_epoch")
        private long lastUpdatedEpoch;
        @JsonProperty("last_updated")
        private String lastUpdated;
        @JsonProperty("temp_c")
        private double tempC;
        @JsonProperty("temp_f")
        private double tempF;
        @JsonProperty("is_day")
        private int isDay;
        private Condition condition;
        @JsonProperty("wind_mph")
        private double windMph;
        @JsonProperty("wind_kph")
        private double windKph;
        @JsonProperty("wind_degree")
        private int windDegree;
        @JsonProperty("wind_dir")
        private String windDir;
        @JsonProperty("pressure_mb")
        private double pressureMb;
        @JsonProperty("pressure_in")
        private double pressureIn;
        @JsonProperty("precip_mm")
        private double precipMm;
        @JsonProperty("precip_in")
        private double precipIn;
        private int humidity;
        private int cloud;
        @JsonProperty("feelslike_c")
        private double feelsLikeC;
        @JsonProperty("feelslike_f")
        private double feelsLikeF;
        @JsonProperty("vis_km")
        private double visKm;
        @JsonProperty("vis_miles")
        private double visMiles;
        @JsonProperty("uv")
        private double uv;
        @JsonProperty("gust_mph")
        private double gustMph;
        @JsonProperty("gust_kph")
        private double gustKph;

        // Getters and Setters
        public long getLastUpdatedEpoch() {
            return lastUpdatedEpoch;
        }

        public void setLastUpdatedEpoch(long lastUpdatedEpoch) {
            this.lastUpdatedEpoch = lastUpdatedEpoch;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public double getTempC() {
            return tempC;
        }

        public void setTempC(double tempC) {
            this.tempC = tempC;
        }

        public double getTempF() {
            return tempF;
        }

        public void setTempF(double tempF) {
            this.tempF = tempF;
        }

        public int getIsDay() {
            return isDay;
        }

        public void setIsDay(int isDay) {
            this.isDay = isDay;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public double getWindMph() {
            return windMph;
        }

        public void setWindMph(double windMph) {
            this.windMph = windMph;
        }

        public double getWindKph() {
            return windKph;
        }

        public void setWindKph(double windKph) {
            this.windKph = windKph;
        }

        public int getWindDegree() {
            return windDegree;
        }

        public void setWindDegree(int windDegree) {
            this.windDegree = windDegree;
        }

        public String getWindDir() {
            return windDir;
        }

        public void setWindDir(String windDir) {
            this.windDir = windDir;
        }

        public double getPressureMb() {
            return pressureMb;
        }

        public void setPressureMb(double pressureMb) {
            this.pressureMb = pressureMb;
        }

        public double getPressureIn() {
            return pressureIn;
        }

        public void setPressureIn(double pressureIn) {
            this.pressureIn = pressureIn;
        }

        public double getPrecipMm() {
            return precipMm;
        }

        public void setPrecipMm(double precipMm) {
            this.precipMm = precipMm;
        }

        public double getPrecipIn() {
            return precipIn;
        }

        public void setPrecipIn(double precipIn) {
            this.precipIn = precipIn;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getCloud() {
            return cloud;
        }

        public void setCloud(int cloud) {
            this.cloud = cloud;
        }

        public double getFeelsLikeC() {
            return feelsLikeC;
        }

        public void setFeelsLikeC(double feelsLikeC) {
            this.feelsLikeC = feelsLikeC;
        }

        public double getFeelsLikeF() {
            return feelsLikeF;
        }

        public void setFeelsLikeF(double feelsLikeF) {
            this.feelsLikeF = feelsLikeF;
        }

        public double getVisKm() {
            return visKm;
        }

        public void setVisKm(double visKm) {
            this.visKm = visKm;
        }

        public double getVisMiles() {
            return visMiles;
        }

        public void setVisMiles(double visMiles) {
            this.visMiles = visMiles;
        }

        public double getUv() {
            return uv;
        }

        public void setUv(double uv) {
            this.uv = uv;
        }

        public double getGustMph() {
            return gustMph;
        }

        public void setGustMph(double gustMph) {
            this.gustMph = gustMph;
        }

        public double getGustKph() {
            return gustKph;
        }

        public void setGustKph(double gustKph) {
            this.gustKph = gustKph;
        }
    }

    public static class Condition {
        private String text;
        private String icon;
        private int code;

        // Getters and Setters
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}