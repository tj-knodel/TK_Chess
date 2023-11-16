package edu.kingsu.SoftwareEngineering.Chess.GUI;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


/**
 * Creates the Main Chess Frame
 * 
 * @author Noah Bulas
 * @version V1
 */
public class CreateMainFrame {

    /**
     * Gets the image from the local jar File
     * 
     * @param imageToGet Name of the image + type
     * @return The image
     */
    public ImageIcon getImage(String imageToGet) {
        return new ImageIcon(getClass().getClassLoader().getResource(imageToGet));
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates the JFrame used by the application
     */
    private void createJFrame() {
        UILibrary.ChessJFrame = new JFrame("Chess");
        UILibrary.ChessJFrame.setSize(new Dimension(UILibrary.uiSize_X, UILibrary.uiSize_Y));
        UILibrary.ChessJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // UILibrary.ChessJFrame.setResizable(false);
        UILibrary.ChessJFrame.setIconImage(getImage("ChessPiecesIcon_black.png").getImage());
        UILibrary.ChessJFrame.getContentPane().setBackground(UILibrary.BackgroundColor);
    }

    /**
     * Creates the MainFrame JLabel, which holds the chess board and its associated
     * frames
     */
    private void CreateMainContentPane() {
        UILibrary.MainFrame = new JLabel();
        UILibrary.MainFrame.setLayout(null);
        UILibrary.MainFrame.setBackground(UILibrary.BackgroundColor);
        UILibrary.resizeModule.setVariableBounds(UILibrary.MainFrame, null, 0, 0, UILibrary.uiSize_X,
                UILibrary.uiSize_Y);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates moves frame, forward/back buttons and text input
     */
    private void createUIElements() {

        JButton StepBackwards_Button = new JButton();
        UILibrary.resizeModule.setVariableBounds(StepBackwards_Button, null, 960, 762, 200, 79); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(StepBackwards_Button, getImage("StepBack.png"));
        StepBackwards_Button.setOpaque(false);
        StepBackwards_Button.setContentAreaFilled(false);
        StepBackwards_Button.setBorderPainted(false);
        UILibrary.MainFrame.add(StepBackwards_Button);
        UILibrary.StepBackwards_Button = StepBackwards_Button;

        JButton StepForwards_Button = new JButton();
        UILibrary.resizeModule.setVariableBounds(StepForwards_Button, null, 1180, 762, 200, 79); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(StepForwards_Button, getImage("StepForward.png"));
        StepForwards_Button.setOpaque(false);
        StepForwards_Button.setContentAreaFilled(false);
        StepForwards_Button.setBorderPainted(false);
        UILibrary.MainFrame.add(StepForwards_Button);
        UILibrary.StepForwards_Button = StepForwards_Button;

        JTextArea MovesLabel = new JTextArea(""); // 150 lines
        UILibrary.resizeModule.setVariableBounds(MovesLabel, null, 984, 209, 372, 531); // Numbers from Figma Design
        MovesLabel.setBackground(UILibrary.ForegroundColor);
        MovesLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 22));
        MovesLabel.setForeground(UILibrary.TextColor_White);
        MovesLabel.setEditable(false);
        MovesLabel.setHighlighter(null);
        UILibrary.MovesLabel = MovesLabel;
        //UILibrary.MainFrame.add(MovesLabel);
        UILibrary.MovesLabel_ScrollPane = new JScrollPane(MovesLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        UILibrary.resizeModule.setVariableBounds(UILibrary.MovesLabel_ScrollPane, null, 984, 209, 372, 531);
        UILibrary.MainFrame.add(UILibrary.MovesLabel_ScrollPane);
        UILibrary.MovesLabel_ScrollPane.setVisible(true);

        JLabel MovesFrame = new JLabel();
        UILibrary.resizeModule.setVariableBounds(MovesFrame, null, 935, 81, 470, 778); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(MovesFrame, getImage("MovesFrame.png"));
        MovesFrame.setOpaque(false);
        UILibrary.MainFrame.add(MovesFrame);

        JTextField EnterMove_TextField = new JTextField("To enter a move click here");
        UILibrary.resizeModule.setVariableBounds(EnterMove_TextField, null, 935, 880, 470, 49); // Numbers from Figma Design
        EnterMove_TextField.setBackground(UILibrary.ForegroundColor);
        EnterMove_TextField.setFont(new Font("Source Sans Pro", Font.BOLD, 20));
        EnterMove_TextField.setForeground(UILibrary.TextColor_Gray);
        EnterMove_TextField.setHorizontalAlignment(JTextField.CENTER);
        EnterMove_TextField.setBorder(BorderFactory.createEmptyBorder());
        UILibrary.MainFrame.add(EnterMove_TextField);
        UILibrary.EnterMove_TextField = EnterMove_TextField;
    }


    /**
     * Adds a focus listener to move text input
     * Clears Text Box on focus and restores instructions on focus lost
     */
    private void addTextFieldFocusListener() {
        // Add a focus listener
         UILibrary.EnterMove_TextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                     UILibrary.EnterMove_TextField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                     UILibrary.EnterMove_TextField.setText("To enter a move click here");
            }
        });
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Board UI Square, contains a 8x8 grid of chess tiles
     */
    private static JLayeredPane boardUI;

    /**
     * Double 8x8 array which holds each individual chess tile object
     */
    public static ChessTileUI boardTilesUI[][];

    /**
     * Create and set up the ChessTiles, add them to the grid
     * 
     * @return Array of chess tile ui elements
     */
    public static ChessTileUI[][] createChessBoard() {
        boardUI = new JLayeredPane();
        UILibrary.resizeModule.setVariableBounds(boardUI, null, 52, 81, 850, 850);
        boardUI.setLayout(new GridLayout(8, 8, 0, 0));

        boardTilesUI = new ChessTileUI[8][8];

        boolean displayWhite = true;
        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {

                boardTilesUI[row][column] = new ChessTileUI((char) row, (char) column, displayWhite);
                boardUI.add(boardTilesUI[row][column]);

                if (column != 7)
                    displayWhite = !displayWhite;
            }
        }

        UILibrary.MainFrame.add(boardUI);
        return boardTilesUI;
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates all the JMenus and JMenuItems which appear in the JFrame
     */
    private void createJMenus() {
        JMenuBar bar = new JMenuBar();
        bar.setBackground(UILibrary.ForegroundColor);
        bar.setForeground(UILibrary.TextColor_White);
        UILibrary.ChessJFrame.setJMenuBar(bar);

        // ---- GAME -------
        JMenu Game_JMenu = new JMenu("Game"); // button on the bar
        bar.add(Game_JMenu);
        Game_JMenu.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Game_JMenu.setForeground(UILibrary.TextColor_White);

        JMenuItem NewGame_Item = new JMenuItem("New Game");
        NewGame_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Game_JMenu.add(NewGame_Item);
        UILibrary.NewGame_JMenuItem = NewGame_Item;

        JMenuItem LoadGame_Item = new JMenuItem("Open Game");
        LoadGame_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Game_JMenu.add(LoadGame_Item);
        UILibrary.LoadGame_JMenuItem = LoadGame_Item;

        JMenuItem SaveGame_Item = new JMenuItem("Save Game");
        SaveGame_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Game_JMenu.add(SaveGame_Item);
        UILibrary.SaveGame_JMenuItem = SaveGame_Item;

        JMenuItem RestartGame_Item = new JMenuItem("Restart Game");
        RestartGame_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Game_JMenu.add(RestartGame_Item);
        UILibrary.RestartGame_JMenuItem = RestartGame_Item;

        JMenuItem ResumeGame_Item = new JMenuItem("Resume Game");
        ResumeGame_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Game_JMenu.add(ResumeGame_Item);
        UILibrary.ResumeGame_JMenuItem = ResumeGame_Item;

        // ---- BoardAppearance -------
        JMenu Appearance_JMenu = new JMenu("Board Appearance"); // button on the bar
        bar.add(Appearance_JMenu);
        Appearance_JMenu.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Appearance_JMenu.setForeground(UILibrary.TextColor_White);

        JMenuItem Possible_Item = new JMenuItem("Toggle Possible Moves");
        Possible_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Appearance_JMenu.add(Possible_Item);
        UILibrary.TogglePossibleMoves_JMenuItem = Possible_Item;

        JMenuItem Previous_Item = new JMenuItem("Toggle Previous Moves");
        Previous_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Appearance_JMenu.add(Previous_Item);
        UILibrary.TogglePreviousMoves_JMenuItem = Previous_Item;

        JMenuItem Coordinates_Item = new JMenuItem("Toggle Coordinates");
        Coordinates_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Appearance_JMenu.add(Coordinates_Item);
        UILibrary.ToggleCoordinates_JMenuItem = Coordinates_Item;

        JMenuItem Appearance_Item = new JMenuItem("Set Board Appearance");
        Appearance_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Appearance_JMenu.add(Appearance_Item);
        UILibrary.SetBoardAppearance_JMenuItem = Appearance_Item;

        JMenuItem FlipBoard = new JMenuItem("Flip Chess Board");
        FlipBoard.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Appearance_JMenu.add(FlipBoard);
        UILibrary.FlipBoard_JMenuItem = FlipBoard;

        // ---- Set Computer Strength -------
        JMenu ComputerStrength_JMenu = new JMenu("Set Computer Strength"); // button on the bar
        bar.add(ComputerStrength_JMenu);
        ComputerStrength_JMenu.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        ComputerStrength_JMenu.setForeground(UILibrary.TextColor_White);

        JMenuItem AiEasy_Item = new JMenuItem("Easy");
        AiEasy_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        ComputerStrength_JMenu.add(AiEasy_Item);
        UILibrary.SetAIStrengthEasy_JMenuItem = AiEasy_Item;

        JMenuItem AiMedium_Item = new JMenuItem("Medium");
        AiMedium_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        ComputerStrength_JMenu.add(AiMedium_Item);
        UILibrary.SetAIStrengthMedium_JMenuItem = AiMedium_Item;

        JMenuItem AiMax_Item = new JMenuItem("Max");
        AiMax_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        ComputerStrength_JMenu.add(AiMax_Item);
        UILibrary.SetAIStrengthMax_JMenuItem = AiMax_Item;

        JMenuItem StrengthSlider_Item = new JMenuItem("Show Strength Slider");
        StrengthSlider_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        ComputerStrength_JMenu.add(StrengthSlider_Item);
        UILibrary.ShowAIStrengthSlider_JMenuItem = StrengthSlider_Item;

        // ---- Help -------
        JMenu Help_JMenu = new JMenu("Help"); // button on the bar
        bar.add(Help_JMenu);
        Help_JMenu.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Help_JMenu.setForeground(UILibrary.TextColor_White);

        JMenuItem About_Item = new JMenuItem("About");
        About_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Help_JMenu.add(About_Item);
        UILibrary.About_JMenuItem = About_Item;

        JMenuItem Help_Item = new JMenuItem("Help");
        Help_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Help_JMenu.add(Help_Item);
        UILibrary.Help_JMenuItem = Help_Item;
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Array of board coordinates\n
     * boardLabels[0]-> boardLabels[7] hold the coordinates 1-8
     * boardLabels[8]-> boardLabels[15] hold the coordinates a-h
     */
    private static JLabel boardLabels[] = new JLabel[16];

        /**
     * Array of board coordinates, for when black is on the bottom\n
     * boardLabels[0]-> boardLabels[7] hold the coordinates 1-8
     * boardLabels[8]-> boardLabels[15] hold the coordinates a-h
     */
    private static JLabel boardLabels_Reverse[] = new JLabel[16];

    /**
     * Creates the board tile coordinates
     */
    private void createBoardMarkers() {

        // White Orientation Label
        boardLabels[0] = new JLabel("1", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[0], null, 22, 855, 28, 40); // Numbers from Figma Design
        boardLabels[1] = new JLabel("2", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[1], null, 22, 748, 28, 40); // Numbers from Figma Design
        boardLabels[2] = new JLabel("3", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[2], null, 22, 640, 28, 40); // Numbers from Figma Design
        boardLabels[3] = new JLabel("4", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[3], null, 22, 532, 28, 40); // Numbers from Figma Design
        boardLabels[4] = new JLabel("5", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[4], null, 22, 429, 28, 40); // Numbers from Figma Design
        boardLabels[5] = new JLabel("6", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[5], null, 22, 320, 28, 40); // Numbers from Figma Design
        boardLabels[6] = new JLabel("7", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[6], null, 22, 217, 28, 40); // Numbers from Figma Design
        boardLabels[7] = new JLabel("8", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[7], null, 22, 114, 28, 40); // Numbers from Figma Design

        boardLabels[8] = new JLabel("a", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[8], null, 92, 929, 28, 40); // Numbers from Figma Design
        boardLabels[9] = new JLabel("b", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[9], null, 195, 929, 28, 40); // Numbers from Figma Design
        boardLabels[10] = new JLabel("c", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[10], null, 304, 929, 28, 40); // Numbers from Figma Design
        boardLabels[11] = new JLabel("d", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[11], null, 411, 929, 28, 40); // Numbers from Figma Design
        boardLabels[12] = new JLabel("e", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[12], null, 522, 929, 28, 40); // Numbers from Figma Design
        boardLabels[13] = new JLabel("f", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[13], null, 621, 929, 28, 40); // Numbers from Figma Design
        boardLabels[14] = new JLabel("g", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[14], null, 724, 929, 28, 40); // Numbers from Figma Design
        boardLabels[15] = new JLabel("h", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels[15], null, 836, 929, 28, 40); // Numbers from Figma Design

        for (JLabel label : boardLabels) {
            label.setFont(new Font("Source Sans Pro", Font.BOLD, 24));
            label.setBackground(UILibrary.ForegroundColor);
            label.setForeground(UILibrary.TextColor_White);
            UILibrary.MainFrame.add(label);
            label.setVisible(true);
        }

        // Black Orientation
        boardLabels_Reverse[0] = new JLabel("1", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[0], null, 22, 124, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[1] = new JLabel("2", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[1], null, 22, 227, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[2] = new JLabel("3", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[2], null, 22, 330, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[3] = new JLabel("4", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[3], null, 22, 439, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[4] = new JLabel("5", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[4], null, 22, 542, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[5] = new JLabel("6", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[5], null, 22, 650, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[6] = new JLabel("7", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[6], null, 22, 758, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[7] = new JLabel("8", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[7], null, 22, 865, 28, 40); // Numbers from Figma Design

        boardLabels_Reverse[8] = new JLabel("a", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[8], null, 836, 929, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[9] = new JLabel("b", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[9], null, 724, 929, 28, 40); // Numbers from FigmaDesign
        boardLabels_Reverse[10] = new JLabel("c", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[10], null, 621, 929, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[11] = new JLabel("d", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[11], null, 522, 929, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[12] = new JLabel("e", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[12], null, 411, 929, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[13] = new JLabel("f", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[13], null, 304, 929, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[14] = new JLabel("g", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[14], null, 195, 929, 28, 40); // Numbers from Figma Design
        boardLabels_Reverse[15] = new JLabel("h", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(boardLabels_Reverse[15], null, 92, 929, 28, 40); // Numbers from Figma Design

        for (JLabel label : boardLabels_Reverse) {
            label.setFont(new Font("Source Sans Pro", Font.BOLD, 24));
            label.setBackground(UILibrary.ForegroundColor);
            label.setForeground(UILibrary.TextColor_White);
            UILibrary.MainFrame.add(label);
            label.setVisible(false);
        }

    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Holds how the board is oriented.
     * true = white on bottom, false = black on bottom
     */
    private static boolean whiteOnBottom = true;

    /**
     * Are the coordinates visible around the board
     */
     private boolean areCoordsVisible = true;


    /**
     * Sets the board orientation
     * @param isWhiteOnBottom true = white will be on bottom, false = black will be on bottom
     */
    private void setBoardOrientation(boolean isWhiteOnBottom) {

        whiteOnBottom = isWhiteOnBottom;
        boardUI.removeAll();

        if (whiteOnBottom) {

            for (JLabel label : boardLabels) {
                label.setVisible(true && areCoordsVisible);
            }
            for (JLabel label : boardLabels_Reverse) {
                label.setVisible(false);
            }

            // Chess Tiles, remove all the tiles and add them in reverse order
            for (int row = 0; row < 8; ++row) {
                for (int column = 0; column < 8; ++column) {
                    boardUI.add(boardTilesUI[row][column]);
                }
            }

        } else {
          
            for (JLabel label : boardLabels) {
                label.setVisible(false);
            }
            for (JLabel label : boardLabels_Reverse) {
                label.setVisible(true && areCoordsVisible);
            }

            for (int row = 7; row >= 0; --row) {
                for (int column = 7; column >= 0; --column) {
                    boardUI.add(boardTilesUI[row][column]);
                }
            }
        }

        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {
                CreateMainFrame.boardTilesUI[row][column].redrawTile();
            }
        }
        boardUI.repaint();
        UILibrary.ChessJFrame.repaint();
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Toggles the coordinates visibility
     */
    private void toggleCoordinatesVisibility() {
        areCoordsVisible = !areCoordsVisible;

        for (JLabel label : boardLabels) {
            label.setVisible(areCoordsVisible && whiteOnBottom);
        }
        for (JLabel label : boardLabels_Reverse) {
            label.setVisible(areCoordsVisible && !whiteOnBottom);
            
        }
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates the UI by calling all the create functions;
     * Add the JMenuItem event listeners for FlipBoard and ToggleCoordinates
     */
    public CreateMainFrame() {
        createJFrame();
        CreateMainContentPane();
        createUIElements();
        createJMenus();
        createBoardMarkers();
        addTextFieldFocusListener();

        // JMenu UI Events
        UILibrary.FlipBoard_JMenuItem.addActionListener(e -> {
            setBoardOrientation(!whiteOnBottom);
        });
        UILibrary.ToggleCoordinates_JMenuItem.addActionListener(e -> {
            toggleCoordinatesVisibility();
        });
    }

}