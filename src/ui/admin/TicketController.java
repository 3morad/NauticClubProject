package ui.admin;

import dao.TicketDAO;
import entities.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.List;

public class TicketController {
    @FXML private TableView<Ticket> ticketTable;
    @FXML private TableColumn<Ticket, Integer> idColumn, eventColumn, userColumn;
    @FXML private TextField eventField, userField;

    private TicketDAO ticketDAO = new TicketDAO();
    private ObservableList<Ticket> ticketList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        eventColumn.setCellValueFactory(cellData -> cellData.getValue().eventIdProperty().asObject());
        userColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());

        loadTickets();
    }

    private void loadTickets() {
        ticketList.clear();
        List<Ticket> tickets = ticketDAO.getAllTickets();
        ticketList.addAll(tickets);
        ticketTable.setItems(ticketList);
    }

    @FXML
    private void addTicket() {
        int eventId = Integer.parseInt(eventField.getText());
        int userId = Integer.parseInt(userField.getText());
        Ticket newTicket = new Ticket(0, eventId, userId, new Date(System.currentTimeMillis()));
        ticketDAO.addTicket(newTicket);
        loadTickets();
    }

    @FXML
    private void updateTicket() {
        Ticket selected = ticketTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            int eventId = Integer.parseInt(eventField.getText());
            int userId = Integer.parseInt(userField.getText());
            ticketDAO.updateTicket(selected.getId(), new Date(System.currentTimeMillis()));
            loadTickets();
        }
    }

    @FXML
    private void deleteTicket() {
        Ticket selected = ticketTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ticketDAO.deleteTicket(selected.getId());
            loadTickets();
        }
    }
}
