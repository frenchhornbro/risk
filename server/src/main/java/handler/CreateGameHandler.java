package handler;

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
            service.authenticate(authToken);
            return service.createGame(authToken);
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
