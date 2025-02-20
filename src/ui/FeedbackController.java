package ui;

import dao.FeedbackDAO;
import entities.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class FeedbackController {
    @FXML private TableView<Feedback> feedbackTable;
    @FXML private TableColumn<Feedback, Integer> idColumn, userColumn, eventColumn, ratingColumn;
    @FXML private TableColumn<Feedback, String> commentColumn;
    @FXML private TextField userField, eventField, commentField, ratingField;

    private final FeedbackDAO feedbackDAO = new FeedbackDAO();
    private final ObservableList<Feedback> feedbackList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        userColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
        eventColumn.setCellValueFactory(cellData -> cellData.getValue().eventIdProperty().asObject());
        commentColumn.setCellValueFactory(cellData -> cellData.getValue().commentProperty());
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());

        loadFeedback();
    }

    private void loadFeedback() {
        feedbackList.clear();
        List<Feedback> feedbacks = feedbackDAO.getAllFeedback();
        feedbackList.addAll(feedbacks);
        feedbackTable.setItems(feedbackList);
    }

    @FXML
    private void addFeedback() {
        int userId = Integer.parseInt(userField.getText());
        int eventId = Integer.parseInt(eventField.getText());
        String comment = commentField.getText();
        int rating = Integer.parseInt(ratingField.getText());

        Feedback newFeedback = new Feedback(0, userId, eventId, comment, rating);
        feedbackDAO.addFeedback(newFeedback);
        loadFeedback();
    }

    @FXML
    private void updateFeedback() {
        Feedback selected = feedbackTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setComment(commentField.getText());
            selected.setRating(Integer.parseInt(ratingField.getText()));
            feedbackDAO.updateFeedback(selected.getId(), selected.getComment(), selected.getRating());
            loadFeedback();
        }
    }

    @FXML
    private void deleteFeedback() {
        Feedback selected = feedbackTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            feedbackDAO.deleteFeedback(selected.getId());
            loadFeedback();
        }
    }
}

