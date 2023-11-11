package edu.kingsu.SoftwareEngineering.Chess.GUI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.awt.Font;


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
        UILibrary.resizeModule.setVariableBounds(UILibrary.SetAIStrengthSliderFrame, null, 0,0,UILibrary.uiSize_X, UILibrary.uiSize_Y);
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
        UILibrary.resizeModule.setVariableBounds( TitleLabel, null, 175, 159, 1089, 63); // Numbers from Figma Design
        TitleLabel.setOpaque(false);
        UILibrary.SetAIStrengthSliderFrame.add(TitleLabel);

        JLabel EasyLabel = new JLabel("EASY", SwingConstants.CENTER);
        EasyLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 20));
        EasyLabel.setForeground(UILibrary.TextColor_White);
        UILibrary.resizeModule.setVariableBounds(EasyLabel, null, 199, 616, 102, 30); // Numbers from Figma Design
        EasyLabel.setOpaque(false);
        UILibrary.SetAIStrengthSliderFrame.add(EasyLabel);

        JLabel MaxLabel = new JLabel("MAX", SwingConstants.CENTER);
        MaxLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 20));
        MaxLabel.setForeground(UILibrary.TextColor_White);
        UILibrary.resizeModule.setVariableBounds(MaxLabel, null, 1149, 616, 102, 30); // Numbers from Figma Design
        MaxLabel.setOpaque(false);
        UILibrary.SetAIStrengthSliderFrame.add(MaxLabel);

        JLabel MediumLabel = new JLabel("MEDIUM", SwingConstants.CENTER);
        MediumLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 20));
        MediumLabel.setForeground(UILibrary.TextColor_White);
        UILibrary.resizeModule.setVariableBounds(MediumLabel, null, 669, 616, 102, 30); // Numbers from Figma Design
        MediumLabel.setOpaque(false);
        UILibrary.SetAIStrengthSliderFrame.add(MediumLabel);

        JLabel SliderBackground = new JLabel("", SwingConstants.CENTER);
        UILibrary.resizeModule.setVariableBounds(SliderBackground, null, 184, 463, 1071, 197); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(SliderBackground, getImage("SliderBackground.png"));
        UILibrary.SetAIStrengthSliderFrame.add(SliderBackground);

        UILibrary.ConfirmSliderButton = new JButton();
        UILibrary.resizeModule.setVariableBounds(UILibrary.ConfirmSliderButton, null, 527, 700, 185, 59); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(UILibrary.ConfirmSliderButton, getImage("ConfirmButton.png"));
        UILibrary.SetAIStrengthSliderFrame.add(UILibrary.ConfirmSliderButton);
        UILibrary.ConfirmSliderButton.setOpaque(false);
        UILibrary.ConfirmSliderButton.setContentAreaFilled(false);
        UILibrary.ConfirmSliderButton.setBorderPainted(false);

        UILibrary.CancelSliderButton = new JButton();
        UILibrary.resizeModule.setVariableBounds(UILibrary.CancelSliderButton, null, 727, 700, 185, 59); // Numbers from Figma Design
        UILibrary.resizeModule.setVariableBounds(UILibrary.CancelSliderButton, getImage("CancelButton.png"));
        UILibrary.SetAIStrengthSliderFrame.add(UILibrary.CancelSliderButton);
        UILibrary.CancelSliderButton.setOpaque(false);
        UILibrary.CancelSliderButton.setContentAreaFilled(false);
        UILibrary.CancelSliderButton.setBorderPainted(false);

        JLabel CurrentSelectedComputer_ImageLabel = new JLabel();
        UILibrary.resizeModule.setVariableBounds(CurrentSelectedComputer_ImageLabel, null, 659, 276, 112, 105); // Numbers from Figma Design
        UILibrary.CurrentSelectedComputer_ImageLabel = UILibrary.resizeModule.setVariableBounds(CurrentSelectedComputer_ImageLabel, getImage("computer_white.png"));
        UILibrary.SetAIStrengthSliderFrame.add( CurrentSelectedComputer_ImageLabel);

        UILibrary.CurrentSelectedComputer_TextLabel = new JLabel("White Computer", SwingConstants.CENTER);
        UILibrary.CurrentSelectedComputer_TextLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 20));
        UILibrary.CurrentSelectedComputer_TextLabel.setForeground(UILibrary.TextColor_White);
        UILibrary.resizeModule.setVariableBounds(UILibrary.CurrentSelectedComputer_TextLabel, null, 621, 409, 187, 29); // Numbers from Figma Design
        UILibrary.CurrentSelectedComputer_TextLabel.setOpaque(false);
        UILibrary.SetAIStrengthSliderFrame.add(UILibrary.CurrentSelectedComputer_TextLabel);

        UILibrary.SetAiStrengthSlider = new JSlider(JSlider.HORIZONTAL);
        UILibrary.resizeModule.setVariableBounds(UILibrary.SetAiStrengthSlider, null, 250, 551, 950, 29);
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