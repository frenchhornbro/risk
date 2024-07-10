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

    public GameData joinPlayer(String username, GameData gameData, String gameID) throws DataAccessError, UserError {
        return super.setPlayer(gameData, gameID, new PlayerData(username), true);
    }
    public GameData validateGameID(String gameID) throws DataAccessError {
        return super.getGame(gameID);
    }
    public void userInGame(GameData gameData, String username) throws DataAccessError {
        PlayerData player = gameData.getPlayer(username);
        if (player == null) throw new DataAccessError("User not in game", 400);
    }
    public void verifyClientTurn(GameData gameData, String username) throws DataAccessError {
        if (!gameData.getPlayerTurn().equals(username)) throw new DataAccessError("Wait for your turn!", 400);
    }

    public void verifyGamePhase(GameData gameData, GameData.phaseType phase) throws DataAccessError {
        if (gameData.getPhase() != (phase)) throw new DataAccessError("Incorrect phase", 400);
    }

    public void verifyClientBalance(String username, GameData gameData, Piece pieceToBuy) throws DataAccessError {
        PlayerData playerData = gameData.getPlayer(username);
        if (playerData.getBalance() < pieceToBuy.getCost()) throw new DataAccessError("Balance is too low", 400);
    }

    public void verifyClientPiece(String username, GameData gameData, Piece piece) throws DataAccessError {
        PlayerData playerData = gameData.getPlayer(username);
        HashMap<Piece, Integer> purchasedPieces = playerData.getPurchasedPieces();
        Integer numPieces = purchasedPieces.get(piece);
        if (numPieces == null || numPieces <= 0) throw new DataAccessError("Piece not purchased", 400);
    }

    public void verifyRegionControl(String username, GameData gameData, int regionID) throws DataAccessError {
        Region region = gameData.getBoard().getBoard().get(regionID);
        if (!region.getRegController().equals(username)) throw new DataAccessError("You don't control that region", 400);
    }

    /**
     * Return updated balance
     */
    public GameData makePurchase(String username, GameData gameData, String gameID, Piece pieceToBuy) throws DataAccessError, UserError {
        PlayerData playerData = gameData.getPlayer(username);

        HashMap<Piece, Integer> purchasedPieces = playerData.getPurchasedPieces();
        Integer numPieces = purchasedPieces.get(pieceToBuy);
        purchasedPieces.put(pieceToBuy, numPieces + 1);
        playerData.setPurchasedPieces(purchasedPieces);
        playerData.setBalance(playerData.getBalance() - pieceToBuy.getCost());

        return super.setPlayer(gameData, gameID, playerData, false);
    }

    public GameData placePiece(String username, GameData gameData, String gameID, Piece piece, int regionID) throws DataAccessError, UserError {
        HashMap<Integer, Region> board = gameData.getBoard().getBoard();
        Region region = board.get(regionID);
        HashMap<Piece, Integer> regionPieces = region.getPieces();
        regionPieces.put(piece, regionPieces.get(piece) + 1);

        PlayerData player = gameData.getPlayer(username);
        HashMap<Piece, Integer> purchasedPieces = player.getPurchasedPieces();
        purchasedPieces.put(piece, purchasedPieces.get(piece) - 1);
        player.setPurchasedPieces(purchasedPieces);
        gameData.updatePlayer(player, false);

        super.updateGame(gameID, gameData);
        return gameData;
    }
}
