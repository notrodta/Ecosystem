import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sunny,Rod,Timothy
 */

/**
 *  Start creates a GUI.
 *  It has a nice message just for you.
 *
 */
public class Start extends JFrame{

    /**
     * Constructor for our GUI
     * It uses JFrame to create a new window.
     * The window is compact and not resizeable.
     * It current creates an array for testing.
     * It converts the array into a HTML to print.
     */
    public Start(){

        super("Intro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        /**
         * Using GridBagLayout for our GUI.
         */
        setLayout(new GridBagLayout());

        // Creating our components.

        String msg = "Hello user! When using this simulation, <br>" +
                "be sure to know that any invalid inputs <br> " +
                "will be changed into defaults. <br><br>" +
                "Defaults: <br>" +
                "% of Carnivores : 10 <br>" +
                "% of Herbivores : 20 <br> " +
                "% of Plants :     30 <br>" +
                "Grid Size :       30 <br><br>" +
                "Restraints: <br>" +
                "Sum must not be greater 100% <br>" +
                "Grid Size must be between 4 and 30.";

        JLabel msg2 = new JLabel("<html><div style='text-align: center;'>" + msg + "</div></html>");
        JButton start = new JButton("Go To Settings");


        // Adding action to button.
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                SettingFrame timframe = new SettingFrame();
                timframe.setTitle("Our Savage World");
                timframe.setVisible(true);
            }
        });

        /**
         * Constraints
         */
        GridBagConstraints constraints = new GridBagConstraints();
        //constraints.insets = new Insets(top,left,bot,right);

        // day Label
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10,10,10,10);
        add(msg2,constraints);

        // Next Day Button
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10,10,10,10);
        add(start,constraints);

        /**
         * JFrame general settings.
         */
        center(this);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("sun.png")));
        
        
        pack(); // Make my thing compact!
        setVisible(true);
    }

    /**
     * Positioning the window near the center of the screen
     * @param frame passing the window you want to center
     */
    public static void center(Window frame){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getHeight())/3);
        int y = (int) ((dimension.getHeight())/5);
        frame.setLocation(x,y);
    }

    public static void main(String[] args){
        new Start();
    }
}