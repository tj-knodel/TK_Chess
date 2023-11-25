package edu.kingsu.SoftwareEngineering.Chess.GUI;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Manages the loading icon in the main frame
 */
public class LoadingIcon extends JLabel implements ActionListener {

    /**
    * Gets the image from the local jar File
    * 
    * @param imageToGet Name of the image + type
    * @return The image
    */
    public ImageIcon getImage(String imageToGet) {
        return new ImageIcon(getClass().getClassLoader().getResource(imageToGet));
    }

    //----------------------------------------------
    //----------------------------------------------

    /**
     * Is the Loading Icon showing?
     * true = showing
     */
    public boolean isDrawing = false;

    /**
     * Timer object used for animation
     */
    Timer timer;

    /**
     * Loading image icon
     */
    Image loadIcon;

    /**
     * Current rotation of the icon
     */
    double rotation = 0;

    /**
     * Overridden paint method
     * @param g sent Graphics
     */
    public void paint(Graphics g) {

        // If its not drawing then return
        if (!isDrawing)
            return;

        super.paint(g); // paint background
        rotation += 0.025; // Increment rotation
        Graphics2D g2D = (Graphics2D) g; // Get Graphics 2d Object

        // Max image size
        int maxSize_X = (int) (this.getSize().width * 0.75);
        int maxSize_Y = (int) (this.getSize().height * 0.75);

        // Following Sizing is copy-paste from ImageViewer
        int original_X_size = loadIcon.getWidth(null);
        int original_Y_size = loadIcon.getHeight(null);
        int newHeight = original_Y_size;
        int newWidth = original_X_size;

        // Maintain Aspect Ratio, Following 2 if statements taken from
        // "https://stackoverflow.com/questions/10245220/"
        if (original_X_size > maxSize_X) {
            newWidth = maxSize_X;
            newHeight = (newWidth * original_Y_size) / original_X_size;
        }
        if (newHeight > maxSize_Y) {
            newHeight = maxSize_Y;
            newWidth = (newHeight * original_X_size) / original_Y_size;
        }

        // Scale the size to fit with JFrame scale
        newWidth = UILibrary.resizeModule.scale_X(newWidth);
        newHeight = UILibrary.resizeModule.scale_Y(newHeight);

        g2D.translate(getWidth() / 2, getHeight() / 2);  // Translate to the center of the image
        g2D.rotate(rotation); // Rotate around the center
        g2D.drawImage(loadIcon, -newWidth / 2, -newHeight / 2, newWidth, newHeight, null); // Draw the image at the center

        g2D.dispose(); // Dispose of the copy of the graphics context
    }  

    /**
     * Action performed method
     * @param e e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    //----------------------------------------------
    //----------------------------------------------

    /**
     * Creates  a new Loading icon in the top right and spins it
     */
    LoadingIcon() {
        UILibrary.MainFrame.add(this);
        UILibrary.resizeModule.setVariableBounds(this, null, 1364, 17, 52, 52);
        this.setBackground(UILibrary.BackgroundColor);

        loadIcon = getImage("loading.png").getImage();
        timer = new Timer(10, this);
        timer.start();
    }

}
