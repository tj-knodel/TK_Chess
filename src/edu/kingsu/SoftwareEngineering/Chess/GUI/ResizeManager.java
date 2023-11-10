package edu.kingsu.SoftwareEngineering.Chess.GUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import edu.kingsu.SoftwareEngineering.Chess.GUI.Table.Table;

public class ResizeManager implements ComponentListener {


    
    // -----------------------------------------------------
    // --------------Scale Functions-------------------
     // -----------------------------------------------------

    /**
     * scaleUIRelativeToAbsoluteSize_X
     * Scales the x value to the current size of the JFrame
     * 
     * @param requestedSize Size in pixels to scale
     * @return scaled size
     */
    public int scale_X(int requestedSize) {
        // Get the original aspect ratio
        double originalSize = (double) UILibrary.uiSize_X;
        double scale = requestedSize / originalSize; // scale is 0 to 1, similar to Roblox UI Scale system
        int currentSizeX = UILibrary.ChessJFrame.getSize().width;

        // System.out.println(requestedSize + " " + scale + " " + currentSizeX);
        return (int) (currentSizeX * scale);
    }

    /**
     * scaleUIRelativeToAbsoluteSize_Y
     * Scales the y value to the current size of the JFrame
     * 
     * @param requestedSize Size in pixels to scale
     * @return scaled size
     */
    public int scale_Y(int requestedSize) {
        // Get the original aspect ratio
        double originalSize = (double) UILibrary.uiSize_Y;
        double scale = requestedSize / originalSize; // scale is 0 to 1, similar to Roblox UI Scale system
        int currentSizeY = UILibrary.ChessJFrame.getSize().height;
        // System.out.println(requestedSize + " " + scale + " " + currentSizeY);
        return (int) (currentSizeY * scale);
    }

    /**
     * scaleUIRelativeToAbsoluteSize_X
     * Scales the x value to the current size of the JFrame
     * 
     * @param requestedSize Size in pixels to scale
     * @return scaled size
     */
    public int scale_X(int requestedSize, double originalSize) {
        // Get the original aspect ratio
        double scale = requestedSize / originalSize; // scale is 0 to 1, similar to Roblox UI Scale system
        int currentSizeX = UILibrary.ChessJFrame.getSize().width;

        // System.out.println(requestedSize + " " + scale + " " + currentSizeX);
        return (int) (currentSizeX * scale);
    }

    /**
     * scaleUIRelativeToAbsoluteSize_Y
     * Scales the y value to the current size of the JFrame
     * 
     * @param requestedSize Size in pixels to scale
     * @return scaled size
     */
    public int scale_Y(int requestedSize, double originalSize) {
        // Get the original aspect ratio
        double scale = requestedSize / originalSize; // scale is 0 to 1, similar to Roblox UI Scale system
        int currentSizeY = UILibrary.ChessJFrame.getSize().height;
        // System.out.println(requestedSize + " " + scale + " " + currentSizeY);
        return (int) (currentSizeY * scale);
    }

    // -----------------------------------------------------
    // -------------UI Element Classes-------------------
    // -----------------------------------------------------

    class UIElement {
        public JComponent ParentComponent;
        public JComponent selfComponent;
        public int originalPosX;
        public int originalPosY;
        public int originalSizeX;
        public int originalSizeY;

        UIElement(JComponent self, JComponent Parent, int originalPosX, int originalPosY, int originalSizeX,  int originalSizeY) {
            this.selfComponent = self;
            this.ParentComponent = Parent;
            this.originalPosX = originalPosX;
            this.originalPosY = originalPosY;
            this.originalSizeX = originalSizeX;
            this.originalSizeY = originalSizeY;
        }

