package dataAccess;

import exceptions.DataAccessError;
import game.GameData;
import game.PlayerData;
import pieces.Infantry;
import pieces.Piece;
import pieces.Tank;

public class GameDataAccess extends DataAccess {
    public GameDataAccess() throws DataAccessError {

    }
    public void validateGameID(String gameID) throws DataAccessError {
        super.getGame(gameID);
    }
    public void userInGame(String gameID, String username) throws DataAccessError {
        getPlayer(gameID, username);
    }
    public void verifyClientTurn(String gameID, String username) throws DataAccessError {
        GameData gameData = super.getGame(gameID);
        if (!gameData.getPlayerTurn().equals(username)) throw new DataAccessError("Wait for your turn!", 400);
    }

    public void verifyGamePhase(String gameID, String phaseName) throws DataAccessError {
        GameData gameData = super.getGame(gameID);
        if (!gameData.getPhase().equals(phaseName)) throw new DataAccessError("Incorrect phase", 400);
    }

    public void verifyClientWallet(String username, String gameID, String pieceToBuy) throws DataAccessError {
        PlayerData playerData = super.getPlayer(gameID, username);
        Piece piece = switch (pieceToBuy) {
            case "Tank" -> new Tank();
            default -> new Infantry();
        };
        if (playerData.getBalance() < piece.getCost()) throw new DataAccessError("Balance is too low", 400);
    }
}
