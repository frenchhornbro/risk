package dataAccess;

import exceptions.DataAccessError;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

abstract public class DataAccess {
    public DataAccess() {

    }

    public Connection connectToDB() throws DataAccessError {
        Properties properties = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
            properties.load(input);

            //Connect to server
            String connectionURL = "jdbc:mysql://" + properties.getProperty("db.host") + ":" + properties.getProperty("db.port");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            Connection connection =  DriverManager.getConnection(connectionURL, username, password);

            //Create database
            String dbCreateStatement = "CREATE DATABASE IF NOT EXISTS " + properties.getProperty("db.name");
            try(PreparedStatement preparedStatement = connection.prepareStatement(dbCreateStatement)) {
                preparedStatement.executeUpdate();
            }

            //Return connection
            return connection;
        }
        catch (IOException ioException) {
            throw new DataAccessError("Invalid properties file: " + ioException.getMessage(), 500);
        }
        catch (SQLException sqlException) {
            throw new DataAccessError("Cannot connect to database: " + sqlException.getMessage(), 500);
        }
    }
}
