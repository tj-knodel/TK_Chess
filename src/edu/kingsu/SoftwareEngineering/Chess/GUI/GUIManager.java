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
        
        UILibrary.MainFrame.setVisible(true);

    }


}