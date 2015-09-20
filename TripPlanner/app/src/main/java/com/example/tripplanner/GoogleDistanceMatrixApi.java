package com.example.tripplanner;

import java.util.List;

public class GoogleDistanceMatrixApi {
    public static final String API_URL = "https://maps.googleapis.com/maps/api/distancematrix/";

    public static class Distance {
        public final String text;
        public final int value;

        public Distance(String text, int value) {
            this.text = text;
            this.value = value;
        }
    }

    public static class Duration {
        public final String text;
        public final int value;

        public Duration(String text, int value) {
            this.text = text;
            this.value = value;
        }
    }

    public static class Element
    {
        public final Distance distance;
        public final Duration duration;
        public final String status;

        public Element(Distance distance, Duration duration, String status)
        {
            this.distance = distance;
            this.duration = duration;
            this.status = status;
        }
    }

    public static class Row {
        public final List<Element> elements;

        public Row(List<Element> elements)
        {
            this.elements = elements;
        }
    }

    public static class Result {
        public final List<Row> rows;
        public final String status;
        public final List<String> origin_addresses;
        public final List<String> destination_addresses;

        public Result(List<Row> rows, String status, List<String> origin_addresses, List<String> destination_addresses) {
            this.rows = rows;
            this.status = status;
            this.origin_addresses = origin_addresses;
            this.destination_addresses = destination_addresses;
        }
    }
}

