import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    private static ArrayList<Bus>     buses         = new ArrayList<>();
    private static ArrayList<Student> students      = new ArrayList<>();
    private static ArrayList<Driver>  drivers       = new ArrayList<>();
    private static LinkedList<Stop>   stops         = new LinkedList<>();
    private static LinkedList<String> travelHistory = new LinkedList<>();
    private static HashMap<Integer, Bus>   busMap   = new HashMap<>();
    private static TreeMap<Integer, Route> routeMap = new TreeMap<>();



    public static boolean addBus(Bus bus) {
        if (bus == null || busMap.containsKey(bus.getBusNumber())) return false;
        buses.add(bus);
        busMap.put(bus.getBusNumber(), bus);
        return true;
    }

    public static ArrayList<Bus> getBuses() { return buses; }

    public static Bus searchBus(int busNumber) { return busMap.get(busNumber); }

    public static boolean updateBus(int busNumber, String busName) {
        Bus bus = busMap.get(busNumber);
        if (bus == null || busName == null || busName.trim().isEmpty()) return false;
        bus.setBusName(busName);
        return true;
    }

    public static boolean deleteBus(int busNumber) {
        Bus bus = busMap.get(busNumber);
        if (bus == null) return false;
        // Remove bus allocation from its students
        for (Student s : bus.getStudents()) s.setBusNumber(-1);
        buses.remove(bus);
        busMap.remove(busNumber);
        return true;
    }

    // Sort buses by capacity (using ArrayList + Comparator)
    public static ArrayList<Bus> sortBusesByCapacity() {
        ArrayList<Bus> sorted = new ArrayList<>(buses);
        sorted.sort(Comparator.comparingInt(Bus::getCapacity));
        return sorted;
    }



    public static boolean addDriver(Driver driver) {
        if (driver == null) return false;
        // Validate driver name is not null or empty
        if (driver.getDriverName() == null || driver.getDriverName().trim().isEmpty()) return false;
        if (searchDriver(driver.getDriverId()) != null) return false;
        drivers.add(driver);
        return true;
    }

    public static ArrayList<Driver> getDrivers() { return drivers; }

    public static Driver searchDriver(int driverId) {
        for (Driver d : drivers)
            if (d.getDriverId() == driverId) return d;
        return null;
    }

    public static boolean updateDriver(int driverId, String name, String phone, String license) {
        Driver d = searchDriver(driverId);
        if (d == null) return false;
        d.setDriverName(name);
        d.setPhoneNumber(phone);
        d.setLicenseNumber(license);
        return true;
    }

    public static boolean deleteDriver(int driverId) {
        Driver driver = searchDriver(driverId);
        if (driver == null) return false;
        // Unassign driver from any bus that holds a reference to them
        for (Bus bus : buses)
            if (bus.getDriver() != null && bus.getDriver().getDriverId() == driverId)
                bus.setDriver(null);
        drivers.remove(driver);
        return true;
    }

    public static boolean assignDriverToBus(int busNumber, int driverId) {
        Bus bus = searchBus(busNumber);
        Driver driver = searchDriver(driverId);
        if (bus == null || driver == null) return false;
        bus.setDriver(driver);
        return true;
    }



    public static boolean addStudent(Student student) {
        if (student == null || searchStudent(student.getStudentId()) != null) return false;
        students.add(student);
        return true;
    }

    public static ArrayList<Student> getStudents() { return students; }

    public static Student searchStudent(int studentId) {
        for (Student s : students)
            if (s.getStudentId() == studentId) return s;
        return null;
    }

    public static Student searchStudentByName(String studentName) {
        if (studentName == null) return null;
        for (Student s : students)
            if (s.getStudentName().equalsIgnoreCase(studentName)) return s;
        return null;
    }

    public static boolean updateStudent(int studentId, String name, int age, String className, String address) {
        Student student = searchStudent(studentId);
        if (student == null || !validateStudent(name, age, className, address)) return false;
        student.setStudentName(name);
        student.setAge(age);
        student.setClassName(className);
        student.setAddress(address);
        return true;
    }

    public static boolean deleteStudent(int studentId) {
        Student student = searchStudent(studentId);
        if (student == null) return false;
        // Remove student from assigned bus
        if (student.getBusNumber() != -1) {
            Bus bus = searchBus(student.getBusNumber());
            if (bus != null) bus.removeStudent(student);
        }
        students.remove(student);
        return true;
    }



    public static boolean addRoute(Route route) {
        if (route == null || routeMap.containsKey(route.getRouteNumber())) return false;
        routeMap.put(route.getRouteNumber(), route);
        return true;
    }

    public static TreeMap<Integer, Route> getRoutes() { return routeMap; }

    public static Route searchRoute(int routeNumber) { return routeMap.get(routeNumber); }

    public static boolean updateRoute(int routeNumber, String routeName) {
        Route route = routeMap.get(routeNumber);
        if (route == null || routeName == null || routeName.trim().isEmpty()) return false;
        route.setRouteName(routeName);
        return true;
    }

    public static boolean deleteRoute(int routeNumber) {
        if (!routeMap.containsKey(routeNumber)) return false;
        routeMap.remove(routeNumber);
        return true;
    }

    public static ArrayList<Route> sortRoutesByNumber() {
        ArrayList<Route> sorted = new ArrayList<>(routeMap.values());
        sorted.sort(Comparator.comparingInt(Route::getRouteNumber));
        return sorted;
    }



    public static boolean addStop(Stop stop) {
        if (stop == null || searchStop(stop.getStopId()) != null) return false;
        stops.add(stop);
        return true;
    }

    public static LinkedList<Stop> getStops() { return stops; }

    public static Stop searchStop(int stopId) {
        for (Stop s : stops)
            if (s.getStopId() == stopId) return s;
        return null;
    }

    public static boolean deleteStop(int stopId) {
        Stop stop = searchStop(stopId);
        if (stop == null) return false;
        stops.remove(stop);
        return true;
    }

    public static boolean addStopToRoute(int routeNumber, int stopId) {
        Route route = searchRoute(routeNumber);
        Stop stop   = searchStop(stopId);
        if (route == null || stop == null) return false;
        route.addStop(stop);
        return true;
    }

    public static boolean removeStopFromRoute(int routeNumber, int stopId) {
        Route route = searchRoute(routeNumber);
        Stop stop   = searchStop(stopId);
        if (route == null || stop == null) return false;
        route.removeStop(stop);
        return true;
    }



    public static boolean allocateStudentToBus(int studentId, int busNumber)
            throws TransportationException {

        Student student = searchStudent(studentId);
        Bus bus = searchBus(busNumber);
        if (student == null || bus == null) return false;

        // FIX: Check capacity BEFORE removing from old bus.
        // This prevents leaving the student with no bus if the new bus is full.
        if (bus.getStudentCount() >= bus.getCapacity())
            throw new TransportationException("Bus capacity exceeded for Bus " + busNumber);

        // Safe to remove from previous bus now that we know new bus has room
        if (student.getBusNumber() != -1) {
            Bus prev = searchBus(student.getBusNumber());
            if (prev != null) prev.removeStudent(student);
        }

        // Bus adds student (will not throw since we pre-checked capacity)
        bus.addStudent(student);
        addTravelHistory("Student " + student.getStudentName() + " allocated to Bus " + busNumber);
        return true;
    }



    public static void addTravelHistory(String history) {
        if (history == null || history.trim().isEmpty()) return;
        travelHistory.add(history);
    }

    public static LinkedList<String> getTravelHistory() { return travelHistory; }



    public static boolean validateStudent(String name, int age, String className, String address) {
        return name     != null && !name.trim().isEmpty()
            && age      > 0
            && className != null && !className.trim().isEmpty()
            && address  != null && !address.trim().isEmpty();
    }

    public static boolean validateBus(int busNumber, String busName, int capacity) {
        return busNumber > 0
            && busName   != null && !busName.trim().isEmpty()
            && capacity  > 0;
    }

    public static boolean validateRoute(int routeNumber, String routeName) {
        return routeNumber > 0
            && routeName  != null && !routeName.trim().isEmpty();
    }



    public static String generateTransportReport() {
        StringBuilder report = new StringBuilder();
        report.append("SCHOOL TRANSPORT REPORT\n=======================\n\n");
        report.append("Total Buses: ")   .append(buses.size())      .append("\n");
        report.append("Total Students: ").append(students.size())   .append("\n");
        report.append("Total Drivers: ") .append(drivers.size())    .append("\n");
        report.append("Total Routes: ")  .append(routeMap.size())   .append("\n");
        report.append("Total Stops: ")   .append(stops.size())      .append("\n");


        report.append("\nBUS DETAILS\n-----------\n");
        for (Bus bus : buses) report.append(bus).append("\n");


        report.append("\nROUTE DETAILS\n-------------\n");
        for (Map.Entry<Integer, Route> entry : routeMap.entrySet())
            report.append(entry.getValue()).append("\n");

        return report.toString();
    }



    private static void loadDemoData() {

        // 5 Buses (ArrayList + HashMap)
        addBus(new Bus(1, "Blue Express",   40));
        addBus(new Bus(2, "Red Rocket",     35));
        addBus(new Bus(3, "Green Shuttle",  30));
        addBus(new Bus(4, "Yellow Star",    45));
        addBus(new Bus(5, "Orange Cruiser", 25));

        // 5 Drivers (ArrayList)
        addDriver(new Driver(101, "Ramesh Kumar",  "9876543210", "DL-101-MH"));
        addDriver(new Driver(102, "Suresh Yadav",  "9123456780", "DL-102-DL"));
        addDriver(new Driver(103, "Mahesh Singh",  "9988776655", "DL-103-UP"));
        addDriver(new Driver(104, "Dinesh Sharma", "9001122334", "DL-104-RJ"));
        addDriver(new Driver(105, "Ganesh Patel",  "9876001234", "DL-105-GJ"));

        // Assign each driver to a bus (HashMap lookup)
        assignDriverToBus(1, 101); assignDriverToBus(2, 102); assignDriverToBus(3, 103);
        assignDriverToBus(4, 104); assignDriverToBus(5, 105);

        // 5 Routes (TreeMap)
        addRoute(new Route(1, "North Route")); addRoute(new Route(2, "South Route"));
        addRoute(new Route(3, "East Route"));  addRoute(new Route(4, "West Route"));
        addRoute(new Route(5, "Central Route"));

        // 5 Stops (LinkedList)
        addStop(new Stop(301, "City Center",     "MG Road"));
        addStop(new Stop(302, "Railway Station", "Station Road"));
        addStop(new Stop(303, "Market Square",   "Nehru Place"));
        addStop(new Stop(304, "Park Colony",     "Sector 14"));
        addStop(new Stop(305, "Mall Junction",   "Ring Road"));

        // Assign each stop to a route (LinkedList inside Route)
        addStopToRoute(1, 301); addStopToRoute(2, 302); addStopToRoute(3, 303);
        addStopToRoute(4, 304); addStopToRoute(5, 305);

        // 5 Students (ArrayList) allocated to buses
        addStudent(new Student(1001, "Aarav Sharma", 14, "9-A",  "12, Lal Bagh"));
        addStudent(new Student(1002, "Diya Patel",   13, "8-B",  "45, Rose Garden"));
        addStudent(new Student(1003, "Rohan Verma",  15, "10-A", "78, Sector 5"));
        addStudent(new Student(1004, "Sneha Reddy",  12, "7-C",  "23, Green Park"));
        addStudent(new Student(1005, "Arjun Nair",   16, "11-B", "90, Hill View"));

        try {
            allocateStudentToBus(1001, 1); allocateStudentToBus(1002, 2);
            allocateStudentToBus(1003, 3); allocateStudentToBus(1004, 4);
            allocateStudentToBus(1005, 5);
        } catch (TransportationException e) {
            // Demo buses have high capacity; this should never throw
        }
    }



    public static void main(String[] args) {
        loadDemoData();
        SwingUtilities.invokeLater(() -> {
            TransportGUI gui = new TransportGUI();
            gui.setVisible(true);
        });
    }
}
