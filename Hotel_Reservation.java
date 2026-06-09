import java.io.*;
import java.util.*;

class Booking {
    String customerName;
    String roomType;

    Booking(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

public class Hotel_Reservation {

    static ArrayList<Booking> bookings = new ArrayList<>();
    static final String FILE_NAME = "bookings.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadBookings();

        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Room Types");
            System.out.println("2. Book Room");
            System.out.println("3. View Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    showRooms();
                    break;

                case 2:
                    bookRoom(sc);
                    break;

                case 3:
                    viewBookings();
                    break;

                case 4:
                    cancelBooking(sc);
                    break;

                case 5:
                    saveBookings();
                    System.out.println("Thank you!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void showRooms() {
        System.out.println("\nAvailable Room Categories:");
        System.out.println("1. Standard - Rs.2000");
        System.out.println("2. Deluxe   - Rs.4000");
        System.out.println("3. Suite    - Rs.7000");
    }

    static void bookRoom(Scanner sc) {
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        showRooms();

        System.out.print("Select Room Type: ");
        int roomChoice = sc.nextInt();
        sc.nextLine();

        String roomType = "";

        switch (roomChoice) {
            case 1:
                roomType = "Standard";
                break;
            case 2:
                roomType = "Deluxe";
                break;
            case 3:
                roomType = "Suite";
                break;
            default:
                System.out.println("Invalid Room Type!");
                return;
        }

        System.out.println("Processing Payment...");
        System.out.println("Payment Successful!");

        bookings.add(new Booking(name, roomType));
        saveBookings();

        System.out.println("Room Booked Successfully!");
    }

    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\nBooking Details:");
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            System.out.println((i + 1) + ". " +
                    b.customerName + " - " + b.roomType);
        }
    }

    static void cancelBooking(Scanner sc) {
        viewBookings();

        if (bookings.isEmpty())
            return;

        System.out.print("Enter booking number to cancel: ");
        int index = sc.nextInt();

        if (index >= 1 && index <= bookings.size()) {
            bookings.remove(index - 1);
            saveBookings();
            System.out.println("Booking Cancelled Successfully!");
        } else {
            System.out.println("Invalid Booking Number!");
        }
    }

    static void saveBookings() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME));

            for (Booking b : bookings) {
                pw.println(b.customerName + "," + b.roomType);
            }

            pw.close();
        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }

    static void loadBookings() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists())
                return;

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                bookings.add(new Booking(data[0], data[1]));
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("Error loading bookings.");
        }
    }
}