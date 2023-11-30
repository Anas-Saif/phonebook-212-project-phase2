public class Contact implements Comparable <String>{

    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String contactAddress;
    private String contactBirthday;
    private String contactNotes;
    private LinkedList<Event> eventsInContact;
    public Contact(String contactName, String contactPhone, String contactEmail, String contactAddress, String contactBirthday, String contactNotes) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.contactAddress = contactAddress;
        this.contactBirthday = contactBirthday;
        this.contactNotes = contactNotes;
        eventsInContact =new LinkedList<>();
    }



    public Contact(Contact c){
        this.contactName=c.contactName;
        this.contactPhone=c.contactPhone;
        this.contactEmail=c.contactEmail;
        this.contactAddress=c.contactAddress;
        this.contactBirthday=c.contactBirthday;
        this.contactNotes=c.contactNotes;
    }

    //default
    public Contact(){
    }

    @Override
    public int compareTo(String cn) {
        return contactName.compareTo(cn);
    }

    public void displayContactDetails(){
        System.out.println("Name: "+contactName);
        System.out.println("Phone Number: "+contactPhone);
        System.out.println("Email Address: "+contactEmail);
        System.out.println("Address: "+contactAddress);
        System.out.println("Birthday: "+contactBirthday);
        System.out.println("Notes: "+contactNotes);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", contactBirthday='" + contactBirthday + '\'' +
                ", contactNotes='" + contactNotes + '\'' +
                '}';
    }

    public LinkedList<Event> getEvents(){
        return eventsInContact;
    }


    public void setEventsInContact(Event s){
        if (!eventsInContact.empty()){

            eventsInContact.findFirst();
            while (!eventsInContact.last()){
                if (s.getTitle().toLowerCase().compareTo(eventsInContact.retrieve().getTitle().toLowerCase()) <= -1){
                    Event tmp = eventsInContact.retrieve();
                    eventsInContact.update(s);
                    eventsInContact.insert(tmp);
                    return;
                }
                eventsInContact.findNext();
            }
            if (s.getTitle().toLowerCase().compareTo(eventsInContact.retrieve().getTitle().toLowerCase()) <= -1){
                Event tmp = eventsInContact.retrieve();
                eventsInContact.update(s);
                eventsInContact.insert(tmp);
                return;
            }

        }
        eventsInContact.insert(s);
        return;
    }
    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public String getContactBirthday() {
        return contactBirthday;
    }

    public String getContactNotes() {
        return contactNotes;
    }

}
