package game;

import board.GameBoard;
import exceptions.UserError;

import java.util.HashMap;

public class GameData {
	private final HashMap<String, PlayerData> players;
	private String playerTurn;
	private phaseType phase;
	private GameBoard board;
	public GameData() {
		players = new HashMap<>();
	}

	//This should be used to store states, board info, etc. This is what is stored in the DB
	public enum phaseType {
		Purchase,
		Place,
		Attack,
		Reinforce
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

	public PlayerData getPlayer(String username) {
		return players.get(username);
	}

	public void updatePlayer(PlayerData playerData, boolean isNew) throws UserError {
		String username = playerData.getUsername();
		if ((players.get(username) == null) != (isNew)) throw new UserError("Player doesn't exist");
		players.put(username, playerData);
	}

	public GameBoard getBoard() {
		return board;
	}

	public void setBoard(GameBoard board) {
		this.board = board;
	}

	@Override
	public String toString() {
		return "Turn: " + playerTurn + "\nPhase: " + phase + "\nPlayers:" + players;
	}
}
