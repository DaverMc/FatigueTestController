package dn.ftc.util.loging;

import dn.ftc.util.sql.Database;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class DatabaseHandler extends Handler {

    private final Database database;

    public DatabaseHandler(Database database) {
        this.database = database;
    }

    @Override
    public void publish(LogRecord record) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
