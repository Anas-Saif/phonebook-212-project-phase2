import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
public class PhoneBook {
    private final ContactBST <Contact> phoneBook = new ContactBST<>(); // Create a Phonebook object
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


            Contact newContact = new Contact(contactName, contactPhone, contactEmail,
                    contactAddress, contactBirthday, contactNotes);

            // Check if the contact already exists using in-order traversal
            if (!contactExists(contactName, contactPhone)) {
                if (phoneBook.insert(contactName, newContact)) {
                    System.out.println("Contact added successfully.");
                } else {
                    System.out.println("Error adding contact.");
                }
            } else {
                System.out.println("A contact with the same name or phone number already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Helper method to check if a contact exists
    private boolean contactExists(String name, String phoneNumber) {
        return contactExistsInOrder(phoneBook.root, name, phoneNumber);
    }

    private boolean contactExistsInOrder(BSTNode<Contact> node, String name, String phoneNumber) {
        if (node == null) {
            return false;
        }

        if (contactExistsInOrder(node.left, name, phoneNumber)) {
            return true;
        }

        Contact contact = node.data;
        if (contact.getContactName().equalsIgnoreCase(name) ||
                contact.getContactPhone().equals(phoneNumber)) {
            return true;
        }

        return contactExistsInOrder(node.right, name, phoneNumber);
    }
    // Method to search for a contact in the phonebook
    public void searchByName () {
        System.out.print("Enter contact name: ");
        String contactName = input.nextLine();
        contactName = contactName.toLowerCase();
        if (phoneBook.findkey(contactName)) {
            Contact tmp = phoneBook.retrieve();
            tmp.displayContactDetails();// Print the contact details
        } else {
            System.out.println("Contact not found");
        }
    }
    // Method to search for a contact by phone number
    public void searchByPhoneNumber() {
        System.out.print("Enter contact phone number: ");
        String phoneNumber = input.nextLine();

        Contact foundContact = inOrderSearchByPhoneNumber(phoneBook.root, phoneNumber);

        if (foundContact != null) {
            System.out.println("Contact with phone number " + phoneNumber + ":");
            foundContact.displayContactDetails();
        } else {
            System.out.println("No contact found with the phone number " + phoneNumber + ".");
        }
    }

    private Contact inOrderSearchByPhoneNumber(BSTNode<Contact> node, String phoneNumber) {
        if (node == null) {
            return null;
        }

        // Traverse the left subtree
        Contact leftResult = inOrderSearchByPhoneNumber(node.left, phoneNumber);
        if (leftResult != null) {
            return leftResult;
        }

        // Check the current node
        if (node.data.getContactPhone().equals(phoneNumber)) {
            return node.data;
        }

        // Traverse the right subtree
        return inOrderSearchByPhoneNumber(node.right, phoneNumber);
    }

    public void searchByEmail() {
        System.out.print("Enter contact email: ");
        String email = input.nextLine();

        LinkedList<Contact> foundContacts = new LinkedList<>();
        inOrderSearchByEmail(phoneBook.root, email, foundContacts);

        if (!foundContacts.empty()) {
            System.out.println("Contacts with email " + email + ":");
            foundContacts.findFirst();
            while (!foundContacts.last()) {
                foundContacts.retrieve().displayContactDetails();
                foundContacts.findNext();
            }
            foundContacts.retrieve().displayContactDetails();
        } else {
            System.out.println("No contacts found with the email " + email + ".");
        }
    }

    private void inOrderSearchByEmail(BSTNode<Contact> node, String email, List<Contact> foundContacts) {
        if (node != null) {
            // Traverse the left subtree
            inOrderSearchByEmail(node.left, email, foundContacts);

            // Check the current node
            if (node.data.getContactEmail().equalsIgnoreCase(email)) {
                foundContacts.insert(node.data);
            }

            // Traverse the right subtree
            inOrderSearchByEmail(node.right, email, foundContacts);
        }
    }

    public void searchByAddress() {
        System.out.print("Enter contact address: ");
        String address = input.nextLine();

        LinkedList<Contact> foundContacts = new LinkedList<>();
        inOrderSearchByAddress(phoneBook.root, address, foundContacts);

        if (!foundContacts.empty()) {
            System.out.println("Contacts with address " + address + ":");
            foundContacts.findFirst();
            while (!foundContacts.last()) {
                foundContacts.retrieve().displayContactDetails();
                foundContacts.findNext();
            }
            foundContacts.retrieve().displayContactDetails();
        } else {
            System.out.println("No contacts found with the address " + address + ".");
        }
    }

    private void inOrderSearchByAddress(BSTNode<Contact> node, String address, LinkedList<Contact> foundContacts) {
        if (node != null) {
            inOrderSearchByAddress(node.left, address, foundContacts);

            if (node.data.getContactAddress().equalsIgnoreCase(address)) {
                foundContacts.insert(node.data);
            }

            inOrderSearchByAddress(node.right, address, foundContacts);
        }
    }

    public void searchByBirthday() {
        System.out.print("Enter contact birthday (YYYY/MM/DD): ");
        String birthday = input.nextLine();

        LinkedList<Contact> foundContacts = new LinkedList<>();
        inOrderSearchByBirthday(phoneBook.root, birthday, foundContacts);

        if (!foundContacts.empty()) {
            System.out.println("Contacts with birthday " + birthday + ":");
            foundContacts.findFirst();
            while (!foundContacts.last()) {
                foundContacts.retrieve().displayContactDetails();
                foundContacts.findNext();
            }
            foundContacts.retrieve().displayContactDetails();
        } else {
            System.out.println("No contacts found with the birthday " + birthday + ".");
        }
    }

    private void inOrderSearchByBirthday(BSTNode<Contact> node, String birthday, LinkedList<Contact> foundContacts) {
        if (node != null) {
            inOrderSearchByBirthday(node.left, birthday, foundContacts);

            if (node.data.getContactBirthday().equals(birthday)) {
                foundContacts.insert(node.data);
            }

            inOrderSearchByBirthday(node.right, birthday, foundContacts);
        }
    }

    public void search(){
        try{
            System.out.println("Search by: ");
            System.out.println("1-Name");
            System.out.println("2-Phone Number");
            System.out.println("3-Email");
            System.out.println("4-Address");
            System.out.println("5-Birthday");
            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine(); // buffer cleaner
            switch (choice) {
                case 1:
                    searchByName();
                    break;
                case 2:
                    searchByPhoneNumber();
                    break;
                case 3:
                    searchByEmail();
                    break;
                case 4:
                    searchByAddress();
                    break;
                case 5:
                    searchByBirthday();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } catch (InputMismatchException e){
            System.out.println("Input miss match");
        }
    }
    public void searchByFirstName() {
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        LinkedList<Contact> foundContacts = new LinkedList<>();
        inOrderSearchByFirstName(phoneBook.root, firstName, foundContacts);

        if (!foundContacts.empty()) {
            System.out.println("Contacts with first name " + firstName + ":");
            foundContacts.findFirst();
            while (!foundContacts.last()) {
                foundContacts.retrieve().displayContactDetails();
                foundContacts.findNext();
            }
            foundContacts.retrieve().displayContactDetails();
        } else {
            System.out.println("No contacts found with the first name " + firstName + ".");
        }
    }

    private void inOrderSearchByFirstName(BSTNode<Contact> node, String firstName, LinkedList<Contact> foundContacts) {
        if (node != null) {
            inOrderSearchByFirstName(node.left, firstName, foundContacts);

            String contactFirstName = node.data.getContactName().split(" ")[0];
            if (contactFirstName.equalsIgnoreCase(firstName)) {
                foundContacts.insert(node.data);
            }

            inOrderSearchByFirstName(node.right, firstName, foundContacts);
        }
    }

}
