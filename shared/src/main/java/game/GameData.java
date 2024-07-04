package game;

public class GameData {
    //This should be used to store states, board info, etc. This is what is stored in the DB
    private String playerTurn;
    public GameData() {

    }

    public void setPlayerTurn(String playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getPlayerTurn() {
        return playerTurn;
    }
}
