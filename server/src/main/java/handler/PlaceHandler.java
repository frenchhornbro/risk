package handler;

import com.google.gson.Gson;
import exceptions.ClientError;
import exceptions.DataAccessError;
import exceptions.ServerError;
import game.GameData;
import pieces.Piece;
import service.PlaceService;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class PlaceHandler extends Handler {
    public final PlaceService service;
    public PlaceHandler() throws DataAccessError {
        service = new PlaceService();
    }
    public Object place(Request request, Response response) throws ServerError {
        try {
            String authToken = super.getAuthToken(request);
            HashMap<String, String> reqBody = super.getReqBody(request);
            String gameID = reqBody.get("gameID");
            Piece piece = Handler.stringToPiece(reqBody.get("piece"));
            int regionID = Integer.parseInt(reqBody.get("region"));
            Object[] info = service.authenticateGame(authToken, gameID, true);
            String username = (String) info[0];
            GameData gameData = (GameData) info[1];
            service.placeReqs(username, gameData, piece, regionID);
            response.body(new Gson().toJson(service.placePiece(username, gameData, gameID, piece, regionID)));
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
