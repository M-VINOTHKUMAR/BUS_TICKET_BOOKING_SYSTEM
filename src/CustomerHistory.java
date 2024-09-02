import java.sql.Timestamp;

public class CustomerHistory {
    private int historyId;
    private Timestamp date;
    private String action;
    private Timestamp bookingDate;
    private String status;
    private String customerName;
    private int tripId;
    private int seatId;
    private Timestamp departureDatetime;
    private Timestamp arrivalDatetime;
    private String seatNumber;
    private String seatType;
    private String startLocation;
    private String endLocation;
    private String busName;
    private String busType;
    private String busNumber;

    // Getters and Setters
    // Include getters and setters for each field

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public Timestamp getDepartureDatetime() {
        return departureDatetime;
    }

    public void setDepartureDatetime(Timestamp departureDatetime) {
        this.departureDatetime = departureDatetime;
    }

    public Timestamp getArrivalDatetime() {
        return arrivalDatetime;
    }

    public void setArrivalDatetime(Timestamp arrivalDatetime) {
        this.arrivalDatetime = arrivalDatetime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    @Override
    public String toString() {
        return "CustomerHistory{" +
                "historyId=" + historyId +
                ", date=" + date +
                ", action='" + action + '\'' +
                ", bookingDate=" + bookingDate +
                ", status='" + status + '\'' +
                ", customerName='" + customerName + '\'' +
                ", tripId=" + tripId +
                ", seatId=" + seatId +
                ", departureDatetime=" + departureDatetime +
                ", arrivalDatetime=" + arrivalDatetime +
                ", seatNumber='" + seatNumber + '\'' +
                ", seatType='" + seatType + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", busName='" + busName + '\'' +
                ", busType='" + busType + '\'' +
                ", busNumber='" + busNumber + '\'' +
                '}';
    }

}
