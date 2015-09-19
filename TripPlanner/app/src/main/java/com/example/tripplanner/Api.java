package com.example.tripplanner;

import java.util.List;

public final class Api {
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
}
