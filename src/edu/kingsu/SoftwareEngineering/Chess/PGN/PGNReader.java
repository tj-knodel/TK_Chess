package edu.kingsu.SoftwareEngineering.Chess.PGN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The class responsible for construction moves
 * in the form of PGNMove from a PGN file.
 *
 * @author Daniell Buchner
 * @version 0.1.0
 */
public class PGNReader {

    private String metaData;
    private String lastReadFile;

    /**
     * Public constructor to just initialize member variables.
     */
    public PGNReader() {
        this.metaData = null;
        this.lastReadFile = null;
    }

    /**
     * This function reads a PGN file, and outputs an ArrayList of
     * the PGNMove class for the moves of the PGN file.
     *
     * @param filePath The file to load.
     * @return The ArrayList of PGNMove class of the moves in sequence.
     */
    public ArrayList<PGNMove> getMovesFromFile(String filePath) {
        this.lastReadFile = filePath;
        ArrayList<PGNMove> moves = new ArrayList<>();
        String allMoves = "";

        // Right now it only reads the moves, can display the information too if needed.
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("["))
                    continue;
                if (line.isEmpty())
                    continue;
                line = line.replaceAll("\n", "");
                allMoves += line;
                allMoves += " ";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int parenCounter = 0;
        int dollarSign = 0;

        String moveOnlyString = "";

        for (char c : allMoves.toCharArray()) {
            if (c == '(') {
                parenCounter++;
                continue;
            } else if (c == '$') {
                dollarSign++;
                continue;
            } else if (c == ')') {
                parenCounter--;
                continue;
            }
            if (dollarSign > 0 && c != ' ') {
                continue;
            } else if (c == ' ' && dollarSign > 0) {
                dollarSign = 0;
            }
            if (parenCounter == 0 && dollarSign == 0) {
                moveOnlyString += c;
            }
            if (c == '\n')
                moveOnlyString += "\n";
        }

        String comment = "";
        boolean inComment = false;

        moveOnlyString = moveOnlyString.replace(".", ". ");
        for (String str : moveOnlyString.split(" ")) {
            if (str.isEmpty()) {
                continue;
            }
            if (str.endsWith(".")) {
                continue;
            } else if (str.startsWith("{")) {
                inComment = true;
            }
            if (inComment) {
                if (str.equalsIgnoreCase(";"))
                    comment += "\n";
                else
                    comment += str + " ";
                if (str.endsWith("}")) {
                    inComment = false;
                    moves.get(moves.size() - 1).setComment(comment.replace("{", "").replace("}", ""));
                }
            } else if (!str.endsWith(".")) {
                moves.add(new PGNMove(str.replace("#", "")));
            }
        }

//        for(PGNMove move : moves) {
//            System.out.println("Move: " + move.getMoveString() + " Comment: " + move.getComment());
//        }

        return moves;
    }
}
