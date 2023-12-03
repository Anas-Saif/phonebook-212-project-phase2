/****************************************
 CLASS: Event.java
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
// this Class is super class for Events and appointments classes
abstract class Event implements Comparable <String> {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private Contact contactInAppointment; // only one contact in an appointment
    private String type;

    public Event(String title,LocalDateTime startTime,LocalDateTime endTime, String location, String type) {
        this.title = title;
        this.startTime = startTime;
        this.endTime=endTime;
        this.location = location;
        this.type = type;
    }
    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {return endTime;}


    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    abstract void displayEvent();

    public int compareTo(String title) {
        return this.title.compareTo(title);
    }
}
