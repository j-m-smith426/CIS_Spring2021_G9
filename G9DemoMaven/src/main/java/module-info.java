module G9DemoMaven.G9DemoMaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
	requires com.microsoft.sqlserver.jdbc;
	requires java.desktop;
	requires javafx.graphics;

    opens G9DemoMaven.G9DemoMaven to javafx.fxml;
    exports G9DemoMaven.G9DemoMaven;
}