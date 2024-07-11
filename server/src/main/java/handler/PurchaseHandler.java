package handler;

import com.google.gson.Gson;
import exceptions.ClientError;
import exceptions.DataAccessError;
import exceptions.ServerError;
import game.GameData;
import pieces.Piece;
import service.PurchaseService;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class PurchaseHandler extends Handler {
	private final PurchaseService service;

	public PurchaseHandler() throws DataAccessError {
		service = new PurchaseService();
	}

	public Object purchase(Request request, Response response) throws ServerError {
		try {
			String authToken = super.getAuthToken(request);
			HashMap<String, String> reqBody = super.getReqBody(request);
			String gameID = reqBody.get("gameID");
			Piece pieceToBuy = Handler.stringToPiece(reqBody.get("pieceToBuy"));
			Object[] info = service.authenticateGame(authToken, gameID, true);
			GameData gameData = (GameData) info[0];
			String username = (String) info[1];
			service.purchaseReqs(username, gameData, pieceToBuy);
			response.body(new Gson().toJson(service.makePurchase(username, gameData, gameID, pieceToBuy)));
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
