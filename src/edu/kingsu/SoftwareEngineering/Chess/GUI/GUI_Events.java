package edu.kingsu.SoftwareEngineering.Chess.GUI;

/**
 * Output for some UI Elements and their associated events
 * 
 * @author Noah Bulas
 * @version V1
 * 
 */
public class GUI_Events {

    /**
     * Constructor creates events
     */
    public GUI_Events() {

        // // Step back a move
        // UILibrary.StepBackwards_Button.addActionListener(e -> {
        //     System.out.println("StepBackwards Button was clicked!");
        // });

        // // Set Forward a move
        // UILibrary.StepForwards_Button.addActionListener(e -> {
        //     System.out.println("StepForwards Button was clicked!");
        // });

        // // Player Manually entered a certain move through the text field
        // UILibrary.EnterMove_TextField.addActionListener(e -> {
        //     String input = UILibrary.EnterMove_TextField.getText();
        //     System.out.println("The text box detected input: " + input);
        // });

    }

    /**
     * Event is received from ChessTileUI;
     * Triggered when a chess tile is clicked
     */
    public static void chessTileWasClicked(char row, char column) { // A square on the board was clicked
        // System.out.println("Tile " + (int) row + " " + (int) column + " was
        // clicked.");
        // MoveController.chessTileClick(row, column);
    }

    /**
     * Event is received from CreateAccessoryUIs;
     * When the upgrade piece frame is shown, this function is triggered when the
     * user selects which piece to upgrade.
     */
    public static void upgradeChessPieceWasClicked(String piece) { // When a chess piece is clicked in the upgrade piece
                                                                   // ui
                                                                   // will be either "queen", "rook", "bishop", "knight"
        System.out.println(piece + " was clicked.");
    }

}
