package dn.ftc.util.sql;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteDriver implements SQLDriver {

    private final String path;

    public SQLiteDriver(String path) {
        this.path = path;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null; //TODO
    }
}
