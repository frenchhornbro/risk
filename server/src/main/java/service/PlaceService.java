package service;

import board.Region;
import exceptions.DataAccessError;
import exceptions.ServiceError;
import exceptions.UserError;
import game.GameData;
import game.PlayerData;
import pieces.Piece;

import java.util.HashMap;

public class PlaceService extends Service {
    public PlaceService() throws DataAccessError {

    }

    public void placeReqs(String username, GameData gameData, Piece piece, int regionID) throws DataAccessError, ServiceError {
        //Phase is "place"
        super.verifyGamePhase(gameData, GameData.phaseType.Place);

        //Player has purchased the piece
        PlayerData playerData = gameData.getPlayer(username);
        HashMap<Piece, Integer> purchasedPieces = playerData.getPurchasedPieces();
        Integer numPieces = purchasedPieces.get(piece);
        if (numPieces == null || numPieces <= 0) throw new ServiceError("Piece not purchased", 400);

        //Player controls the region
        Region region = gameData.getBoard().getBoard().get(regionID);
        if (!region.getRegController().equals(username)) throw new ServiceError("You don't control that region", 400);
    }

    public GameData placePiece(String username, GameData gameData, String gameID, Piece piece, int regionID) throws DataAccessError, UserError {
        //Put piece on region
        Region region = gameData.getBoard().getBoard().get(regionID);
        HashMap<Piece, Integer> regionPieces = region.getPieces();
        regionPieces.put(piece, regionPieces.get(piece) + 1);

        //Remove piece from player's purchased pieces
        PlayerData player = gameData.getPlayer(username);
        HashMap<Piece, Integer> purchasedPieces = player.getPurchasedPieces();
        purchasedPieces.put(piece, purchasedPieces.get(piece) - 1);
        player.setPurchasedPieces(purchasedPieces);
        gameData.updatePlayer(player, false);

        //Update database
        gameDataAccess.setGame(gameID, gameData);
        return gameData;
    }
}
