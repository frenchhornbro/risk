package handler;

import com.google.gson.Gson;
import exceptions.ClientError;
import exceptions.DataAccessError;
import exceptions.ServerError;
import game.GameData;
import service.JoinGameService;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class JoinGameHandler extends Handler {
	private final JoinGameService service;

	public JoinGameHandler() throws DataAccessError {
		service = new JoinGameService();
	}

	public Object joinGame(Request request, Response response) throws ServerError {
		try {
			String authToken = super.getAuthToken(request);
			HashMap<String, String> reqBody = super.getReqBody(request);
			String gameID = reqBody.get("gameID");
			Object[] info = service.authenticateGame(authToken, gameID, false);
			String username = (String) info[0];
			GameData gameData = (GameData) info[1];
			response.body(new Gson().toJson(service.joinPlayer(username, gameData, gameID)));
			response.status(200);
		}
		catch (ClientError clientError) {
			super.handleDataAccessError(response, clientError);
		}
		catch (Exception exception) {
			super.throwServerError(exception);
		}
		return response;
	}
}
