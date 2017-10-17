import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by Sunny,Rod,Timothy
 */

/**
 *  Login creates a GUI.
 *  It shows the day of the ecosystem.
 *  It shows the ecosystem with a line border.
 *  It has a button to proceed to next day.
 */
public class SimulationFrame extends JFrame{

    // This is the day of our ecosystem.
    int day = 0;
    String displayArray;
    Ecosystem myArea2;
    int cCount = 0;
    int hCount = 0;
    int pCount = 0;
    
    JLabel imageLabel = new JLabel(); // for displaying weather images

    /**
     * Constructor for our GUI
     * It uses JFrame to create a new window.
     * The window is compact and not resizeable.
     * It current creates an array for testing.
     * It converts the array into a HTML to print.
     */
    public SimulationFrame(){

        super("Fancy Title");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        /**
         * Using GridBagLayout for our GUI.
         */
        setLayout(new GridBagLayout());

        // Creating our components.

        JLabel myArray = new JLabel("Press \"Begin\" to begin");
        myArray.setFont(new Font("monospaced", Font.PLAIN, 12));
        JLabel dayLabel = new JLabel("Day " + day);
        JButton beginButton = new JButton("Begin");
        JLabel census = new JLabel("<html>Carnivore : " + cCount + "<br>Herbivore : " + hCount + "<br>Plant : " + pCount + "</html>");
        JButton Reset = new JButton("Reset");
        JButton Skip = new JButton("Days To Skip: ");
        JTextField dSkiptext = new JTextField("2");


        // Adding action to button.
        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ErrorWindow.DeleteErrorMsg();
                System.out.println("We moved to the next day.");
                day++;
                cCount = 0;
                hCount = 0;
                pCount = 0;



                // Display Array
                displayArray = arraytostring(myArea2.map);
                myArray.setText(displayArray);

                // Update our eco to gui.
                myArea2.NextCycle();

                // Update Text
                dayLabel.setText("Day "+ day);
                beginButton.setText("Next Day");
                census.setText("<html>Carnivore : " + cCount + "<br>Herbivore : " + hCount + "<br>Plant : " + pCount + "</html>");
                
                //skip button
                SkipButton(dSkiptext,Skip);
                
                //UpdateWeather
               WeatherReport();
                             
                pack();
            }
        });
        beginButton.setToolTipText("Peekaboo");

        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                SettingFrame timframe = new SettingFrame();
                timframe.setTitle("Our savage world");
                timframe.setVisible(true);

            }
        });

        //Action listener for  skip button
        Skip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ErrorWindow.DeleteErrorMsg();
                String daySkip = dSkiptext.getText();
                int iDaySkip;

                if ( daySkip.isEmpty() ) //if value is empty, display error window
                {
                	new ErrorWindow();
                }
                else
                {
                    if(SettingFrame.isNumberic(daySkip)) { //if value is a number
                        iDaySkip = Integer.parseInt(dSkiptext.getText());
                        
                        if (iDaySkip >= 2 && iDaySkip <= 10){ //if number is greater than 1, skip button will work
	                        cCount = 0;
	                        hCount = 0;
	                        pCount = 0;
	
	                        for(int i = 0; i < iDaySkip; i++){
	                            day++;
	                            myArea2.NextCycle();
	                        }
	
	                        displayArray = arraytostring(myArea2.map);
	                        myArray.setText(displayArray);
	
	                        dayLabel.setText("Day "+ day);
	                        beginButton.setText("Next Day");
	                        census.setText("<html>Carnivore : " + cCount + "<br>Herbivore : " + hCount + "<br>Plant : " + pCount + "</html>");
	
	                        WeatherReport();
                        }
                        
                        if(iDaySkip<2 || iDaySkip > 10){ //if skip value is less than 2, display error window
                        	new ErrorWindow();
                        }
                    }
                    else{ //display error window
                    	new ErrorWindow();
                    }
                }
            }
        });

        // Gives a border to our array and titles it.
        myArray.setBorder(BorderFactory.createLineBorder(Color.black));
        myArray.setBorder(BorderFactory.createTitledBorder("Savage World"));

        /**
         * Constraints
         */
        GridBagConstraints constraints = new GridBagConstraints();

        // day Label
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10,10,10,50);
        add(dayLabel,constraints);

        // Ecosystem Label
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10,20,10,10);
        add(myArray,constraints);

        // Next Day Button
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10,50,10,10);
        add(beginButton,constraints);

        // Census Label
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10,10,10,10);
        add(census,constraints);

        // Reset Label
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10,10,10,10);
        add(Reset,constraints);

        /**
         * JFrame general settings.
         */
        center(this);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("sun.png")));
        
        pack(); // Make my thing compact!
        setVisible(true);
    }

    /**
     * positioning of the window near center
     * @param frame pass in which frame to position
     */
    public static void center(Window frame){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getHeight())/3);
        int y = (int) ((dimension.getHeight())/5);
        frame.setLocation(x,y);
    }

    /**
     * Changing the Ecosystem array of organisms to string
     * @param area 2darray of ecosystem
     * @return String of all the organisms
     */
    public String arraytostring(Organism[][] area){

        String str = "<html>";
        for(int i=0;i<Ecosystem.dimension;i++){
            for(int j=0;j<Ecosystem.dimension;j++){
                if(area[i][j] != null){
                    str += area[i][j];
                    if(area[i][j] instanceof Carnivore)
                    {
                        ++cCount;
                    }
                    if(area[i][j] instanceof Herbivore)
                    {
                        ++hCount;
                    }
                    if(area[i][j] instanceof Plant)
                    {
                        ++pCount;
                    }
                }
                else{
                    str += ".  ";
                }
            }
            str += "<br>";
        }
        str += "</html>";
        System.out.println(str);
        return str;
    }
    
    /**
     * Randomly choose a weather for each individual cycle
     */
    public void WeatherReport()
    {
    	SecureRandom rand = new SecureRandom();
    	int num = rand.nextInt(5);
    	String weatherImageName = "";
    	
    	if (num == 0)
    		weatherImageName = "sunny.png";
    	else if (num == 1)
    		weatherImageName = "windy.png";
    	else if (num == 2)
    		weatherImageName = "rainy.png";
    	else if (num == 3)
    		weatherImageName = "cloudy.png";
    	else if (num == 4)
    		weatherImageName = "snowy.png";
    	
    	 //weather report     
    	
	    GridBagConstraints constraints = new GridBagConstraints();
	    
	    ImageIcon weatherImage = new ImageIcon(getClass().getResource(weatherImageName));
	    imageLabel.setIcon(null);
	    imageLabel.setIcon(weatherImage);
	    constraints.gridwidth = 2;
	    constraints.gridx = 2;
	    constraints.gridy = 1;
	    constraints.anchor = GridBagConstraints.NORTHWEST;
	    add(imageLabel, constraints);
    }

    /**
     * Skip Button to skip 'x' amount of days ahead in the ecosystem cycle
     * @param dSkiptext Entering how many days you want to skip
     * @param Skip Skip button to execute the skip
     */
    public void SkipButton(JTextField dSkiptext, JButton Skip)
    {
    	GridBagConstraints constraints = new GridBagConstraints();
    	
        // Skip Text
        constraints.gridwidth = 1;
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10,10,10,10);
        add(dSkiptext,constraints);

        // Skip Button
        constraints.gridwidth = 1;
        constraints.gridx = 2;//3
        constraints.gridy = 0;//1
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10,10,10,10);
        add(Skip,constraints);
    }
}