package Modified;

import HW2.systemDataBase;

import javax.swing.*;

public class AddDroneWindow extends JFrame {

    
    private systemDataBase system;

    public AddDroneWindow(systemDataBase system) {
        this.system = system;
        setTitle("Add Drone");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a menu item for each drone type
        JMenuItem droneItem = new JMenuItem("Drone");
        JMenuItem expressDroneItem = new JMenuItem("Express Drone");
        JMenuItem distanceDroneItem = new JMenuItem("Distance Drone");

        // Add action listeners to menu items
        droneItem.addActionListener(e -> openDroneForm("Drone"));
        expressDroneItem.addActionListener(e -> openDroneForm("Express Drone"));
        distanceDroneItem.addActionListener(e -> openDroneForm("Distance Drone"));

        // Add menu items to the menu bar
        menuBar.add(droneItem);
        menuBar.add(expressDroneItem);
        menuBar.add(distanceDroneItem);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);
    }

    private void openDroneForm(String droneType) {
        DroneForm droneForm = new DroneForm(system, droneType);
        droneForm.setVisible(true);
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
    }
}

