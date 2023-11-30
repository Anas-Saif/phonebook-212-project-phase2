import java.time.LocalDateTime;
public class Events extends Event {
    private LinkedList<Contact> contactsInEvent; // this list is for the contacts that are invited to the event

    public Events(String title, LocalDateTime startTime, LocalDateTime endTime, String location, String type, LinkedList<Contact> contactsInEvent) {
        super(title, startTime, endTime, location, type);
        this.contactsInEvent = contactsInEvent;
    }

    public void displayEvent(){
        System.out.println("title: "+super.getTitle());
        System.out.println("starts at: "+ super.getStartTime());
        System.out.println("ends at: "+ super.getEndTime());
        System.out.println("location: "+super.getLocation());
        System.out.println("with: ");
        if (contactsInEvent.empty()){
            System.out.println("No contacts in this event");
        }
        else{
            contactsInEvent.findFirst();
            while(!contactsInEvent.last()){
                System.out.println(contactsInEvent.retrieve().getContactName());
                contactsInEvent.findNext();
            }
            System.out.println(contactsInEvent.retrieve().getContactName());
        }
    }

    public LinkedList<Contact> getContactsInEvent(){
        return contactsInEvent;
    }
}