package dataAccess;

import exceptions.DataAccessError;
import exceptions.UserError;
import game.GameData;
import game.PlayerData;
import pieces.Infantry;
import pieces.Piece;
import pieces.Tank;

import java.util.ArrayList;

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
        if (!gameData.getPhase().toString().equals(phaseName)) throw new DataAccessError("Incorrect phase", 400);
    }

    public void verifyClientBalance(String username, String gameID, String pieceToBuy) throws DataAccessError {
        PlayerData playerData = super.getPlayer(gameID, username);
        Piece piece = stringToPiece(pieceToBuy);
        if (playerData.getBalance() < piece.getCost()) throw new DataAccessError("Balance is too low", 400);
    }

    /**
     * Return updated balance
    */
    public int makePurchase(String username, String gameID, String pieceToBuy) throws DataAccessError, UserError {
        PlayerData playerData = super.getPlayer(gameID, username);
        Piece piece = stringToPiece(pieceToBuy);

        ArrayList<Piece> purchasedPieces = playerData.getPurchasedPieces();
        purchasedPieces.add(piece);
        playerData.setPurchasedPieces(purchasedPieces);
        playerData.setBalance(playerData.getBalance() - piece.getCost());

        super.setPlayer(gameID, playerData);
        return playerData.getBalance();
    }

    private Piece stringToPiece(String pieceName) {
        return switch (pieceName) {
            case "Tank" -> new Tank();
            default -> new Infantry();
        };
    }
}
