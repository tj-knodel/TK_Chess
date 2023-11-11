package edu.kingsu.SoftwareEngineering.Chess.GUI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;


/**
 * Creates the Start Frame;
 * Start Frames is where the user selects a game-mode
 * 
 * @author Noah Bulas
 * @version V1
 */
public class CreateStartFrame {


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
     * Creates the Start Frame JLabel which holds the buttons to select a specified game mode
     */
    private void CreateNewGameContentPanel() {
        UILibrary.NewGameFrame = new JLabel();
        UILibrary.NewGameFrame.setLayout(null);
        UILibrary.resizeModule.setVariableBounds(UILibrary.NewGameFrame, null, 0,0,UILibrary.uiSize_X, UILibrary.uiSize_Y);
        UILibrary.NewGameFrame.setBackground(UILibrary.BackgroundColor);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates , and game-mode buttons
     */
    private void createUIElements() {

        // Text Labels
        JLabel TitleLabel = new JLabel("New Chess Game", SwingConstants.CENTER);
        TitleLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 65));
        TitleLabel.setForeground(UILibrary.TextColor_White);
        UILibrary.resizeModule.setVariableBounds(TitleLabel, null, 437, 50, 566, 86); // Numbers from Figma Design
        TitleLabel.setOpaque(false);
        UILibrary.NewGameFrame.add(TitleLabel);

        JLabel TextLabel = new JLabel("Select a Game Mode:", SwingConstants.CENTER);
        TextLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 55));
        TextLabel.setForeground(UILibrary.TextColor_White);
        UILibrary.resizeModule.setVariableBounds(TextLabel, null, 426, 230, 588, 63); // Numbers from Figma Design
        TextLabel.setOpaque(false);
        UILibrary.NewGameFrame.add(TextLabel);

        // Buttons
        UILibrary.WhitePlayer_VS_BlackPlayer_Button = new JButton();
        UILibrary.resizeModule.setVariableBounds(UILibrary.WhitePlayer_VS_BlackPlayer_Button, null, 221, 320, 472, 190); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(UILibrary.WhitePlayer_VS_BlackPlayer_Button, getImage("White_Player_VS_Black_Player.png"));
        UILibrary.NewGameFrame.add(UILibrary.WhitePlayer_VS_BlackPlayer_Button);
        UILibrary.WhitePlayer_VS_BlackPlayer_Button.setOpaque(false);
        UILibrary.WhitePlayer_VS_BlackPlayer_Button.setContentAreaFilled(false);
        UILibrary.WhitePlayer_VS_BlackPlayer_Button.setBorderPainted(false);

        UILibrary.WhitePlayer_VS_BlackComp_Button = new JButton();
        UILibrary.resizeModule.setVariableBounds(UILibrary.WhitePlayer_VS_BlackComp_Button, null, 221, 530, 472, 190); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(UILibrary.WhitePlayer_VS_BlackComp_Button, getImage("White_Player_VS_Black_Comp.png"));
        UILibrary.NewGameFrame.add(UILibrary.WhitePlayer_VS_BlackComp_Button);
        UILibrary.WhitePlayer_VS_BlackComp_Button.setOpaque(false);
        UILibrary.WhitePlayer_VS_BlackComp_Button.setContentAreaFilled(false);
        UILibrary.WhitePlayer_VS_BlackComp_Button.setBorderPainted(false);
        

        UILibrary.WhiteComp_VS_BlackComp_Button = new JButton();
        UILibrary.resizeModule.setVariableBounds(UILibrary.WhiteComp_VS_BlackComp_Button, null, 221, 750, 472, 190); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(UILibrary.WhiteComp_VS_BlackComp_Button, getImage("White_Comp_VS_Black_Comp.png"));
        UILibrary.NewGameFrame.add(UILibrary.WhiteComp_VS_BlackComp_Button);
        UILibrary.WhiteComp_VS_BlackComp_Button.setOpaque(false);
        UILibrary.WhiteComp_VS_BlackComp_Button.setContentAreaFilled(false);
        UILibrary.WhiteComp_VS_BlackComp_Button.setBorderPainted(false);


        UILibrary.WhiteComp_VS_BlackPlayer_Button = new JButton();
        UILibrary.resizeModule.setVariableBounds(UILibrary.WhiteComp_VS_BlackPlayer_Button, null, 748, 320, 472, 190); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(UILibrary.WhiteComp_VS_BlackPlayer_Button, getImage("White_Comp_VS_Black_Player.png"));
        UILibrary.NewGameFrame.add(UILibrary.WhiteComp_VS_BlackPlayer_Button);
        UILibrary.WhiteComp_VS_BlackPlayer_Button.setOpaque(false);
        UILibrary.WhiteComp_VS_BlackPlayer_Button.setContentAreaFilled(false);
        UILibrary.WhiteComp_VS_BlackPlayer_Button.setBorderPainted(false);


        UILibrary.RDMPlayer_VS_RDMComp_Button = new JButton();
        UILibrary.resizeModule.setVariableBounds(UILibrary.RDMPlayer_VS_RDMComp_Button, null, 748, 530, 472, 190); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(UILibrary.RDMPlayer_VS_RDMComp_Button, getImage("RDM_Comp_VS_RDM_Player.png"));
        UILibrary.NewGameFrame.add(UILibrary.RDMPlayer_VS_RDMComp_Button);
        UILibrary.RDMPlayer_VS_RDMComp_Button.setOpaque(false);
        UILibrary.RDMPlayer_VS_RDMComp_Button.setContentAreaFilled(false);
        UILibrary.RDMPlayer_VS_RDMComp_Button.setBorderPainted(false);


        UILibrary.LearnChessButton = new JButton();
        UILibrary.resizeModule.setVariableBounds(UILibrary.LearnChessButton, null, 748, 750, 472, 190); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(UILibrary.LearnChessButton, getImage("LearnChessButton.png"));
        UILibrary.NewGameFrame.add(UILibrary.LearnChessButton);
        UILibrary.LearnChessButton.setOpaque(false);
        UILibrary.LearnChessButton.setContentAreaFilled(false);
        UILibrary.LearnChessButton.setBorderPainted(false);
    }

  

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Constructor which calls the functions to create the UI
     */
    public CreateStartFrame() {
        CreateNewGameContentPanel();
        createUIElements();
    
    }

}