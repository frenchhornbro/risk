package board;

import java.util.ArrayList;

public class Region {
    private final String regName;
    private final int regID;
    private final ArrayList<Integer> adjacentRegIDs;
    public Region(String regName, int regID, ArrayList<Integer> adjacentRegIDs) {
        this.regName = regName;
        this.regID = regID;
        this.adjacentRegIDs = adjacentRegIDs;
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