public class Driver {

    private int driverId;
    private String driverName;
    private String phoneNumber;
    private String licenseNumber;

    // Constructor
    public Driver(int driverId, String driverName, String phoneNumber, String licenseNumber) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
    }

    // Getters
    public int getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    // Setters
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    // Display driver information
    @Override
    public String toString() {
        return "Driver ID: " + driverId +
                ", Name: " + driverName +
                ", Phone: " + phoneNumber +
                ", License: " + licenseNumber;
    }
}
