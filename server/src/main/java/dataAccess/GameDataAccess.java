package dataAccess;

import board.Region;
import com.google.gson.Gson;
import exceptions.DataAccessError;
import exceptions.UserError;
import game.GameData;
import game.PlayerData;
import pieces.Piece;

import java.util.HashMap;

public class GameDataAccess extends DataAccess {
    public GameDataAccess() throws DataAccessError {

    }

    public String createGame(String username) throws DataAccessError, UserError {
        GameData newGame = new GameData();
        //TODO: ^^^ Need to give each player a starting balance and some regions to control
        newGame.updatePlayer(new PlayerData(username), true);
        String newGameText = new Gson().toJson(newGame);
        super.updateDB(true, "INSERT INTO gameData (gameData) VALUES (?)", newGameText);
        return super.queryDB("SELECT gameID FROM gameData WHERE gameData=?", newGameText).getFirst();
    }
    public GameData joinPlayer(String username, String gameID) throws DataAccessError, UserError {
        return super.setPlayer(gameID, new PlayerData(username), true);
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

    public void verifyGamePhase(String gameID, GameData.phaseType phase) throws DataAccessError {
        GameData gameData = super.getGame(gameID);
        if (gameData.getPhase() != (phase)) throw new DataAccessError("Incorrect phase", 400);
    }

    public void verifyClientBalance(String username, String gameID, Piece pieceToBuy) throws DataAccessError {
        PlayerData playerData = super.getPlayer(gameID, username);
        if (playerData.getBalance() < pieceToBuy.getCost()) throw new DataAccessError("Balance is too low", 400);
    }

    public void verifyClientPiece(String username, String gameID, Piece piece) throws DataAccessError {
        PlayerData playerData = super.getPlayer(gameID, username);
        HashMap<Piece, Integer> purchasedPieces = playerData.getPurchasedPieces();
        Integer numPieces = purchasedPieces.get(piece);
        if (numPieces == null || numPieces <= 0) throw new DataAccessError("Piece not purchased", 400);
    }

    public void verifyRegionControl(String username, String gameID, int regionID) throws DataAccessError {
        HashMap<Integer, Region> board = super.getGame(gameID).getBoard().getBoard();
        Region region = board.get(regionID);
        if (!region.getRegController().equals(username)) throw new DataAccessError("You don't control that region", 400);
    }

    /**
     * Return updated balance
    */
    public GameData makePurchase(String username, String gameID, Piece pieceToBuy) throws DataAccessError, UserError {
        PlayerData playerData = super.getPlayer(gameID, username);

        HashMap<Piece, Integer> purchasedPieces = playerData.getPurchasedPieces();
        purchasedPieces.get(pieceToBuy);
        Integer numPieces = purchasedPieces.get(pieceToBuy);
        purchasedPieces.put(pieceToBuy, numPieces + 1);
        playerData.setPurchasedPieces(purchasedPieces);
        playerData.setBalance(playerData.getBalance() - pieceToBuy.getCost());

        return super.setPlayer(gameID, playerData, false);
    }

    public GameData placePiece(String username, String gameID, Piece piece, int regionID) throws DataAccessError, UserError {
        GameData gameData = super.getGame(gameID);
        HashMap<Integer, Region> board = gameData.getBoard().getBoard();
        Region region = board.get(regionID);
        HashMap<Piece, Integer> regionPieces = region.getPieces();
        regionPieces.put(piece, regionPieces.get(piece) + 1);

        PlayerData player = super.getPlayer(gameID, username);
        HashMap<Piece, Integer> purchasedPieces = player.getPurchasedPieces();
        purchasedPieces.put(piece, purchasedPieces.get(piece) - 1);
        player.setPurchasedPieces(purchasedPieces);
        gameData.updatePlayer(player, false);

        super.updateGame(gameID, gameData);
        return gameData;
    }
}
