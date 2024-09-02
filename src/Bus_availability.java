import java.sql.*;
import java.util.Scanner;

public class Bus_availability {
    static String startLocation;
    static String endLocation;
    static String time;
    static int f = -1;
    static boolean res = false;

    public static boolean bus_availability() throws SQLException {
        input();
        available();
        return res;
    }

    private static void available() throws SQLException {
        // Improved query with formatting placeholders
        String query = "SELECT DISTINCT bd.bus_id, bd.bus_number, bd.bus_name, bd.bus_type, bd.capacity, t.trip_id, " +
                "t.departure_datetime AS trip_departure, t.arrival_datetime AS trip_arrival, bt.arrival_time AS stop_arrival_time, " +
                "bt.departure_time AS stop_departure_time, tp.price AS ticket_price " +
                "FROM buses_details bd " +
                "JOIN trips t ON bd.bus_id = t.bus_id " +
                "JOIN bus_routes br ON t.route_id = br.route_id " +
                "JOIN bus_timing bt ON bt.bus_id = bd.bus_id " +
                "JOIN ticket_price tp ON br.route_id = tp.route_id " +
                "WHERE br.start_location = ? AND br.end_location = ? AND bt.departure_time > ? AND tp.seat_type = 'standard' " +
                "ORDER BY bd.bus_id, t.trip_id";

        try (Connection conn = data_base_con.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, startLocation);
            stmt.setString(2, endLocation);
            stmt.setString(3, time);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\n*--- NO BUSES AVAILABLE ---*");
                return;
            }

            // Reset cursor to the start
           // rs.beforeFirst();

            while (rs.next()) {
                if (f != rs.getInt("bus_id")) {
                    printBusDetails(rs);
                    f = rs.getInt("bus_id");
                }
                res = true;
            }
        }
    }

    private static void printBusDetails(ResultSet rs) throws SQLException {
        System.out.println("\n*--- Bus Details ---*");
        System.out.println("Bus ID: " + rs.getInt("bus_id"));
        System.out.println("Bus Name: " + rs.getString("bus_name"));
        System.out.println("Bus Number: " + rs.getString("bus_number"));
        System.out.println("Bus Type: " + rs.getString("bus_type"));
        System.out.println("Capacity: " + rs.getInt("capacity"));
        System.out.println("Trip ID: " + rs.getInt("trip_id"));
        System.out.println("Departure: " + rs.getTimestamp("trip_departure"));
        System.out.println("Arrival: " + rs.getTimestamp("trip_arrival"));
        System.out.println("Stop Arrival Time: " + rs.getTime("stop_arrival_time"));
        System.out.println("Stop Departure Time: " + rs.getTime("stop_departure_time"));
        System.out.println("Ticket Price: " + rs.getBigDecimal("ticket_price"));
        System.out.println("-----------------------------");
    }

    private static void input() {
        Scanner in = new Scanner(System.in);
        System.out.println("\n*--- Enter Travel Details ---*");
        System.out.print("Enter your starting location: ");
        startLocation = in.nextLine();
        startLocation = suitableString(startLocation);

        System.out.print("Enter your destination location: ");
        endLocation = in.nextLine();
        endLocation = suitableString(endLocation);

        System.out.print("Enter your journey starting time (24 hrs format, e.g., 14 for 2 PM): ");
        int t = in.nextInt();
        time = String.format("%02d:00:00", t);

        System.out.println("\n*--- Searching for buses ---*");
    }

    static String suitableString(String s) {
        // Convert the string to capitalize the first letter and lowercase the rest
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
