public class Stop {

    private int stopId;
    private String stopName;
    private String location;

    // Constructor
    public Stop(int stopId, String stopName, String location) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.location = location;
    }

    // Getters
    public int getStopId() {
        return stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public String getLocation() {
        return location;
    }

    // Setters
    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Display stop information
    @Override
    public String toString() {
        return "Stop ID: " + stopId +
                ", Stop Name: " + stopName +
                ", Location: " + location;
    }
}
