package handler;

import com.google.gson.Gson;
import exceptions.ClientError;
import exceptions.DataAccessError;
import exceptions.ServerError;
import service.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends Handler {
	private final CreateGameService service;

	public CreateGameHandler() throws DataAccessError {
		service = new CreateGameService();
	}

	public Object createGame(Request request, Response response) throws ServerError {
		try {
			String authToken = super.getAuthToken(request);
			String username = service.authenticateUser(authToken);
			response.body(new Gson().toJson(service.createGame(username)));
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
