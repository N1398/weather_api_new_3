package com.example.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkWeatherResponse {
    private List<BulkItem> bulk;

    public List<BulkItem> getBulk() {
        return bulk;
    }

    public void setBulk(List<BulkItem> bulk) {
        this.bulk = bulk;
    }

    public static class BulkItem {
        private Query query;

        public Query getQuery() {
            return query;
        }

        public void setQuery(Query query) {
            this.query = query;
        }
    }

    public static class Query {
        private String custom_id;
        private String q;
        private CurrentWeatherResponse.Location location;
        private CurrentWeatherResponse.Current current;


        public String getCustom_id() { return custom_id; }
        public void setCustom_id(String custom_id) { this.custom_id = custom_id; }

        public String getQ() { return q; }
        public void setQ(String q) { this.q = q; }

        public CurrentWeatherResponse.Location getLocation() { return location; }
        public void setLocation(CurrentWeatherResponse.Location location) { this.location = location; }

        public CurrentWeatherResponse.Current getCurrent() { return current; }
        public void setCurrent(CurrentWeatherResponse.Current current) { this.current = current; }
    }
}
