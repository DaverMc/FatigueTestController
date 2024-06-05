package dn.ftc;

import dn.ftc.hardware.ForceSensor;
import dn.ftc.hardware.Piston;
import dn.ftc.hardware.RaspberryPi;
import dn.ftc.javafx.FatigueTestController;
import dn.ftc.util.Configuration;
import dn.ftc.util.loging.Level;
import dn.ftc.util.loging.Logger;
import dn.ftc.util.sql.Database;
import dn.ftc.util.sql.SQLiteDriver;
import javafx.application.Application;
import javafx.application.Platform;

public class Main {

    public static void main(String[] args) {
        //Logging
        Logger.init();
        Logger.startUncaughtExceptionHandler();
        Logger.setConsoleHandler(Level.INFO);
        Logger.addFileHandler(Level.INFO);

        //Configuration
        Configuration config = new Configuration();
        config.loadFromResource("config.properties");

        //Database
        Database database = new Database(new SQLiteDriver(config.getString("db.filepath")), new Database.Config(config));
        Logger.addDatabaseHandler(database, Level.INFO);

        //Hardware
        RaspberryPi pi = RaspberryPi.init(config);
        Piston piston = new Piston(pi.getPi4j(), pi.getADDABoard(), new Piston.Config(config));
        ForceSensor forceSensor = new ForceSensor(pi.getPi4j(), new ForceSensor.Config(config));
        pi.addHardware("piston", piston);
        pi.addHardware("forceSensor", forceSensor);

        //JavaFx
        Application.launch(FatigueTestController.class, args);
        Platform.runLater(Logger::startUncaughtExceptionHandler);
    }

}
