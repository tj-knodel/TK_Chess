package edu.kingsu.SoftwareEngineering.Chess.GUI;

import java.awt.Color;

import javax.swing.*;

/**
 This file does not contain any real "functional" code, its more of a
 library for all common variables and allows certain variable to be 
 passed around and used with ease.

  * @author Noah Bulas
 * @version XX.XX,23
 */
public abstract class UILibrary  {


    public static final int uiSize_X = 1455; //  JFrame x Size // 1440
    public static final int uiSize_Y = 1065; //  JFrame y Size // 1024



    //----- UI-------------
    
    // Main
    public static JFrame MainFrame; // Main JFrame
    public static JButton StepBackwards_Button;
    public static JButton StepForwards_Button;
    public static JTextField EnterMove_TextField;

    // MenuBar  / Menu items
    //public static JMenuItem FileExploreButton; // Select Image Directory





    // Colors
    public static final Color BackgroundColor = new Color(54,53,48); // Main JFrame Background Color
    public static final Color ForegroundColor = new Color(42,41,37); // Main JFrame Foreground Color
    public static final Color TextColor_White = new Color(255,255,255); // Text Color for all text
    public static final Color TextColor_Gray = new Color(192,192,192); // Text Color

    /**
     * Constructor not Used
     */
    UILibrary(){}
}