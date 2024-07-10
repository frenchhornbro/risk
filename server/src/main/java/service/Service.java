package service;

import dataAccess.GameDataAccess;
import dataAccess.UserDataAccess;
import exceptions.CredentialError;
import exceptions.DataAccessError;
import exceptions.UserError;
import game.GameData;
import pieces.Piece;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {
    private final UserDataAccess userDataAccess;
    private final GameDataAccess gameDataAccess;

    public Service() throws DataAccessError {
        userDataAccess = new UserDataAccess();
        gameDataAccess = new GameDataAccess();
    }

    /**
     * Check if the email and password follow regex, the password and confirmation match, and that the username isn't taken
     */
    public void validateCredentials(String email, String username, String password, String confirm) throws CredentialError, DataAccessError {
        if (email == null || username == null || password == null || confirm == null) throw new CredentialError("Credentials can't be null", 400);

        Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher emailMatcher = emailRegex.matcher(email);
        if (!emailMatcher.matches()) throw new CredentialError("Invalid email", 400);

        Pattern passwordRegex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher passwordMatcher = passwordRegex.matcher(password);
        if (!passwordMatcher.matches()) throw new CredentialError("Invalid password", 400);
        if (!password.equals(confirm)) throw new CredentialError("Passwords don't match", 400);
        userDataAccess.usernameNotTaken(username);
    }

    /**
     * @return authToken
     */
    public String storeCredentials(String email, String username, String password) throws DataAccessError {
        return userDataAccess.storeCredentials(email, username, password);
    }

    /**
     * @return username
     */
    public String authenticateUser(String authToken) throws DataAccessError {
        return userDataAccess.validateAuthToken(authToken).getFirst();
    }

    public String createGame(String username) throws DataAccessError, UserError {
        return gameDataAccess.createGame(username);
    }

    public GameData joinPlayer(String username, GameData gameData, String gameID) throws DataAccessError, UserError {
        return gameDataAccess.joinPlayer(username, gameData, gameID);
    }

    /**
     * Validate authToken, validate gameID, check if client is in game, check if it is client's turn
     * @return username
     */
    public Object[] authenticateGame(String authToken, String gameID, boolean takingTurn) throws DataAccessError {
        ArrayList<String> userData = userDataAccess.validateAuthToken(authToken);
        String username = userData.getFirst();
        GameData gameData = gameDataAccess.validateGameID(gameID);
        if (takingTurn) {
            gameDataAccess.userInGame(gameData, username);
            gameDataAccess.verifyClientTurn(gameData, username);
        }
        return new Object[]{gameData, username};
    }

    public void purchaseReqs(String username, GameData gameData, Piece pieceToBuy) throws DataAccessError {
        gameDataAccess.verifyGamePhase(gameData, GameData.phaseType.Purchase);
        gameDataAccess.verifyClientBalance(username, gameData, pieceToBuy);
    }

    public void placeReqs(String username, GameData gameData, Piece piece, int regionID) throws DataAccessError {
        gameDataAccess.verifyGamePhase(gameData, GameData.phaseType.Place);
        gameDataAccess.verifyClientPiece(username, gameData, piece);
        gameDataAccess.verifyRegionControl(username, gameData, regionID);
    }

    public GameData makePurchase(String username, GameData gameData, String gameID, Piece pieceToBuy) throws DataAccessError, UserError {
        return gameDataAccess.makePurchase(username, gameData, gameID, pieceToBuy);
    }

    public GameData placePiece(String username, GameData gameData, String gameID, Piece piece, int regionID) throws DataAccessError, UserError {
        return gameDataAccess.placePiece(username, gameData, gameID, piece, regionID);
    }
}
