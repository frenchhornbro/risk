package dataAccess;

import com.google.gson.Gson;
import exceptions.DataAccessError;
import exceptions.UserError;
import game.GameData;
import game.PlayerData;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
                    password VARCHAR(256) NOT NULL,
                    email VARCHAR(254) NOT NULL,
                    authToken VARCHAR(36) NOT NULL
                );
                """,
                """
                CREATE TABLE IF NOT EXISTS gameData(
                    gameID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                    gameData LONGTEXT
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

    private PreparedStatement getCompletedStatement(Connection connection, String statement, Object... params) throws DataAccessError {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setString(i+1, params[i].toString());
            }
            return preparedStatement;
        }
        catch (SQLException sqlException) {
            throw new DataAccessError("Error building completed statement: " + sqlException.getMessage(), 500);
        }
    }

    protected ArrayList<String> queryDB(String queryStatement, Object... params) throws DataAccessError {
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

    protected void updateDB(boolean dbInitialized, String updatedStatement, Object... params) throws DataAccessError {
        if (this.getClass() != DataAccess.class) dbInitialized = true;
        try (Connection connection = getConnection(dbInitialized)) {
            try (PreparedStatement completedStatement = getCompletedStatement(connection, updatedStatement, params)) {
                completedStatement.executeUpdate();
            }
        }
        catch (SQLException sqlException) {
            throw new DataAccessError("Error updating DB: " + sqlException.getMessage(), 500);
        }
    }

    protected GameData getGame(String gameID) throws DataAccessError {
        ArrayList<String> gameData = queryDB("SELECT gameData FROM gameData WHERE gameID=?", gameID);
        if (gameData.isEmpty()) throw new DataAccessError("Invalid game ID", 400);
        return new Gson().fromJson(gameData.getFirst(), GameData.class);
    }

    protected void setGame(String gameID, GameData gameData) throws DataAccessError {
        updateDB(true, "INSERT INTO GameData (gameID, gameData) VALUES (?, ?)", gameID, gameData);
    }

    protected PlayerData getPlayer(String gameID, String username) throws DataAccessError {
        GameData gameData = getGame(gameID);
        HashMap<String, PlayerData> players = gameData.getPlayers();
        PlayerData player = players.get(username);
        if (player == null) throw new DataAccessError("User not in game", 400);
        return player;
    }

    protected void setPlayer(String gameID, PlayerData playerData) throws DataAccessError, UserError {
        GameData gameData = getGame(gameID);
        gameData.updatePlayer(playerData, false);
        updateDB(true, "INSERT INTO GameData (gameID, gameData) VALUES (?, ?)", gameID, gameData);
    }
}
