import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;
public class PhoneBook {
    private ContactBST <Contact> phoneBook = new ContactBST<>(); // Create a Phonebook object
    Scanner input = new Scanner(System.in); // Create a Scanner object

    // Method to add a new contact to the phonebook
    public void addContact () {
        try {
            // Prompt the user for contact detail
            System.out.print("Enter contact name: ");
            String contactName = input.nextLine();
            //contactName = contactName.toLowerCase();

            System.out.print("Enter contact phone: ");
            String contactPhone = input.next();

            //check phone number format
            while (!contactPhone.matches("\\d{10}")) {
                System.out.println("Wrong Phone number format! Please enter 10 numeric digits.");
                System.out.print("Enter contact Phone Number: ");
                contactPhone = input.next();
            }

            System.out.print("Enter contact email: ");
            String contactEmail = input.next();

            System.out.print("Enter contact address: ");
            String contactAddress = input.next();

            System.out.print("Enter contact birthday (YYYY/MM/DD): ");
            String contactBirthday = input.next();


            //check date format
            while (!contactBirthday.matches("^\\d{4}/\\d{2}/\\d{2}$")) {
                System.out.println("Invalid date of birth format. Use (YYYY/MM/DD).");
                System.out.print("Enter contact birthday (YYYY/MM/DD): ");
                contactBirthday = input.next();
            }

            System.out.print("Enter contact notes: ");
            input.nextLine(); //buffer cleaner
            String contactNotes = input.nextLine();
            input.nextLine(); //buffer cleaner


            Contact tmp = new Contact(contactName, contactPhone, contactEmail, contactAddress, contactBirthday, contactNotes);

            // Check if the contact already exists and add it to the phonebook
            if (phoneBook.insert(contactName, tmp)) {
                System.out.println("Added Successfully");
            } else {
                System.out.println("Contact already exists");
            }
        } catch (InputMismatchException e){
            System.out.println("Input miss match");
        }

        return;
    }
    // Method to search for a contact in the phonebook
    public void searchByName () {
        System.out.print("Enter contact name: ");
        String contactName = input.nextLine();
        contactName = contactName.toLowerCase();
        if (phoneBook.findkey(contactName)) {
            Contact tmp = phoneBook.retrieve();
            System.out.print (tmp.toString()) ;// Print the contact details
        } else {
            System.out.println("Contact not found");
        }
        return;
    }
    // Method to search for a contact by phone number
    public void searchByPhoneNumber () {
        System.out.print("Enter contact Phone Number: ");
        String contactPhoneNumber = input.next();

        //check phone number format
        while (!contactPhoneNumber.matches("\\d{10}")) {
            System.out.println("Wrong Phone number format! Please enter 10 numeric digits.");
            System.out.print("Enter contact Phone Number: ");
            contactPhoneNumber = input.next();
        }
        if (phoneBook.findkey(contactPhoneNumber)) {
            Contact tmp = phoneBook.retrieve();
            System.out.print (tmp.toString()) ;// Print the contact details
        } else {
            System.out.println("Contact not found");
        }
        return;
    }









    public void searchByFirstName () {
        System.out.print("Enter contact name: ");
        String contactName = input.nextLine();
        contactName = contactName.toLowerCase();
        String key=contactName.split(" ")[0];
        if (phoneBook.findkey(key)) {
            Contact tmp = phoneBook.retrieve();
            System.out.print (tmp.toString()) ;// Print the contact details
        } else {
            System.out.println("Contact not found");
        }
        return;
    }

}
