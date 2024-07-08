package pieces;

import java.util.Objects;

public abstract class Piece {
    protected int cost;
    protected int travelDistance;
    protected int defenseNum;
    protected int attackNum;
    protected String userLoyalty; //This is the username of the player who placed it

    public int getCost() {
        return cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, travelDistance, defenseNum, attackNum, userLoyalty);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        return (obj.hashCode() != this.hashCode());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}