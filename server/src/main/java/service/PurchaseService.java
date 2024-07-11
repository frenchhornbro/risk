package service;

import exceptions.DataAccessError;
import exceptions.ServiceError;
import exceptions.UserError;
import game.GameData;
import game.PlayerData;
import pieces.Piece;

import java.util.HashMap;

public class PurchaseService extends Service {
	public PurchaseService() throws DataAccessError {

	}

	public void purchaseReqs(String username, GameData gameData, Piece pieceToBuy) throws DataAccessError, ServiceError {
		//Phase is "purchase"
		super.verifyGamePhase(gameData, GameData.phaseType.Purchase);

		//Player has sufficient balance
		PlayerData playerData = gameData.getPlayer(username);
		if (playerData.getBalance() < pieceToBuy.getCost()) throw new ServiceError("Balance is too low", 400);
	}

	public GameData makePurchase(String username, GameData gameData, String gameID, Piece pieceToBuy) throws DataAccessError, UserError {
		//Add piece to player's purchased pieces
		PlayerData playerData = gameData.getPlayer(username);
		HashMap<Piece, Integer> purchasedPieces = playerData.getPurchasedPieces();
		Integer numPieces = purchasedPieces.get(pieceToBuy);
		purchasedPieces.put(pieceToBuy, numPieces + 1);
		playerData.setPurchasedPieces(purchasedPieces);

		//Deduct cost from player's wallet
		playerData.setBalance(playerData.getBalance() - pieceToBuy.getCost());

		//Update database
		gameData.updatePlayer(playerData, false);
		gameDataAccess.setGame(gameID, gameData);
		return gameData;
	}
}
