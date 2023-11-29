package edu.kingsu.SoftwareEngineering.Chess.GameLoop;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import edu.kingsu.SoftwareEngineering.Chess.Board.Board;
import edu.kingsu.SoftwareEngineering.Chess.Board.BoardLocation;
import edu.kingsu.SoftwareEngineering.Chess.Board.MoveResult;
import edu.kingsu.SoftwareEngineering.Chess.Board.Team;
import edu.kingsu.SoftwareEngineering.Chess.GUI.*;
import edu.kingsu.SoftwareEngineering.Chess.PGN.PGNMove;
import edu.kingsu.SoftwareEngineering.Chess.Players.AIPlayer;
import edu.kingsu.SoftwareEngineering.Chess.Players.AIThread;

/**
 * The class that acts as the middle man between the Board class and the GUI
 * functionality.
 *
 * @author Daniell Buchner
 * @version 1.0
 */
public class GameLoop {

    /**
     * The GUIStarter to handle some GUI.
     * @see GUIStarter
     */
    private final GUIStarter guiStarter;

    /**
     * The MoveController to handle player (human) inputs.
     * @see MoveController
     */
    private MoveController moveController;

    /**
     * The board for the chess game.
     * @see Board
     */
    private Board board;

    /**
     * The team the AI is playing.
     */
    private int aiTeam;

    /**
     * If it is AI vs AI.
     */
    private boolean aiVsAi = false;

    /**
     * The type of game that is being played.
     * @see GameType
     */
    private GameType gameType;

    /**
     * The white team's timer.
     * @see Timer
     */
    private Timer whiteTimer;

    /**
     * The black team's timer.
     * @see Timer
     */
    private Timer blackTimer;

    /**
     * All the tutorial moves when loading a tutorial.
     * @see PGNMove
     */
    private ArrayList<PGNMove> tutorialMoves;

    /**
     * The max AI think time in seconds.
     */
    private int aiThinkTimeSeconds;

    /**
     * The max ai strength level.
     */
    private int aiStrength;

