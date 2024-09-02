import java.sql.*;
import java.util.Scanner;

public class Cancellation {
    static Scanner in=new Scanner(System.in);
  static   int booking_id;
    static String reason;
    public  static  void cancellation() throws SQLException {
          input();
        String checkBookingQuery = "SELECT status_ FROM booking_details WHERE booking_id = " + booking_id;
        ResultSet rs = data_base_con.read(checkBookingQuery);
        if (rs.next()) {
            String status_ = rs.getString("status_");

            if ("cancelled".equalsIgnoreCase(status_)) {
                System.out.println("The ticket is already cancelled.");
                return;
            }

            // Step 2: Update the booking status to 'cancelled'
            String updateBookingQuery = "UPDATE booking_details SET status_ = 'cancelled' WHERE booking_id = " + booking_id;
            int val=data_base_con.update(updateBookingQuery);

            if (val > 0) {
                // Step 3: Insert cancellation details into the cancellation table
                String insertCancellationQuery = "INSERT INTO cancellation (booking_id, cancellation_date, reason) " +
                        "VALUES (" + booking_id + ", NOW(), '" + reason + "')";
                val=data_base_con.update(insertCancellationQuery);

                System.out.println("Ticket cancelled successfully.");
            } else {
                System.out.println("Failed to cancel the ticket.");
            }

        } else {
            System.out.println("No booking found with the given ID.");
        }

    }
    public  static void input()
    {
        System.out.print("ENTER YOUR BOOKING ID TO CANCEL THE TICKET :");
      booking_id=in.nextInt();
      System.out.println();
        System.out.print("ENTER THE REASON TO CANCEL THE TICKET :");
        reason=in.nextLine();
        reason=in.nextLine();;
        System.out.println();
    }
}
