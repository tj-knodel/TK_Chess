package edu.kingsu.SoftwareEngineering.Chess.PGN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PGNReader {

    private String metaData;
    private String lastReadFile;

    public ArrayList<PGNMove> getMovesFromFile(String filePath) {
        this.lastReadFile = filePath;
        ArrayList<PGNMove> moves = new ArrayList<>();
        String allMoves = "";

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

        String comment = "";
        String move = "";
        boolean inComment = false;
        for (String str : allMoves.split(" ")) {
            // System.out.println(str);
            if (str.endsWith(".")) {
                continue;
            } else if (str.startsWith("{")) {
                inComment = true;
            }
            if (inComment) {
                if (str.endsWith("}")) {
                    inComment = false;
                }
                comment += str;
            } else if (!str.endsWith(".")) {
                // move += str;
                // String s = str.replace("\n", "");
                // if (!str.isBlank())
                moves.add(new PGNMove(str));
                // move = "";
            }
        }
        for (PGNMove m : moves) {
            System.out.println(m.getMoveString());
        }
        // while (i < allMoves.length()) {
        //     if (!inMove) {
        //         try {
        //             Integer.parseInt(allMoves.substring(i, allMoves.indexOf(".", i)));
        //             String movess = allMoves.substring(i, allMoves.indexOf(".", i));
        //             System.out.println("Move Number: " + movess);
        //             i += movess.length();
        //             inMove = true;
        //             continue;
        //         } catch (NumberFormatException e) {

        //         }
        //     }
        //     i++;
        // }

        return moves;
    }
}
