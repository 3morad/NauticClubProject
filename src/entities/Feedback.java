package entities;

public class Feedback {
    private int id;
    private int userId;
    private int eventId;
    private String comment;
    private int rating;

    public Feedback(int id, int userId, int eventId, String comment, int rating) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.comment = comment;
        this.rating = rating;
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getEventId() { return eventId; }
    public String getComment() { return comment; }
    public int getRating() { return rating; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public void setComment(String comment) { this.comment = comment; }
    public void setRating(int rating) { this.rating = rating; }

    @Override
    public String toString() {
        return "Feedback {ID: " + id + ", User ID: " + userId + ", Event ID: " + eventId + ", Comment: " + comment + ", Rating: " + rating + "}";
    }
}
