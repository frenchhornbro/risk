package server;

import exceptions.DataAccessError;
import handler.*;
import spark.Spark;

public class Server {
	private CreateUserHandler createUserHandler;
	private CreateGameHandler createGameHandler;
	private JoinGameHandler joinGameHandler;
	private PurchaseHandler purchaseHandler;
	private PlaceHandler placeHandler;
	private AttackHandler attackHandler;
	private ReinforceHandler reinforceHandler;
	private ChangePhaseHandler changePhaseHandler;
	private EndTurnHandler endTurnHandler;
	public Server() {
		try {
			createUserHandler = new CreateUserHandler();
			createGameHandler = new CreateGameHandler();
			joinGameHandler = new JoinGameHandler();
			purchaseHandler = new PurchaseHandler();
			placeHandler = new PlaceHandler();
			attackHandler = new AttackHandler();
			reinforceHandler = new ReinforceHandler();
			changePhaseHandler = new ChangePhaseHandler();
			endTurnHandler = new EndTurnHandler();
		}
		catch (DataAccessError dataAccessError) {
			//TODO: Log error
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.run(8080);
	}

	public int run(int portNum) {
		Spark.port(portNum);
		//Can serve up static files from here
		createRoutes();
		Spark.awaitInitialization();
		return Spark.port();
	}

	private void createRoutes() {
		//TODO: Also include routes to login, delete user, and join a game
		Spark.post("/user", this.createUserHandler::createUser);
		Spark.post("/game", this.createGameHandler::createGame);
		Spark.patch("/join-game", this.joinGameHandler::joinGame);
		Spark.put("/purchase", this.purchaseHandler::purchase);
		Spark.put("/place", this.placeHandler::place);
		Spark.put("/attack", this.attackHandler::attack);
		Spark.put("/reinforce", this.reinforceHandler::reinforce);
		Spark.put("/next-phase", this.changePhaseHandler::changePhase);
		Spark.put("/end-turn", this.endTurnHandler::endTurn);
	}
}