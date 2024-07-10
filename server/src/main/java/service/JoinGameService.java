package service;

import exceptions.DataAccessError;
import exceptions.UserError;
import game.GameData;
import game.PlayerData;

public class JoinGameService extends Service {
    public JoinGameService() throws DataAccessError {

    }

    public GameData joinPlayer(String username, GameData gameData, String gameID) throws DataAccessError, UserError {
        //Add player to game
        gameData.updatePlayer(new PlayerData(username), true);

        //Update database
        gameDataAccess.setGame(gameID, gameData);
        return gameData;

    }
}
