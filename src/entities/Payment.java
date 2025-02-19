package entities;

import java.util.Date;

public class Payment {
    private int id;
    private int ticketId;
    private double amount;
    private String method;
    private Date paymentDate;

    public Payment(int id, int ticketId, double amount, String method, Date paymentDate) {
        this.id = id;
        this.ticketId = ticketId;
        this.amount = amount;
        this.method = method;
        this.paymentDate = paymentDate;
    }

    // Getters
    public int getId() { return id; }
    public int getTicketId() { return ticketId; }
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
    public Date getPaymentDate() { return paymentDate; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setMethod(String method) { this.method = method; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    @Override
    public String toString() {
        return "Payment {ID: " + id + ", Ticket ID: " + ticketId + ", Amount: $" + amount + ", Method: " + method + ", Date: " + paymentDate + "}";
    }
}
