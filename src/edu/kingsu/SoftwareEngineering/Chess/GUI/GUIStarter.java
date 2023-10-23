package edu.kingsu.SoftwareEngineering.Chess.GUI;


/**
 * Starts up the GUI.
 * 
 * @author Noah Bulas
 * @version V1
 */
public class GUIStarter{


    /**
     * Starts up the GUI
     */
    public GUIStarter(){

        new CreateMainFrame(); // Create the frame
        new GUI_Events();
        //new CreateStartFrame();
        //new CreateCompSliderFrame();
        //new ChessUIManager(new CreateAccessoryUIs());
        
        UILibrary.ChessJFrame.add(UILibrary.MainFrame);
        UILibrary.ChessJFrame.setVisible(true);


    }


}