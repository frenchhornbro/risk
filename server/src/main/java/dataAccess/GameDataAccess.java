package dataAccess;

import com.google.gson.Gson;
import exceptions.DataAccessError;
import game.GameData;

import java.util.ArrayList;

public class GameDataAccess extends DataAccess {
    public GameDataAccess() throws DataAccessError {

    }
    public void validateGameID(String gameID) throws DataAccessError {
        ArrayList<String> dbResponse = super.queryDB("SELECT gameID FROM gameData WHERE gameID=?", gameID);
        if (dbResponse.isEmpty()) throw new DataAccessError("Invalid game ID", 400);
    }
    public void userInGame(String gameID, String username) throws DataAccessError {
        ArrayList<String> dbUsernames = super.queryDB("SELECT player1, player2, player3 FROM gameData WHERE gameID=?", gameID);
        for (String dbUsername : dbUsernames) {
            if (dbUsername.equals(username)) return;
        }
        throw new DataAccessError("User not in game", 400);
    }
    public void verifyClientTurn(String gameID, String username) throws DataAccessError {
        ArrayList<String> dbResponse = super.queryDB("SELECT game FROM gameData WHERE gameID=?", gameID);
        String gameText = dbResponse.getFirst();
        GameData gameData = new Gson().fromJson(gameText, GameData.class);
        if (!gameData.getPlayerTurn().equals(username)) throw new DataAccessError("Wait for your turn!", 400);
    }
}
