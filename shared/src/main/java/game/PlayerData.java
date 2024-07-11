package game;

import pieces.Piece;

import java.util.HashMap;

public class PlayerData {
	private final String username;
	private int balance;
	private HashMap<Piece, Integer> purchasedPieces;

	public PlayerData(String username) {
		this.username = username;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getUsername() {
		return username;
	}

	public HashMap<Piece, Integer> getPurchasedPieces() {
		return purchasedPieces;
	}

	public void setPurchasedPieces(HashMap<Piece, Integer> purchasedPieces) {
		this.purchasedPieces = purchasedPieces;
	}

	@Override
	public String toString() {
		return "Username: " + username + ", Balance: " + balance + ", Purchased pieces: " + purchasedPieces + "\n";
	}
}
