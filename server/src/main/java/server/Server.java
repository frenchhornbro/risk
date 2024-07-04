package server;

import exceptions.DataAccessError;
import handler.*;
import spark.*;

public class Server {
    private PurchaseHandler purchaseHandler;
    private PlaceHandler placeHandler;
    private AttackHandler attackHandler;
    private ReinforceHandler reinforceHandler;
    private ChangePhaseHandler changePhaseHandler;
    private EndTurnHandler endTurnHandler;
    public Server() {
        try {
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

    public int run(int portNum) {
        Spark.port(portNum);
        //Can serve up static files from here
        createRoutes();
        Spark.awaitInitialization();
        return Spark.port();
    }

    private void createRoutes() {
        //TODO: Also include routes to login, create a new user, list games, and join a game
        Spark.put("/purchase", this.purchaseHandler::purchase);
        Spark.put("/place", this.placeHandler::place);
        Spark.put("/attack", this.attackHandler::attack);
        Spark.put("/reinforce", this.reinforceHandler::reinforce);
        Spark.put("/next-phase", this.changePhaseHandler::changePhase);
        Spark.put("/end-turn", this.endTurnHandler::endTurn);
    }
}