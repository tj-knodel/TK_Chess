package edu.kingsu.SoftwareEngineering.Chess.GUI;

/**
 * Starts up the GUI.
 * 
 * @author Noah Bulas
 * @version V1
 */
public class GUIStarter {

    /**
     * Holds the ChessUIManager class (basically static but non static because of how it gets images)
     */
    public ChessUIManager chessUIManager;

    /**
     * Starts up the GUI
     */
    public GUIStarter() {

        // Script Configuration
        new CreateMainFrame(); // Create the frame
        new GUI_Events();
        new CreateStartFrame();
        new CreateCompSliderFrame();
        chessUIManager = new ChessUIManager(new CreateAccessoryUIs());
        new AboutMenu();
        new ChangeBoardAppearance();

        // Frame Configuration
        UILibrary.ChessJFrame.add(UILibrary.NewGameFrame);
        UILibrary.ChessJFrame.add(UILibrary.MainFrame);
        UILibrary.ChessJFrame.add(UILibrary.SetAIStrengthSliderFrame);
        UILibrary.NewGameFrame.setVisible(false);
        UILibrary.MainFrame.setVisible(false);
        UILibrary.SetAIStrengthSliderFrame.setVisible(false);

        // Show UI
        UILibrary.ChessJFrame.setVisible(true);
         //ChessUIManager.showNewGameFrame();
        ChessUIManager.showMainFrame();

    }

}