    /**
     * Creates a new game loop class and sets up all the listeners
     * to the UILibrary.
     * @see UILibrary
     */
    public GameLoop() {
        this.aiThinkTimeSeconds = 1;
        this.aiStrength = 0;
        this.aiTeam = -1;
        this.moveController = new MoveController();
        this.tutorialMoves = new ArrayList<>();
        this.whiteTimer = new Timer(Team.WHITE_TEAM);
        this.blackTimer = new Timer(Team.BLACK_TEAM);
        this.guiStarter = new GUIStarter();
        UILibrary.ResumeGame_Button.setVisible(false);
        whiteTimer.startTimer();
        whiteTimer.pause();
        blackTimer.startTimer();
        blackTimer.pause();
        resetGUIAndListeners();
        ChessUIManager.showNewGameFrame();
        UILibrary.WhiteTimer.setText("");
        UILibrary.BlackTimer.setText("");

        UILibrary.WhitePlayer_VS_BlackPlayer_Button.addActionListener(e -> {
            this.gameType = GameType.PLAYER_VS_PLAYER;
            createGame(-1);
            setPlayerClickListeners();
            UILibrary.ResumeGame_Button.setVisible(true);
        });

        UILibrary.WhitePlayer_VS_BlackComp_Button.addActionListener(e -> {
            this.gameType = GameType.WHITE_PLAYER_VS_BLACK_AI;
            createGame(Team.BLACK_TEAM);
            setPlayerClickListeners();
            UILibrary.ResumeGame_Button.setVisible(true);
        });

        UILibrary.WhiteComp_VS_BlackPlayer_Button.addActionListener(e -> {
            this.gameType = GameType.WHITE_AI_VS_BLACK_PLAYER;
            createGame(Team.WHITE_TEAM);
            setPlayerClickListeners();
            UILibrary.ResumeGame_Button.setVisible(true);
        });

        UILibrary.WhiteComp_VS_BlackComp_Button.addActionListener(e -> {
            this.gameType = GameType.AI_VS_AI;
            createGame(Team.WHITE_TEAM);
            aiVsAi = true;
            UILibrary.ResumeGame_Button.setVisible(true);
        });

        UILibrary.RDMPlayer_VS_RDMComp_Button.addActionListener(e -> {
            Random random = new Random(System.currentTimeMillis());
            int aiTeam = random.nextInt(2);
            if (aiTeam == Team.WHITE_TEAM)
                this.gameType = GameType.WHITE_AI_VS_BLACK_PLAYER;
            else
                this.gameType = GameType.WHITE_PLAYER_VS_BLACK_AI;
            createGame(aiTeam);
            setPlayerClickListeners();
            UILibrary.ResumeGame_Button.setVisible(true);
        });

        CreateCompSliderFrame temp = new CreateCompSliderFrame();
        UILibrary.ShowAIStrengthSlider_JMenuItem.addActionListener(e -> {
            Integer value = temp.showAISlider(25);
            if (value != null) {
                aiStrength = value;
            }
        });
        UILibrary.ShowAITimeOutSlider_JMenuItem.addActionListener(e -> {
            Integer value = temp.showAISliderTimeOut(3);
            if (value != null) {
                aiThinkTimeSeconds = value;
            }
        });

        UILibrary.StepBackwards_Button.addActionListener(e -> {
            if (aiVsAi)
                return;
            this.board.undoMove();
            setCommentString();
            redrawUI();
        });

        UILibrary.StepForwards_Button.addActionListener(e -> {
            if (aiVsAi)
                return;
            this.board.redoMove();
            setCommentString();
            redrawUI();
        });

        // Used https://stackoverflow.com/questions/14589386/how-to-save-file-using-jfilechooser-in-java
        UILibrary.SaveGame_JMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    FileWriter fw = new FileWriter(file);
                    fw.write(board.getAlgebraicNotation());
                    fw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        UILibrary.LoadGame_JMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                createGame(-1);
                board.loadPGNFile(file);
                redrawUI();
                setPlayerClickListeners();
            }
        });

        UILibrary.EnterMove_TextField.addActionListener(e -> {
            String input = UILibrary.EnterMove_TextField.getText();
            if (aiTeam == board.getTeamTurn())
                return;
            MoveResult result = board.applyMovePGNNotationOverride(input);
            if (!result.isSuccessful())
                JOptionPane.showMessageDialog(null, "The notation " + input + " is incorrect!", "Invalid Move!",
                        JOptionPane.INFORMATION_MESSAGE);
            redrawUI();
            UILibrary.EnterMove_TextField.setText("");
        });

        UILibrary.NewGame_JMenuItem.addActionListener(e -> {
            board.setIsPaused(true);
            pauseTimers();
            ChessUIManager.showNewGameFrame();
        });

        UILibrary.RestartGame_JMenuItem.addActionListener(e -> {
            restartGame();
        });

        UILibrary.ResumeGame_JMenuItem.addActionListener(e -> {
            board.setIsPaused(false);
            resumeGame();
        });

        UILibrary.ResumeGame_Button.addActionListener(e -> {
            board.setIsPaused(false);
            resumeGame();
        });

        UILibrary.endNewGameButton.addActionListener(e -> {
            board.setIsPaused(true);
            ChessUIManager.showNewGameFrame();
        });

        UILibrary.endRematchButton.addActionListener(e -> {
            restartGame();
            resetGUIAndListeners();
            setPlayerClickListeners();
        });

        UILibrary.endViewBoardButton.addActionListener(e -> {
            resumeGame();
            resetGUIAndListeners();
            setPlayerClickListeners();
            ChessUIManager.HideEndGameFrame();
        });

        UILibrary.LearnChessButton.addActionListener(e -> {
            // The JOption pane stuff taken from: https://stackoverflow.com/questions/43658679/show-joptionpane-with-dropdown-menu-on-the-top-of-other-windows
            Object[] options = { "Overview", "Capturing Pieces", "Capturing Pieces Knight", "Castling", "Checking",
                    "Checkmate", "Promotion" };
            Object selectionObject = JOptionPane.showInputDialog(null, "Choose", "Tutorials Available",
                    JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            if (selectionObject == null)
                return;
            String selectedString = String.valueOf(selectionObject);

            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("PGN Tutorials/" + selectedString.replace(" ", "") + ".pgn");
            this.gameType = GameType.TUTORIAL;
            createGame(-1);
            this.tutorialMoves = board.loadPGNFileFromStart(inputStream);
            clearChessNotationLabel();
        });
    }

    /**
     * Restart a game based on the game type.
     */
    private void restartGame() {
        resetGUIAndListeners();
        if (gameType == GameType.AI_VS_AI) {
            createGame(Team.WHITE_TEAM);
            aiVsAi = true;
        } else if (gameType == GameType.PLAYER_VS_PLAYER) {
            createGame(-1);
            setPlayerClickListeners();
        } else if (gameType == GameType.WHITE_AI_VS_BLACK_PLAYER) {
            createGame(Team.WHITE_TEAM);
            setPlayerClickListeners();
        } else if (gameType == GameType.WHITE_PLAYER_VS_BLACK_AI) {
            createGame(Team.BLACK_TEAM);
            setPlayerClickListeners();
        }
    }

    /**
     * Resumes a game.
     */
    private void resumeGame() {
        board.setIsPaused(false);
        if (gameType != GameType.AI_VS_AI) {
            resetGUIAndListeners();
            setPlayerClickListeners();
        }
        switchTimers();
        ChessUIManager.showMainFrame();
        redrawUI();
        if (aiVsAi || aiTeam == board.getTeamTurn()) {
            runAI();
        }
    }

    /**
     * Create a new game with or without an AI.
     * @param aiTeam The team the AI should be. -1 for no AI.
     */
    private void createGame(int aiTeam) {
        ChessUIManager.HideEndGameFrame();
        ChessUIManager.showMainFrame();
        clearChessNotationLabel();
        aiVsAi = false;
        this.board = new Board(this);
        this.aiTeam = aiTeam;
        redrawUI();
        resetTimers();
        sendUpdateBoardState();
        resetGUIAndListeners();
    }

    /**
     * Reset the timer values.
     */
    private void resetTimers() {
        UILibrary.WhiteTimer.setText("WHITE TIME: 00:00");
        UILibrary.BlackTimer.setText("BLACK TIME: 00:00");
        whiteTimer.resetTimer();
        blackTimer.resetTimer();
        pauseTimers();
    }

    /**
     * Pause both timers.
     */
    private void pauseTimers() {
        whiteTimer.pause();
        blackTimer.pause();
    }

    /**
     * Sets the loading pawn to spin.
     * @param isLoading Should the pawn spin and be visible.
     */
    public void setLoadingPawn(boolean isLoading) {
        guiStarter.chessUIManager.setLoadIconVisibility(isLoading);
    }

    /**
     * Updates the turns and switches timers if the resulting move was successful.
     */
    public void sendUpdateBoardState() {
        MoveResult result = board.getLastMoveResult();
        if (result != null)
            checkEndGameState(result);
        if (aiVsAi) {
            aiTeam = (aiTeam == Team.WHITE_TEAM) ? Team.BLACK_TEAM : Team.WHITE_TEAM;
        }
        String teamName = (board.getTeamTurn() == Team.WHITE_TEAM) ? "WHITE'S TURN" : "BLACK'S TURN";
        switchTimers();
        UILibrary.PlayerTurn.setText(teamName);
        runAI();
    }

    /**
     * Checks if the game is finished.
     * @param result The last move to check against.
     */
    private void checkEndGameState(MoveResult result) {
        if (result.isCheckmate()) {
            showCheckmatePopup(result);
        } else if (result.isStalemate()) {
            showStalemateScreen();
        }
    }

    /**
     * Show the stalemate screen.
     */
    private void showStalemateScreen() {
        ChessUIManager.ShowEndGameFrame("Stalemate!");
        board.setIsPaused(true);
    }

    /**
     * Show the checkmate popup.
     * @param result The move result of the last move to check for which team won.
     */
    private void showCheckmatePopup(MoveResult result) {
        ChessUIManager.ShowEndGameFrame(
                ((result.getCheckmateTeam() == Team.WHITE_TEAM) ? "Black" : "White") + " team wins!");
        board.setIsPaused(true);
    }

    /**
     * Switch the timers for how they are counting.
     */
    private void switchTimers() {
        if (board.getTeamTurn() == Team.WHITE_TEAM) {
            whiteTimer.unpause();
            blackTimer.pause();
        } else if (board.getTeamTurn() == Team.BLACK_TEAM) {
            blackTimer.unpause();
            whiteTimer.pause();
        }
    }

    /**
     * Set the notation label to be the comment string if tutorial was selected.
     */
    private void setCommentString() {
        if (gameType == GameType.TUTORIAL) {
            String comment = tutorialMoves.get(tutorialMoves.size() - board.getUndoMoveCount() - 1).getComment();
            if (comment != null) {
                clearChessNotationLabel();
                ChessUIManager.appendMovesLabel(comment);
            }
        }
    }

    /**
     * Runs the AI.
     */
    private void runAI() {
        if (board.getIsPaused())
            return;
        if (aiTeam == board.getTeamTurn()) {
            AIThread ai = new AIThread(new AIPlayer((int) (aiStrength / 100.0 * 10.0), aiTeam, "Smart AI", this), board,
                    this);
            Thread runningThread = new Thread(ai);
            runningThread.start();
        }
    }

    /**
     * Gets the promotion piece when a pawn is promoting.
     * @return A single character as a string of the promotion piece.
     */
    public String getPromotionPiece() {
        if (aiTeam == board.getTeamTurn())
            return "Q";
        else {
            String result = guiStarter.chessUIManager.showUpgradeFrame(board.getTeamTurn() == Team.WHITE_TEAM);
            if (result == null)
                return "P";
            if (result.equalsIgnoreCase("KNIGHT"))
                return "N";
            return String.valueOf(result.charAt(0));
        }
    }

    /**
     * Updates the chess notation label on the right of the GUI.
     * @param value The value to add to the notation.
     */
    public void updateChessNotationLabel(String value) {
        ChessUIManager.appendMovesLabel(value);
    }

    /**
     * Clears the notation label on the right of the GUI.
     */
    public void clearChessNotationLabel() {
        ChessUIManager.clearMovesLabel();
    }

    /**
     * Get the max AI think time in seconds the AI should think.
     * @return The max think time in seconds.
     */
    public int getAiThinkTimeSeconds() {
        return aiThinkTimeSeconds;
    }

    /**
     * Resets all the GUi and event listeners
     * to how it was when the game was in the main menu.
     */
    private void resetGUIAndListeners() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessTileUI chessTile = guiStarter.chessUIManager.boardTiles[j][i];
                for (MouseListener adapter : chessTile.getMouseListeners()) {
                    chessTile.removeMouseListener(adapter);
                }
            }
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                guiStarter.chessUIManager.boardTiles[r][c].setPreviousMoveSquareVisibility(false);
            }
        }
    }

    /**
     * Get an instance of this class.
     * @return This class.
     */
    private GameLoop getGameLoop() {
        return this;
    }

    /**
     * Set the click listeners on the chess tiles for the player.
     */
    private void setPlayerClickListeners() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessTileUI chessTile = guiStarter.chessUIManager.boardTiles[j][i];
                chessTile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (aiTeam == board.getTeamTurn())
                            return;
                        moveController.handleClick(board, chessTile, guiStarter, getGameLoop());
                    }
                });
            }
        }
    }

    /**
     * Redraws the GUI (updates it).
     */
    public void redrawUI() {
        if (!board.isAtStart()) {
            BoardLocation lastMove = board.getLastMoveLocation();
            BoardLocation currentMove = board.getCurrentMoveLocation();
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if (!moveController.getIsFirstClick()) {
                        if (r == moveController.getFirstClickLocation().row
                                && c == moveController.getFirstClickLocation().column)
                            continue;
                    }
                    guiStarter.chessUIManager.boardTiles[r][c].setPreviousMoveSquareVisibility(false);
                }
            }
            guiStarter.chessUIManager.boardTiles[lastMove.row][lastMove.column]
                    .setPreviousMoveSquareVisibility(true);
            guiStarter.chessUIManager.boardTiles[currentMove.row][currentMove.column]
                    .setPreviousMoveSquareVisibility(true);
        }

        guiStarter.chessUIManager.drawBoard(board.getBoard());
        UILibrary.MainFrame.repaint();
    }
}