package edu.kingsu.SoftwareEngineering.Chess.GUI;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Creates the Frame which holds the slider frame.
 * 
 * @author Noah Bulas
 * @version V1
 */
public class CreateCompSliderFrame {

    // -----------------------------------------------------
    // -----------------------------------------------------

    static private JSlider createSlider(final JOptionPane optionPane, int min, int max) {
        JSlider slider = new JSlider(min, max);
        slider.setMajorTickSpacing(25);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider theSlider = (JSlider) changeEvent.getSource();
                if (!theSlider.getValueIsAdjusting()) {
                    optionPane.setInputValue((Integer) theSlider.getValue());
                }
            }
        };

        slider.addChangeListener(changeListener);
        return slider;
    }

    private JDialog createJDialog(JOptionPane optionPane, int min, int max, String body) {
        JFrame parent = new JFrame();

        JSlider slider = createSlider(optionPane, min, max);
        optionPane.setMessage(new Object[] { body, slider });
        optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
        optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
        return optionPane.createDialog(parent, "AI Slider");
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    public Integer showAISlider() {

        JOptionPane optionPane = new JOptionPane();
        JDialog dialog = createJDialog(optionPane, 0, 100, "Set AI Strength Slider");
        dialog.setVisible(true); // This line yields until input is closed

        if (optionPane.getValue() == null || optionPane.getValue() == (Integer) 2) { // null means "X" button, 2 as an "Object" means "Cancel"
            System.out.println("Cancel");
            return null;
        } else { // User Pressed Ok
            System.out.println("Selected " + optionPane.getInputValue());
            return (Integer)optionPane.getInputValue();
        }
    }


    // -----------------------------------------------------
    // -----------------------------------------------------

    public Integer showAISliderTimeOut() {

        JOptionPane optionPane = new JOptionPane();
        JDialog dialog = createJDialog(optionPane, 2, 20, "Set AI Max AI Computation Time (seconds)");
        dialog.setVisible(true); // This line yields until input is closed

        if (optionPane.getValue() == null || optionPane.getValue() == (Integer) 2) { // null means "X" button, 2 as an "Object" means "Cancel"
            System.out.println("Cancel");
            return null;
        } else { // User Pressed Ok
            System.out.println("Selected " + optionPane.getInputValue());
            return (Integer)optionPane.getInputValue();
        }
    }


    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates the AI Strength Slider Frame by calling its associated functions
     */
    public CreateCompSliderFrame() {
    }


}