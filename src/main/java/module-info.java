module com.example.demomod6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demomod6 to javafx.fxml;
    exports com.example.demomod6;
}