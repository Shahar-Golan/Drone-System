package Modified;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import HW2.DistanceDrone;
import HW2.Drone;
import HW2.ExpressDrone;
import HW2.systemDataBase;

class DroneForm extends JFrame {
    private JTextField codeField, batteryPercentageField, speedField, extraPriceField, maxDistanceField, numOfBatteriesField;
    private JRadioButton availableButton, takenButton;
    public DroneForm(systemDataBase system, String droneType) {
        setTitle("Add " + droneType);
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create form components
        codeField = new JTextField(10);
        batteryPercentageField = new JTextField(10);
        speedField = new JTextField(10);
        extraPriceField = new JTextField(10);
        maxDistanceField = new JTextField(10);
        numOfBatteriesField = new JTextField(10);
        availableButton = new JRadioButton("Available", true);
        takenButton = new JRadioButton("Taken");

        
        ButtonGroup group = new ButtonGroup();
        group.add(availableButton);
        group.add(takenButton);

        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        
        formPanel.add(new JLabel("Enter code:"));
        formPanel.add(codeField);
        formPanel.add(new JLabel("Battery Percentage:"));
        formPanel.add(batteryPercentageField);

        // Add specific components based on drone type
        if ("Express Drone".equals(droneType)) {
            formPanel.add(new JLabel("Speed:"));
            formPanel.add(speedField);
            formPanel.add(new JLabel("Extra Price:"));
            formPanel.add(extraPriceField);
        } else if ("Distance Drone".equals(droneType)) {
            formPanel.add(new JLabel("Extra Price:"));
            formPanel.add(extraPriceField);
            formPanel.add(new JLabel("Max Distance:"));
            formPanel.add(maxDistanceField);
            formPanel.add(new JLabel("Num of Batteries:"));
            formPanel.add(numOfBatteriesField);
        }

        // Add radio buttons
        formPanel.add(availableButton);
        formPanel.add(takenButton);


        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int code = 0;
                int batteryPercentage = 0;

                try {
                    code = Integer.parseInt(codeField.getText());
                    batteryPercentage = Integer.parseInt(batteryPercentageField.getText());
                } catch (NumberFormatException ex) {
                    // Handle the exception if either input is not a valid integer
                    JOptionPane.showMessageDialog(DroneForm.this, "Wrong input", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

                // מבצע המרה ממחרוזת לסוג הטיפוס

                Boolean isAvailable = availableButton.isSelected(); // True if 'Available' is selected
                Drone drone = findDroneByCode(system, Integer.valueOf(code));
                if(drone == null) {
                    // Handling for different drone types
                    if ("Express Drone".equals(droneType)) {
                        double speed = 0.0;
                        int extraPrice = 0;

                        try {
                            speed = Double.parseDouble(speedField.getText());
                            extraPrice = Integer.parseInt(extraPriceField.getText());

                        } catch (NumberFormatException ex) {
                            // Handle the exception if either input is not valid (not a valid double for speed or not a valid integer for extraPrice)
                            JOptionPane.showMessageDialog(DroneForm.this, "Wrong input for speed or extra price", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return; 
                        }


                        ExpressDrone expressDrone = new ExpressDrone(code, isAvailable, batteryPercentage, speed, extraPrice);
                        system.addDrone(expressDrone);


                    } else if ("Distance Drone".equals(droneType)) {
                        double extraPrice = 0.0;
                        double maxDistance = 0.0;
                        int numOfBatteries = 0;

                        try {
                            extraPrice = Double.parseDouble(extraPriceField.getText());
                            maxDistance = Double.parseDouble(maxDistanceField.getText());
                            numOfBatteries = Integer.parseInt(numOfBatteriesField.getText());

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(DroneForm.this, "Please enter valid numbers for extra price, max distance, and number of batteries", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return; // Optionally return from the method if any input is invalid
                        }


                        DistanceDrone distanceDrone = new DistanceDrone(code, isAvailable, batteryPercentage, maxDistance, extraPrice, numOfBatteries);
                        system.addDrone(distanceDrone);
                    }
                    else {      // מקרה בו לחצו על רחפן רגיל
                        drone = new Drone(code, isAvailable, batteryPercentage);
                        system.addDrone(drone);
                    }
                    JOptionPane.showMessageDialog(DroneForm.this, "Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Close the window
                    dispose();
                } else {
                    // If drone already exists in the system, show a different message
                    JOptionPane.showMessageDialog(DroneForm.this, "Drone already exists in the system", "Error", JOptionPane.ERROR_MESSAGE);
                }
                    
            }
        });
        
            formPanel.add(addButton);

            add(formPanel);
          
        
    }


    // Helper method to find a drone by drone code
	public static Drone findDroneByCode(systemDataBase s, int droneCode) {
	    for (Drone drone : s.getDrones()) {
	        if (drone.getDroneCode() == droneCode) {
	            return drone;
	        }
	    }
	    return null;
	}

}
