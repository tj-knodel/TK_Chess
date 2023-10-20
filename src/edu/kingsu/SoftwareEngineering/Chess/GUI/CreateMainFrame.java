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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Set;

import edu.kingsu.SoftwareEngineering.Chess.GUI.ChessTileUI.PIECES_ENUM;

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
     * Creates the JFrame
     */
    private void createJFrame() {
        UILibrary.ChessJFrame = new JFrame("Chess");
        // MainFrame.setBounds(50,50,UILibrary.uiSize_X, UILibrary.uiSize_Y);
        UILibrary.ChessJFrame.setSize(new Dimension(UILibrary.uiSize_X, UILibrary.uiSize_Y));
        UILibrary.ChessJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UILibrary.ChessJFrame.setResizable(false);
        UILibrary.ChessJFrame.setIconImage(getImage("ChessPiecesIcon_black.png").getImage());
        UILibrary.ChessJFrame.getContentPane().setBackground(UILibrary.BackgroundColor);
    }

    private void CreateMainContentPane() {
        UILibrary.MainFrame = new JLabel();
        UILibrary.MainFrame.setLayout(null);
        UILibrary.MainFrame.setBackground(UILibrary.BackgroundColor);
        UILibrary.MainFrame.setBounds(0, 0, UILibrary.uiSize_X, UILibrary.uiSize_Y);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates watermark, moves frame, forward/back buttons and text input
     */
    private void createUIElements() {
        JLabel KingsU_Watermark = new JLabel("", SwingConstants.CENTER);
        KingsU_Watermark.setBounds(1372, 970, 54, 42); // Numbers from Figma Design
        KingsU_Watermark.setIcon(
                new ImageIcon(getImage("KingsUCrown.png").getImage().getScaledInstance(54, 42, Image.SCALE_DEFAULT)));
        UILibrary.MainFrame.add(KingsU_Watermark);

        JButton StepBackwards_Button = new JButton();
        StepBackwards_Button.setBounds(960, 772, 200, 79); // Numbers from Figma Design
        StepBackwards_Button.setIcon(
                new ImageIcon(getImage("StepBack.png").getImage().getScaledInstance(200, 79, Image.SCALE_DEFAULT)));
        StepBackwards_Button.setOpaque(false);
        StepBackwards_Button.setContentAreaFilled(false);
        StepBackwards_Button.setBorderPainted(false);
        UILibrary.MainFrame.add(StepBackwards_Button);
        UILibrary.StepBackwards_Button = StepBackwards_Button;

        JButton StepForwards_Button = new JButton();
        StepForwards_Button.setBounds(1180, 772, 200, 79); // Numbers from Figma Design
        StepForwards_Button.setIcon(
                new ImageIcon(getImage("StepForward.png").getImage().getScaledInstance(200, 79, Image.SCALE_DEFAULT)));
        StepForwards_Button.setOpaque(false);
        StepForwards_Button.setContentAreaFilled(false);
        StepForwards_Button.setBorderPainted(false);
        UILibrary.MainFrame.add(StepForwards_Button);
        UILibrary.StepForwards_Button = StepForwards_Button;

        // TODO
        // DEFINE MOVES JLabel OR WHATEVER TO DISPLAY MOVE STRINGS "1. Qe4 "
        // TODO

        JLabel MovesFrame = new JLabel("", SwingConstants.CENTER);
        MovesFrame.setBounds(935, 91, 470, 778); // Numbers from Figma Design
        MovesFrame.setIcon(
                new ImageIcon(getImage("MovesFrame.png").getImage().getScaledInstance(470, 778, Image.SCALE_DEFAULT)));
        MovesFrame.setOpaque(false);
        UILibrary.MainFrame.add(MovesFrame);

        JTextField EnterMove_TextField = new JTextField("To enter a move click here");
        EnterMove_TextField.setBounds(935, 890, 470, 49); // Numbers from Figma Design
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

    ///////////////////// TEMPORARY /////////////////////////
    private final String[][] board_TEMP = new String[][] {
            { "R_B", "N_B", "B_B", "Q_B", "K_B", "B_B", "K_B", "R_B" },
            { "P_B", "P_B", "P_B", "P_B", "P_B", "P_B", "P_B", "P_B" },
            { "", "", "", "", "", "", "", "" },
            { "", "", "", "", "", "", "", "" },
            { "", "", "", "", "", "", "", "" },
            { "", "", "", "", "", "", "", "" },
            { "P_W", "P_W", "P_W", "P_W", "P_W", "P_W", "P_W", "P_W" },
            { "R_W", "N_W", "B_W", "Q_W", "K_W", "B_W", "K_W", "R_W" },
    };
    ///////////////////// TEMPORARY /////////////////////////

    /**
     * Gets the image from the local jar File
     * 
     * @param imageToGet Name of the image + type
     * @return The image
     */
    public ImageIcon getBoardImage(String imageToGet) {
        return new ImageIcon(getClass().getClassLoader().getResource("BoardImages_Clash/" + imageToGet));
    }

    public ChessTileUI boardTilesUI[][] = new ChessTileUI[8][8];

    public void createChessBoard() {
        JLayeredPane boardUI = new JLayeredPane();
        boardUI.setBounds(52, 91, 848, 850);
        boardUI.setLayout(new GridLayout(8, 8));

        boolean displayWhite = false;
        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {

                boardTilesUI[row][column] = new ChessTileUI((char) row, (char) column, displayWhite);
                boardUI.add(boardTilesUI[row][column]);

                //////////// TEMP ////////////
                PIECES_ENUM piece = PIECES_ENUM.None;
                boolean isWhite = true;
                if (board_TEMP[row][column] == "R_B") {
                    piece = PIECES_ENUM.Rook;
                    isWhite = false;
                } else if (board_TEMP[row][column] == "N_B") {
                    piece = PIECES_ENUM.Knight;
                    isWhite = false;
                } else if (board_TEMP[row][column] == "B_B") {
                    piece = PIECES_ENUM.Bishop;
                    isWhite = false;
                } else if (board_TEMP[row][column] == "Q_B") {
                    piece = PIECES_ENUM.Queen;
                    isWhite = false;
                } else if (board_TEMP[row][column] == "K_B") {
                    piece = PIECES_ENUM.King;
                    isWhite = false;
                } else if (board_TEMP[row][column] == "P_B") {
                    piece = PIECES_ENUM.Pawn;
                    isWhite = false;
                } else if (board_TEMP[row][column] == "R_W") {
                    piece = PIECES_ENUM.Rook;
                    isWhite = true;
                } else if (board_TEMP[row][column] == "N_W") {
                    piece = PIECES_ENUM.Knight;
                    isWhite = true;
                } else if (board_TEMP[row][column] == "B_W") {
                    piece = PIECES_ENUM.Bishop;
                    isWhite = true;
                } else if (board_TEMP[row][column] == "Q_W") {
                    piece = PIECES_ENUM.Queen;
                    isWhite = true;
                } else if (board_TEMP[row][column] == "K_W") {
                    piece = PIECES_ENUM.King;
                    isWhite = true;
                } else if (board_TEMP[row][column] == "P_W") {
                    piece = PIECES_ENUM.Pawn;
                    isWhite = true;
                }

                boardTilesUI[row][column].setPieceImage(piece, isWhite);

                ////////////// TEMP /////////////////////

                if (column != 7)
                    displayWhite = !displayWhite;

            }
        }

        UILibrary.MainFrame.add(boardUI);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

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

        JMenuItem StrengthSlider_Item = new JMenuItem("Toggle Strength Slider");
        StrengthSlider_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        ComputerStrength_JMenu.add(StrengthSlider_Item);
        UILibrary.ToggleAIStrengthSlider_JMenuItem = StrengthSlider_Item;

        // ---- Help -------
        JMenu Help_JMenu = new JMenu("Help"); // button on the bar
        bar.add(Help_JMenu);
        Help_JMenu.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Help_JMenu.setForeground(UILibrary.TextColor_White);

        JMenuItem About_Item = new JMenuItem("About");
        About_Item.setFont(new Font("Source Sans Pro", Font.BOLD, 14));
        Help_JMenu.add(About_Item);
        UILibrary.SetAIStrengthEasy_JMenuItem = About_Item;
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    public CreateMainFrame() {
        createJFrame();
        CreateMainContentPane();
        createUIElements();
        createChessBoard();
        createJMenus();
    }

}