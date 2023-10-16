package edu.kingsu.SoftwareEngineering.Chess.GUI;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

/**
 * Creates the JFrame
 * 
 * @author Noah Bulas
 * @version V1 Se,23
 */
public class CreateMainFrame {


     /**
         * Gets the image from the local jar File
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
            JFrame MainFrame = new JFrame("Chess");
            //MainFrame.setBounds(50,50,UILibrary.uiSize_X, UILibrary.uiSize_Y);
            MainFrame.setSize(new Dimension(UILibrary.uiSize_X, UILibrary.uiSize_Y));
            MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            MainFrame.setLayout(null);
            MainFrame.setResizable(false);

            // Icon
            MainFrame.setIconImage(getImage("ChessPiecesIcon_black.png").getImage());
            MainFrame.getContentPane().setBackground(UILibrary.BackgroundColor);

            UILibrary.MainFrame = MainFrame;
        }

        // -----------------------------------------------------
        // -----------------------------------------------------

        /**
           Creates watermark, moves frame, forward/back buttons and text input
        */
        private void createUIElements() {
            JLabel KingsU_Watermark = new JLabel("", SwingConstants.CENTER);
            KingsU_Watermark.setBounds(1372,970,54,42); // Numbers from Figma Design
            KingsU_Watermark.setIcon(new ImageIcon(getImage("KingsUCrown.png").getImage().getScaledInstance(54, 42, Image.SCALE_DEFAULT)));
            UILibrary.MainFrame.add(KingsU_Watermark);
            
            JButton StepBackwards_Button = new JButton();
            StepBackwards_Button.setBounds(960,772,200,79); // Numbers from Figma Design
            StepBackwards_Button.setIcon(new ImageIcon(getImage("StepBack.png").getImage().getScaledInstance(200, 79, Image.SCALE_DEFAULT)));
            StepBackwards_Button.setOpaque(false);
            StepBackwards_Button.setContentAreaFilled(false);
            StepBackwards_Button.setBorderPainted(false);
            UILibrary.MainFrame.add(StepBackwards_Button);
            UILibrary.StepBackwards_Button = StepBackwards_Button;
           
            JButton StepForwards_Button = new JButton();
            StepForwards_Button.setBounds(1180,772,200,79); // Numbers from Figma Design
            StepForwards_Button.setIcon(new ImageIcon(getImage("StepForward.png").getImage().getScaledInstance(200, 79, Image.SCALE_DEFAULT)));
            StepForwards_Button.setOpaque(false);
            StepForwards_Button.setContentAreaFilled(false);
            StepForwards_Button.setBorderPainted(false);
            UILibrary.MainFrame.add(StepForwards_Button);
            UILibrary.StepForwards_Button = StepForwards_Button;

            // TODO
            // DEFINE MOVES JLabel OR WHATEVER TO DISPLAY MOVE STRINGS "1. Qe4 "
            // TODO


            JLabel MovesFrame = new JLabel("", SwingConstants.CENTER);
            MovesFrame.setBounds(935,91,470,778); // Numbers from Figma Design
            MovesFrame.setIcon(new ImageIcon(getImage("MovesFrame.png").getImage().getScaledInstance(470, 778, Image.SCALE_DEFAULT)));
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

        // TEMPORARY
        private final String[][] board_TEMP = new String[][] { 
            {"R_B", "N_B", "B_B", "Q_B", "K_B", "B_B", "K_B", "R_B" },
            {"P_B", "P_B", "P_B", "P_B", "P_B", "P_B", "P_B", "P_B" },
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"P_W", "P_W", "P_W", "P_W", "P_W", "P_W", "P_W", "P_W" },
             {"R_W", "N_W", "B_W", "Q_W", "K_W", "B_W", "K_W", "R_W" },
        };

    /**
         * Gets the image from the local jar File
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
            boardUI.setLayout(new GridLayout(8,8));

            boolean displayWhite = false;
            for (int row = 0; row < 8; ++row) {
                  for (int column = 0; column < 8; ++column) {

                    boardTilesUI[row][column] = new ChessTileUI((char)row, (char)column, displayWhite);
                    boardUI.add(boardTilesUI[row][column]);

                    if (column != 7)
                        displayWhite = !displayWhite;
                    
                }
            }

           UILibrary.MainFrame.add(boardUI);
        }

        // -----------------------------------------------------
        // -----------------------------------------------------

        public CreateMainFrame() {
            createJFrame();
            createUIElements();
            createChessBoard();
        }

    }