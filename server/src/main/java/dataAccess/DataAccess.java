package dataAccess;

import exceptions.DataAccessError;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

abstract public class DataAccess {
    private String dbName;
    private String connectionURL;
    private String username;
    private String password;
    public DataAccess() throws DataAccessError {
        readPropertiesFile();
        createDB();
        createTables();
    }

    private void readPropertiesFile() throws DataAccessError {
        Properties properties = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
            properties.load(input);
            connectionURL = "jdbc:mysql://" + properties.getProperty("db.host") + ":" + properties.getProperty("db.port");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
            dbName = properties.getProperty("db.name");
        }
        catch (IOException ioException) {
            throw new DataAccessError("Invalid properties file: " + ioException.getMessage(), 500);
        }
    }

    private void createDB() throws DataAccessError {
        updateDB(false, "CREATE DATABASE IF NOT EXISTS " + dbName);
    }

    private void createTables() throws DataAccessError {
        ArrayList<String> createStatements = new ArrayList<>(Arrays.asList(
                """
                CREATE TABLE IF NOT EXISTS userData(
                    username VARCHAR(256) NOT NULL PRIMARY KEY,
                    password VARCHAR(256) NOT NULL
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS gameData(
                    gameID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                    player1 VARCHAR(256),
                    player2 VARCHAR(256),
                    player3 VARCHAR(256),
                    game LONGTEXT
                );
                """
        ));
        
        for (String createStatement : createStatements) {
            updateDB(true, createStatement);
        }
    }

    private Connection getConnection(boolean dbInitialized) throws DataAccessError {
        try {
            Connection connection = DriverManager.getConnection(connectionURL, username, password);
            if (dbInitialized) connection.setCatalog(dbName);
            return connection;
        }
        catch (SQLException sqlException) {
            throw new DataAccessError("Cannot connect to database: " + sqlException.getMessage(), 500);
        }
    }

    private PreparedStatement getCompletedStatement(Connection connection, String statement, String... params) throws DataAccessError {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setString(i+1, params[i]);
            }
            return preparedStatement;
        }
        catch (SQLException sqlException) {
            throw new DataAccessError("Error building completed statement: " + sqlException.getMessage(), 500);
        }
    }

    protected ArrayList<String> queryDB(String queryStatement, String... params) throws DataAccessError {
        try (
            Connection connection = getConnection(true);
            PreparedStatement completedStatement = getCompletedStatement(connection, queryStatement, params);
            ResultSet resultSet = completedStatement.executeQuery()
        ) {
            ArrayList<String> answer = new ArrayList<>();
            while (resultSet.next()) {
                answer.add(resultSet.getString(1));
            }
            return answer;
        }
        catch (SQLException sqlException) {
            throw new DataAccessError("Error querying DB: " + sqlException.getMessage(), 500);
        }
    }

    protected void updateDB(boolean dbInitialized, String updatedStatement, String... params) throws DataAccessError {
        try (Connection connection = getConnection(dbInitialized)) {
            try (PreparedStatement completedStatement = getCompletedStatement(connection, updatedStatement, params)) {
                completedStatement.executeUpdate();
            }
        }
        catch (SQLException sqlException) {
            throw new DataAccessError("Error updating DB: " + sqlException.getMessage(), 500);
        }
    }
}
