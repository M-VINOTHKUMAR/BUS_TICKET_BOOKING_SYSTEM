import java.sql.*;
import java.util.Scanner;

public class Seat_avaliable {
    static Scanner in = new Scanner(System.in);
    //private static DatabaseConnection data_base_con; // Ensure this is initialized with your actual DB connection

    public static String seat_avaliable() throws SQLException {
        while (true) {
            System.out.println("ENTER YOUR WANTED SEAT NUMBER (e.g., S20):");
            String seatNumber = in.nextLine().toUpperCase();

            // Ensure seat number starts with 'S'
            if (!seatNumber.startsWith("S")) {
                System.out.println("Invalid seat number. It should start with 'S'.");
                continue;
            }

            // Construct query
            String query = "SELECT bd.status_ FROM booking_details bd " +
                    "JOIN seats s ON bd.seat_id = s.seat_id " +
                    "WHERE s.seat_number = '" + seatNumber + "'";

            try (Statement stmt = data_base_con.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                if (!rs.next()) {
                    // If no results, the seat is available
                    System.out.println("Seat " + seatNumber + " is available.");

                    return seatNumber;
                }

                // If result is found, check if it is booked
                String status = rs.getString("status_");
                if ("confirmed".equals(status)) {
                    System.out.println("Seat " + seatNumber + " is already booked.");

                } else {
                    System.out.println("Seat " + seatNumber + " is available.");
                    return seatNumber;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("An error occurred while checking seat availability.");
            }
        }
    }


}
