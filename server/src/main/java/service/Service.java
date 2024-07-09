package service;

import dataAccess.GameDataAccess;
import dataAccess.UserDataAccess;
import exceptions.CredentialError;
import exceptions.DataAccessError;
import exceptions.UserError;
import game.GameData;
import pieces.Piece;

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
    public void placeReqs(String username, String gameID, Piece piece, int regionID) throws DataAccessError {
        gameDataAccess.verifyGamePhase(gameID, GameData.phaseType.Place);
        gameDataAccess.verifyClientPiece(username, gameID, piece);
        gameDataAccess.verifyRegionControl(username, gameID, regionID);
    }
    public int makePurchase(String username, String gameID, Piece pieceToBuy) throws DataAccessError, UserError {
        return gameDataAccess.makePurchase(username, gameID, pieceToBuy);
    }
    public GameData placePiece(String username, String gameID, Piece piece, int regionID) throws DataAccessError, UserError {
        return gameDataAccess.placePiece(username, gameID, piece, regionID);
    }
}
