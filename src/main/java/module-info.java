module com.example.toyinterpreter {
    requires javafx.controls;
    requires javafx.fxml;
    requires pcollections;


    opens com.example.toyinterpreter to javafx.fxml;
    exports com.example.toyinterpreter;
}