import java.sql.*;

public class SeatPattern {

    public static void printSeatPattern(int busId) throws SQLException {
        // Query to get all seat information
        String seatQuery = "SELECT s.seat_id, s.seat_number, s.seat_type FROM seats s WHERE s.bus_id = " + busId;
        ResultSet seatRs = data_base_con.read(seatQuery);

        // Query to get all booked seats
        String bookingQuery = "SELECT s.seat_id FROM booking_details bd " +
                "JOIN seats s ON bd.seat_id = s.seat_id " +
                "WHERE s.bus_id = " + busId + " AND bd.status_ = 'confirmed'";
        ResultSet bookingRs = data_base_con.read(bookingQuery);

        // Use an array to store booked seats
        boolean[] bookedSeats = new boolean[1000]; // Adjust size based on expected seat count
        while (bookingRs.next()) {
            int seatId = bookingRs.getInt("seat_id");
            bookedSeats[seatId] = true; // Mark the seat as booked
        }

        // Determine the bus capacity
        int capacity = getBusCapacity(busId); // Implement this method to get the bus capacity
        boolean isDivisibleBy3 = (capacity % 3 == 0);

        // Print seat pattern
        printPattern(seatRs, bookedSeats, isDivisibleBy3);
    }

    private static void printPattern(ResultSet seatRs, boolean[] bookedSeats, boolean isDivisibleBy3) throws SQLException {
        System.out.println("Bus Seating Layout:");

        // Print Windows
        System.out.println("   _____________________________________________________________________________");
        System.out.println("  /                                                                             \\");
        System.out.println(" |            [ CONDUCTOR ]                                [ DRIVER]              |  <- Windows");

        // Print Doors
        System.out.println(" |  _______________________________________   ____________________________________|");
        System.out.println(" | |                                        |                                     | <- Doors");
        System.out.println(" | |________________________________________|_____________________________________|");

        // Print Seats
        int seatPerRow = 4; // Assuming 4 seats per row
        int seatCounter = 0; // Counter for seat printing
        int rowsPrinted = 0; // Counter for rows printed
        int seatsInCurrentRow = 0; // Number of seats in the current row

        System.out.print(" | ");
        while (seatRs.next()) {
            String seatNumber = seatRs.getString("seat_number");
            String seatType = seatRs.getString("seat_type");
            int seatId = seatRs.getInt("seat_id");

            // Determine seat status
            String status = bookedSeats[seatId] ? "B" + seatNumber.substring(1) : "A" + seatNumber.substring(1);
            String type = seatType.equals("standard") ? "std" : "pre";

            // Print the seat with type and status
            System.out.print("[ " + seatNumber + " (" + type + "): " + status + " ]     ");

            seatCounter++;
            seatsInCurrentRow++;

            // Print a new line after every row of seats
            if (seatsInCurrentRow % seatPerRow == 0) {
                System.out.println("|"); // End of row
                if (seatCounter < 45) { // Adjust this to print the correct number of rows
                    System.out.print(" | ");
                }
                seatsInCurrentRow = 0; // Reset seats in the current row
                rowsPrinted++;
            }
        }

        // Print the last row if there are remaining seats
        if (seatsInCurrentRow > 0) {
            System.out.println("|");
        }

        // Ensure there's a new line at the end
        System.out.println(" |________________________________________________________________________________|");
    }

    private static int getBusCapacity(int busId) throws SQLException {
        // Implement this method to retrieve the capacity of the bus
        String query = "SELECT capacity FROM buses_details WHERE bus_id = " + busId;
        ResultSet rs = data_base_con.read(query);
        if (rs.next()) {
            return rs.getInt("capacity");
        }
        return 0; // Default value if capacity is not found
    }
}
