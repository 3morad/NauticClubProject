module com.example.nauticclubproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.nauticclubproject to javafx.fxml;
    exports com.example.nauticclubproject;
}