package main;

import dao.PaymentDAO;
import dao.FeedbackDAO;
import dao.TicketDAO;
import database.DatabaseConnection;
import entities.Payment;
import entities.Feedback;
import entities.Ticket;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // ✅ 1. Test Database Connection
        System.out.println("🔄 Testing Database Connection...");
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Database connected successfully!");
        } else {
            System.out.println("❌ Database connection failed!");
            return; // Stop execution if no connection
        }

        // ✅ 2. Create DAO instances
        PaymentDAO paymentDAO = new PaymentDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        TicketDAO ticketDAO = new TicketDAO();

        System.out.println("\n-------------------------");
        System.out.println("🟢 TESTING CRUD OPERATIONS");
        System.out.println("-------------------------\n");

        // ✅ 3. Test CRUD for Payments
        System.out.println("💰 Testing Payment CRUD Operations...");
        Payment newPayment = new Payment(0, 1, 150.75, "Credit Card", new Date());
        paymentDAO.addPayment(newPayment);

        List<Payment> payments = paymentDAO.getAllPayments();
        for (Payment p : payments) {
            System.out.println(p);
        }

        paymentDAO.updatePayment(1, 180.00, "PayPal");
        paymentDAO.deletePayment(2); // Change ID as needed

        // ✅ 4. Test CRUD for Feedback
        System.out.println("\n💬 Testing Feedback CRUD Operations...");
        Feedback newFeedback = new Feedback(0, 2, 3, "Amazing event!", 5);
        feedbackDAO.addFeedback(newFeedback);

        List<Feedback> feedbackList = feedbackDAO.getAllFeedback();
        for (Feedback f : feedbackList) {
            System.out.println(f);
        }

        feedbackDAO.updateFeedback(1, "Really enjoyed it!", 4);
        feedbackDAO.deleteFeedback(2); // Change ID as needed

        // ✅ 5. Test CRUD for Tickets
        System.out.println("\n🎟️ Testing Ticket CRUD Operations...");
        Ticket newTicket = new Ticket(0, 4, 5, new Date());
        ticketDAO.addTicket(newTicket);

        List<Ticket> tickets = ticketDAO.getAllTickets();
        for (Ticket t : tickets) {
            System.out.println(t);
        }

        ticketDAO.updateTicket(1, new java.sql.Date(new Date().getTime()));  // ✅ Correct type (java.sql.Date)
        ticketDAO.deleteTicket(2); // Change ID as needed

        System.out.println("\n✅ ALL TESTS COMPLETED SUCCESSFULLY!");
    }
}
