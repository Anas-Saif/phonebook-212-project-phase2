
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PhoneBook mainPhoneBook = new PhoneBook();
        while (true) {
            try {
                System.out.println("Welcome to the LinkedTree Phonebook!");
                System.out.println("Please select an option:");
                System.out.println("1. Add a contact");
                System.out.println("2. Search for contact");
                System.out.println("3. Delete a contact");
                System.out.println("4. Schedule an event");
                System.out.println("5. Print event details");
                System.out.println("6. Print contact by first name");
                System.out.println("7. Print all events alphabetically");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                int choice = input.nextInt();
                input.nextLine(); //buffer cleaner


                switch (choice) {
                    case 1: {
                        mainPhoneBook.addContact();
                        break;
                    }
                    case 2: {
                        mainPhoneBook.search();
                        break;
                    }
                    case 3: {
                        mainPhoneBook.deleteContact();
                        break;
                    }
                    case 4: {
                       mainPhoneBook.addEvent();
                        break;
                    }
                    case 5: {
                        mainPhoneBook.searchEvent();
                        break;
                    }
                    case 6: {
                        mainPhoneBook.searchByFirstName();
                        break;
                    }
                    case 7: {
                        mainPhoneBook.displayAllEvents();
                        break;
                    }
                    case 8: {
                        System.out.println("Goodbye!");
                        System.exit(0);
                    }
                    default: {
                        System.out.println("Invalid choice. Please try again.");
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.next(); // Clear the invalid input
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}


