package edu.gatech.seclass.jobcompare6300;

public class Location {
    private String city;
    private String state;
    private int costOfLiving;

    public Location(String city, String state, int costOfLiving) {
        this.city = city;
        this.state = state;
        this.costOfLiving = costOfLiving;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getCostOfLiving() {
        return costOfLiving;
    }

    public void setCostOfLiving(int costOfLiving) {
        this.costOfLiving = costOfLiving;
    }

    @Override
    public String toString() {
        return city + ", " + state;
    }
}