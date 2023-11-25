package edu.kingsu.SoftwareEngineering.Chess.GUI;

import java.awt.Color;

import javax.swing.*;

/**
 * This file does not contain any real "functional" code, its more of a
 * library for all common variables and allows certain variables to be
 * passed around and used with ease.
 * 
 * @author Noah Bulas
 * @version XX.XX,23
 */
public abstract class UILibrary {

  /**
   * Size of the JFrame X in pixels
   */
  public static final int uiSize_X = 1455; // JFrame x Size // 1440
  /**
   * Size of the JFrame Y in pixels
   */
  public static final int uiSize_Y = 1040; // JFrame y Size 
  // Note to self, use figma y pos numbers - 10

  /**
   * The board appearance folder to be used, either make it point to a different
   * folder in src/assets or
   * change ChessTileUI.getBoardImage() to be absolute file path, not relative.
   */
  public static String boardAppearanceFolder = "BoardImages_Blue/";

  /**
   * Used with UILibrary.boardAppearanceFolder
   * true if UILibrary.boardAppearanceFolder is an absolute file path. 
   */
  public static boolean isAbsoluteFilePath = false;

  // ----- UI Elements-------------

  /**
   * The main JFrame used by the application
   */
  public static JFrame ChessJFrame; // Main JFrame

  /**
   * A JLabel which holds all the UI elements present on the main screen
   */
  public static JLabel MainFrame;

  /**
   * A JLabel which holds all the UI elements present on the new game screen
   */
  public static JLabel NewGameFrame;

  /**
   * A JLabel which holds all the UI elements present on the frame to set the AI
   * strength
   */
  public static JLabel SetAIStrengthSliderFrame;

  // ----- Main Frame-------------
  /**
   * Located in the MainFrame;
   * JButton to step backwards in the game
   */
  public static JButton StepBackwards_Button;

  /**
   * Located in the MainFrame;
   * JButton to step forwards in the game
   */
  public static JButton StepForwards_Button;

  /**
   * Located in the MainFrame;
   * JTextField to take text input (moves) from the user
   */
  public static JTextField EnterMove_TextField;

  /**
   * Located in the MainFrame;
   * JLabel which holds all the previous moves in chess notation.
   */
  // public static JLabel MovesLabel;
  public static JTextArea MovesLabel;

  /**
   * Shows white's timer
   */
  public static JLabel WhiteTimer;
  
  /**
   * Shows black's timer
   */
  public static JLabel BlackTimer;

  /**
   * Shows Whosever turn it is
   */
  public static JLabel PlayerTurn;

  // ----- New Game Frame-------------

  /**
   * Resume Game from New Game Frame
   */
  public static JButton ResumeGame_Button;

  /**
   * Located in the New Game Frame;
   * Selects the White Player vs Black Player Game mode.
   */
  public static JButton WhitePlayer_VS_BlackPlayer_Button;

  /**
   * Located in the New Game Frame;
   * Selects the White Player vs Black Computer AI Game mode.
   */
  public static JButton WhitePlayer_VS_BlackComp_Button;

  /**
   * Located in the New Game Frame;
   * Selects the White Computer AI vs Black Computer AI Game mode.
   */
  public static JButton WhiteComp_VS_BlackComp_Button;

  /**
   * Located in the New Game Frame;
   * Selects the White Computer AI vs Black Player Game mode.
   */
  public static JButton WhiteComp_VS_BlackPlayer_Button;

  /**
   * Located in the New Game Frame;
   * Selects the Random Player Color vs Random Computer AI Color Game mode.
   */
  public static JButton RDMPlayer_VS_RDMComp_Button;

  /**
   * Located in the New Game Frame;
   * Selects the learn chess 'Game mode'.
   */
  public static JButton LearnChessButton;


  /**
   * Popup shown in MainFrame;
   * JLabel which shows when the user upgrades a piece
   */
  public static JLabel UpgradePieceFrame;

  /**
   * Popup shown in MainFrame;
   * JLabel which is shown to the user when the game ends
   */
  public static JLabel EndGameFrame;