        public void updateSize() {
            if (ParentComponent != null) {
                Rectangle parentBounds = ParentComponent.getBounds();
                this.selfComponent.setBounds(scale_X(originalPosX, parentBounds.x), scale_Y(originalPosY, parentBounds.y), scale_X(originalSizeX, parentBounds.width), scale_Y(originalSizeX, parentBounds.height));
            } else
                this.selfComponent.setBounds(scale_X(originalPosX), scale_Y(originalPosY), scale_X(originalSizeX),  scale_Y(originalSizeY));
        }
    }


   class UIImage_Button {
        public AbstractButton selfComponent;
        public ImageIcon image;

        UIImage_Button(AbstractButton self, ImageIcon image) {
            this.selfComponent = self;
            this.image = image;
        }

        public void updateSize() {
            Dimension size = selfComponent.getSize();
        
            selfComponent.setIcon( new ImageIcon(image.getImage().getScaledInstance(size.width, size.height, Image.SCALE_DEFAULT)));
        }
    }


   class UIImage_Label {
        public JLabel  selfComponent;
        public ImageIcon image;

        UIImage_Label(JLabel self, ImageIcon image) {
            this.selfComponent = self;
            this.image = image;
        }

        public void updateSize() {
            Dimension size = selfComponent.getSize();
            selfComponent.setIcon( new ImageIcon(image.getImage().getScaledInstance(size.width, size.height, Image.SCALE_DEFAULT)));
        }
    }


    // -----------------------------------------------------
    // -----------------------------------------------------

    private Table<UIElement> GuiComponents;
    private Table<UIImage_Button> Gui_Buttons;
    private Table<UIImage_Label> Gui_Labels;

    public void setVariableBounds(JComponent self, JComponent Parent, int originalPosX, int originalPosY, int originalSizeX, int originalSizeY) {
        GuiComponents.insert(new UIElement(self, Parent, originalPosX, originalPosY, originalSizeX, originalSizeY));
    }
    
    public void setVariableBounds(AbstractButton self,  ImageIcon image) {
        Gui_Buttons.insert(new UIImage_Button(self, image));
    }

    public void setVariableBounds(JLabel self,  ImageIcon image) {
        Gui_Labels.insert(new UIImage_Label(self, image));
    }
    
    // -----------------------------------------------------
    // -----------------------------------------------------

    public void resizeEverything() {
        
        // GUI COMPONENTS
        for (int i = 0; i < GuiComponents.size(); ++i) {
            UIElement element = GuiComponents.at(i);

            // Update the parent first
            if (element.ParentComponent == null)
                element.updateSize();
        }

        for (int i = 0; i < GuiComponents.size(); ++i) {
            UIElement element = GuiComponents.at(i);

            // Update the child components last
            if (element.ParentComponent != null)
                element.updateSize();
        }

        // IMAGES - BUTTONS
        for (int i = 0; i < Gui_Buttons.size(); ++i) {
            UIImage_Button element = Gui_Buttons.at(i);
            element.updateSize();
        }

         // IMAGES - Label
        for (int i = 0; i < Gui_Labels.size(); ++i) {
            UIImage_Label element = Gui_Labels.at(i);
            element.updateSize();
        }

        UILibrary.MainFrame.repaint();
    }
    // -----------------------------------------------------
    // -----------------------------------------------------
    
    public void componentHidden(ComponentEvent ce) {};
    public void componentShown(ComponentEvent ce) {};
    public void componentMoved(ComponentEvent ce) {};

    private long lastUpdate= 0; // For Resize debounce

    public void componentResized(ComponentEvent ce) {
        long eventTime = System.currentTimeMillis();
        lastUpdate = eventTime;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
        if (lastUpdate == eventTime) {
            resizeEverything();
           // UILibrary.MovesLabel_ScrollPane.setBorder(BorderFactory.createEmptyBorder());  // One off fix
        }
    };

    public void detectJFramedResized() {
        UILibrary.ChessJFrame.getContentPane().addComponentListener(this);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    public ResizeManager() {
        GuiComponents = new Table<UIElement>();
        Gui_Buttons = new Table<UIImage_Button>();
        Gui_Labels = new Table<UIImage_Label>();
    }
}
