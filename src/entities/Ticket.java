package entities;

import javafx.beans.property.*;

import java.sql.Date;

public class Ticket {
    private final IntegerProperty id;
    private final IntegerProperty eventId;
    private final IntegerProperty userId;
    private final ObjectProperty<Date> issueDate;

    public Ticket(int id, int eventId, int userId, Date issueDate) {
        this.id = new SimpleIntegerProperty(id);
        this.eventId = new SimpleIntegerProperty(eventId);
        this.userId = new SimpleIntegerProperty(userId);
        this.issueDate = new SimpleObjectProperty<>(issueDate);
    }

    // ✅ JavaFX Property Getters for TableView
    public IntegerProperty idProperty() { return id; }
    public IntegerProperty eventIdProperty() { return eventId; }
    public IntegerProperty userIdProperty() { return userId; }
    public ObjectProperty<Date> issueDateProperty() { return issueDate; }

    // ✅ Standard Getters
    public int getId() { return id.get(); }
    public int getEventId() { return eventId.get(); }
    public int getUserId() { return userId.get(); }
    public Date getIssueDate() { return issueDate.get(); }

    // ✅ Setters (If Needed)
    public void setId(int id) { this.id.set(id); }
    public void setEventId(int eventId) { this.eventId.set(eventId); }
    public void setUserId(int userId) { this.userId.set(userId); }
    public void setIssueDate(Date issueDate) { this.issueDate.set(issueDate); }

    @Override
    public String toString() {
        return "Ticket {ID: " + getId() + ", Event ID: " + getEventId() +
                ", User ID: " + getUserId() + ", Issue Date: " + getIssueDate() + "}";
    }
}
