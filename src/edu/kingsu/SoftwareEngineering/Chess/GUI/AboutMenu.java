package edu.kingsu.SoftwareEngineering.Chess.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Displays the Help Menu Window
 *
 * @author Noah Bulas
 * @version V1 Nov,23
 */
public class AboutMenu {

    private static JFrame AboutFrame;


    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Add the action listener to open the HelpMenu when the About JMenu is selected
     */
    private static void detectAboutFrameToggle() {
        UILibrary.About_JMenuItem.addActionListener(e -> {
          AboutFrame.setVisible(!AboutFrame.isVisible());
        });
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Creates the second AboutFrame JFrame and its containing elements
     */
    private void createUI() { // Most of the following code is taken from  https://www.tutorialspoint.com/swingexamples/using_jeditorpane_to_show_html.htm

        // Create the Frame
        AboutFrame = new JFrame("Java Chess Application Help");
        AboutFrame.setSize(580, 500);
        AboutFrame.setLocationRelativeTo(null);

        // Create the Panel
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        // Add in the jEditorPane and the HTML source
        try {
            java.net.URL url = getClass().getClassLoader().getResource("AboutApplication.html");

            JEditorPane jEditorPane = new JEditorPane(url);
            jEditorPane.setEditable(false);
           
            JScrollPane jScrollPane = new JScrollPane(jEditorPane);
            jScrollPane.setPreferredSize(new Dimension(550, 450));
            panel.add(jScrollPane);
            AboutFrame.getContentPane().add(panel, BorderLayout.CENTER);

        } catch (Exception e) {
            System.out.println("Failed to create in-application help menu.");
        }

        AboutFrame.setVisible(false);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

/**
 * Calls the Create UI function than adds the actionListener for the Help JMenu
 */
    public AboutMenu() {
        createUI();
        detectAboutFrameToggle();
    }

}
