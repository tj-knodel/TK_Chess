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

/**
 * UI Elements use this class to `setBounds`, this module auto-magically resizes and repositions all frames when the JFrame is resized.
 */
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

    /**
     * Holds info about a basic UI Element to resize and reposition when needed
     */
    class UIElement {
        /**
         * If the size/parent of the component is relative to its parent
         * usually null, not used, maybe remove later
         */
        public JComponent ParentComponent;

        /**
         * UI Component itself to be resized
         */
        public JComponent selfComponent;

        /**
            Original UI Position X 
        */
        public int originalPosX;
        
        /**
            Original UI Position Y 
        */
        public int originalPosY;

        /**
         * Original UI Size X
         */
        public int originalSizeX;
        
        /**
         * Original UI Size Y
         */
        public int originalSizeY;

        /**
         * Constructor, sets UI variables
         * @param self UI component itself
         * @param Parent parent component if needed or null
         * @param originalPosX  Original X Position
         * @param originalPosY Original Y Position
         * @param originalSizeX Original X Size
         * @param originalSizeY Original Y Size
         */
        UIElement(JComponent self, JComponent Parent, int originalPosX, int originalPosY, int originalSizeX, int originalSizeY) {
            this.selfComponent = self;
            this.ParentComponent = Parent;
            this.originalPosX = originalPosX;
            this.originalPosY = originalPosY;
            this.originalSizeX = originalSizeX;
            this.originalSizeY = originalSizeY;
        }

        /**
         * Updates the size of the component with the new JFrame's Size
         */
        public void updateSize() {
            if (ParentComponent != null) {
                Rectangle parentBounds = ParentComponent.getBounds();
                this.selfComponent.setBounds(scale_X(originalPosX, parentBounds.x),
                        scale_Y(originalPosY, parentBounds.y), scale_X(originalSizeX, parentBounds.width),
                        scale_Y(originalSizeX, parentBounds.height));
            } else
                this.selfComponent.setBounds(scale_X(originalPosX), scale_Y(originalPosY), scale_X(originalSizeX),
                        scale_Y(originalSizeY));
        }
    }
    
    /**
     * Holds info UI Image Buttons, resizes images auto-magically
    */
    class UIImage_Button {
        
        /**
         * UI Component itself
         */
        public AbstractButton selfComponent;
        
        /**
         * ImageIcon which is displayed in selfComponent
         */
        public ImageIcon image;

        /**
         * Sets class variables
         * @param self UI component itself
         * @param image Image displayed in self
         */
        UIImage_Button(AbstractButton self, ImageIcon image) {
            this.selfComponent = self;
            this.image = image;
        }

        /**
         * Updates the size of the image when JFrame's resized
         */
        public void updateSize() {
            Dimension size = selfComponent.getSize();

            selfComponent.setIcon(
                    new ImageIcon(image.getImage().getScaledInstance(size.width, size.height, Image.SCALE_DEFAULT)));
        }
    }

    /**
     *  Holds info UI Image Labels, resizes images auto-magically
     */
    class UIImage_Label {

        /**
         * UI Component itself, holds the image
         */
        public JLabel selfComponent;

        /**
         * Image displayed on selfComponent
         */
        public ImageIcon image;

        /**
         * Sets class variables
         * @param self UI Component which holds the image
         * @param image image to be auto resized
         */
        UIImage_Label(JLabel self, ImageIcon image) {
            this.selfComponent = self;
            this.image = image;
        }

        /**
         * Auto scales the size of the image when the JFrame is resized
         */
        public void updateSize() {
            Dimension size = selfComponent.getSize();
            selfComponent.setIcon(
                    new ImageIcon(image.getImage().getScaledInstance(size.width, size.height, Image.SCALE_DEFAULT)));
        }
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Table which holds all the UIElements which need to be resized
     */
    private Table<UIElement> GuiComponents;

    /**
     * Table which holds all the UIElements buttons whose images needs to be resized
     */
    private Table<UIImage_Button> Gui_Buttons;

    /**
     * Table which holds all the UIElements labels whose images needs to be resized
     */
    private Table<UIImage_Label> Gui_Labels;

    /**
     * Think of this method as `setBounds` for UIComponents
     * @param self UI Elements to be auto resized
     * @param Parent Parent if position and size is relative, or null
     * @param originalPosX Original UI Position X
     * @param originalPosY Original UI Position Y
     * @param originalSizeX Original UI Size X
     * @param originalSizeY Original UI Size Y
     */
    public void setVariableBounds(JComponent self, JComponent Parent, int originalPosX, int originalPosY, int originalSizeX, int originalSizeY) {
                GuiComponents.insert(new UIElement(self, Parent, originalPosX, originalPosY, originalSizeX, originalSizeY));
    }

    /**
     * Sets the images inside of UI element buttons to be resized when its container is resized
     * @param self UI element which holds image
     * @param image image to be resized
     */
    public void setVariableBounds(AbstractButton self, ImageIcon image) {
        Gui_Buttons.insert(new UIImage_Button(self, image));
    }

     /**
     * Sets the images inside of UI element labels  to be resized when its container is resized
     * @param self UI element which holds image
     * @param image image to be resized
     */   
    public UIImage_Label setVariableBounds(JLabel self, ImageIcon image) {
        UIImage_Label label = new UIImage_Label(self, image);
        Gui_Labels.insert(label);
        return label;
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Resizes every element, Repaints JFrame
     * COMPUTATIONALLY EXPENSIVE
     */
    public void resizeEverything() {

        // Repaints each chess tile
        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {
                CreateMainFrame.boardTilesUI[row][column].redrawTile(); 
            }
        }

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

        // Repaints each chess tile, this is done twice because some functions need to be called before and after the UIBoard itself is resized
        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {
                CreateMainFrame.boardTilesUI[row][column].redrawTile();
            }
        }
        
        // Repaint JFrame and Main JLabels
        UILibrary.MainFrame.repaint();
        UILibrary.ChessJFrame.repaint();
        UILibrary.MovesLabel_ScrollPane.repaint();
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Not used
     */
    public void componentHidden(ComponentEvent ce) {};

    /**
     * Not used
     */
    public void componentShown(ComponentEvent ce) {};

    /**
     * Not used
     */
    public void componentMoved(ComponentEvent ce) {};

    /**
     * Resize Debounce, avoid calling resizeEverything too many times in close succession
     *  For Resize debounce
     */
    private long lastUpdate = 0; //

    /**
        Fires when the JFrame is resized, contains a debounce to avoid spam calls
    */
    public void componentResized(ComponentEvent ce) {
        long eventTime = System.currentTimeMillis();
        lastUpdate = eventTime; // Mark the current update as the current time

        // Wait `n`ms
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}

        // If the this function was not called during Thread.sleep, then resizeEverything
        if (lastUpdate == eventTime) {
            resizeEverything();
        }

    };

    /**
     * Adds action listener to detect when the JFrame is being resized
     */
    public void detectJFramedResized() {
        UILibrary.ChessJFrame.getContentPane().addComponentListener(this);
    }

    // -----------------------------------------------------
    // -----------------------------------------------------

    /**
     * Constructor initializes tables
     */
    public ResizeManager() {
        GuiComponents = new Table<UIElement>();
        Gui_Buttons = new Table<UIImage_Button>();
        Gui_Labels = new Table<UIImage_Label>();
    }
}
