# School Bus Tracking and Management System

**Case Study 42 — B.Tech CSE 2025-29 | Java Programming | Semester III**  
**Student Name:** Daksh Ranjan Srivastava  
**Roll Number:** 150096725087

A desktop application built with Java Swing to manage school transportation. Administrators can manage buses, drivers, students, routes, and stops, assign students to buses, search records, sort data, and generate transport reports — all from a single dark-themed GUI.

---

## Problem Statement

A school requires a system to manage buses, drivers, routes, students, and transportation schedules. The system should maintain student transportation records and allow administrators to monitor route assignments efficiently.

---

## Objectives

- Manage buses and drivers
- Register students for transportation
- Manage routes and stops
- Assign students to buses
- Search and sort transportation records
- Maintain route and student history

---

## Java Concepts Used

| Java Concept         | Application in Project                              |
|----------------------|-----------------------------------------------------|
| Classes and Objects  | `Bus`, `Driver`, `Student`, `Route`, `Stop`         |
| Constructors         | Initialization of all domain objects                |
| Array                | Bus seating capacity (`Student[]` inside `Bus`)     |
| ArrayList            | Buses list, Students list, Drivers list             |
| LinkedList           | Route stops, Transportation history                 |
| HashMap              | Bus Number to Bus object lookup                     |
| TreeMap              | Routes sorted by route number (auto-sorted)         |
| CRUD Operations      | Full create/read/update/delete for all entities     |
| Searching            | Search student by ID or name, bus by number, route  |
| Sorting              | Sort buses by capacity, routes by number            |
| Swing GUI            | Full desktop interface with sidebar navigation      |
| Exception Handling   | `TransportationException` for bus capacity exceeded |
| Validation           | Student details, bus capacity, route and bus fields |

---

## Project Structure

```
SchoolBusTrackingSystem/
├── src/
│   ├── Main.java                     # Controller — all data structures and business logic
│   ├── TransportGUI.java             # Swing GUI — 8-panel sidebar navigation
│   ├── Bus.java                      # Bus entity with Student[] seating array
│   ├── Student.java                  # Student entity
│   ├── Driver.java                   # Driver entity
│   ├── Route.java                    # Route entity with LinkedList<Stop>
│   ├── Stop.java                     # Stop entity
│   └── TransportationException.java  # Custom exception
├── Implementation SS/                # Operational implementation screenshots
├── out/                              # Compiled .class files
├── DOCUMENTATION.md                  # Comprehensive project documentation
├── .gitignore
└── README.md
```

---

## Documentation
For complete technical documentation, data structure analysis, test verification matrix, and architecture details, refer to:
- **[DOCUMENTATION.md](DOCUMENTATION.md)**

---

## Modules

1. **Student Management** — Add, update, delete, and view all students
2. **Bus Management** — Manage bus fleet, capacity, and driver assignments
3. **Driver Management** — Register drivers and assign them to buses
4. **Route Management** — Define transport routes with stop counts
5. **Stop Management** — Add stops and assign them to routes
6. **Student Bus Allocation** — Assign students to buses with capacity enforcement
7. **Search and Sort** — Search by ID/name; sort buses by capacity, routes by number
8. **History and Report** — View travel history log and full system report

---

## How to Run

**Prerequisites:** JDK 11 or higher

```bash
# Compile
javac -d out src/*.java

# Run
java -cp out Main
```

The application pre-loads 5 demo records in every module on startup so you can explore all features immediately.

---

## Implementation Screenshots

### 1. Student Management
Displays all registered students with assigned buses. Fill in details to add students; select any row to prefill for updates or deletion.

![Students Panel](Implementation%20SS/01_students_management.png)

---

### 2. Bus Fleet Management
Shows the entire fleet with total capacities, passenger counts (`Passengers / Capacity`), and assigned drivers.

![Buses Panel](Implementation%20SS/02_bus_management.png)

---

### 3. Driver Management
Register drivers with phone numbers and commercial licenses, and assign drivers to buses.

![Drivers Panel](Implementation%20SS/03_driver_management.png)

---

### 4. Route Management
Lists transportation routes stored in a `TreeMap` (auto-sorted by route number) with current stop counts.

![Routes Panel](Implementation%20SS/04_routes_management.png)

---

### 5. Stop Management
Manage pickup/drop-off locations and attach them to transport routes.

![Stops Panel](Implementation%20SS/05_stops_management.png)

---

### 6. Student Bus Allocation
Assign students to buses with automatic seating checks. If the bus is full, atomic protection prevents overbooking.

![Allocation Panel](Implementation%20SS/06_student_bus_allocation.png)

---

### 7. Search Functionality
Search by Student ID, Student Name, Bus Number, or Route Number.

![Search Panel](Implementation%20SS/07_search_student.png)

---

### 8. Sorting Algorithms
Sort buses in ascending capacity order using `ArrayList` and `Comparator`.

![Sort Buses](Implementation%20SS/08_sort_buses.png)

---

### 9. Travel History & Audit Trail
Sequential chronological log of student allocation activities.

![History Panel](Implementation%20SS/09_travel_history.png)

---

### 10. System Transport Report
Generate complete system-wide transportation audit reports.

![Report Panel](Implementation%20SS/10_transport_report.png)

---

### 11. Capacity Constraint Enforcement
Demonstrates custom exception (`TransportationException`) triggered when attempting to overbook a bus.

![Capacity Exception](Implementation%20SS/11_capacity_exception.png)

---

## Key Design Decisions

**Atomic Bus Allocation**
Capacity is checked before removing the student from their previous bus. This prevents a student being left without a bus if the new bus turns out to be full.

**Click-to-Prefill**
Clicking any row in a table automatically fills the form fields above it, eliminating manual re-entry when updating or deleting records.

**HashMap for O(1) Bus Lookup**
Bus searches use a `HashMap<Integer, Bus>` instead of iterating the `ArrayList`, giving constant-time lookup by bus number.

**TreeMap for Route Storage**
Routes are stored in a `TreeMap<Integer, Route>` which keeps them sorted by route number at all times without a separate sort step.

**Custom Exception**
`TransportationException` is thrown when a student allocation attempt exceeds bus capacity, separating transport-domain errors from generic exceptions.

---

## Conclusion

The application demonstrates the practical use of Java data structures (`ArrayList`, `LinkedList`, `HashMap`, `TreeMap`) and GUI programming with Java Swing to manage school transportation and route allocation efficiently. Exception handling and validation ensure data integrity throughout all operations.

---

## Project Information

- **Student Name:** Daksh Ranjan Srivastava
- **Roll Number:** 150096725087
- **Program:** B.Tech Computer Science and Engineering (2025-29)
- **Course:** Java Programming | Semester III
- **Case Study:** Case Study 42 — School Bus Tracking & Management System
