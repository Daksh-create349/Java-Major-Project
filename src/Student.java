public class Student {

    private int studentId;
    private String studentName;
    private int age;
    private String className;
    private String address;
    private int busNumber;

    // Constructor
    public Student(int studentId, String studentName, int age,
                   String className, String address) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.age = age;
        this.className = className;
        this.address = address;

        // -1 means no bus has been assigned
        this.busNumber = -1;
    }

    // Getters
    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getAge() {
        return age;
    }

    public String getClassName() {
        return className;
    }

    public String getAddress() {
        return address;
    }

    public int getBusNumber() {
        return busNumber;
    }

    // Setters
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    // Display student information
    @Override
    public String toString() {

        String busInfo;

        if (busNumber == -1) {
            busInfo = "Not Allocated";
        } else {
            busInfo = String.valueOf(busNumber);
        }

        return "Student ID: " + studentId +
                ", Name: " + studentName +
                ", Age: " + age +
                ", Class: " + className +
                ", Address: " + address +
                ", Bus: " + busInfo;
    }
}
