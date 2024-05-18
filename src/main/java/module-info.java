module dn.ftc.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires eu.hansolo.tilesfx;
    requires java.logging;
    requires com.pi4j;
    requires java.desktop;
    requires org.slf4j;
    requires java.sql;

    exports dn.ftc.javafx;

    opens dn.ftc.javafx to javafx.fxml;
}