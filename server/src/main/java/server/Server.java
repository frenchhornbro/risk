package server;

import spark.*;

public class Server {
    public Server() {

    }

    public int run(int portNum) {
        Spark.port(portNum);
        //Can serve up static files from here
        createRoutes();
        Spark.awaitInitialization();
        return Spark.port();
    }

    private void createRoutes() {
        //TODO: Figure out routes to be defined here. Each interaction with the server should receive a route.
    }
}