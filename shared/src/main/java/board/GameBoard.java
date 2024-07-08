package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GameBoard {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        gameBoard.printAdjacencies();
    }
    private HashMap<Integer, Region> board; //RegionID, Region
    public GameBoard() {
        board = new HashMap<>();
        setupWorldBoard();
    }

    private void setupWorldBoard() {
        board.clear();
        ArrayList<String> regNames = new ArrayList<>(Arrays.asList(
                "United States",    "Canada",   "Greenland",            "Iceland",          "Mexico",
                "Cuba",             "Haiti",    "Dominican Republic",   "Jamaica",          "Puerto Rico",
                "Guatemala",        "Belize",   "Honduras",             "El Salvador",      "Nicaragua",
                "Costa Rica",       "Panama",   "Colombia",             "Venezuela",        "Ecuador",
                "Peru",             "Guyana",   "Suriname",             "French Guiana",    "Brazil",
                "Bolivia",          "Paraguay", "Uruguay",              "Chile",            "Argentina"
        ));
        Integer[][] adjacencies = {
                {1, 4},                 {0},            {},                     {},                 {0, 10, 11},
                {},                     {7},            {6},                    {},                 {},
                {4, 11, 12, 13},        {4, 10},        {10, 13, 14},           {10, 12},           {12, 15},
                {14, 16},               {15, 17},       {16, 18, 19, 20, 24},   {17, 21, 24},       {17, 20},
                {17, 19, 24 ,25, 28},   {18, 22, 24},   {21, 23, 24},           {22, 24},           {17, 18, 20, 21, 22, 23, 25, 26, 27, 29},
                {20, 24, 26, 28, 29},   {24, 25, 29},   {24, 29},               {20, 25, 29},       {24, 25, 26, 27, 28},
        };

        for (int i = 0; i < regNames.size(); i++) {
            ArrayList<Integer> adjacency = new ArrayList<>(Arrays.asList(adjacencies[i]));
            Region region = new Region(regNames.get(i), i, adjacency);
            board.put(region.getRegID(), region);
        }
    }

    private void printAdjacencies() {
        for (Region reg : board.values()) {
            ArrayList<Integer> adjacencyIDs = reg.getAdjacentRegIDs();
            ArrayList<String> adjacencyNames = new ArrayList<>();
            for (Integer regionID : board.keySet()) {
                for (Integer thisAdjacencyID : adjacencyIDs) {
                    if (thisAdjacencyID.equals(regionID)) {
                        Region thisAdjacencyRegion = board.get(thisAdjacencyID);
                        adjacencyNames.add(thisAdjacencyRegion.getName());
                    }
                }
            }
            System.out.println(reg.getName() + " ".repeat(Math.max(0, 18 - reg.getName().length())) + "\t->\t" + adjacencyNames);
        }
    }

    public HashMap<Integer, Region> getBoard() {
        return board;
    }

    public void setBoard(HashMap<Integer, Region> board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return board.toString();
    }
}