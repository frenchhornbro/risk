package service;

import dataAccess.GameDataAccess;
import dataAccess.UserDataAccess;
import exceptions.DataAccessError;
import exceptions.ServiceError;

public class Service {
    private final UserDataAccess userDataAccess;
    private final GameDataAccess gameDataAccess;
    public Service() {
        userDataAccess = new UserDataAccess();
        gameDataAccess = new GameDataAccess();
    }

    /**
     * Validate authToken, validate gameID, check if client is in game, check if it is client's turn
     */
    public void authenticate(String authToken, String gameID) throws ServiceError {
        try {
            userDataAccess.validateAuthToken(authToken);
            String username = userDataAccess.getUsername(authToken);
            gameDataAccess.validateGameID(gameID);
            gameDataAccess.userInGame(gameID, username);
            gameDataAccess.isClientsTurn(gameID, username);
        }
        catch (DataAccessError dataAccessError) {
            throw new ServiceError(dataAccessError.getMessage(), dataAccessError.getErrorCode());
        }
    }
    public void purchaseReqs(String pieceToBuy) throws ServiceError {
        //TODO: Throw an error if something goes wrong here
    }
    public String makePurchase(String pieceToBuy) {
        //TODO: Throw an error if something goes wrong here
    }
}
