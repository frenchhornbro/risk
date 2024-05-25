package board;

public class Position {
    private final String posName;
    private final int posNum;
    private final int[] adjacentPosNums;
    public Position(String posName, int posNum, int[] adjacentPosNums) {
        this.posName = posName;
        this.posNum = posNum;
        this.adjacentPosNums = adjacentPosNums;
    }

    public String getName() {
        return posName;
    }

    public int getPosNum() {
        return posNum;
    }

    public int[] getAdjacentPosNums() {
        return adjacentPosNums;
    }

    @Override
    public String toString() {
        return posName;
    }
}