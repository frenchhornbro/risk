package service;

import com.google.gson.Gson;
import exceptions.DataAccessError;
import exceptions.ServiceError;
import exceptions.UserError;
import game.GameData;
import game.PlayerData;

import java.util.ArrayList;

public class CreateGameService extends Service {
    public CreateGameService() throws DataAccessError {

    }

    /**
     * @return username
     */
    public String authenticateUser(String authToken) throws DataAccessError, ServiceError {
        //User is registered
        ArrayList<String> userData = userDataAccess.getUserData(authToken);
        if (userData.isEmpty()) throw new ServiceError("Unauthorized", 401);
        return userData.getFirst();
    }

    public String createGame(String username) throws DataAccessError, ServiceError, UserError {
        //Create a new game
        GameData newGame = new GameData();
        //TODO: ^^^ Need to give each player a starting balance and some regions to control
        newGame.updatePlayer(new PlayerData(username), true);
        String newGameText = new Gson().toJson(newGame);

        //Insert into database
        return gameDataAccess.createGame(newGameText);
    }
}
