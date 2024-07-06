package pieces;

public abstract class Piece {
    protected int cost;
    protected int travelDistance;
    protected int defenseNum;
    protected int attackNum;
    protected int teamNum;
    protected String playerColor;

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}