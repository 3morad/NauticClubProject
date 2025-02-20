package entities;

import javafx.beans.property.*;

import java.sql.Date;

public class Payment {
    private final IntegerProperty id;
    private final IntegerProperty ticketId;
    private final DoubleProperty amount;
    private final StringProperty method;
    private final ObjectProperty<Date> date;

    public Payment(int id, int ticketId, double amount, String method, Date date) {
        this.id = new SimpleIntegerProperty(id);
        this.ticketId = new SimpleIntegerProperty(ticketId);
        this.amount = new SimpleDoubleProperty(amount);
        this.method = new SimpleStringProperty(method);
        this.date = new SimpleObjectProperty<>(date);
    }

    // ✅ JavaFX Property Getters for TableView
    public IntegerProperty idProperty() { return id; }
    public IntegerProperty ticketIdProperty() { return ticketId; }
    public DoubleProperty amountProperty() { return amount; }
    public StringProperty methodProperty() { return method; }
    public ObjectProperty<Date> dateProperty() { return date; }

    // ✅ Standard Getters
    public int getId() { return id.get(); }
    public int getTicketId() { return ticketId.get(); }
    public double getAmount() { return amount.get(); }
    public String getMethod() { return method.get(); }
    public Date getDate() { return date.get(); }

    // ✅ Setters (If Needed)
    public void setId(int id) { this.id.set(id); }
    public void setTicketId(int ticketId) { this.ticketId.set(ticketId); }
    public void setAmount(double amount) { this.amount.set(amount); }
    public void setMethod(String method) { this.method.set(method); }
    public void setDate(Date date) { this.date.set(date); }

    @Override
    public String toString() {
        return "Payment {ID: " + getId() + ", Ticket ID: " + getTicketId() +
                ", Amount: $" + getAmount() + ", Method: " + getMethod() +
                ", Date: " + getDate() + "}";
    }
}
