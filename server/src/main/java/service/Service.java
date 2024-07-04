package service;

import dataAccess.GameDataAccess;
import dataAccess.UserDataAccess;
import exceptions.DataAccessError;

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
    public void purchaseReqs(String username, String gameID, String pieceToBuy) throws DataAccessError {
        gameDataAccess.verifyGamePhase(gameID, "purchase");
        gameDataAccess.verifyClientWallet(username, gameID, pieceToBuy);
    }
    public String makePurchase(String pieceToBuy) {
        //TODO: Throw an error if something goes wrong here
        return "placeholderUpdatedBalance";
    }
}
