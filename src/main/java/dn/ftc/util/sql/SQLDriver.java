package dn.ftc.util.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQLDriver {

    Connection getConnection() throws SQLException;

}
