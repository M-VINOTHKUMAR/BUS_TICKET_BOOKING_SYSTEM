import java.sql.SQLException;
import java.util.Scanner;

public class Booking {
    static Scanner in = new Scanner(System.in);
    static String name;
    static String email;
    static String phone;
    static String address;

    public static void booking() throws SQLException, InterruptedException {
        printHeader("CUSTOMER DETAILS");
        customer_details(); // Collect customer details

        // Display bus availability and get the preferred bus ID
        Bus_availability ba=new Bus_availability();
        boolean res = ba.bus_availability();
        if (!res) {
            System.out.println("\nNo buses available at the moment. Please try again later.");
            return;
        }

        int busId = prefer_bus();
        if (busId == -1) {
            System.out.println("\nYou chose not to book a bus. Exiting booking process.");
            return;
        }
        printHeader("SEAT SELECTION");
        SeatPattern.printSeatPattern(busId);

        // Get the seat number from the user
        String seatNumber = Seat_avaliable.seat_avaliable();
        if (seatNumber == null || seatNumber.isEmpty()) {
            System.out.println("\nInvalid seat number. Please restart the booking process.");
            return;
        }

        // Update booking details and other related information
        BookingUpdate.updateBooking(seatNumber, name, email, phone, address);

        // Payment process
        printHeader("PAYMENT");
        System.out.println("*---PROCESSING PAYMENT---*");
        LoadingSpinner.start();
        System.out.println("\n*---BOOKING CONFIRMED---*");
        System.out.println("Thank you for booking with us. Have a safe journey!");
    }

    private static int prefer_bus() {
        int bus_id = -1;
        while (true) {
            System.out.println("\n--> ENTER YOUR PREFERRED BUS ID <--");
            System.out.println("--> IF NO BUSES ARE NEEDED, ENTER -1 <--");
            try {
                bus_id = Integer.parseInt(in.nextLine());
                if (bus_id >= -1) break;
                else System.out.println("Invalid input. Please enter a valid bus ID.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return bus_id;
    }

    private static void customer_details() {
      //  System.out.println("\n*--- CUSTOMER DETAILS ---*");
        System.out.print("Enter your name: ");
        name = in.nextLine();
        System.out.print("Enter your email ID: ");
        email = in.nextLine();
        System.out.print("Enter your phone number: ");
        phone = in.nextLine();
        System.out.print("Enter your address: ");
        address = in.nextLine();
        System.out.println("*--------------------------*");
    }

    private static void printHeader(String title) {
        System.out.println("\n*--- " + title + " ---*");
        System.out.println("*" + "-".repeat(title.length() + 2) + "*");
    }
}
