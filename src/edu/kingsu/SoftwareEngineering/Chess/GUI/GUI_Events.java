package edu.kingsu.SoftwareEngineering.Chess.GUI;

public class GUI_Events {
    
    // Mostly Temporary, awaiting connections
    public GUI_Events() {


        UILibrary.StepBackwards_Button.addActionListener(e-> {
                System.out.println("StepBackwards Button was clicked!");
        });


        UILibrary.StepForwards_Button.addActionListener(e-> {
                System.out.println("StepForwards Button was clicked!");
        });


         UILibrary.EnterMove_TextField.addActionListener(e -> {
            String input =  UILibrary.EnterMove_TextField.getText();
            System.out.println("The text box detected input: " + input);
        });



    }

}
