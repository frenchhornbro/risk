package game;

import java.util.ArrayList;

public class GameData {
    //This should be used to store states, board info, etc. This is what is stored in the DB
    private String playerTurn;
    private String phase;
    private ArrayList<PlayerData> players;
    public GameData() {

    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(String playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public ArrayList<PlayerData> getPlayers() {
        return players;
    }
}
