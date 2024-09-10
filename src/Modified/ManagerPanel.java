package Modified;

import javax.swing.*;

import HW2.Drone;
import HW2.Manager;
import HW2.Subscription;
import HW2.systemDataBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ManagerPanel extends JFrame {
    private JTextField subscriptionCodeField, droneCodeField, dayField, hourField;

    public ManagerPanel(systemDataBase system, Manager manager) {
        setTitle("Manager Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create labels and text fields
        JLabel subscriptionCodeLabel = new JLabel("Subscription Code:");
        subscriptionCodeField = new JTextField(10);

        JLabel droneCodeLabel = new JLabel("Drone Code:");
        droneCodeField = new JTextField(10);

        JLabel dayLabel = new JLabel("Day:");
        dayField = new JTextField(10);

        JLabel hourLabel = new JLabel("Hour:");
        hourField = new JTextField(10);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int subCode = Integer.parseInt(subscriptionCodeField.getText());
                    int droneCode = Integer.parseInt(droneCodeField.getText());
                    int day = Integer.parseInt(dayField.getText());
                    int hour = Integer.parseInt(hourField.getText());

                    // Check for valid hour range
                    if (hour < 0 || hour > 23) {
                        throw new IllegalArgumentException("Hour must be between 0 and 23");
                    }

                    // Check for valid day range
                    if (day < 1 || day > 31) {
                        throw new IllegalArgumentException("Day must be between 1 and 31");
                    }
                    
                    // Check if the subscriber and drone are in the system
                    Subscription subscriber = findSubscriberByCode(system, subCode);
                    Drone drone = findDroneByCode(system, droneCode);
                    // Use these values for further processing
                    if (subscriber != null && drone != null) {
                        if(drone.isStatus()) {
                            if(manager.isDroneResponsible(drone)) {
                                JOptionPane.showMessageDialog(null, "Ready to add new order!");
                                // Open the Manager window
                                OrderEntryWindow orderEntryWindow = new OrderEntryWindow(system, manager, drone, subscriber, day, hour);
                                orderEntryWindow.setVisible(true);
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Manager is not responsible for the drone.");
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Drone is not avilable.");
                        }
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Subscriber or drone not found.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ManagerPanel.this, "Please enter valid numbers", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(ManagerPanel.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        JPanel panel = new JPanel(new GridLayout(5, 2)); 
        panel.add(subscriptionCodeLabel);
        panel.add(subscriptionCodeField);
        panel.add(droneCodeLabel);
        panel.add(droneCodeField);
        panel.add(dayLabel);
        panel.add(dayField);
        panel.add(hourLabel);
        panel.add(hourField);
        panel.add(submitButton);

        // Add the panel to the frame
        add(panel);

    }







        // Helper method to find a subscriber by subscription code
	public static Subscription findSubscriberByCode(systemDataBase s, int subCode) {
		for(Map.Entry<Integer,Subscription> entry : s.getSubscribers().entrySet()) {
            Subscription val=entry.getValue();
			if(val.getSubCode() == subCode){
				return val; 
			}
		}

	    return null;
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




