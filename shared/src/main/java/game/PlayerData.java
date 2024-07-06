package game;

import pieces.Piece;

import java.util.ArrayList;

public class PlayerData {
    private int balance;
    private final String username;
    private ArrayList<Piece> purchasedPieces;
    public PlayerData(String username) {
        this.username = username;
    }

    public int getBalance() {
        return balance;
    }

    public String getUsername() {
        return username;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ArrayList<Piece> getPurchasedPieces() {
        return purchasedPieces;
    }
    public void setPurchasedPieces(ArrayList<Piece> purchasedPieces) {
        this.purchasedPieces = purchasedPieces;
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Balance: " + balance + ", Purchased pieces: " + purchasedPieces + "\n";
    }
}
