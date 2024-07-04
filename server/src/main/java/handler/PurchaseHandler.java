package handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exceptions.DataAccessError;
import exceptions.ServerError;
import service.Service;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class PurchaseHandler {
    private final Service service;

    public PurchaseHandler() throws DataAccessError {
        service = new Service();
    }

    public Object purchase(Request request, Response response) throws ServerError {
        Gson serial = new Gson();
        try {
            String authToken = request.headers("authToken");
            HashMap<String, String> reqBody = serial.fromJson(request.body(), new TypeToken<HashMap<String, String>>(){}.getType());
            String gameID = reqBody.get("gameID");
            String pieceToBuy = reqBody.get("pieceToBuy");
            String username = service.authenticate(authToken, gameID);
            service.purchaseReqs(username, gameID, pieceToBuy);
            response.status(200);
            response.body(serial.toJson(service.makePurchase(pieceToBuy)));
        }
        catch (DataAccessError dataAccessError) {
            response.status(dataAccessError.getErrorCode());
            response.body(serial.toJson(dataAccessError.getMessage()));
        }
        catch (Exception exception) {
            throw new ServerError(exception.getMessage(), 500);
        }
        return response;
    }
}
