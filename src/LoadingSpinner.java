public class LoadingSpinner {

    public static void start() throws InterruptedException {
        // Define the spinner characters
        char[] spinner = {'/', '|', '\\', '-'};

        // Create a thread to display the spinner
        Thread spinnerThread = new Thread(() -> {
            try {
                // Loop through the spinner characters
                int index = 0;
                while (!Thread.currentThread().isInterrupted()) {
                    // Print the spinner character
                    System.out.print("\rPAYMENT IS GOING ... " + spinner[index % spinner.length]);
                    index++;
                    // Sleep for 200 milliseconds
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                // Handle thread interruption
                Thread.currentThread().interrupt();
            }
        });

        // Start the spinner thread
        spinnerThread.start();

        // Wait for some time to simulate loading (5 seconds)
        Thread.sleep(5000);

        // Stop the spinner thread after the loading is complete
        spinnerThread.interrupt();
        // Print a newline to move to the next line after spinner stops
        System.out.print("\rPAYMENT COMPLETE!        \n"); // Clear the spinner and show a final message
    }
}
