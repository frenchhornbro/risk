package handler;

import com.google.gson.Gson;
import exceptions.DataAccessError;
import exceptions.ServerError;
import service.Service;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends Handler {
    private final Service service;
    public CreateGameHandler() throws DataAccessError {
        service = new Service();
    }
    public Object createGame(Request request, Response response) throws ServerError {
        try {
            String authToken = super.getAuthToken(request);
            String username = service.authenticateUser(authToken);
            response.body(new Gson().toJson(service.createGame(username)));
            response.status(200);
        }
        catch (DataAccessError dataAccessError) {
            super.handleDataAccessError(response, dataAccessError);
        }
        catch (Exception exception) {
            super.throwServerError(exception);
        }
        return response;
    }
}
