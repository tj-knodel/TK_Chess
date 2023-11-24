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
        UILibrary.resizeModule = new ResizeManager();
        new CreateMainFrame(); // Create the frame
        new CreateStartFrame();
        chessUIManager = new ChessUIManager(new CreateAccessoryUIs());
        new AboutMenu();
        new ChangeBoardAppearance();
        UILibrary.resizeModule.detectJFramedResized();
        UILibrary.resizeModule.resizeEverything();

        // Frame Configuration
        UILibrary.ChessJFrame.add(UILibrary.NewGameFrame);
        UILibrary.ChessJFrame.add(UILibrary.MainFrame);
        UILibrary.NewGameFrame.setVisible(false);
        UILibrary.MainFrame.setVisible(false);


        // Show UI
        UILibrary.ChessJFrame.setVisible(true);

        //ChessUIManager.showNewGameFrame();
        ChessUIManager.showMainFrame();
    }

}