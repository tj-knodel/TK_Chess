package edu.kingsu.SoftwareEngineering.Chess.GUI;

public class GUI_Events {
    
    // Mostly Temporary, awaiting connections
    public GUI_Events() {


        UILibrary.StepBackwards_Button.addActionListener(e-> {
                System.out.println("StepBackwards Button was clicked!");
        });


        UILibrary.StepForwards_Button.addActionListener(e-> {
                System.out.println("StepForwards Button was clicked!");
        });


         UILibrary.EnterMove_TextField.addActionListener(e -> {
            String input =  UILibrary.EnterMove_TextField.getText();
            System.out.println("The text box detected input: " + input);
        });

    }

        // Event is received from ChessTileUI
        public static void chessTileWasClicked(char row, char column) {
              System.out.println("Tile " + (int)row + " " + (int)column + " was clicked.");
        }

        // Event is received from CreateAccessoryUIs
        public static void upgradeChessPieceWasClicked(String piece) {
            // will be either "queen", "rook", "bishop", "knight"
            System.out.println(piece + " was clicked.");
        }

}
