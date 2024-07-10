package handler;

import exceptions.ClientError;
import exceptions.DataAccessError;
import exceptions.ServerError;
import service.CreateUserService;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class CreateUserHandler extends Handler {
    public final CreateUserService service;
    public CreateUserHandler() throws DataAccessError {
        service = new CreateUserService();
    }
    public Object createUser(Request request, Response response) throws ServerError {
        try {
            HashMap<String, String> reqBody = super.getReqBody(request);
            String email = reqBody.get("email");
            String username = reqBody.get("username");
            String password = reqBody.get("password");
            String confirm = reqBody.get("confirm");
            service.validateCredentials(email, username, password, confirm);
            response.body(service.storeCredentials(email, username, password));
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
