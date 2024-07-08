package board;

import pieces.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public class Region {
    private final String regName;
    private final int regID;
    private final ArrayList<Integer> adjacentRegIDs;
    private String regController;
    private HashMap<Piece, Integer> pieces;
    public Region(String regName, int regID, ArrayList<Integer> adjacentRegIDs) {
        this.regName = regName;
        this.regID = regID;
        this.adjacentRegIDs = adjacentRegIDs;
        this.regController = null;
        this.pieces = new HashMap<>();
    }

    public String getName() {
        return regName;
    }

    public int getRegID() {
        return regID;
    }

    public ArrayList<Integer> getAdjacentRegIDs() {
        return adjacentRegIDs;
    }

    public String getRegController() {
        return regController;
    }
    public void setRegController(String regController) {
        this.regController = regController;
    }

    public HashMap<Piece, Integer> getPieces() {
        return pieces;
    }
    public void setPieces(HashMap<Piece, Integer> pieces) {
        this.pieces = pieces;
    }

    @Override
    public int hashCode() {
        return regID;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (this.getClass() != object.getClass()) return false;
        Region testPos = (Region) object;
        return (this.hashCode() == testPos.hashCode());
    }

    @Override
    public String toString() {
        return regName;
    }
}