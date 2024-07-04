package board;

import pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RandomAdjacencyGenerator {
    public HashMap<Region, ArrayList<Piece>> board;
    public RandomAdjacencyGenerator(HashMap<Region, ArrayList<Piece>> board) {
        this.board = board;
    }

    private void setupRandomizedBoard() {
        ArrayList<String> regNames = new ArrayList<>(Arrays.asList(
                "Area 1", "Area 2", "Area 3", "Area 4", "Area 5", "Area 6", "Area 7", "Area 8", "Area 9", "Area 10",
                "Area 11", "Area 12", "Area 13", "Area 14", "Area 15", "Area 16", "Area 17", "Area 18", "Area 19", "Area 20"
        ));
        int numRegs = (int) (Math.random() * regNames.size());

        for (int i = 0; i < numRegs; i++) {
            int area = (int) (Math.random() * 70);

            int[] coords = new int[]{3, 5};

//            board.put(new Region(regNames.get(i), i, area, createRegAdjacencies(i, area)), new ArrayList<>());
        }
        //IDEA: Could have regNames randomly selected (so long as duplicates aren't selected)

        //TODO: Think of how the board should be set up at the start
    }

    private HashMap<Integer, Integer> createRegAdjacencies(int regID, int area) {
        //TODO: Design an algorithm that follows the following rules:
        //  A region cannot be adjacent to itself
        //  If A is adjacent to B, B must be adjacent to A
        //  There must be a limit to the size of an adjacency list
        //      The limit is determined by the area of the position
        //      If B is at it's max adjacencies, A can't add it as an adjacency.
        //  Some positions (such as along the coast) must of necessity have fewer adjacencies
        //      Perhaps proximity to water could be calculated for this

        HashMap<Integer, Integer> adjacencyList = new HashMap<>();

        //If A is adjacent to B, B must be adjacent to A
        for (Region thisReg : board.keySet()) {
            if (thisReg.getAdjacentRegIDs().get(regID) == regID) {
                adjacencyList.put(thisReg.getRegID(), thisReg.getRegID());
            }
        }

        int maxAdjacencies = area / 10; // No more than 7 adjacencies
        int numAdjacencies = (int) (Math.random() * maxAdjacencies);
        for (int i = 0; i < numAdjacencies; i++) {
            //TODO: Randomly assign regions, but based on distance, and not exceeding either region's max adjacencies
            //      I need to assign coordinates to each location. Query a random point, but reject it if it is too close
            //      to another region, or if it is moderately close to a region that has reached its max adjacencies (?)
            //          ^^^ Is this second criterion necessary? If it's reached max adjacencies, that would just mean
            //              it would be too close to any of the points there
            int test = (int) ((1 + Math.random()) * Math.sqrt(area));
        }

        //TODO: Figure out how to tie distance between positions with the region

        //IDEA: Randomly set a coordinate (with at least a little distance in between the rest) for each position.
        //      Perhaps could use k-nearest neighbors to determine position borders?

        return adjacencyList;
    }
}