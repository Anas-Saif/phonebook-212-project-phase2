import java.time.LocalDateTime;
public class Appointment extends Event {
    private Contact contactInAppointment; // only one contact in an appointment

    public Appointment(String title, LocalDateTime startTime, LocalDateTime endTime, String location, String type, Contact contactInAppointment) {
        super(title, startTime, endTime, location, type);
        this.contactInAppointment = contactInAppointment;
    }

    public void displayEvent(){
        System.out.println("title: "+super.getTitle());
        System.out.println("starts at: "+ super.getStartTime());
        System.out.println("ends at: "+ super.getEndTime());
        System.out.println("location: "+super.getLocation());
        System.out.println("with: "+ contactInAppointment.getContactName());
    }
    Contact getContactInAppointment(){
        return contactInAppointment;
    }
}
