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
import java.awt.Image;

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
        UILibrary.ChessJFrame.setResizable(false);
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
        UILibrary.MainFrame.setBounds(0, 0, UILibrary.uiSize_X, UILibrary.uiSize_Y);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates moves frame, forward/back buttons and text input
     */
    private void createUIElements() {

        JButton StepBackwards_Button = new JButton();
        StepBackwards_Button.setBounds(960, 762, 200, 79); // Numbers from Figma Design
        StepBackwards_Button.setIcon(
                new ImageIcon(getImage("StepBack.png").getImage().getScaledInstance(200, 79, Image.SCALE_DEFAULT)));
        StepBackwards_Button.setOpaque(false);
        StepBackwards_Button.setContentAreaFilled(false);
        StepBackwards_Button.setBorderPainted(false);
        UILibrary.MainFrame.add(StepBackwards_Button);
        UILibrary.StepBackwards_Button = StepBackwards_Button;

        JButton StepForwards_Button = new JButton();
        StepForwards_Button.setBounds(1180, 762, 200, 79); // Numbers from Figma Design
        StepForwards_Button.setIcon(
                new ImageIcon(getImage("StepForward.png").getImage().getScaledInstance(200, 79, Image.SCALE_DEFAULT)));
        StepForwards_Button.setOpaque(false);
        StepForwards_Button.setContentAreaFilled(false);
        StepForwards_Button.setBorderPainted(false);
        UILibrary.MainFrame.add(StepForwards_Button);
        UILibrary.StepForwards_Button = StepForwards_Button;

        JTextArea MovesLabel = new JTextArea(""); // 150 lines
        MovesLabel.setBounds(984, 209, 372, 531); // Numbers from Figma Design
        MovesLabel.setBackground(UILibrary.ForegroundColor);
        MovesLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 22));
        MovesLabel.setForeground(UILibrary.TextColor_White);
        MovesLabel.setEditable(false);
        MovesLabel.setHighlighter(null);
        UILibrary.MovesLabel = MovesLabel;
        JScrollPane MovesLabel_ScrollPane = new JScrollPane(MovesLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        MovesLabel_ScrollPane.setBounds(984, 209, 372, 531);
        UILibrary.MainFrame.add(MovesLabel_ScrollPane);
        MovesLabel_ScrollPane.setVisible(true);
        MovesLabel_ScrollPane.setBorder(BorderFactory.createEmptyBorder());

        JLabel MovesFrame = new JLabel();
        MovesFrame.setBounds(935, 81, 470, 778); // Numbers from Figma Design
        MovesFrame.setIcon(
                new ImageIcon(getImage("MovesFrame.png").getImage().getScaledInstance(470, 778, Image.SCALE_DEFAULT)));
        MovesFrame.setOpaque(false);
        UILibrary.MainFrame.add(MovesFrame);

        JTextField EnterMove_TextField = new JTextField("To enter a move click here");
        EnterMove_TextField.setBounds(935, 880, 470, 49); // Numbers from Figma Design
        EnterMove_TextField.setBackground(UILibrary.ForegroundColor);
        EnterMove_TextField.setFont(new Font("Source Sans Pro", Font.BOLD, 20));
        EnterMove_TextField.setForeground(UILibrary.TextColor_Gray);
        EnterMove_TextField.setHorizontalAlignment(JTextField.CENTER);
        EnterMove_TextField.setBorder(BorderFactory.createEmptyBorder());
        UILibrary.MainFrame.add(EnterMove_TextField);
        UILibrary.EnterMove_TextField = EnterMove_TextField;
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
    private static ChessTileUI boardTilesUI[][];

    /**
     * Create and set up the ChessTiles, add them to the grid
     * 
     * @return Array of chess tile ui elements
     */
    public static ChessTileUI[][] createChessBoard() {
        boardUI = new JLayeredPane();
        boardUI.setBounds(52, 81, 848, 850);
        boardUI.setLayout(new GridLayout(8, 8));

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

    /**
     * Redraws the tile, re-gets images
     */
    public static void redrawTiles() {
        for (ChessTileUI[] row : boardTilesUI) {
            for (ChessTileUI tile : row) {
                tile.redrawTile();
            }
        }
        UILibrary.MainFrame.repaint();
        UILibrary.ChessJFrame.repaint();
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

        JMenuItem LoadGame_Item = new JMenuItem("Load Game");
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
     * Creates the board tile coordinates
     */
    private void createBoardMarkers() {

        boardLabels[0] = new JLabel("1", SwingConstants.CENTER);
        boardLabels[0].setBounds(22, 855, 28, 40); // Numbers from Figma Design
        boardLabels[1] = new JLabel("2", SwingConstants.CENTER);
        boardLabels[1].setBounds(22, 748, 28, 40); // Numbers from Figma Design
        boardLabels[2] = new JLabel("3", SwingConstants.CENTER);
        boardLabels[2].setBounds(22, 640, 28, 40); // Numbers from Figma Design
        boardLabels[3] = new JLabel("4", SwingConstants.CENTER);
        boardLabels[3].setBounds(22, 532, 28, 40); // Numbers from Figma Design
        boardLabels[4] = new JLabel("5", SwingConstants.CENTER);
        boardLabels[4].setBounds(22, 429, 28, 40); // Numbers from Figma Design
        boardLabels[5] = new JLabel("6", SwingConstants.CENTER);
        boardLabels[5].setBounds(22, 320, 28, 40); // Numbers from Figma Design
        boardLabels[6] = new JLabel("7", SwingConstants.CENTER);
        boardLabels[6].setBounds(22, 217, 28, 40); // Numbers from Figma Design
        boardLabels[7] = new JLabel("8", SwingConstants.CENTER);
        boardLabels[7].setBounds(22, 114, 28, 40); // Numbers from Figma Design

        boardLabels[8] = new JLabel("a", SwingConstants.CENTER);
        boardLabels[8].setBounds(92, 929, 28, 40); // Numbers from Figma Design
        boardLabels[9] = new JLabel("b", SwingConstants.CENTER);
        boardLabels[9].setBounds(195, 929, 28, 40); // Numbers from Figma Design
        boardLabels[10] = new JLabel("c", SwingConstants.CENTER);
        boardLabels[10].setBounds(304, 929, 28, 40); // Numbers from Figma Design
        boardLabels[11] = new JLabel("d", SwingConstants.CENTER);
        boardLabels[11].setBounds(411, 929, 28, 40); // Numbers from Figma Design
        boardLabels[12] = new JLabel("e", SwingConstants.CENTER);
        boardLabels[12].setBounds(522, 929, 28, 40); // Numbers from Figma Design
        boardLabels[13] = new JLabel("f", SwingConstants.CENTER);
        boardLabels[13].setBounds(621, 929, 28, 40); // Numbers from Figma Design
        boardLabels[14] = new JLabel("g", SwingConstants.CENTER);
        boardLabels[14].setBounds(724, 929, 28, 40); // Numbers from Figma Design
        boardLabels[15] = new JLabel("h", SwingConstants.CENTER);
        boardLabels[15].setBounds(836, 929, 28, 40); // Numbers from Figma Design

        for (JLabel label : boardLabels) {
            label.setFont(new Font("Source Sans Pro", Font.BOLD, 24));
            label.setBackground(UILibrary.ForegroundColor);
            label.setForeground(UILibrary.TextColor_White);
            UILibrary.MainFrame.add(label);
            label.setVisible(true);
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
     * Sets the board orientation
     * 
     * @param isWhiteOnBottom true = white will be on bottom, false = black will be
     *                        on bottom
     */
    private static void setBoardOrientation(boolean isWhiteOnBottom) {
        whiteOnBottom = isWhiteOnBottom;
        if (whiteOnBottom) {
            // 1-8
            boardLabels[0].setBounds(22, 865, 28, 40); // Numbers from Figma Design
            boardLabels[1].setBounds(22, 758, 28, 40); // Numbers from Figma Design
            boardLabels[2].setBounds(22, 650, 28, 40); // Numbers from Figma Design
            boardLabels[3].setBounds(22, 542, 28, 40); // Numbers from Figma Design
            boardLabels[4].setBounds(22, 439, 28, 40); // Numbers from Figma Design
            boardLabels[5].setBounds(22, 330, 28, 40); // Numbers from Figma Design
            boardLabels[6].setBounds(22, 227, 28, 40); // Numbers from Figma Design
            boardLabels[7].setBounds(22, 124, 28, 40); // Numbers from Figma Design

            // a - h
            boardLabels[8].setBounds(92, 939, 28, 40); // Numbers from Figma Design
            boardLabels[9].setBounds(195, 939, 28, 40); // Numbers from Figma Design
            boardLabels[10].setBounds(304, 939, 28, 40); // Numbers from Figma Design
            boardLabels[11].setBounds(411, 939, 28, 40); // Numbers from Figma Design
            boardLabels[12].setBounds(522, 939, 28, 40); // Numbers from Figma Design
            boardLabels[13].setBounds(621, 939, 28, 40); // Numbers from Figma Design
            boardLabels[14].setBounds(724, 939, 28, 40); // Numbers from Figma Design
            boardLabels[15].setBounds(836, 939, 28, 40); // Numbers from Figma Design

            // Chess Tiles, remove all the tiles and add them in reverse order
            boardUI.removeAll();
            for (int row = 0; row < 8; ++row) {
                for (int column = 0; column < 8; ++column) {
                    boardUI.add(boardTilesUI[row][column]);
                }
            }

        } else {
            // 1-8
            boardLabels[0].setBounds(22, 124, 28, 40); // Numbers from Figma Design
            boardLabels[1].setBounds(22, 227, 28, 40); // Numbers from Figma Design
            boardLabels[2].setBounds(22, 330, 28, 40); // Numbers from Figma Design
            boardLabels[3].setBounds(22, 439, 28, 40); // Numbers from Figma Design
            boardLabels[4].setBounds(22, 542, 28, 40); // Numbers from Figma Design
            boardLabels[5].setBounds(22, 650, 28, 40); // Numbers from Figma Design
            boardLabels[6].setBounds(22, 758, 28, 40); // Numbers from Figma Design
            boardLabels[7].setBounds(22, 865, 28, 40); // Numbers from Figma Design

            // a - h
            boardLabels[8].setBounds(836, 939, 28, 40); // Numbers from Figma Design
            boardLabels[9].setBounds(724, 939, 28, 40); // Numbers from Figma Design
            boardLabels[10].setBounds(621, 939, 28, 40); // Numbers from Figma Design
            boardLabels[11].setBounds(522, 939, 28, 40); // Numbers from Figma Design
            boardLabels[12].setBounds(411, 939, 28, 40); // Numbers from Figma Design
            boardLabels[13].setBounds(304, 939, 28, 40); // Numbers from Figma Design
            boardLabels[14].setBounds(195, 939, 28, 40); // Numbers from Figma Design
            boardLabels[15].setBounds(92, 939, 28, 40); // Numbers from Figma Design

            boardUI.removeAll();
            for (int row = 7; row >= 0; --row) {
                for (int column = 7; column >= 0; --column) {
                    boardUI.add(boardTilesUI[row][column]);
                }
            }
        }

        redrawTiles();
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Toggles the coordinates visibility
     */
    private void toggleCoordinatesVisibility() {
        for (JLabel label : boardLabels) {
            label.setVisible(!label.isVisible());
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

        // JMenu UI Events
        UILibrary.FlipBoard_JMenuItem.addActionListener(e -> {
            setBoardOrientation(!whiteOnBottom);
        });
        UILibrary.ToggleCoordinates_JMenuItem.addActionListener(e -> {
            toggleCoordinatesVisibility();
        });
    }

}