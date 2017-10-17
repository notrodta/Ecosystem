import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ErrorWindow extends JFrame{
	
	public static ErrorWindow errorWindow;
	
	/**
	 * Error Window if you enter invalid value for the Skip Button
	 */
	public ErrorWindow()
	{
		super("Error");
		
		DeleteErrorMsg();
		
		errorWindow = this;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
  
        JLabel errorMessage = new JLabel("<html><br><center>ERROR:<br></center><center>You have entered an invalid value.<br></center><center>Please enter an integer value between 2 to 10.<br></center><br> </html>");
        
        add(errorMessage);
        pack();
        center(this);
        setVisible(true);
	}
	
	/**
	 * Delete any additional Error message window
	 */
	public static void DeleteErrorMsg()
	{
		if (errorWindow != null)
		{
			errorWindow.dispose();
			errorWindow = null;
		}
	}
	
	public static void center(Window frame){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getHeight())/3 + 30);
        int y = (int) ((dimension.getHeight())/5);
        frame.setLocation(x,y);
    }
}
