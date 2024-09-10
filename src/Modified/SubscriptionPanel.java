package Modified;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import HW2.Drone;
import HW2.Order;
import HW2.Subscription;
import HW2.systemDataBase;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SubscriptionPanel extends JFrame {
    private systemDataBase system;
    private Subscription subscriber;
    private JTable orderTable;

    public SubscriptionPanel(systemDataBase system, Subscription subscriber) {
        this.system = system;
        this.subscriber = subscriber;

        setTitle("Subscription Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize the order table
        String[] columnNames = {"Order Details", "Drone Type", "Drone Details"};
        DefaultTableModel orderModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(orderModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderTable.getTableHeader().setVisible(false); // Initially hide the table header
        orderTable.setVisible(false); // Initially hide the table

        
        JMenuBar menuBar = new JMenuBar();
        JMenu showOrdersMenu = new JMenu("Show Orders");
        JMenu updatePersonalDetails = new JMenu("Update Personal Details");

        updatePersonalDetails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UpdatePersonalData updatePersonalDataWindow = new UpdatePersonalData(system, subscriber);
                updatePersonalDataWindow.setVisible(true);
            }
        });
        menuBar.add(showOrdersMenu);
        menuBar.add(updatePersonalDetails);

        


        // Add mouse listener to show/hide the table on hover
        showOrdersMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                populateAndShowOrdersTable();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hideOrdersTable();
            }
        });

        setJMenuBar(menuBar);
        add(scrollPane); // Add the JScrollPane (which contains the table) to the frame
    }

    private void populateAndShowOrdersTable() {
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        model.setRowCount(0); // Clear the table

        ArrayList<Order> orders = system.subscriberPersonalOrders(subscriber.getSubCode());
        for (Order order : orders) {
            Drone drone = findDroneByCode(system, order.getDroneCode());
            Object[] rowData = {order.toString(), drone.getClass().getSimpleName(), drone.toString()};
            model.addRow(rowData);
        }

        orderTable.getTableHeader().setVisible(true);
        orderTable.setVisible(true);
    }

    private void hideOrdersTable() {
        orderTable.getTableHeader().setVisible(false);
        orderTable.setVisible(false);
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
