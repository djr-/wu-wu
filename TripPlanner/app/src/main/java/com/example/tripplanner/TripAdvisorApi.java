package com.example.tripplanner;

import java.util.List;

public final class TripAdvisorApi {
    public static final String API_URL = "http://api.tripadvisor.com/api/partner/2.0/";

    public static class Geo {
        public final String location_string;
        public int location_id;

        public Geo(String location_string, int location_id) {
            this.location_string = location_string;
            this.location_id = location_id;
        }
    }


    public static class SearchResults {
        public final List<Geo> geos;

        public SearchResults(List<Geo> geos) {
            this.geos = geos;
        }
    }


    public static class Data {
        public final String name;
        public final int location_id;
        public final double latitude;
        public final double longitude;
        public final double rating;
        public final boolean selected;
        public String toString(){
            return this.name;
        }


        public Data(String location_string, int location_id, double latitude, double longitude, double rating) {
            this.name = location_string;
            this.location_id = location_id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.rating = rating;
            this.selected = false;

        }
    }
    public static class SearchAttractionResults {
        public final List<Data> data;

        public SearchAttractionResults(List<Data> data) {this.data = data;}
    }
}
