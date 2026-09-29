public class TestAll {
    static int pass = 0, fail = 0;

    static void check(String name, boolean result) {
        System.out.println("  " + (result ? "PASS" : "FAIL") + "  " + name);
        if (result) pass++; else fail++;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("\n=== SCHOOL BUS SYSTEM — FULL FUNCTION TEST ===\n");

        // ── BUS ─────────────────────────────────────────────
        System.out.println("[BUS]");
        Bus b1 = new Bus(1, "Blue Express", 3);
        Bus b2 = new Bus(2, "Red Rocket",   2);
        check("addBus(valid)",         Main.addBus(b1));
        check("addBus(duplicate)",    !Main.addBus(b1));
        check("addBus(null)",         !Main.addBus(null));
        check("addBus(second)",        Main.addBus(b2));
        check("getBuses() size==2",    Main.getBuses().size() == 2);
        check("searchBus(found)",      Main.searchBus(1) != null);
        check("searchBus(missing)",    Main.searchBus(99) == null);
        check("updateBus(valid)",      Main.updateBus(1, "Updated Express"));
        check("updateBus(missing)",   !Main.updateBus(99, "X"));
        check("updateBus(empty name)",!Main.updateBus(1, ""));
        java.util.ArrayList<Bus> sortedB = Main.sortBusesByCapacity();
        check("sortBusesByCapacity()", sortedB.get(0).getCapacity() <= sortedB.get(1).getCapacity());
        check("validateBus(valid)",        Main.validateBus(1, "Bus", 30));
        check("validateBus(num<=0)",      !Main.validateBus(0, "Bus", 30));
        check("validateBus(empty name)",  !Main.validateBus(1, "", 30));
        check("validateBus(capacity<=0)", !Main.validateBus(1, "Bus", 0));

        // ── DRIVER ──────────────────────────────────────────
        System.out.println("\n[DRIVER]");
        Driver d1 = new Driver(101, "Ramesh", "9876543210", "DL-101");
        Driver d2 = new Driver(102, "Suresh", "9123456780", "DL-102");
        check("addDriver(valid)",        Main.addDriver(d1));
        check("addDriver(duplicate)",   !Main.addDriver(d1));
        check("addDriver(null)",        !Main.addDriver(null));
        check("addDriver(empty name)",  !Main.addDriver(new Driver(103, "", "0", "X")));
        check("addDriver(second)",       Main.addDriver(d2));
        check("getDrivers() size==2",    Main.getDrivers().size() == 2);
        check("searchDriver(found)",     Main.searchDriver(101) != null);
        check("searchDriver(missing)",   Main.searchDriver(999) == null);
        check("updateDriver(valid)",     Main.updateDriver(101, "Ramesh Kumar", "9999999999", "DL-NEW"));
        check("updateDriver(missing)",  !Main.updateDriver(999, "X", "0", "0"));
        check("assignDriverToBus(valid)",   Main.assignDriverToBus(1, 101));
        check("assignDriverToBus(bad bus)", !Main.assignDriverToBus(99, 101));
        check("bus has driver",          Main.searchBus(1).getDriver() != null);
        Main.deleteDriver(101);
        check("deleteDriver unassigns bus",  Main.searchBus(1).getDriver() == null);
        check("deleteDriver removed",        Main.searchDriver(101) == null);

        // ── STUDENT ─────────────────────────────────────────
        System.out.println("\n[STUDENT]");
        Student s1 = new Student(1001, "Aarav", 14, "9-A",  "Lal Bagh");
        Student s2 = new Student(1002, "Diya",  13, "8-B",  "Rose Garden");
        Student s3 = new Student(1003, "Rohan", 15, "10-A", "Sector 5");
        check("addStudent(valid)",       Main.addStudent(s1));
        check("addStudent(duplicate)",  !Main.addStudent(s1));
        check("addStudent(null)",       !Main.addStudent(null));
        check("addStudent(s2,s3)",       Main.addStudent(s2) && Main.addStudent(s3));
        check("getStudents() size>=3",   Main.getStudents().size() >= 3);
        check("searchStudent(found)",    Main.searchStudent(1001) != null);
        check("searchStudent(missing)",  Main.searchStudent(9999) == null);
        check("searchStudentByName(found)",   Main.searchStudentByName("Aarav") != null);
        check("searchStudentByName(case)",    Main.searchStudentByName("aarav") != null);
        check("searchStudentByName(missing)", Main.searchStudentByName("Nobody") == null);
        check("searchStudentByName(null)",    Main.searchStudentByName(null) == null);
        check("updateStudent(valid)",    Main.updateStudent(1001, "Aarav Sharma", 15, "10-A", "MG Road"));
        check("updateStudent(missing)", !Main.updateStudent(9999, "X", 10, "1", "Y"));
        check("validateStudent(valid)",       Main.validateStudent("X", 5, "A", "B"));
        check("validateStudent(age<=0)",     !Main.validateStudent("X", 0, "A", "B"));
        check("validateStudent(empty name)", !Main.validateStudent("", 5, "A", "B"));

        // ── ROUTE ────────────────────────────────────────────
        System.out.println("\n[ROUTE]");
        Route r1 = new Route(10, "North Route");
        Route r2 = new Route(5,  "South Route");
        check("addRoute(valid)",       Main.addRoute(r1));
        check("addRoute(duplicate)",  !Main.addRoute(r1));
        check("addRoute(null)",       !Main.addRoute(null));
        check("addRoute(r2)",          Main.addRoute(r2));
        check("getRoutes() size==2",   Main.getRoutes().size() == 2);
        check("searchRoute(found)",    Main.searchRoute(10) != null);
        check("searchRoute(missing)",  Main.searchRoute(99) == null);
        check("updateRoute(valid)",    Main.updateRoute(10, "Updated North"));
        check("updateRoute(missing)", !Main.updateRoute(99, "X"));
        check("updateRoute(empty)",   !Main.updateRoute(10, ""));
        java.util.ArrayList<Route> sortedR = Main.sortRoutesByNumber();
        check("sortRoutesByNumber()",  sortedR.get(0).getRouteNumber() < sortedR.get(1).getRouteNumber());
        check("validateRoute(valid)",  Main.validateRoute(1, "R"));
        check("validateRoute(num<=0)", !Main.validateRoute(0, "R"));
        check("validateRoute(empty)",  !Main.validateRoute(1, ""));
        check("deleteRoute(valid)",    Main.deleteRoute(10));
        check("deleteRoute(missing)", !Main.deleteRoute(99));

        // ── STOP ─────────────────────────────────────────────
        System.out.println("\n[STOP]");
        Stop st1 = new Stop(301, "City Center",     "MG Road");
        Stop st2 = new Stop(302, "Railway Station", "Station Rd");
        check("addStop(valid)",       Main.addStop(st1));
        check("addStop(duplicate)",  !Main.addStop(st1));
        check("addStop(null)",       !Main.addStop(null));
        check("addStop(st2)",         Main.addStop(st2));
        check("getStops() size==2",   Main.getStops().size() == 2);
        check("searchStop(found)",    Main.searchStop(301) != null);
        check("searchStop(missing)",  Main.searchStop(999) == null);
        // route 5 still exists
        check("addStopToRoute(valid)",      Main.addStopToRoute(5, 301));
        check("addStopToRoute(bad route)",  !Main.addStopToRoute(99, 301));
        check("addStopToRoute(bad stop)",   !Main.addStopToRoute(5, 999));
        check("removeStopFromRoute(valid)", Main.removeStopFromRoute(5, 301));
        check("removeStopFromRoute(bad)",   !Main.removeStopFromRoute(99, 301));
        check("deleteStop(valid)",    Main.deleteStop(302));
        check("deleteStop(missing)", !Main.deleteStop(999));

        // ── ALLOCATION ───────────────────────────────────────
        System.out.println("\n[ALLOCATION]");
        // Bus1 capacity=3
        check("allocate s1->bus1", Main.allocateStudentToBus(1001, 1));
        check("allocate s2->bus1", Main.allocateStudentToBus(1002, 1));
        check("allocate s3->bus1", Main.allocateStudentToBus(1003, 1));
        check("s1.busNumber==1",   Main.searchStudent(1001).getBusNumber() == 1);
        check("bus1 count==3",     Main.searchBus(1).getStudentCount() == 3);
        // Bus2 capacity=2 — fill it
        Student s4 = new Student(1004, "Sneha", 12, "7-C", "Green Park");
        Student s5 = new Student(1005, "Arjun", 16, "11-B","Hill View");
        Main.addStudent(s4); Main.addStudent(s5);
        Main.allocateStudentToBus(1004, 2);
        Main.allocateStudentToBus(1005, 2);
        // Try to add one more — should throw
        Student s6 = new Student(1006, "Extra", 10, "6-A", "Somewhere");
        Main.addStudent(s6);
        boolean threw = false;
        try   { Main.allocateStudentToBus(1006, 2); }
        catch (TransportationException e) { threw = true; }
        check("allocation throws on full bus",       threw);
        check("s4 still on bus2 (atomic safety)",    Main.searchStudent(1004).getBusNumber() == 2);
        check("s6 not assigned (was unallocated)",   Main.searchStudent(1006).getBusNumber() == -1);
        check("allocate missing student",            !Main.allocateStudentToBus(9999, 1));
        check("allocate missing bus",                !Main.allocateStudentToBus(1001, 99));
        // Re-allocate s1 from bus1 to bus2 (after removing s4 to free a seat)
        Main.deleteStudent(1004);
        check("re-allocate s1 to bus2 (move bus)", Main.allocateStudentToBus(1001, 2));
        check("s1 now on bus2",  Main.searchStudent(1001).getBusNumber() == 2);
        check("bus1 count==2",   Main.searchBus(1).getStudentCount() == 2);

        // ── DELETE WITH SIDE EFFECTS ─────────────────────────
        System.out.println("\n[DELETE SIDE EFFECTS]");
        int before = Main.searchBus(1).getStudentCount();
        Main.deleteStudent(1002);
        check("deleteStudent removes from bus",  Main.searchBus(1).getStudentCount() == before - 1);
        check("deleteStudent(missing)",         !Main.deleteStudent(9999));
        Main.deleteBus(1);
        check("deleteBus removes bus",   Main.searchBus(1) == null);
        check("deleteBus(missing)",     !Main.deleteBus(99));

        // ── HISTORY & REPORT ─────────────────────────────────
        System.out.println("\n[HISTORY & REPORT]");
        int hBefore = Main.getTravelHistory().size();
        Main.addTravelHistory("Test entry");
        check("addTravelHistory added",        Main.getTravelHistory().size() == hBefore + 1);
        Main.addTravelHistory(null);
        Main.addTravelHistory("   ");
        check("rejects null/blank history",    Main.getTravelHistory().size() == hBefore + 1);
        String report = Main.generateTransportReport();
        check("report not null",               report != null);
        check("report has header",             report.contains("SCHOOL TRANSPORT REPORT"));
        check("report has Total Buses",        report.contains("Total Buses:"));
        check("report has Total Students",     report.contains("Total Students:"));
        check("report has Total Drivers",      report.contains("Total Drivers:"));
        check("report has Total Routes",       report.contains("Total Routes:"));
        check("report has Total Stops",        report.contains("Total Stops:"));

        // ── SUMMARY ──────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  TOTAL  " + (pass+fail) + "  |  PASS  " + pass + "  |  FAIL  " + fail);
        System.out.println("══════════════════════════════════════════\n");
        if (fail > 0) System.exit(1);
    }
}
