module library.management {
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;
    requires javafx.swing;
    requires com.jfoenix;
    requires com.google.gson;
    requires com.zaxxer.hikari;
    requires java.sql;
    requires jfxtras.gauge.linear;
    requires com.gluonhq.charm.glisten;
    requires jdk.compiler;
    requires org.slf4j;
    // Application
    opens library.management.ui.applications to javafx.graphics, javafx.fxml;
    exports library.management.ui.applications to javafx.graphics, javafx.fxml;
    //main
    opens library.management.ui to javafx.graphics, javafx.fxml;
    exports library.management.ui to javafx.graphics, javafx.fxml;
    exports library.management.ui.controllers to javafx.fxml, javafx.graphics;
    opens library.management.ui.controllers to javafx.fxml, javafx.graphics;
    exports library.management to javafx.fxml, javafx.graphics;
    opens library.management to javafx.fxml, javafx.graphics;
//  exports library.management.ui to javafx.fxml, javafx.graphics;
//  opens library.management.ui to javafx.fxml, javafx.graphics;
    opens library.management.data.entity to javafx.base, javafx.fxml;
    exports library.management.data.entity;
}