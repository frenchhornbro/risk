package handler;

import com.google.gson.Gson;
import exceptions.DataAccessError;
import exceptions.ServerError;
import pieces.Piece;
import service.Service;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class PlaceHandler extends Handler {
    public final Service service;
    public PlaceHandler() throws DataAccessError {
        service = new Service();
    }
    public Object place(Request request, Response response) throws ServerError {
        try {
            String authToken = super.getAuthToken(request);
            HashMap<String, String> reqBody = super.getReqBody(request);
            String gameID = reqBody.get("gameID");
            Piece piece = Handler.stringToPiece(reqBody.get("piece"));
            int regionID = Integer.parseInt(reqBody.get("region"));
            String username = service.authenticate(authToken, gameID, true);
            service.placeReqs(authToken, gameID, piece, regionID);
            response.status(200);
            response.body(new Gson().toJson(service.placePiece(username, gameID, piece, regionID)));
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
