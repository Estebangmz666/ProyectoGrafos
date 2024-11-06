module com.github.estebangmz666 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.github.estebangmz666 to javafx.fxml;
    opens com.github.estebangmz666.controller to javafx.fxml;
    exports com.github.estebangmz666;
    exports com.github.estebangmz666.controller to javafx.fxml;
}
