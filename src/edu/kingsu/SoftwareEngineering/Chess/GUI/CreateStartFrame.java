package edu.kingsu.SoftwareEngineering.Chess.GUI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;


/**
 * Creates the Start Frame
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

    private void CreateNewGameContentPanel() {
        UILibrary.NewGameFrame = new JLabel();
        UILibrary.NewGameFrame.setLayout(null);
        UILibrary.NewGameFrame.setBounds(0,0,UILibrary.uiSize_X, UILibrary.uiSize_Y);
        UILibrary.NewGameFrame.setBackground(UILibrary.BackgroundColor);
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
        UILibrary.NewGameFrame.add(KingsU_Watermark);

        // Text Labels
        JLabel TitleLabel = new JLabel("New Chess Game", SwingConstants.CENTER);
        TitleLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 65));
        TitleLabel.setForeground(UILibrary.TextColor_White);
        TitleLabel.setBounds(437, 74, 566, 86); // Numbers from Figma Design
        TitleLabel.setOpaque(false);
        UILibrary.NewGameFrame.add(TitleLabel);

        JLabel TextLabel = new JLabel("Select a Game Mode:", SwingConstants.CENTER);
        TextLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 55));
        TextLabel.setForeground(UILibrary.TextColor_White);
        TextLabel.setBounds(426, 260, 588, 63); // Numbers from Figma Design
        TextLabel.setOpaque(false);
        UILibrary.NewGameFrame.add(TextLabel);

        // Buttons
        UILibrary.WhitePlayer_VS_BlackPlayer_Button = new JButton();
        UILibrary.WhitePlayer_VS_BlackPlayer_Button.setBounds(221, 365, 472, 190); // Numbers from Figma Design
        UILibrary.WhitePlayer_VS_BlackPlayer_Button.setIcon(
            new ImageIcon(getImage("White_Player_VS_Black_Player.png").getImage().getScaledInstance(472, 190, Image.SCALE_DEFAULT)));
        UILibrary.NewGameFrame.add(UILibrary.WhitePlayer_VS_BlackPlayer_Button);
        UILibrary.WhitePlayer_VS_BlackPlayer_Button.setOpaque(false);
        UILibrary.WhitePlayer_VS_BlackPlayer_Button.setContentAreaFilled(false);
        UILibrary.WhitePlayer_VS_BlackPlayer_Button.setBorderPainted(false);

        UILibrary.WhitePlayer_VS_BlackComp_Button = new JButton();
        UILibrary.WhitePlayer_VS_BlackComp_Button.setBounds(221, 575, 472, 190); // Numbers from Figma Design
        UILibrary.WhitePlayer_VS_BlackComp_Button.setIcon(
            new ImageIcon(getImage("White_Player_VS_Black_Comp.png").getImage().getScaledInstance(472, 190, Image.SCALE_DEFAULT)));
        UILibrary.NewGameFrame.add(UILibrary.WhitePlayer_VS_BlackComp_Button);
        UILibrary.WhitePlayer_VS_BlackComp_Button.setOpaque(false);
        UILibrary.WhitePlayer_VS_BlackComp_Button.setContentAreaFilled(false);
        UILibrary.WhitePlayer_VS_BlackComp_Button.setBorderPainted(false);
        

        UILibrary.WhiteComp_VS_BlackComp_Button = new JButton();
        UILibrary.WhiteComp_VS_BlackComp_Button.setBounds(221, 795, 472, 190); // Numbers from Figma Design
        UILibrary.WhiteComp_VS_BlackComp_Button.setIcon(
            new ImageIcon(getImage("White_Comp_VS_Black_Comp.png").getImage().getScaledInstance(472, 190, Image.SCALE_DEFAULT)));
        UILibrary.NewGameFrame.add(UILibrary.WhiteComp_VS_BlackComp_Button);
        UILibrary.WhiteComp_VS_BlackComp_Button.setOpaque(false);
        UILibrary.WhiteComp_VS_BlackComp_Button.setContentAreaFilled(false);
        UILibrary.WhiteComp_VS_BlackComp_Button.setBorderPainted(false);


        UILibrary.WhiteComp_VS_BlackPlayer_Button = new JButton();
        UILibrary.WhiteComp_VS_BlackPlayer_Button.setBounds(748, 365, 472, 190); // Numbers from Figma Design
        UILibrary.WhiteComp_VS_BlackPlayer_Button.setIcon(
            new ImageIcon(getImage("White_Comp_VS_Black_Player.png").getImage().getScaledInstance(472, 190, Image.SCALE_DEFAULT)));
        UILibrary.NewGameFrame.add(UILibrary.WhiteComp_VS_BlackPlayer_Button);
        UILibrary.WhiteComp_VS_BlackPlayer_Button.setOpaque(false);
        UILibrary.WhiteComp_VS_BlackPlayer_Button.setContentAreaFilled(false);
        UILibrary.WhiteComp_VS_BlackPlayer_Button.setBorderPainted(false);


        UILibrary.RDMPlayer_VS_RDMComp_Button = new JButton();
        UILibrary.RDMPlayer_VS_RDMComp_Button.setBounds(748, 575, 472, 190); // Numbers from Figma Design
        UILibrary.RDMPlayer_VS_RDMComp_Button.setIcon(
            new ImageIcon(getImage("RDM_Comp_VS_RDM_Player.png").getImage().getScaledInstance(472, 190, Image.SCALE_DEFAULT)));
        UILibrary.NewGameFrame.add(UILibrary.RDMPlayer_VS_RDMComp_Button);
        UILibrary.RDMPlayer_VS_RDMComp_Button.setOpaque(false);
        UILibrary.RDMPlayer_VS_RDMComp_Button.setContentAreaFilled(false);
        UILibrary.RDMPlayer_VS_RDMComp_Button.setBorderPainted(false);


        UILibrary.LearnChessButton = new JButton();
        UILibrary.LearnChessButton.setBounds(748, 795, 472, 190); // Numbers from Figma Design
        UILibrary.LearnChessButton.setIcon(
            new ImageIcon(getImage("LearnChessButton.png").getImage().getScaledInstance(472, 190, Image.SCALE_DEFAULT)));
        UILibrary.NewGameFrame.add(UILibrary.LearnChessButton);
        UILibrary.LearnChessButton.setOpaque(false);
        UILibrary.LearnChessButton.setContentAreaFilled(false);
        UILibrary.LearnChessButton.setBorderPainted(false);
    }

  

    // -----------------------------------------------------
    // -----------------------------------------------------

    public CreateStartFrame() {
        CreateNewGameContentPanel();
        createUIElements();
    
    }

}