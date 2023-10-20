package edu.kingsu.SoftwareEngineering.Chess.GUI;

import java.awt.Color;
import java.awt.Container;

import javax.swing.*;

/**
 This file does not contain any real "functional" code, its more of a
 library for all common variables and allows certain variable to be 
 passed around and used with ease.

  * @author Noah Bulas
 * @version XX.XX,23
 */
public abstract class UILibrary  {


    public static final int uiSize_X = 1455; //  JFrame x Size // 1440
    public static final int uiSize_Y = 1085; //  JFrame y Size // 1024


    //----- UI-------------
    
    // Main
    public static JFrame ChessJFrame; // Main JFrame

    public static JLabel MainFrame; // Was a Container, but background was broken
    public static JLabel NewGameFrame;
    public static JLabel SetAIStrengthSliderFrame;
    public static JLabel PuzzleFrame;

    // Main Frame
    public static JButton StepBackwards_Button;
    public static JButton StepForwards_Button;
    public static JTextField EnterMove_TextField;

    // New Game Frame
    public static JButton WhitePlayer_VS_BlackPlayer_Button;
    public static JButton WhitePlayer_VS_BlackComp_Button;
    public static JButton WhiteComp_VS_BlackComp_Button;
    public static JButton WhiteComp_VS_BlackPlayer_Button;
    public static JButton RDMPlayer_VS_RDMComp_Button;
    public static JButton LearnChessButton;  

    // Set Ai Strength Slider Frame
    public static JButton ConfirmSliderButton;
    public static JButton CancelSliderButton;
    public static JSlider SetAiStrengthSlider;


    // MenuBar  / Menu items
    public static JMenuItem NewGame_JMenuItem; 
    public static JMenuItem LoadGame_JMenuItem;
    public static JMenuItem SaveGame_JMenuItem; 
    public static JMenuItem RestartGame_JMenuItem; 
    public static JMenuItem TogglePossibleMoves_JMenuItem;
    public static JMenuItem TogglePreviousMoves_JMenuItem;
    public static JMenuItem SetBoardAppearance_JMenuItem;
    public static JMenuItem ToggleCoordinates_JMenuItem;
    public static JMenuItem SetAIStrengthEasy_JMenuItem;
    public static JMenuItem SetAIStrengthMedium_JMenuItem;
    public static JMenuItem SetAIStrengthMax_JMenuItem;
    public static JMenuItem ToggleAIStrengthSlider_JMenuItem;
    public static JMenuItem About_JMenuItem;

    /*
      JMenuItems can be connected by doing:
      UILibrary.myJMenuItem.addActionListener(e -> {
           System.out.println("My JMenu Item was clicked");
      });
     */

    // Colors
    public static final Color BackgroundColor = new Color(54,53,48); // Main JFrame Background Color
    public static final Color ForegroundColor = new Color(42,41,37); // Main JFrame Foreground Color
    public static final Color TextColor_White = new Color(255,255,255); // Text Color for all text
    public static final Color TextColor_Gray = new Color(192,192,192); // Text Color
    public static final Color ForegroundTileColor = new Color(255,227,115, 70); // Text Color


    /**
     * Constructor not Used
     */
    UILibrary(){}
}