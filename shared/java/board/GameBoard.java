package board;

import pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GameBoard {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        gameBoard.setupWorldBoard();
        gameBoard.printAdjacencies();
    }
    public HashMap<Region, ArrayList<Piece>> board;
    public GameBoard() {
        // Each position should have a Map of Pieces.
        // Region should be its own class
        board = new HashMap<>();
    }

    private void setupWorldBoard() {
        board.clear();
        ArrayList<String> regNames = new ArrayList<>(Arrays.asList(
                "United States",    "Canada",   "Greenland",            "Iceland",          "Mexico",
                "Cuba",             "Haiti",    "Dominican Republic",   "Jamaica",          "Puerto Rico",
                "Guatemala",       "Belize",   "Honduras",             "El Salvador",      "Nicaragua",
                "Costa Rica",      "Panama",   "Colombia",             "Venezuela",        "Ecuador",
                "Peru",            "Guyana",   "Suriname",             "French Guiana",    "Brazil",
                "Bolivia",         "Paraguay", "Uruguay",              "Chile",            "Argentina"
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
            board.put(region, new ArrayList<>());
        }
    }

    private void printAdjacencies() {
        for (Region reg: board.keySet()) {
            ArrayList<Integer> adjacencies = reg.getAdjacentRegIDs();
            ArrayList<String> adjNames = new ArrayList<>();
            for (Region thisReg: board.keySet()) {
                for (Integer thisAdj : adjacencies) {
                    if (thisAdj == thisReg.getRegID()) adjNames.add(thisReg.getName());
                }
            }
            System.out.println(reg.getName() + " ".repeat(Math.max(0, 18 - reg.getName().length())) + "\t->\t" + adjNames);
        }
    }

    @Override
    public String toString() {
        return board.toString();
    }
}