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

        // Script Configuration
        new CreateMainFrame(); // Create the frame
        new GUI_Events();
        new CreateStartFrame();
        new CreateCompSliderFrame();
        new ChessUIManager(new CreateAccessoryUIs());
        
        // Frame Configuration
        UILibrary.ChessJFrame.add(UILibrary.NewGameFrame);
        UILibrary.ChessJFrame.add(UILibrary.MainFrame);
        UILibrary.ChessJFrame.add(UILibrary.SetAIStrengthSliderFrame);
        UILibrary.NewGameFrame.setVisible(false);
        UILibrary.MainFrame.setVisible(false);
        UILibrary.SetAIStrengthSliderFrame.setVisible(false);

        // Show UI
        UILibrary.ChessJFrame.setVisible(true);
        ChessUIManager.showMainFrame();


    }


}