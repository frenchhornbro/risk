package game;

import exceptions.UserError;

import java.util.HashMap;

public class GameData {
    //This should be used to store states, board info, etc. This is what is stored in the DB
    public enum phaseType {
        Purchase,
        Place,
        Attack,
        Reinforce
    }
    private String playerTurn;
    private phaseType phase;
    private final HashMap<String, PlayerData> players;
    public GameData() {
        players = new HashMap<>();
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(String playerTurn) {
        this.playerTurn = playerTurn;
    }

    public phaseType getPhase() {
        return phase;
    }

    public void setPhase(phaseType phase) {
        this.phase = phase;
    }

    public HashMap<String, PlayerData> getPlayers() {
        return players;
    }

    public void updatePlayer(PlayerData playerData) throws UserError {
        String username = playerData.getUsername();
        if (players.get(username) == null) throw new UserError("Player doesn't exist");
        players.put(username, playerData);
    }

    @Override
    public String toString() {
        return "Turn: " + playerTurn + "\nPhase: " + phase + "\nPlayers:" + players;
    }
}
