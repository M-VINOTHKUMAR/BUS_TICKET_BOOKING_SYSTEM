import java.sql.*;

public class BookingUpdate {

    public static void updateBooking(String seatNumber, String name, String email, String phone, String address) throws SQLException {
        printHeader("BOOKING UPDATE");

        // Get or create customer ID based on provided details
        int customerId = getOrCreateCustomerId(name, email, phone, address);

        if (customerId == -1) {
            System.out.println("Error: Failed to create or retrieve customer details.");
            return;
        }

        // Get seat ID from seat number
        int seatId = getSeatId(seatNumber);

        if (seatId == -1) {
            System.out.println("Error: Invalid seat number.");
            return;
        }

        // Check if the seat is available
        if (!isSeatAvailable(seatId)) {
            System.out.println("Error: Seat is already booked.");
            return;
        }

        // Insert booking details
        int bookingId = insertBookingDetails(customerId, seatId);

        if (bookingId == -1) {
            System.out.println("Error: Failed to book the seat.");
            return;
        }

        // Update customer history
        updateCustomerHistory(customerId, bookingId);
        System.out.println("\n*---BOOKING CONFIRMED---*");
        System.out.println("Your booking ID is: " + bookingId);
        System.out.println("Thank you for booking with us!");
    }

    // Method to get or create customer ID based on details
    private static int getOrCreateCustomerId(String name, String email, String phone, String address) throws SQLException {
        int customerId = getCustomerId(name, email, phone);

        if (customerId == -1) {
            // Customer not found, insert new customer
            customerId = insertCustomer(name, email, phone, address);
            if (customerId != -1) {
                System.out.println("New customer created with ID: " + customerId);
            }
        }

        return customerId;
    }

    // Method to get customer ID based on details
    private static int getCustomerId(String name, String email, String phone) throws SQLException {
        String query = "SELECT customer_id FROM customer_details WHERE name = ? AND email = ? AND phone = ?";
        try (Connection conn = data_base_con.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("customer_id");
                }
                return -1; // Customer not found
            }
        }
    }

    // Method to insert new customer details
    private static int insertCustomer(String name, String email, String phone, String address) throws SQLException {
        String query = "INSERT INTO customer_details (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = data_base_con.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return new customer ID
                    }
                }
            }
            return -1; // Failed to insert customer
        }
    }

    // Method to get seat ID from seat number
    private static int getSeatId(String seatNumber) throws SQLException {
        String query = "SELECT seat_id FROM seats WHERE seat_number = ?";
        try (Connection conn = data_base_con.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, seatNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("seat_id");
                }
                return -1; // Seat not found
            }
        }
    }

    // Method to check if a seat is available based on booking status
    private static boolean isSeatAvailable(int seatId) throws SQLException {
        String query = "SELECT status_ FROM booking_details WHERE seat_id = ? AND status_ = 'confirmed'";
        try (Connection conn = data_base_con.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, seatId);
            try (ResultSet rs = stmt.executeQuery()) {
                return !rs.next(); // Seat is available if no confirmed booking found
            }
        }
    }

    // Method to insert booking details
    private static int insertBookingDetails(int customerId, int seatId) throws SQLException {
        String query = "INSERT INTO booking_details (customer_id, seat_id, booking_date, status_) VALUES (?, ?, NOW(), 'confirmed')";
        try (Connection conn = data_base_con.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, seatId);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return booking ID
                    }
                }
            }
            return -1; // Booking insertion failed
        }
    }

    // Method to update customer history
    private static void updateCustomerHistory(int customerId, int bookingId) throws SQLException {
        String query = "INSERT INTO customer_history (customer_id, booking_id, date, action) VALUES (?, ?, NOW(), 'booking')";
        try (Connection conn = data_base_con.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, bookingId);
            stmt.executeUpdate();
        }
    }

    private static void printHeader(String title) {
        System.out.println("\n*--- " + title + " ---*");
        System.out.println("*" + "-".repeat(title.length() + 4) + "*");
    }
}
