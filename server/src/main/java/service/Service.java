package service;

import com.google.gson.Gson;
import dataAccess.GameDataAccess;
import dataAccess.UserDataAccess;
import exceptions.DataAccessError;
import exceptions.ServiceError;
import game.GameData;
import game.PlayerData;

import java.util.ArrayList;

public abstract class Service {
	protected final UserDataAccess userDataAccess;
	protected final GameDataAccess gameDataAccess;

	public Service() throws DataAccessError {
		userDataAccess = new UserDataAccess();
		gameDataAccess = new GameDataAccess();
	}

	/**
	 * Validate authToken, validate gameID, check if client is in game, check if it is client's turn
	 *
	 * @return username
	 */
	public Object[] authenticateGame(String authToken, String gameID, boolean takingTurn) throws DataAccessError, ServiceError {
		//Validate authToken
		ArrayList<String> userData = userDataAccess.getUserData(authToken);
		if (userData.isEmpty()) throw new ServiceError("Unauthorized", 401);
		String username = userData.getFirst();

		//Validate gameID
		ArrayList<String> gameDataText = gameDataAccess.getGame(gameID);
		if (gameDataText.isEmpty()) throw new ServiceError("Invalid game ID", 400);
		GameData gameData = new Gson().fromJson(gameDataText.getFirst(), GameData.class);

		if (takingTurn) {
			//Check if user is in the game
			PlayerData player = gameData.getPlayer(username);
			if (player == null) throw new ServiceError("User not in game", 400);

			//Verify it's the player's turn
			if (!gameData.getPlayerTurn().equals(username)) throw new ServiceError("Wait for your turn!", 400);
		}
		return new Object[]{gameData, username};
	}

	public void verifyGamePhase(GameData gameData, GameData.phaseType phase) throws ServiceError {
		if (gameData.getPhase() != (phase)) throw new ServiceError("Incorrect phase", 400);
	}
}
