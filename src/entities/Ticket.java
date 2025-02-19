package entities;

import java.util.Date;

public class Ticket {
    private int id;
    private int eventId;
    private int userId;
    private Date issueDate;

    public Ticket(int id, int eventId, int userId, Date issueDate) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.issueDate = issueDate;
    }

    // Getters
    public int getId() { return id; }
    public int getEventId() { return eventId; }
    public int getUserId() { return userId; }
    public Date getIssueDate() { return issueDate; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }

    @Override
    public String toString() {
        return "Ticket {ID: " + id + ", Event ID: " + eventId + ", User ID: " + userId + ", Issue Date: " + issueDate + "}";
    }
}
