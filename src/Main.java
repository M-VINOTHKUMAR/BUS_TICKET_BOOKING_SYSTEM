import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        Scanner in = new Scanner(System.in);
        boolean continueRunning = true;

        while (continueRunning) {
            displayOptions();
            int choice = getValidChoice(in);

            switch (choice) {
                case 1:
                    Booking.booking();
                    break;
                case 2:

                    Bus_availability.bus_availability();
                    break;
                case 3:
                    Ticket_price.ticket_price();
                    break;
                case 4:
                    Bus_location.bus_location();
                    break;
                case 5:
                    Customer_history.getCustomerHistory();
                    break;
                case 6:
                    Cancellation.cancellation();
                    break;
                case 7:
                    System.out.println("\nThank you for using the bus booking system. Have a great day!");
                    continueRunning = false;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please select a valid option.");
                    break;
            }
        }
        in.close();
    }

    private static void displayOptions() {
        System.out.println("\n*--- CLICK THE NUMBER WHICH CORRESPONDS TO THE OPTION ---*");
        System.out.println("| 1 ==> BOOKING ");
        System.out.println("| 2 ==> BUS AVAILABILITY ");
        System.out.println("| 3 ==> TICKET PRICE ");
        System.out.println("| 4 ==> CURRENT BUS LOCATION ");
        System.out.println("| 5 ==> CUSTOMER HISTORY ");
        System.out.println("| 6 ==> CANCELLATION ");
        System.out.println("| 7 ==> EXIT ");
        System.out.println("*----------------------------------------------*");
    }

    private static int getValidChoice(Scanner in) {
        int choice;
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(in.nextLine());
                if (choice < 1 || choice > 7) {
                    throw new NumberFormatException();
                }
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
            }
        }
    }
}
