package Modified;

import HW2.Drone;
import HW2.Manager;
import HW2.systemDataBase;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddDroneToManagerWindow extends JFrame {
    private JTextField managerIDField, droneCodeField;

    public AddDroneToManagerWindow(systemDataBase system) {
        setTitle("Add Drone to Manager");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        JLabel managerIDLabel = new JLabel("Manager ID:");
        managerIDField = new JTextField(10);

        JLabel droneCodeLabel = new JLabel("Drone Code:");
        droneCodeField = new JTextField(10);

        
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String managerID = managerIDField.getText();
    
                int droneCode = 0;
                try {
                    droneCode = Integer.parseInt(droneCodeField.getText());
                } catch (NumberFormatException ex) {
                    // Handle the exception if the input is not a valid integer
                    JOptionPane.showMessageDialog(AddDroneToManagerWindow.this, "Please enter a valid number for Drone Code", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

                // This code block copied from last HW
                
                Drone droneToAdd = null;
                Manager managerToAddTo = null;
                
                
                for(Drone d : system.getDrones()) {
                    if(d.getDroneCode() == droneCode) {
                        droneToAdd = d;
                        break;
                    }
                }
                if(droneToAdd==null) {      // זורק הודעה אם אין רחפן
                    JOptionPane.showMessageDialog(AddDroneToManagerWindow.this, "Invalid Drone Code.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for(Manager m : system.getManagers()) {
                    if(m.getId().equals(managerID)) {
                        managerToAddTo = m;
                        break;
                    }
                }
                
                if(managerToAddTo==null) {      // זורק הערה אם אין מנהל
                    JOptionPane.showMessageDialog(AddDroneToManagerWindow.this, "Invalid Manager ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<Drone> dronesUnderManager = system.responsibleDrones(managerToAddTo);		// משתמש בפונקציה מסעיף ב ומייבא את אוסף הרחפנים תחת המנהל
                ArrayList<Manager> managersThatResponsibleOnDrone = system.supervisedByManagers(droneToAdd);	// משתמש בפונקציה מסעיף ג ומייבא את אוסף המנהלים שאחראים על הרחפן
                
                // מבצע בדיקות כדי שלא יהיו כפילויות
                boolean messageDisplayed = false;
                if(!dronesUnderManager.contains(droneToAdd)) {
                    managerToAddTo.addDrone(droneToAdd);
                    JOptionPane.showMessageDialog(AddDroneToManagerWindow.this, "Drone added to Manager successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    messageDisplayed = true;
                } else {
                    JOptionPane.showMessageDialog(AddDroneToManagerWindow.this, "The drone is already supervised by the manager", "Information", JOptionPane.INFORMATION_MESSAGE);
                }

                if(!managersThatResponsibleOnDrone.contains(managerToAddTo)) {
                    droneToAdd.addManager(managerToAddTo);
                    if(!messageDisplayed) {
                        JOptionPane.showMessageDialog(AddDroneToManagerWindow.this, "Manager added to drone list successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (!messageDisplayed) {
                    JOptionPane.showMessageDialog(AddDroneToManagerWindow.this, "The manager is already connected to the drone", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(managerIDLabel);
        panel.add(managerIDField);
        panel.add(droneCodeLabel);
        panel.add(droneCodeField);
        panel.add(submitButton);

        
        add(panel);
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
    }

}

