package be.thomasmore.party.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Venue {
    @Id
    private int id;
    private String venueName;
    private String linkMoreInfo;
    private int capacity;
    private boolean FoodProvided;
    private boolean Indoor;
    private boolean Outdoor;
    private boolean FreeParkingAvailable;
    private String city;
    private int distanceFromPublicTransportInKm;

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setLinkMoreInfo(String linkMoreInfo) {
        this.linkMoreInfo = linkMoreInfo;
    }

    public String getLinkMoreInfo() {
        return linkMoreInfo;
    }

    public Venue() {
    }

    public Venue(String venueName, String linkMoreInfo) {
        this.venueName = venueName;
        this.linkMoreInfo = linkMoreInfo;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFoodProvided() {
        return FoodProvided;
    }

    public boolean isIndoor() {
        return Indoor;
    }

    public boolean isOutdoor() {
        return Outdoor;
    }

    public boolean isFreeParkingAvailable() {
        return FreeParkingAvailable;
    }

    public String getCity() {
        return city;
    }

    public int getDistanceFromPublicTransportInKm() {
        return distanceFromPublicTransportInKm;
    }

    public Venue(String venueName, String linkMoreInfo, int capacity, boolean isFoodProvided, boolean isIndoor, boolean isOutdoor, boolean isFreeParkingAvailable, String city, int distanceFromPublicTransportInKm) {
        this.venueName = venueName;
        this.linkMoreInfo = linkMoreInfo;
        this.capacity = capacity;
        this.FoodProvided = isFoodProvided;
        this.Indoor = isIndoor;
        this.Outdoor = isOutdoor;
        this.FreeParkingAvailable = isFreeParkingAvailable;
        this.city = city;
        this.distanceFromPublicTransportInKm = distanceFromPublicTransportInKm;
    }
}
