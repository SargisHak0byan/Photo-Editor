module com.photoeditor {
	requires java.desktop;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.base;
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    
    opens com.photoeditor to javafx.fxml;
    exports com.photoeditor;
}