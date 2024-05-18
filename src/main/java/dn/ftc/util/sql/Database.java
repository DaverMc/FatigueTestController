package dn.ftc.util.sql;

import dn.ftc.util.Configuration;
import dn.ftc.util.loging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private final SQLDriver driver;

    public Database(SQLDriver driver, Config config) {
        this.driver = driver;
    }

    public void execute(String statement) {
        try {
            Connection connection = driver.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(statement);
            pStatement.execute();
        } catch (SQLException exception) {
            Logger.error(exception);
        }
    }

    public record Config(String url, String username, String password) {

        public Config(Configuration configuration) {
            this(
                    configuration.getString("url"),
                    configuration.getString("username"),
                    configuration.getString("password")
            );
        }

    }
}
