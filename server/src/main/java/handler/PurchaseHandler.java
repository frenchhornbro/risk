package handler;

import com.google.gson.Gson;
import exceptions.DataAccessError;
import exceptions.ServerError;
import pieces.Piece;
import service.Service;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class PurchaseHandler extends Handler {
    private final Service service;

    public PurchaseHandler() throws DataAccessError {
        service = new Service();
    }

    public Object purchase(Request request, Response response) throws ServerError {
        try {
            String authToken = super.getAuthToken(request);
            HashMap<String, String> reqBody = super.getReqBody(request);
            String gameID = reqBody.get("gameID");
            Piece pieceToBuy = Handler.stringToPiece(reqBody.get("pieceToBuy"));
            String username = service.authenticate(authToken, gameID, true);
            service.purchaseReqs(username, gameID, pieceToBuy);
            response.status(200);
            response.body(new Gson().toJson(service.makePurchase(username, gameID, pieceToBuy)));
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
