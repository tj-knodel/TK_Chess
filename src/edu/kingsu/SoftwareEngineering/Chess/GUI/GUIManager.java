package edu.kingsu.SoftwareEngineering.Chess.GUI;


/**
 * Starts up the GUI.
 * 
 * @author Noah Bulas
 * @version V1
 */
public class GUIManager {


    /**
     * Starts up the GUI
     */
    public GUIManager(){

        new CreateMainFrame(); // Create the frame
        new GUI_Events();
        new CreateStartFrame();
        new CreateCompSliderFrame();

        UILibrary.ChessJFrame.add(UILibrary.SetAIStrengthSliderFrame);
        UILibrary.ChessJFrame.setVisible(true);


    }


}