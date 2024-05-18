package dn.ftc.util.loging;

import dn.ftc.util.sql.Database;
import javafx.application.Platform;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;
import java.util.logging.*;

public class Logger {

    private static final java.util.logging.Logger logger = LogManager.getLogManager().getLogger("");
    private static Level level = Level.ALL;

    public static void init() {
        //Standard Handler setzen
        resetHandler();
        setLevel(level);
        Logger.setConsoleHandler(level);
    }

    public static void startUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> error(e));
    }

    public static void resetHandler() {
        //Handler aktualisieren
        for(Handler handler : logger.getHandlers()) logger.removeHandler(handler);
    }

    private static <H extends Handler> H getHandler(Class<H> handlerClass) {
        for(Handler handler : logger.getHandlers()) {
            if(handlerClass.equals(handler.getClass())) return (H) handler;
        }
        return null;
    }

    public static void setConsoleHandler(Level level) {
        ConsoleHandler consoleHandler = getHandler(ConsoleHandler.class);
        if(consoleHandler == null) {
            consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new LogOutputFormatter());
            logger.addHandler(consoleHandler);
            Logger.info("Starting Console Logging...");
        }
        consoleHandler.setLevel(level.toJavaLevel());
        Logger.error("Console Log Level set to " + level.name());
    }

    public static void addFileHandler(Level level) {
        //Log speicherung
        FileHandler fileHandler;
        try {
            fileHandler = new LogFileHandler();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        fileHandler.setFormatter(new LogOutputFormatter());
        fileHandler.setLevel(level.toJavaLevel());
        logger.addHandler(fileHandler);
    }

    public static void addDatabaseHandler(Database database, Level level) {
        //Datenbank speicherung
        DatabaseHandler databaseHandler = new DatabaseHandler(database);
        databaseHandler.setLevel(level.toJavaLevel());
        logger.addHandler(databaseHandler);
    }

    public static void setLevel(Level level) {
        Logger.level = level;
        logger.setLevel(level.toJavaLevel());
        for(Handler handler : logger.getHandlers()) handler.setLevel(level.toJavaLevel());
    }

    public static void log(Level level, String msg) {
        logger.log(level.toJavaLevel(), msg, level);
    }

    public static void info(String msg) {
        log(Level.INFO, msg);
    }

    public static void config(String msg) {
        log(Level.CONFIG, msg);
    }

    public static void debug(String msg) {
        log(Level.DEBUG, msg);
    }

    public static void detail(String msg) {
        log(Level.DETAIL, msg);
    }

    public static void data(String msg) {
        log(Level.DATA, msg);
    }

    public static void warn(String msg) {
        log(Level.WARNING, msg);
    }

    public static void error(String msg) {
        log(Level.ERROR, msg);
    }

    public static void error(Throwable exception) {
        logger.log(Level.ERROR.toJavaLevel(), "An exception occurred!", exception);
    }

}
