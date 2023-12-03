/****************************************
 CLASS: PhoneBook.java
 CSC212 Data structures - Project phase II
 Fall 2023
 EDIT DATE:
 03-12-2023
 TEAM:
 Logic
 AUTHORS:
 Anas Saif (443106538)
 Abdullah Alothman (443101712)
 Mohammed Lazhar (443102272)
 ****************************************/
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
            System.out.print("Enter contact name: ");                                                //1
            String contactName = input.nextLine();//
            contactName = contactName.toLowerCase();                                                 //1

            System.out.print("Enter contact phone: ");                                               //1
            String contactPhone = input.next();                                                      //1

            //check phone number format
            while (!contactPhone.matches("\\d{10}")) {                                         //t
                System.out.println("Wrong Phone number format! Please enter 10 numeric digits.");    //t
                System.out.print("Enter contact Phone Number: ");                                    //t
                contactPhone = input.next();                                                         //t
            }

            System.out.print("Enter contact email: ");                                               //1
            String contactEmail = input.next();                                                      //1

            System.out.print("Enter contact address: ");                                             //1
            String contactAddress = input.next();                                                    //1

            System.out.print("Enter contact birthday (YYYY/MM/DD): ");                               //1
            String contactBirthday = input.next();                                                   //1


            //check date format
            while (!contactBirthday.matches("^\\d{4}/\\d{2}/\\d{2}$")) {                        //r
                System.out.println("Invalid date of birth format. Use (YYYY/MM/DD).");               //r
                System.out.print("Enter contact birthday (YYYY/MM/DD): ");                           //r
                contactBirthday = input.next();                                                      //r
            }

            System.out.print("Enter contact notes: ");                                               //1
            input.nextLine(); //buffer cleaner                                                       //1
            String contactNotes = input.nextLine();                                                  //1
            input.nextLine(); //buffer cleaner                                                       //1


            Contact newContact = new Contact(contactName, contactPhone, contactEmail,
                    contactAddress, contactBirthday, contactNotes);                                  //1

            // Check if the contact already exists using in-order traversal
            if (!contactExists(contactName, contactPhone)) {                                         //n
                if (phoneBook.insert(contactName, newContact)) {                                     //log(n)
                    System.out.println("Contact added successfully.");                               //1
                } else {                                                                             //1
                    System.out.println("Error adding contact.");                                     //1
                }
            } else {                                                                                 //1
                System.out.println("A contact with the same name or phone number already exists.");  //1
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());                              //1
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
            System.out.println("Search by: ");                     //1
            System.out.println("1-Name");                          //1
            System.out.println("2-Phone Number");                  //1
            System.out.println("3-Email");                         //1
            System.out.println("4-Address");                       //1
            System.out.println("5-Birthday");                      //1
            System.out.print("Enter your choice: ");               //1
            int choice = input.nextInt();                          //1
            input.nextLine(); // buffer cleaner                    //1
            switch (choice) {                                      //1
                case 1:
                    searchByName();                                //log(n)
                    break;
                case 2:
                    searchByPhoneNumber();                         //n
                    break;
                case 3:
                    searchByEmail();                               //n
                    break;
                case 4:
                    searchByAddress();                             //n
                    break;
                case 5:
                    searchByBirthday();                            //n
                    break;
                default:
                    System.out.println("Invalid choice");          //1
            }
        } catch (InputMismatchException e){
            System.out.println("Input miss match");
        }
    }
    public void searchByFirstName() {
        System.out.print("Enter first name: ");                                              //1
        String firstName = input.nextLine();                                                 //1

        LinkedList<Contact> foundContacts = new LinkedList<>();                              //1
        inOrderSearchByFirstName(phoneBook.root, firstName, foundContacts);                  //n

        if (!foundContacts.empty()) {                                                        //1
            System.out.println("Contacts with first name " + firstName + ":");               //1
            foundContacts.findFirst();                                                       //1
            while (!foundContacts.last()) {                                                  //n
                foundContacts.retrieve().displayContactDetails();                            //n
                foundContacts.findNext();                                                    //n
            }
            foundContacts.retrieve().displayContactDetails();                                //1
        } else {
            System.out.println("No contacts found with the first name " + firstName + ".");  //1
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
            input.nextLine(); //buffer cleaner                                                                                    //1
            System.out.print("Enter event title: ");                                                                              //1
            String eventTitle = input.nextLine();                                                                                 //1
            System.out.print("Enter contact name: ");                                                                             //1
            String contactName = input.nextLine();                                                                                //1
            input.nextLine(); //buffer cleaner                                                                                    //1
            // Search for the contact by name
            Contact tmpContact = findContactByName(contactName);                                                                  //log(n)
            if (tmpContact != null){                                                                                              //1
                // Prompt for event details
                System.out.print("Enter event date Ex: YYYY/MM/DD : ");                                                           //1
                String date = input.next();                                                                                       //1

                //check date format
                while (!date.matches("^\\d{4}/\\d{2}/\\d{2}$")) {                                                            //t
                    System.out.println("Invalid date of birth format. Use (YYYY/MM/DD).");                                        //t
                    System.out.print("Enter contact birthday (YYYY/MM/DD): ");                                                    //t
                    date = input.next();                                                                                          //t
                }

                System.out.print("Enter event Start time Ex: HH:MM : ");                                                          //1
                String startTime = input.next();                                                                                  //1
                System.out.print("Enter event End time Ex: HH:MM : ");                                                            //1
                String endTime = input.next();                                                                                    //1

                // Parse date and time information and create a LocalDateTime object
                int year = Integer.parseInt(date.split("/")[0]);                                                             //1
                int month = Integer.parseInt(date.split("/")[1]);                                                            //1
                int day = Integer.parseInt(date.split("/")[2]);                                                              //1
                int startHour = Integer.parseInt(startTime.split(":")[0]);                                                   //1
                int startMinute = Integer.parseInt(startTime.split(":")[1]);                                                 //1
                int endHour = Integer.parseInt(endTime.split(":")[0]);                                                       //1
                int endMinute = Integer.parseInt(endTime.split(":")[1]);                                                     //1

                // represent the event's start and end times
                LocalDateTime startEvent = LocalDateTime.of(year, month, day, startHour, startMinute);                             //1
                LocalDateTime endEvent = LocalDateTime.of(year, month, day, endHour, endMinute);                                   //1
                System.out.print("Enter event location: ");//1
                String eventLocation = input.next();//1

                // Create a new Event object
                Event tmpEvent = new Appointment(eventTitle, startEvent, endEvent, eventLocation, "Appointment",tmpContact);  //1
                if (!isConflict(tmpEvent)) {                                                                                       //n
                    sortEvent(tmpEvent);                                                                                           //n
                    tmpContact.setEventsInContact(tmpEvent);                                                                       //1
                    System.out.println("Event has been added successfully");                                                       //1
                }
            } else {
                System.out.println("The contact does not exist");                                                                  //1
                return;                                                                                                            //1
            }
        } catch (InputMismatchException e) {
            System.out.println("Input Miss Mach");                                                                                 //1
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
        int choice;                                        //1
        System.out.println("Enter type :");                //1
        System.out.println("1-Event");                     //1
        System.out.println("2-Appointment");               //1
        System.out.println("Enter your choice :");         //1

        switch (choice = input.nextInt()) {                //1
            case 1:
                addEvents();                               //n
                break;
            case 2:
                addAppointment();                          //n
                break;
            default:
                System.out.println("Invalid choice");      //1
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
            System.out.println("Search by: ");                //1
            System.out.println("1-Title");                    //1
            System.out.println("2-Contact Name");             //1
            System.out.print("Enter your choice: ");          //1
            int choice = input.nextInt();                     //1
            switch (choice) {                                 //1
                case 1:
                    searchEventByTitle();                     //n^2
                    break;
                case 2:
                    searchEventByContactName();               //n^2
                    break;
                default:
                    System.out.println("Invalid choice");     //1
            }
        } catch (InputMismatchException e) {
            System.out.println("Input miss match");
        }
    }

    public void displayAllEvents(){
        if(!allEvents.empty()){                        //1
            allEvents.findFirst();                     //1
            while (!allEvents.last()){                 //n
                allEvents.retrieve().displayEvent();   //n
                allEvents.findNext();                  //n
            }
            allEvents.retrieve().displayEvent();       //1
            return;                                    //1
        }
        System.out.println("No events to display");    //1
    }



    public void deleteContact() {
        try {
            System.out.print("Enter contact name: ");                                           //1
            String contactName = input.nextLine();                                              //1
            contactName = contactName.toLowerCase();                                            //1

            if (phoneBook.findkey(contactName)) {                                               //log(n)
                Contact tmp = phoneBook.retrieve();                                             //1

                // Update events: delete appointments and remove the contact from events
                updateEventsForDeletedContact(tmp);                                             //n^2

                // Remove the contact from the phone book
                phoneBook.remove_key(contactName);                                              //n
                System.out.println("Contact and associated events updated successfully");       //1
            } else {
                System.out.println("Contact not found");                                        //1
            }
        } catch (InputMismatchException e) {
            System.out.println("Input mismatch");                                               //1
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
