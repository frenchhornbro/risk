package dataAccess;

import exceptions.DataAccessError;
import game.GameData;

import java.util.ArrayList;

public class GameDataAccess extends DataAccess {
    public GameDataAccess() throws DataAccessError {

    }

    public String createGame(String newGameText) throws DataAccessError {
        super.updateDB(true, "INSERT INTO gameData (gameData) VALUES (?)", newGameText);
        return super.queryDB("SELECT gameID FROM gameData WHERE gameData=?", newGameText).getFirst();
    }

    public void setGame(String gameID, GameData gameData) throws DataAccessError {
        super.updateDB(true, "UPDATE GameData SET gameData=? WHERE gameID=?", gameData, gameID);
    }

    public ArrayList<String> getGame(String gameID) throws DataAccessError {
        return queryDB("SELECT gameData FROM gameData WHERE gameID=?", gameID);
    }
}
