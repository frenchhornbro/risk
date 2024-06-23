package server;

import handlers.*;
import spark.*;

public class Server {
    private final PurchaseHandler purchaseHandler;
    private final PlaceHandler placeHandler;
    private final AttackHandler attackHandler;
    private final ReinforceHandler reinforceHandler;
    private final ChangePhaseHandler changePhaseHandler;
    private final EndTurnHandler endTurnHandler;
    public Server() {
        purchaseHandler = new PurchaseHandler();
        placeHandler = new PlaceHandler();
        attackHandler = new AttackHandler();
        reinforceHandler = new ReinforceHandler();
        changePhaseHandler = new ChangePhaseHandler();
        endTurnHandler = new EndTurnHandler();
    }

    public int run(int portNum) {
        Spark.port(portNum);
        //Can serve up static files from here
        createRoutes();
        Spark.awaitInitialization();
        return Spark.port();
    }

    private void createRoutes() {
        //TODO: Also include routes to login, create a new user, list games, and join a game
        //Purchase
        //Place
        //Attack
        //Reinforce
        //Next phase
        //End turn
        Spark.put("/purchase", this.purchaseHandler::purchase);
        Spark.put("/place", this.placeHandler::place);
        Spark.put("/attack", this.attackHandler::attack);
        Spark.put("/reinforce", this.reinforceHandler::reinforce);
        Spark.put("/next-phase", this.changePhaseHandler::changePhase);
        Spark.put("/end-turn", this.endTurnHandler::endTurn);
    }
}