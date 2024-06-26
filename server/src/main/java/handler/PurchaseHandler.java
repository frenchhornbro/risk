package handler;

import com.google.gson.Gson;
import exceptions.ServerError;
import exceptions.ServiceError;
import service.Service;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class PurchaseHandler {
    private final Service service;

    public PurchaseHandler() {
        service = new Service();
    }

    public Object purchase(Request request, Response response) throws ServerError {
        Gson serial = new Gson();
        try {
            String authToken = request.headers("authToken");
            HashMap<String, String> reqBody = serial.fromJson(request.body(), HashMap.class);
            String gameID = reqBody.get("gameID");
            String pieceToBuy = reqBody.get("pieceToBuy");
            service.authenticate(authToken, gameID);
            service.purchaseReqs(pieceToBuy);
            response.status(200);
            response.body(serial.toJson(service.makePurchase(pieceToBuy)));
        }
        catch (ServiceError serviceError) {
            response.status(serviceError.getErrorCode());
            response.body(serial.toJson(serviceError.getMessage()));
        }
        catch (Exception exception) {
            throw new ServerError(exception.getMessage(), 500);
        }
        return response;
    }
}
