package edu.kingsu.SoftwareEngineering.Chess.GUI;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public boolean isDrawing = false;
    Timer timer;
    Image loadIcon;
    double rotation = 0;

    public void paint(Graphics g) {

        if (!isDrawing) return;

        super.paint(g); // paint background

        Graphics2D g2D = (Graphics2D) g;
        rotation += 0.025;
        
       Image icon =  new ImageIcon(loadIcon).getImage();//.getScaledInstance(this.getSize().width, this.getSize().height, Image.SCALE_DEFAULT);

       int maxSize_X = (int) (this.getSize().width * 0.8); 
       int maxSize_Y = (int) (this.getSize().height * 0.8); 

       // Following Sizing is copy-paste from ImageViewer
       int original_X_size = icon.getWidth(null);
       int original_Y_size = icon.getHeight(null);
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

       newWidth = UILibrary.resizeModule.scale_X(newWidth);
       newHeight = UILibrary.resizeModule.scale_Y(newHeight);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Translate to the center of the image
        g2D.translate(centerX, centerY);
        g2D.rotate(rotation); // Rotate around the center
        g2D.drawImage(icon, -newWidth / 2, -newHeight / 2, newWidth, newHeight, null); // Draw the image at the center

        g2D.dispose(); // Dispose of the copy of the graphics context
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    //----------------------------------------------
    //----------------------------------------------

    LoadingIcon() {
        UILibrary.MainFrame.add(this);
        UILibrary.resizeModule.setVariableBounds(this, null, 1364, 17, 52, 52);
        this.setBackground(UILibrary.BackgroundColor);
        
        loadIcon = getImage("loading.png").getImage();
        timer = new Timer(10, this);
        timer.start();
    }

}
