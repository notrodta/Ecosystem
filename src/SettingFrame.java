//import com.sun.deploy.util.StringUtils;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.*;

public class SettingFrame extends JFrame {

    private JPanel contentPane;

    JLabel Herbspawn = new JLabel("Enter amount of Herbivores: ");
    JTextField H = new JTextField("20");
    JLabel CarSpawn = new JLabel("Enter amount of Carnivores: ");
    JTextField C = new JTextField("10");
    JLabel PlantSpawn = new JLabel("Enter amount of Plants: ");
    JTextField P = new JTextField("30");
    JLabel EcoSizeX = new JLabel("Gridsize: ");
    JTextField X = new JTextField("30");
    JButton reset = new JButton("Reset");
    JButton b1 = new JButton("Start Simulation");
    String gridSize;
    String cPop;
    String hPop;
    String pPop;
    int igridSize;
    int icPop;
    int ihPop;
    int ipPop;

    public static boolean isNumberic(String str){
        try{
            int i = Integer.parseInt(str);
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * Create the frame.
     */
    public SettingFrame() {

        setVisible(true);
        setLayout(new GridBagLayout());
        setResizable(false);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridSize = X.getText();
                cPop = C.getText();
                hPop = H.getText();
                pPop = P.getText();
                if ( gridSize.isEmpty() )
                {
                    igridSize = 30;
                }
                else
                {
                    if(isNumberic(gridSize)) {
                        igridSize = Integer.parseInt(X.getText());
                        if(igridSize<4 || igridSize > 30){
                            igridSize = 30;
                        }
                    }
                    else{
                        igridSize = 30;
                    }
                }
                //System.out.println("3");
                if( cPop.isEmpty() || hPop.isEmpty() || pPop.isEmpty() )
                {
                    icPop = 10;
                    ihPop = 20;
                    ipPop = 30;
                }
                else
                {
                    if(isNumberic(cPop) && isNumberic(hPop) && isNumberic(pPop)){
                        icPop = Integer.parseInt(C.getText());
                        ihPop = Integer.parseInt(H.getText());
                        ipPop = Integer.parseInt(P.getText());
                        if(icPop+ihPop+ipPop>= 100){
                            icPop = 10;
                            ihPop = 20;
                            ipPop = 30;
                        }
                    }
                    else{
                        icPop = 10;
                        ihPop = 20;
                        ipPop = 30;
                    }

                }
                Ecosystem myArea1 = new Ecosystem(igridSize,icPop,ihPop,ipPop);

                b1.setEnabled(false);
                setVisible(false);
                dispose();

                SimulationFrame myFrame2 = new SimulationFrame();
                myFrame2.myArea2 = myArea1;
                myFrame2.setTitle("Simulation");


            }
        });

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.ipadx = 20;

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20,20,20,20);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(b1,constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(20,20,0,0);
        add(CarSpawn, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(20,0,0,20);
        add(C, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0,20,0,0);
        add(Herbspawn, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0,0,0,20);

        add(H, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0,20,0,0);
        add(PlantSpawn, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0,0,0,20);

        add(P, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0,20,0,0);
        add(EcoSizeX, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0,0,0,20);
        add(X, constraints);

        center(this);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("sun.png")));
        pack();
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

}