  /**
   * Scroll Pane in the Moves Label (label which shows the previous moves in algebraic notation)
   */
  public static JScrollPane MovesLabel_ScrollPane;

  /**
   * Shows on end game popup, Rematch game
   */
  public static JButton endRematchButton;
  /**
   * Shows on end game popup, View the current board
   */
  public static JButton endViewBoardButton;
  /**
   * Shows on end game popup, Start a new game
   */
  public static JButton endNewGameButton;

  // ----- MenuBar / Menu items -----------------
  /**
   * JMenuItem which selects a new game
   */
  public static JMenuItem NewGame_JMenuItem;

  /**
   * JMenuItem which selects load game, create game from pgn
   */
  public static JMenuItem LoadGame_JMenuItem;

  /**
   * JMenuItem which saves the current game to a Pgn file
   */
  public static JMenuItem SaveGame_JMenuItem;

  /**
   * JMenuItem which restarts the current game
   */
  public static JMenuItem RestartGame_JMenuItem;

  /**
   *  Resume the chess current game
   */
  public static JMenuItem ResumeGame_JMenuItem;

  /**
   * JMenuItem which toggles whether the possible move circles on the board are
   * shown or not.
   */
  public static JMenuItem TogglePossibleMoves_JMenuItem;

  /**
   * JMenuItem which toggles where the last board move is highlighted
   */
  public static JMenuItem TogglePreviousMoves_JMenuItem;

  /**
   * Allows the user to open a folder and change the appearance of the board using
   * the images in the folder
   * Images in the folder must be names: "pawn_black", "king_white",
   * "queen_white", etc for all 12 pieces and
   * "square_black", "square_white","PossibleMoveCircle", 15 Images total
   */
  public static JMenuItem SetBoardAppearance_JMenuItem;

  /**
   * JMenuItem which Toggles whether the coordinates on the board are shown or not
   */
  public static JMenuItem ToggleCoordinates_JMenuItem;

  /**
   * JMenuItem which flips the board so black appears on the bottom and white on
   * top or vise versa.
   */
  public static JMenuItem FlipBoard_JMenuItem;

  /**
   * JMenuItem which sets the AI Strength to easy
   */
  public static JMenuItem SetAIStrengthEasy_JMenuItem;

  /**
   * JMenuItem which sets the AI Strength to medium
   */
  public static JMenuItem SetAIStrengthMedium_JMenuItem;

  /**
   * JMenuItem which sets the AI Strength to max
   */
  public static JMenuItem SetAIStrengthMax_JMenuItem;

  /**
   * JMenuItem which shows the Set AI Strength Frame
   */
  public static JMenuItem ShowAIStrengthSlider_JMenuItem;

  /**
   * JMenuItem which shows the Set AI Strength Frame
   */
  public static JMenuItem ShowAITimeOutSlider_JMenuItem;

  /**
   * JMenuItem which shows the about section
   */
  public static JMenuItem About_JMenuItem;

  /**
  * JMenuItem which shows the about section
  */
  public static JMenuItem Help_JMenuItem;

  /*
   * JMenuItems can be connected by doing:
   * UILibrary.myJMenuItem.addActionListener(e -> {
   * System.out.println("My JMenu Item was clicked");
   * });
   */

  // ----- Colors -----------------
  /**
   * Main JFrame Background Color
   */
  public static final Color BackgroundColor = new Color(54, 53, 48);

  /**
   * Main JFrame Foreground Color
   */
  public static final Color ForegroundColor = new Color(42, 41, 37);

  /**
   * White Text Color for some text
   */
  public static final Color TextColor_White = new Color(255, 255, 255);

  /**
   * Gray Text Color for some text
   */
  public static final Color TextColor_Gray = new Color(192, 192, 192);

  /**
   * Color of the tiles which indicates the previous move happened on that tile.
   */
  public static final Color ForegroundTileColor = new Color(255, 227, 115, 70);

  /**
   * ResizeModule object, responsible for auto-scaling all the UI Elements
   */
  public static ResizeManager resizeModule;

  /**
   * Constructor not used
   */
  UILibrary() {
  }


}