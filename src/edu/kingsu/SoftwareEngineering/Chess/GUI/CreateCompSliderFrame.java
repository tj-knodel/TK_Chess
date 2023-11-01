package edu.kingsu.SoftwareEngineering.Chess.GUI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;


/**
 * Creates the Frame which holds the slider frame.
 * 
 * @author Noah Bulas
 * @version V1
 */
public class CreateCompSliderFrame {


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
     * Creates the JLabel Change AI Strength Slider Frame
     */
    private void CreateSliderContentPanel() {
        UILibrary.SetAIStrengthSliderFrame = new JLabel();
        UILibrary.SetAIStrengthSliderFrame.setLayout(null);
        UILibrary.SetAIStrengthSliderFrame.setBounds(0,0,UILibrary.uiSize_X, UILibrary.uiSize_Y);
        UILibrary.SetAIStrengthSliderFrame.setBackground(UILibrary.BackgroundColor);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates, Needed JLabel titles, slider itself, and confirm / cancel buttons
     */
    private void createUIElements() {

        // Text Labels
        JLabel TitleLabel = new JLabel("Set Computer Chess AI Strength", SwingConstants.CENTER);
        TitleLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 55));
        TitleLabel.setForeground(UILibrary.TextColor_White);
        TitleLabel.setBounds(175, 159, 1089, 63); // Numbers from Figma Design
        TitleLabel.setOpaque(false);
        UILibrary.SetAIStrengthSliderFrame.add(TitleLabel);

        JLabel EasyLabel = new JLabel("EASY", SwingConstants.CENTER);
        EasyLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 20));
        EasyLabel.setForeground(UILibrary.TextColor_White);
        EasyLabel.setBounds(199, 616, 102, 30); // Numbers from Figma Design
        EasyLabel.setOpaque(false);
        UILibrary.SetAIStrengthSliderFrame.add(EasyLabel);

        JLabel MaxLabel = new JLabel("MAX", SwingConstants.CENTER);
        MaxLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 20));
        MaxLabel.setForeground(UILibrary.TextColor_White);
        MaxLabel.setBounds(1149, 616, 102, 30); // Numbers from Figma Design
        MaxLabel.setOpaque(false);
        UILibrary.SetAIStrengthSliderFrame.add(MaxLabel);

        JLabel MediumLabel = new JLabel("MEDIUM", SwingConstants.CENTER);
        MediumLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 20));
        MediumLabel.setForeground(UILibrary.TextColor_White);
        MediumLabel.setBounds(669, 616, 102, 30); // Numbers from Figma Design
        MediumLabel.setOpaque(false);
        UILibrary.SetAIStrengthSliderFrame.add(MediumLabel);

        JLabel SliderBackground = new JLabel("", SwingConstants.CENTER);
        SliderBackground.setBounds(184, 463, 1071, 197); // Numbers from Figma Design
        SliderBackground.setIcon(
            new ImageIcon(getImage("SliderBackground.png").getImage().getScaledInstance(1071, 197, Image.SCALE_DEFAULT)));
        UILibrary.SetAIStrengthSliderFrame.add(SliderBackground);

        UILibrary.ConfirmSliderButton = new JButton();
        UILibrary.ConfirmSliderButton.setBounds(527, 700, 185, 59); // Numbers from Figma Design
        UILibrary.ConfirmSliderButton.setIcon(
            new ImageIcon(getImage("ConfirmButton.png").getImage().getScaledInstance(185, 59, Image.SCALE_DEFAULT)));
        UILibrary.SetAIStrengthSliderFrame.add(UILibrary.ConfirmSliderButton);
        UILibrary.ConfirmSliderButton.setOpaque(false);
        UILibrary.ConfirmSliderButton.setContentAreaFilled(false);
        UILibrary.ConfirmSliderButton.setBorderPainted(false);

        UILibrary.CancelSliderButton = new JButton();
        UILibrary.CancelSliderButton.setBounds(727, 700, 185, 59); // Numbers from Figma Design
        UILibrary.CancelSliderButton.setIcon(
            new ImageIcon(getImage("CancelButton.png").getImage().getScaledInstance(185, 59, Image.SCALE_DEFAULT)));
        UILibrary.SetAIStrengthSliderFrame.add(UILibrary.CancelSliderButton);
        UILibrary.CancelSliderButton.setOpaque(false);
        UILibrary.CancelSliderButton.setContentAreaFilled(false);
        UILibrary.CancelSliderButton.setBorderPainted(false);

        UILibrary.CurrentSelectedComputer_ImageLabel = new JLabel();
        UILibrary.CurrentSelectedComputer_ImageLabel.setBounds(659, 276, 112, 105); // Numbers from Figma Design
        UILibrary.CurrentSelectedComputer_ImageLabel.setIcon(
            new ImageIcon(getImage("computer_white.png").getImage().getScaledInstance(112, 105, Image.SCALE_DEFAULT)));
        UILibrary.SetAIStrengthSliderFrame.add( UILibrary.CurrentSelectedComputer_ImageLabel);

        UILibrary.CurrentSelectedComputer_TextLabel = new JLabel("White Computer", SwingConstants.CENTER);
        UILibrary.CurrentSelectedComputer_TextLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 20));
        UILibrary.CurrentSelectedComputer_TextLabel.setForeground(UILibrary.TextColor_White);
        UILibrary.CurrentSelectedComputer_TextLabel.setBounds(621, 409, 187, 29); // Numbers from Figma Design
        UILibrary.CurrentSelectedComputer_TextLabel.setOpaque(false);
        UILibrary.SetAIStrengthSliderFrame.add(UILibrary.CurrentSelectedComputer_TextLabel);

        UILibrary.SetAiStrengthSlider = new JSlider(JSlider.HORIZONTAL);
        UILibrary.SetAiStrengthSlider.setBounds(250, 551, 950, 29);
        UILibrary.SetAiStrengthSlider.setBackground(UILibrary.ForegroundColor);
        UILibrary.SetAiStrengthSlider.setForeground(UILibrary.TextColor_Gray);
        UILibrary.SetAIStrengthSliderFrame.add(UILibrary.SetAiStrengthSlider);
        
    }

  

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates the AI Strength Slider Frame by calling its associated functions
     */
    public CreateCompSliderFrame() {
        CreateSliderContentPanel();
        createUIElements();
    
    }

}