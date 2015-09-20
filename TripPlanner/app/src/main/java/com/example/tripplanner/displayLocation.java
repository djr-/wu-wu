package com.example.tripplanner;

/**
 * Created by Rado on 9/19/2015.
 */
public class displayLocation {
        private final Integer locID;
        private final String name;
        private final Boolean selected;

            public displayLocation(Integer aLocID, String aName, Boolean aSelected)
            {
                locID   = aLocID;
                name = aName;
                selected = aSelected;
            }

        public Integer locID()      { return locID; }
        public String  name()       { return name; }
        public Boolean selected()   { return selected;}

}
