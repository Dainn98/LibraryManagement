module library.management{
    requires javafx.fxml;
    requires javafx.web;
    requires transitive javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;
    requires java.logging;
    requires javafx.controls;
    requires javafx.swing;
    requires com.jfoenix;


  //Login
    opens library.management.ui.login to javafx.graphics, javafx.fxml;
    exports library.management.ui.login to javafx.graphics, javafx.fxml;
    //REGISTER
    opens library.management.ui.register to javafx.graphics, javafx.fxml;
    exports library.management.ui.register to javafx.graphics, javafx.fxml;
    //main
    opens library.management.ui.main to javafx.graphics, javafx.fxml;
    exports library.management.ui.main to javafx.graphics, javafx.fxml;


}