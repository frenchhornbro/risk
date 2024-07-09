package handler;

import exceptions.DataAccessError;
import exceptions.ServerError;
import service.Service;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class CreateUserHandler extends Handler {
    public final Service service;
    public CreateUserHandler() throws DataAccessError {
        service = new Service();
    }
    public Object createUser(Request request, Response response) throws ServerError {
        try {
            HashMap<String, String> reqBody = super.getReqBody(request);
            String email = reqBody.get("email");
            String username = reqBody.get("username");
            String password = reqBody.get("password");
            String confirm = reqBody.get("confirm");
            service.validateCredentials(email, username, password, confirm);
            response.status(200);
            response.body(); //TODO: Store credentials
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
