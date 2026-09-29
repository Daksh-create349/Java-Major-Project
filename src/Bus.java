public class Bus {

    private int busNumber;
    private String busName;
    private int capacity;
    private Driver driver;

    // Array to store students according to bus seating capacity
    private Student[] students;
    private int studentCount;

    // Constructor
    public Bus(int busNumber, String busName, int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Bus capacity must be greater than 0."
            );
        }

        this.busNumber = busNumber;
        this.busName = busName;
        this.capacity = capacity;

        // Array size is equal to bus seating capacity
        this.students = new Student[capacity];
        this.studentCount = 0;
    }

    // Getters
    public int getBusNumber() {
        return busNumber;
    }

    public String getBusName() {
        return busName;
    }

    public int getCapacity() {
        return capacity;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getStudentCount() {
        return studentCount;
    }

    // Setters
    public void setBusName(String busName) {
        this.busName = busName;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    // Add student to bus
    public void addStudent(Student student)
            throws TransportationException {

        // Check whether bus is full
        if (studentCount >= capacity) {
            throw new TransportationException(
                    "Bus capacity exceeded for Bus " + busNumber
            );
        }

        // Add student to array
        students[studentCount] = student;
        studentCount++;

        // Assign bus number to student
        student.setBusNumber(busNumber);
    }

    // Remove student from bus
    public void removeStudent(Student student) {

        for (int i = 0; i < studentCount; i++) {

            if (students[i] == student) {

                // Shift remaining students to the left
                for (int j = i; j < studentCount - 1; j++) {
                    students[j] = students[j + 1];
                }

                // Remove last duplicate reference
                students[studentCount - 1] = null;

                studentCount--;

                // Remove bus allocation from student
                student.setBusNumber(-1);

                break;
            }
        }
    }

    // Return students currently assigned to this bus
    public Student[] getStudents() {

        Student[] currentStudents = new Student[studentCount];

        for (int i = 0; i < studentCount; i++) {
            currentStudents[i] = students[i];
        }

        return currentStudents;
    }

    // Display bus information
    @Override
    public String toString() {

        String driverName;

        if (driver == null) {
            driverName = "Not Assigned";
        } else {
            driverName = driver.getDriverName();
        }

        return "Bus Number: " + busNumber +
                ", Bus Name: " + busName +
                ", Capacity: " + capacity +
                ", Students: " + studentCount +
                ", Driver: " + driverName;
    }
}
