import java.util.LinkedList;

public class Route {

    private int routeNumber;
    private String routeName;

    // LinkedList for route stops
    private LinkedList<Stop> stops;

    // LinkedList for transportation history
    private LinkedList<String> travelHistory;

    // Constructor
    public Route(int routeNumber, String routeName) {
        this.routeNumber = routeNumber;
        this.routeName = routeName;

        stops = new LinkedList<>();
        travelHistory = new LinkedList<>();
    }

    // Getters
    public int getRouteNumber() {
        return routeNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public LinkedList<Stop> getStops() {
        return stops;
    }

    public LinkedList<String> getTravelHistory() {
        return travelHistory;
    }

    // Setter
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    // Add a stop to the route
    public void addStop(Stop stop) {
        stops.add(stop);
    }

    // Remove a stop from the route
    public void removeStop(Stop stop) {
        stops.remove(stop);
    }

    // Add transportation history
    public void addTravelHistory(String history) {
        travelHistory.add(history);
    }

    // Display route information
    @Override
    public String toString() {
        return "Route Number: " + routeNumber +
                ", Route Name: " + routeName +
                ", Stops: " + stops.size();
    }
}