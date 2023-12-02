import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;
public class PhoneBook {
    private final ContactBST <Contact> phoneBook = new ContactBST<>(); // Create a Phonebook object
    private final LinkedList <Event> allEvents = new LinkedList<>();
    Scanner input = new Scanner(System.in); // Create a Scanner object

    // Method to add a new contact to the phonebook
    public void addContact () {
        try {
            // Prompt the user for contact detail
            System.out.print("Enter contact name: ");
            String contactName = input.nextLine();
            contactName = contactName.toLowerCase();

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

    // Helper method to check if an event exists then return contact object
    private Contact findContactByName(String name) {
        if (phoneBook.findkey(name)) {
            return phoneBook.retrieve();
        } else {
            System.out.println("Contact with name '" + name + "' not found.");
            return null;}}
    // Method to schedule an event
    public void addAppointment(){
        try {
            input.nextLine(); //buffer cleaner
            System.out.print("Enter event title: ");
            String eventTitle = input.nextLine();
            System.out.print("Enter contact name: ");
            String contactName = input.nextLine();
            input.nextLine(); //buffer cleaner
            // Search for the contact by name
            Contact tmpContact = findContactByName(contactName);
            if (tmpContact != null){
                // Prompt for event details
                System.out.print("Enter event date Ex: YYYY/MM/DD : ");
                String date = input.next();

                //check date format
                while (!date.matches("^\\d{4}/\\d{2}/\\d{2}$")) {
                    System.out.println("Invalid date of birth format. Use (YYYY/MM/DD).");
                    System.out.print("Enter contact birthday (YYYY/MM/DD): ");
                    date = input.next();
                }

                System.out.print("Enter event Start time Ex: HH:MM : ");
                String startTime = input.next();
                System.out.print("Enter event End time Ex: HH:MM : ");
                String endTime = input.next();

                // Parse date and time information and create a LocalDateTime object
                int year = Integer.parseInt(date.split("/")[0]);
                int month = Integer.parseInt(date.split("/")[1]);
                int day = Integer.parseInt(date.split("/")[2]);
                int startHour = Integer.parseInt(startTime.split(":")[0]);
                int startMinute = Integer.parseInt(startTime.split(":")[1]);
                int endHour = Integer.parseInt(endTime.split(":")[0]);
                int endMinute = Integer.parseInt(endTime.split(":")[1]);

                // represent the event's start and end times
                LocalDateTime startEvent = LocalDateTime.of(year, month, day, startHour, startMinute);
                LocalDateTime endEvent = LocalDateTime.of(year, month, day, endHour, endMinute);
                System.out.print("Enter event location: ");
                String eventLocation = input.next();

                // Create a new Event object
                Event tmpEvent = new Appointment(eventTitle, startEvent, endEvent, eventLocation, "Appointment",tmpContact);
                if (!isConflict(tmpEvent)) {
                    sortEvent(tmpEvent);
                    tmpContact.setEventsInContact(tmpEvent);
                    System.out.println("Event has been added successfully");
                }
            } else {
                System.out.println("The contact does not exist");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Input Miss Mach");
        }
    }



    public boolean isConflict(Event e) {
        if (!allEvents.empty()) {
            allEvents.findFirst();
            while (!allEvents.last()) {
                Event currentEvent = allEvents.retrieve();
                if (currentEvent.getStartTime().compareTo(e.getEndTime()) < 0
                        && currentEvent.getEndTime().compareTo(e.getStartTime()) > 0) {
                    System.out.println("There's a conflict with another event");
                    return true;
                }
                allEvents.findNext();
            }

            // Check the last event after the loop
            Event currentEvent = allEvents.retrieve();
            if (currentEvent.getStartTime().compareTo(e.getEndTime()) < 0
                    && currentEvent.getEndTime().compareTo(e.getStartTime()) > 0) {
                System.out.println("There's a conflict with another event");
                return true;
            }
        }
        return false;
    }

    public void sortEvent(Event s){

        if (!allEvents.empty()){

            allEvents.findFirst();
            while (!allEvents.last()){
                if (s.getTitle().toLowerCase().compareTo(allEvents.retrieve().getTitle().toLowerCase()) <= -1){
                    Event tmp = allEvents.retrieve();
                    allEvents.update(s);
                    allEvents.insert(tmp);
                    return;
                }
                allEvents.findNext();
            }
            if (s.getTitle().toLowerCase().compareTo(allEvents.retrieve().getTitle().toLowerCase()) <= -1){
                Event tmp = allEvents.retrieve();
                allEvents.update(s);
                allEvents.insert(tmp);
                return;
            }

        }
        allEvents.insert(s);
        return;
    }

    public void addEvents() {
        try {
            input.nextLine(); //buffer cleaner
            System.out.print("Enter event title: ");
            String eventTitle = input.nextLine();

            System.out.print("Enter contacts name separated by a comma: ");
            String contactsName = input.nextLine();
            LinkedList<Contact> contactsInEvent = new LinkedList<>();

            String[] names = contactsName.split(",");
            for (String name : names) {
                Contact tmpContact = findContactByName(name);
                if (tmpContact != null) {
                    contactsInEvent.insert(tmpContact);
                } else {
                    System.out.println("The contact does not exist");
                    return;
                }
            }

            input.nextLine(); //buffer cleaner
            // Search for the contact by name

            // Prompt for event details
            System.out.print("Enter event date Ex: YYYY/MM/DD : ");
            String date = input.next();

            //check date format
            while (!date.matches("^\\d{4}/\\d{2}/\\d{2}$")) {
                System.out.println("Invalid date of birth format. Use (YYYY/MM/DD).");
                System.out.print("Enter contact birthday (YYYY/MM/DD): ");
                date = input.next();
            }

            System.out.print("Enter event Start time Ex: HH:MM : ");
            String startTime = input.next();
            System.out.print("Enter event End time Ex: HH:MM : ");
            String endTime = input.next();

            // Parse date and time information and create a LocalDateTime object
            int year = Integer.parseInt(date.split("/")[0]);
            int month = Integer.parseInt(date.split("/")[1]);
            int day = Integer.parseInt(date.split("/")[2]);
            int startHour = Integer.parseInt(startTime.split(":")[0]);
            int startMinute = Integer.parseInt(startTime.split(":")[1]);
            int endHour = Integer.parseInt(endTime.split(":")[0]);
            int endMinute = Integer.parseInt(endTime.split(":")[1]);

            // represent the event's start and end times
            LocalDateTime startEvent = LocalDateTime.of(year, month, day, startHour, startMinute);
            LocalDateTime endEvent = LocalDateTime.of(year, month, day, endHour, endMinute);
            System.out.print("Enter event location: ");
            String eventLocation = input.next();

            // Create a new Event object
            Event tmpEvent = new Events(eventTitle, startEvent, endEvent, eventLocation, "Event", contactsInEvent);
            if (!isConflict(tmpEvent)) {
                while (!contactsInEvent.last()) {
                    Contact tmpContact = contactsInEvent.retrieve();
                    tmpContact.setEventsInContact(tmpEvent);
                    contactsInEvent.findNext();
                }
                //for last contact
                Contact tmpContact = contactsInEvent.retrieve();
                tmpContact.setEventsInContact(tmpEvent);


                //adding the event to linkedlist (sorted)
                sortEvent(tmpEvent);
                System.out.println("Event has been added successfully");

            }

        } catch(InputMismatchException e){
            System.out.println("Input Miss Mach");
        }
    }

    public void addEvent(){
        int choice;
        System.out.println("Enter type :");
        System.out.println("1-Event");
        System.out.println("2-Appointment");
        System.out.println("Enter your choice :");

        switch (choice = input.nextInt()) {
            case 1:
                addEvents();
                break;
            case 2:
                addAppointment();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    public void searchEventByTitle() {
        try {
            input.nextLine(); //buffer cleaner
            System.out.print("Enter event title: ");
            String eventTitle = input.nextLine();
            input.nextLine(); //buffer cleaner
            boolean found = false;
            if (!allEvents.empty()) {
                allEvents.findFirst();
                while (!allEvents.last()) {
                    if (allEvents.retrieve().getTitle().equalsIgnoreCase(eventTitle)) {
                        allEvents.retrieve().displayEvent();
                        found = true;
                    }
                    allEvents.findNext();
                }
                if (allEvents.retrieve().getTitle().equalsIgnoreCase(eventTitle)) {
                    allEvents.retrieve().displayEvent();
                    return;
                }
            }
            if (!found)
                System.out.println("Event not found");
        } catch (InputMismatchException e){
            System.out.println("Input miss match");
        }
    }

    public void searchEventByContactName() {
        try {
            input.nextLine(); // buffer cleaner
            System.out.print("Enter contact name: ");
            String contactName = input.nextLine();
            boolean found = false;

            if (!allEvents.empty()) {
                allEvents.findFirst();
                while (!allEvents.last()) {
                    if (isEventLinkedToContact(allEvents.retrieve(), contactName)) {
                        allEvents.retrieve().displayEvent();
                        found = true;
                    }
                    allEvents.findNext();
                }

                // Check the last event in the list
                if (isEventLinkedToContact(allEvents.retrieve(), contactName)) {
                    allEvents.retrieve().displayEvent();
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No events found for contact '" + contactName + "'.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input mismatch");
        }
    }

    private boolean isEventLinkedToContact(Event event, String contactName) {
        if (event instanceof Appointment) {
            // Check if the appointment's contact matches the provided name
            return ((Appointment) event).getContactInAppointment().getContactName().equalsIgnoreCase(contactName);
        } else {
            // For general events, check each contact in the event's contact list
            LinkedList<Contact> contacts = ((Events) event).getContactsInEvent(); // Assuming a getContacts() method exists
            if (!contacts.empty()) {
                contacts.findFirst();
                while (!contacts.last()) {
                    if (contacts.retrieve().getContactName().equalsIgnoreCase(contactName)) {
                        return true;
                    }
                    contacts.findNext();
                }
                return contacts.retrieve().getContactName().equalsIgnoreCase(contactName);
            }
        }
        return false;
    }

    public void searchEvent() {
        try {
            System.out.println("Search by: ");
            System.out.println("1-Title");
            System.out.println("2-Contact Name");
            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    searchEventByTitle();
                    break;
                case 2:
                    searchEventByContactName();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input miss match");
        }
    }

    public void displayAllEvents(){
        if(!allEvents.empty()){
            allEvents.findFirst();
            while (!allEvents.last()){
                allEvents.retrieve().displayEvent();
                allEvents.findNext();
            }
            allEvents.retrieve().displayEvent();
            return;
        }
        System.out.println("No events to display");
    }



    public void deleteContact() {
        try {
            System.out.print("Enter contact name: ");
            String contactName = input.nextLine();
            contactName = contactName.toLowerCase();

            if (phoneBook.findkey(contactName)) {
                Contact tmp = phoneBook.retrieve();

                // Update events: delete appointments and remove the contact from events
                updateEventsForDeletedContact(tmp);

                // Remove the contact from the phone book
                phoneBook.remove_key(contactName);
                System.out.println("Contact and associated events updated successfully");
            } else {
                System.out.println("Contact not found");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input mismatch");
        }
    }
    private void updateEventsForDeletedContact(Contact contactToDelete) {
        if (!allEvents.empty()) {
            allEvents.findFirst();
            while (!allEvents.last()) {
                Event currentEvent = allEvents.retrieve();
                if (currentEvent instanceof Appointment && ((Appointment) currentEvent).getContactInAppointment().getContactName().equalsIgnoreCase(contactToDelete.getContactName())) {
                    allEvents.remove(); // Remove the appointment
                } else if (currentEvent instanceof Events) {
                    removeFromEventIfPresent(currentEvent, contactToDelete); // Remove the contact from the event
                    allEvents.findNext();
                }
            }

            // Check and update the last event in the list
            Event currentEvent = allEvents.retrieve();
            if (currentEvent instanceof Appointment && ((Appointment) currentEvent).getContactInAppointment().getContactName().equalsIgnoreCase(contactToDelete.getContactName())) {
                allEvents.remove(); // Remove the appointment
            } else if (currentEvent instanceof Events) {
                removeFromEventIfPresent(currentEvent, contactToDelete); // Remove the contact from the event
            }
        }
    }

    private void removeFromEventIfPresent(Event event, Contact contact) {
        LinkedList<Contact> contacts = ((Events)event).getContactsInEvent(); // Assuming Event has getContacts()
        contacts.findFirst();
        while (!contacts.last()) {
            if (contacts.retrieve().equals(contact)) {
                contacts.remove(); // Remove the contact from the event
                if (contacts.empty())
                    allEvents.remove(); // Remove the event if it has no contacts
                return;
            }
            contacts.findNext();
        }
        if (contacts.retrieve().equals(contact)) {
            contacts.remove(); // Check and remove the last contact in the list
        }
    }

}
