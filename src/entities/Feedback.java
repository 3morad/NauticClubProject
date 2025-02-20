package entities;

import javafx.beans.property.*;

public class Feedback {
    private final IntegerProperty id;
    private final IntegerProperty userId;
    private final IntegerProperty eventId;
    private final StringProperty comment;
    private final IntegerProperty rating;

    public Feedback(int id, int userId, int eventId, String comment, int rating) {
        this.id = new SimpleIntegerProperty(id);
        this.userId = new SimpleIntegerProperty(userId);
        this.eventId = new SimpleIntegerProperty(eventId);
        this.comment = new SimpleStringProperty(comment);
        this.rating = new SimpleIntegerProperty(rating);
    }

    // ✅ JavaFX Property Getters (Required for TableView)
    public IntegerProperty idProperty() { return id; }
    public IntegerProperty userIdProperty() { return userId; }
    public IntegerProperty eventIdProperty() { return eventId; }
    public StringProperty commentProperty() { return comment; }
    public IntegerProperty ratingProperty() { return rating; }

    // ✅ Standard Getters (For Other Logic)
    public int getId() { return id.get(); }
    public int getUserId() { return userId.get(); }
    public int getEventId() { return eventId.get(); }
    public String getComment() { return comment.get(); }
    public int getRating() { return rating.get(); }

    // ✅ Setters (If Needed)
    public void setId(int id) { this.id.set(id); }
    public void setUserId(int userId) { this.userId.set(userId); }
    public void setEventId(int eventId) { this.eventId.set(eventId); }
    public void setComment(String comment) { this.comment.set(comment); }
    public void setRating(int rating) { this.rating.set(rating); }

    @Override
    public String toString() {
        return "Feedback {ID: " + getId() + ", User ID: " + getUserId() +
                ", Event ID: " + getEventId() + ", Comment: " + getComment() +
                ", Rating: " + getRating() + "}";
    }
}
