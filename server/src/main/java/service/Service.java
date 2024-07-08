package service;

import dataAccess.GameDataAccess;
import dataAccess.UserDataAccess;
import exceptions.DataAccessError;
import exceptions.UserError;
import game.GameData;
import pieces.Piece;

public class Service {
    private final UserDataAccess userDataAccess;
    private final GameDataAccess gameDataAccess;
    public Service() throws DataAccessError {
        userDataAccess = new UserDataAccess();
        gameDataAccess = new GameDataAccess();
    }

    /**
     * Validate authToken, validate gameID, check if client is in game, check if it is client's turn
     */
    public String authenticate(String authToken, String gameID) throws DataAccessError {
        userDataAccess.validateAuthToken(authToken);
        String username = userDataAccess.getUsername(authToken);
        gameDataAccess.validateGameID(gameID);
        gameDataAccess.userInGame(gameID, username);
        gameDataAccess.verifyClientTurn(gameID, username);
        return username;
    }
    public void purchaseReqs(String username, String gameID, Piece pieceToBuy) throws DataAccessError {
        gameDataAccess.verifyGamePhase(gameID, GameData.phaseType.Purchase);
        gameDataAccess.verifyClientBalance(username, gameID, pieceToBuy);
    }
    public void placeReqs(String authToken, String gameID, Piece piece, int regionID) throws DataAccessError {
        String username = userDataAccess.getUsername(authToken);
        gameDataAccess.verifyGamePhase(gameID, GameData.phaseType.Place);
        gameDataAccess.verifyClientPiece(username, gameID, piece);
        gameDataAccess.verifyRegionControl(username, gameID, regionID);
    }
    public int makePurchase(String username, String gameID, Piece pieceToBuy) throws DataAccessError, UserError {
        return gameDataAccess.makePurchase(username, gameID, pieceToBuy);
    }
}
