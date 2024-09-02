import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Customer_history {
    static int customerId;

    // Method to read customer history from the database
    public static void getCustomerHistory() throws SQLException {
        input();
        String query = STR."SELECT ch.history_id, ch.date, ch.action, bd.booking_date, bd.status_, c.name AS customer_name, bd.trip_id, bd.seat_id, t.departure_datetime, t.arrival_datetime, s.seat_number, s.seat_type, br.start_location, br.end_location, bd2.bus_name, bd2.bus_type, bd2.bus_number FROM customer_history ch JOIN booking_details bd ON ch.booking_id = bd.booking_id JOIN customer_details c ON ch.customer_id = c.customer_id JOIN trips t ON bd.trip_id = t.trip_id JOIN seats s ON bd.seat_id = s.seat_id JOIN bus_routes br ON t.route_id = br.route_id JOIN buses_details bd2 ON t.bus_id = bd2.bus_id WHERE ch.customer_id = \{customerId} ORDER BY ch.date DESC";

        ResultSet rs = data_base_con.read(query);

        // Process each record one by one and display in square pattern
        while (rs.next()) {
            printRecordInSquarePattern(rs);
        }
    }

    // Method to print a record in a square pattern
    private static void printRecordInSquarePattern(ResultSet rs) throws SQLException {
            // Define maximum lengths for consistent box size
            int maxFieldLength = 20;
            System.out.println("┌───────────────────────────────────────────────────────────────────────────────┐");
            System.out.printf("│ History ID      : %-20s Date            : %-20s │%n", fitString(rs.getString("history_id"), maxFieldLength), fitString(rs.getTimestamp("date").toString(), maxFieldLength));
            System.out.printf("│ Action          : %-20s Booking Date    : %-20s │%n", fitString(rs.getString("action"), maxFieldLength), fitString(rs.getTimestamp("booking_date").toString(), maxFieldLength));
            System.out.printf("│ Status          : %-20s Customer Name   : %-20s │%n", fitString(rs.getString("status_"), maxFieldLength), fitString(rs.getString("customer_name"), maxFieldLength));
            System.out.printf("│ Trip ID         : %-20s Seat ID         : %-20s │%n", fitString(rs.getString("trip_id"), maxFieldLength), fitString(rs.getString("seat_id"), maxFieldLength));
            System.out.printf("│ Departure Time  : %-20s Arrival Time    : %-20s │%n", fitString(rs.getTimestamp("departure_datetime").toString(), maxFieldLength), fitString(rs.getTimestamp("arrival_datetime").toString(), maxFieldLength));
            System.out.printf("│ Seat Number     : %-20s Seat Type       : %-20s │%n", fitString(rs.getString("seat_number"), maxFieldLength), fitString(rs.getString("seat_type"), maxFieldLength));
            System.out.printf("│ Start Location  : %-20s End Location    : %-20s │%n", fitString(rs.getString("start_location"), maxFieldLength), fitString(rs.getString("end_location"), maxFieldLength));
            System.out.printf("│ Bus Name        : %-20s Bus Type        : %-20s │%n", fitString(rs.getString("bus_name"), maxFieldLength), fitString(rs.getString("bus_type"), maxFieldLength));
            System.out.printf("│ Bus Number      : %-20s                                        │%n", fitString(rs.getString("bus_number"), maxFieldLength));
            System.out.println("└───────────────────────────────────────────────────────────────────────────────┘");
            System.out.println(); // Adds space between records
        }

    private static String fitString(String value, int length) {
        if (value.length() > length) {
            return value.substring(0, length - 3) + "..."; // Truncate and add ellipsis if too long
        } else {
            return String.format("%-" + length + "s", value); // Pad with spaces if too short
        }
    }

    // Method to take customer ID input from the user
    public static void input() {
        Scanner in = new Scanner(System.in);
        System.out.println("ENTER THE CUSTOMER ID: ");
        customerId = in.nextInt();
    }
}
