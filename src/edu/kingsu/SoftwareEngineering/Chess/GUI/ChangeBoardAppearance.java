package edu.kingsu.SoftwareEngineering.Chess.GUI;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Creates Miscellaneous accessory frames;
 * Upgrade Piece Frame, End game frame
 * 
 * @author Noah Bulas
 * @version V1
 */
public class ChangeBoardAppearance {

    /**
     * Opens a Directory and return the selected directory
     * 
     * @return Whichever directory the user selected or null
     */
    public static File getChessIconFolderFromDirectory() {
        // Create chooser
        JFileChooser chooser = new JFileChooser();
        chooser.setPreferredSize(new Dimension(700, 700));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile();

        // nothing happened
        return null;
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
        Makes Sure all the needed files are within the directory
        @param gottenFolder The folder to search through and ensure all necessary are there
        @return true means the folder can be used for icons, false means the folder cant be used.
     */
    private static boolean checkFolderForNecessaryFiles(File gottenFolder) {
        String neededFiles[] = {"bishop_black.png", "bishop_white.png", "king_black.png", "king_white.png", "knight_black.png", 
        "knight_white.png", "pawn_black.png", "pawn_white.png", "queen_black.png", "queen_white.png", "rook_black.png", 
        "rook_white.png", "square_black.png", "square_white.png", "PossibleMoveCircle.png"};

          File[] childrenFiles = gottenFolder.listFiles();
        if (childrenFiles == null) return false;
            
        for (File child : childrenFiles) {
            for (int i = 0; i < neededFiles.length; ++i) {
                if (neededFiles[i].equals(child.getName())) 
                    neededFiles[i] = "";
            }
         }    

         // See if all the files were found
         for (String str : neededFiles) {
            if (!str.equals(""))
                return false;
         }

         return true;
}


    /**
     * Array of Strings, default options for board appearance
     */
    Object[] possibleValues = { "Blue", "Chaos", "Clash", "Wood", "KingsU",  "Custom Theme" };

    /**
     *  Shows a JOption Pane and allows the user to select from a default option or custom appearance
     * @return Object, selected object from possibleValues, or null
     * @see possibleValues
     */
    private Object chooseFromDefaultOptions() {
       return JOptionPane.showInputDialog(null, "Select a Board Appearance Type", "Select a Board Appearance Type",
            JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
    }


    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Calls the functions to create the accessory Frames, upgrade a piece, end game
     * frame.
     */
    public ChangeBoardAppearance() {
        UILibrary.SetBoardAppearance_JMenuItem.addActionListener(e -> {

            // Get user selection
            Object chosenAppearance = chooseFromDefaultOptions();

            // Filter through default options
            if (chosenAppearance   != possibleValues[possibleValues.length - 1] && chosenAppearance != null) {
                if (chosenAppearance == possibleValues[0]) { // Blue
                    UILibrary.boardAppearanceFolder = "BoardImages_Blue/";
                } else if (chosenAppearance == possibleValues[1]) { // Chaos
                     UILibrary.boardAppearanceFolder = "BoardImages_Chaos/";
                }  else if (chosenAppearance == possibleValues[2]) { // Clash
                     UILibrary.boardAppearanceFolder = "BoardImages_Clash/";
                }  else if (chosenAppearance == possibleValues[3]) { // Wood
                     UILibrary.boardAppearanceFolder = "BoardImages_Wood/";
                }  else if (chosenAppearance == possibleValues[4]) { // KingsU
                     UILibrary.boardAppearanceFolder = "BoardImages_KingsU/";
                } 
                UILibrary.isAbsoluteFilePath = false;
                UILibrary.resizeModule.resizeEverything();
                return;
            }

            // make sure selection is "Custom"
            if (chosenAppearance != possibleValues[3]) return; // If not custom return

            // Open Window and allow user to select a folder
            File gottenFolder = getChessIconFolderFromDirectory();
            if (gottenFolder != null) {
                boolean isValidFolder = checkFolderForNecessaryFiles(gottenFolder);
                if (isValidFolder) {
                    UILibrary.boardAppearanceFolder = gottenFolder.getAbsolutePath() + "\\";
                    UILibrary.isAbsoluteFilePath = true;
                    UILibrary.resizeModule.resizeEverything();
                    return;
              }
            }

            // Incorrect folder selected
            String body = "Please make sure the folder you select contains the following files:\nbishop_black.png\nbishop_white.png\nking_black.png\nking_white.png\nknight_black.png\nknight_white.png\npawn_black.png\npawn_white.png\nqueen_black.png\nqueen_white.png\nrook_black.png\nrook_white.png\nsquare_black.png\nsquare_white.png\nPossibleMoveCircle.png";
            JOptionPane.showMessageDialog(null, body, "Error Selecting Folder", JOptionPane.ERROR_MESSAGE);
        });

    }

}
