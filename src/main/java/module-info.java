module com.app.dictionaryapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.jfoenix;
    requires AnimateFX;
    requires java.net.http;
    requires javafx.media;
    requires java.desktop;
    requires org.json;
    requires redis.clients.jedis;
    requires javafx.web;
    requires org.jsoup;

    exports com.app.dictionaryapp.PresentationLayer;
    opens com.app.dictionaryapp.PresentationLayer to javafx.fxml;
    exports com.app.dictionaryapp.BusinessLogicLayer;
    opens com.app.dictionaryapp.BusinessLogicLayer to javafx.fxml;
  exports com.app.dictionaryapp.DataAccessLayer;
  opens com.app.dictionaryapp.DataAccessLayer to javafx.fxml;
}