import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class Ticket_price {
        // Attributes to store input from the user
        private static String startLocation;
        private static String endLocation;
        public static void ticket_price() throws SQLException {
            // SQL query to fetch ticket prices and bus details based on route locations
            input();
            String query = String.format("""
            SELECT 
                br.route_name,
                bd.bus_name,
                bd.bus_number,
                bd.bus_type,
                bd.capacity,
                t.departure_datetime,
                t.arrival_datetime,
                tp.price,
                tp.seat_type
            FROM 
                bus_routes br
            JOIN 
                trips t ON br.route_id = t.route_id
            JOIN 
                buses_details bd ON t.bus_id = bd.bus_id
            JOIN 
                ticket_price tp ON br.route_id = tp.route_id
            WHERE 
                br.start_location = '%s' 
                AND br.end_location = '%s'
            ORDER BY 
                t.departure_datetime;
            """, startLocation, endLocation);

          ResultSet rs=data_base_con.read(query);

                // Check if any results are returned
                if (!rs.isBeforeFirst()) {
                    System.out.println("No buses available for the selected route.");
                    return;
                }

                // Display header for the information
                System.out.println("Available Buses and Ticket Prices:");
                System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.println("│ Route Name         │ Bus Name           │ Bus Number    │ Bus Type   │ Capacity │ Departure Time       │ Arrival Time         │ Seat Type │ Price    │");
                System.out.println("├────────────────────┼────────────────────┼───────────────┼────────────┼──────────┼──────────────────────┼──────────────────────┼───────────┼──────────┤");

                // Display each bus's information in a structured format
                while (rs.next()) {
                    System.out.printf(
                            "│ %-18s │ %-18s │ %-13s │ %-10s │ %-8d │ %-20s │ %-20s │ %-9s │ %-8.2f │%n",
                            fitString(rs.getString("route_name"), 18),
                            fitString(rs.getString("bus_name"), 18),
                            fitString(rs.getString("bus_number"), 13),
                            fitString(rs.getString("bus_type"), 10),
                            rs.getInt("capacity"),
                            fitString(rs.getTimestamp("departure_datetime").toString(), 20),
                            fitString(rs.getTimestamp("arrival_datetime").toString(), 20),
                            fitString(rs.getString("seat_type"), 9),
                            rs.getDouble("price")
                    );
                }
                System.out.println("└────────────────────┴────────────────────┴───────────────┴────────────┴──────────┴──────────────────────┴──────────────────────┴───────────┴──────────┘");
                System.out.println(); // Adds space between records
        }

        // Method to take route input from the user
        public static void input() {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter Start Location: ");
            startLocation = in.nextLine();
            System.out.print("Enter End Location: ");
            endLocation = in.nextLine();
        }

        // Helper method to adjust string length to fit the box size
        private static String fitString(String value, int length) {
            if (value.length() > length) {
                return value.substring(0, length - 3) + "..."; // Truncate and add ellipsis if too long
            } else {
                return String.format("%-" + length + "s", value); // Pad with spaces if too short
            }
        }

}
