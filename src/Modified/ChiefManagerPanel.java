package Modified;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import HW2.ChiefManager;
import HW2.DistanceDrone;
import HW2.Drone;
import HW2.ExpressDrone;
import HW2.Manager;
import HW2.Order;
import HW2.Subscription;
import HW2.systemDataBase;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;

public class ChiefManagerPanel extends JFrame {
    private systemDataBase system;
    private JTable subscriberTable, managerTable, droneTable;
    public ChiefManagerPanel(systemDataBase system) {
        this.system = system;
        setTitle("Chief Manager Panel");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create menu items
        JMenu subscribersMenu = new JMenu("Subscribers");
        JMenu managersMenu = new JMenu("Managers");
        JMenu dronesMenu = new JMenu("Drones");

        // מאפשר אופציה לעכבר רגיש שיפתח טבלה


        subscribersMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showSubscribersTable();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hideSubscribersTable();
            }
        });

        
        managersMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showManagersTable();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hideManagersTable();
            }
        });

        // Add drones to the Drones menu
        dronesMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showDronesTable();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hideDronesTable();
            }
        });

            // ארבע קטעי קוד שכל אחד מהם יוצר מניו אייטם ופותח את החלון כאשר יש לחיצה

        JMenuItem addManagerMenuItem = new JMenuItem("Add New Manager");
        addManagerMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddManagerWindow addManagerWindow = new AddManagerWindow(system);
                addManagerWindow.setVisible(true);
            }
        });
        menuBar.add(addManagerMenuItem);

        JMenuItem addDroneMenuItem = new JMenuItem("Add New Drone");
        addDroneMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddDroneWindow addDroneWindow = new AddDroneWindow(system);
                addDroneWindow.setVisible(true);
            }
        });
        menuBar.add(addDroneMenuItem);

        JMenuItem addDroneToManagerMenuItem = new JMenuItem("Add Drone To Manager");
        addDroneToManagerMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddDroneToManagerWindow addDroneToManagerWindow = new AddDroneToManagerWindow(system);
                addDroneToManagerWindow.setVisible(true);
            }
        });
        menuBar.add(addDroneToManagerMenuItem);

        JMenuItem calculatorWindowMenuItem = new JMenuItem("Calculator");
        calculatorWindowMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CalculatorWindow calculatorWindow = new CalculatorWindow();
                calculatorWindow.setVisible(true);
            }
        });
        menuBar.add(calculatorWindowMenuItem);

        // create a menu item that implements the reading from the text file
        JMenuItem scanManagers = new JMenuItem("Scan Managers");
        scanManagers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filename = "SystemManagers.txt"; // The file where managers are stored
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(filename));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(", ");
                        if (parts.length >= 3) {
                            String id = parts[0].trim();
                            String firstName = parts[1].trim();
                            String lastName = parts[2].trim();
                            // add the mangers to the system
                            system.addManager(new Manager(id, firstName, lastName));
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "The file is ilegal, not enogh details: ");
                        }
                    }
                    reader.close();
                    JOptionPane.showMessageDialog(null, "The managers added successfully");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading the file: " + ex.getMessage());
                }
            }
        });
        menuBar.add(scanManagers);


        // פה אני מבצע מימוש זהה למימוש שעשיתי בקריאה מהקובץ מנהלים
        JMenuItem scanSubscriptions = new JMenuItem("Scan Subscriptions");
        scanSubscriptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filename = "members.txt"; // The file where subscriptions are stored
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(filename));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(", ");
                        if (parts.length >= 5) {
                            int subCode = Integer.parseInt(parts[0].trim());
                            String firstName = parts[1].trim();
                            String lastName = parts[2].trim();
                            String address = parts[3].trim();
                            String phone = parts[4].trim();
                            // add the subscriptions to the system
                            system.addSubscription(new Subscription(subCode, firstName, lastName, address, phone));
                        }
                    }
                    reader.close();
                    JOptionPane.showMessageDialog(null, "The subscriptions added successfully");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading the file: " + ex.getMessage());
                }
            }
        });
        menuBar.add(scanSubscriptions);
        



        

        // מוסיף את הכפתור של הטבלא
        menuBar.add(subscribersMenu);
        menuBar.add(managersMenu);
        menuBar.add(dronesMenu);

        
        setJMenuBar(menuBar);

        // Create tables
        String subColumn[] = {"Details"};
        DefaultTableModel subModel = new DefaultTableModel(subColumn, 0); 
        subscriberTable = new JTable(subModel);

        String managerColumn[] = {"Details"};
        DefaultTableModel managerModel = new DefaultTableModel(managerColumn, 0); 
        managerTable = new JTable(managerModel);

        String droneColumn[] = {"Drone Type", "Drone Code"};
        DefaultTableModel droneModel = new DefaultTableModel(droneColumn, 0); // 2 columns: Drone Code, Drone Type
        droneTable = new JTable(droneModel);

        JScrollPane subscriberScrollPane = new JScrollPane(subscriberTable);
        JScrollPane managerScrollPane = new JScrollPane(managerTable);
        JScrollPane droneScrollPane = new JScrollPane(droneTable);

        
        setLayout(new GridLayout(3, 1)); 
        add(subscriberScrollPane);
        add(managerScrollPane);
        add(droneScrollPane);

        // מסתיר את הטבלאות כדי שלא יהיה חשוף

        hideSubscribersTable(); 
        hideManagersTable(); 
        hideDronesTable();
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); 

        JButton managerButton = new JButton("Download Managers");
        managerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeManagersToFile();
            }
        });
        JButton subscribersButton = new JButton("Download Subscribers");
        subscribersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeSubscribersToFile();
            }
        });
        
        JButton ordersButton = new JButton("Download Orders");
        ordersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeOrdersToFile();
            }
        });
        
        JButton dronesButton = new JButton("Download Drones");
        dronesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeDronesToFile();
            }
        });

        buttonPanel.add(managerButton);
        buttonPanel.add(subscribersButton);
        buttonPanel.add(ordersButton);
        buttonPanel.add(dronesButton);

        add(buttonPanel);







    }

    //////////////////// להראות טבלת מנויים

    private void showSubscribersTable() {
        DefaultTableModel model = (DefaultTableModel) subscriberTable.getModel();
        model.setRowCount(0); // Clear existing rows

        // Populate data array
        ArrayList<Subscription> subToSort = convertTreeMapToArrayList(system.getSubscribers());
        Collections.sort(subToSort);     // Sorting the subscribers collection.
        for (Subscription subscriber : subToSort) {
            Object[] rowData = {subscriber.toString()};
            model.addRow(rowData); // Add row to the table model
        }
        subscriberTable.getTableHeader().setVisible(true);
        subscriberTable.setVisible(true);
    }

    private void hideSubscribersTable() {
        subscriberTable.getTableHeader().setVisible(false);
        subscriberTable.setVisible(false);
    }

    /////////////////// להראות טבלת מנהל

    private void showManagersTable() {
        DefaultTableModel model = (DefaultTableModel) managerTable.getModel();
        model.setRowCount(0); // Clear existing rows

        // Populate data array
        Collections.sort(system.getManagers());     // Sorting the manger collection.
        for (Manager manager : system.getManagers()) {
            Object[] rowData = {manager.toString()};
            model.addRow(rowData); // Add row to the table model
        }
        managerTable.getTableHeader().setVisible(true);
        managerTable.setVisible(true);
    }

    private void hideManagersTable() {
        managerTable.getTableHeader().setVisible(false);
        managerTable.setVisible(false);
    }

    /////////////////// להראות טבלת רחפנים

    private void showDronesTable() {
        DefaultTableModel model = (DefaultTableModel) droneTable.getModel();
        model.setRowCount(0); // Clear existing rows

        // Populate data array
        for (Drone drone : system.getDrones()) {
            Object[] rowData = {drone.getClass().getSimpleName(), drone.toString()};
            model.addRow(rowData); // Add row to the table model
        }
        droneTable.getTableHeader().setVisible(true);
        droneTable.setVisible(true);
    }

    private void hideDronesTable() {
        droneTable.getTableHeader().setVisible(false);
        droneTable.setVisible(false);
    }


    // דריסת קובץ קיים וכתיבה אליו מחדש
    private void writeManagersToFile() {
        ArrayList<Manager> managersTemp = new ArrayList<Manager>();
        // יוצר אוסף חדש שהוא רק מנהלים ללא ראשיים
        for(Manager m : system.getManagers()) {
            if(!(m instanceof ChiefManager))
                managersTemp.add(m);
        }
        // מבצע מיון לאוסף לפי למבדה
        Collections.sort(managersTemp, (m1, m2) -> Integer.compare(Integer.parseInt(m1.getId()), Integer.parseInt(m2.getId())));


        // נבצע דריסה בשורה למטה
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SystemManagers.txt", false))) { // false to overwrite!!! file
            for (Manager manager : managersTemp) {
                String line = manager.getId() + ", " + manager.getFirstName() + ", " + manager.getLastName() + "\n";
                writer.write(line);
            }
            JOptionPane.showMessageDialog(this, "Managers written to SystemManagers.txt");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage());
        }
    }

    private void writeSubscribersToFile() {
        Hashtable<Integer, Subscription> subscribers = system.getSubscribers();
        ArrayList<Subscription> subscriberList = new ArrayList<>(subscribers.values());
        Collections.sort(subscriberList, (s1, s2) -> s1.getLastName().compareTo(s2.getLastName()));
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("members.txt", false))) {
            for (Subscription subscriber : subscriberList) {
                String line = subscriber.getSubCode() + ", " + subscriber.getFirstName() + ", " + subscriber.getLastName() + ", " + subscriber.getAddress() + ", " + subscriber.getPhone() + "\n";
                writer.write(line);
            }
            JOptionPane.showMessageDialog(this, "Subscribers written to members.txt");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage());
        }
    }

    private void writeOrdersToFile() {
        ArrayList<Order> orders = system.getOrders(); // Get the unsorted ArrayList of Orders
        Collections.sort(orders, (o1, o2) -> Integer.compare(o1.getOrderCode(), o2.getOrderCode()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt", false))) {
            for (Order order : orders) {
                String line = "Order code: " + order.getOrderCode() + ", Day: " + order.getDay() + ", Month: " + order.getMonth() + ", Hour: " +
                            order.getHour() + ", Minutes: " + order.getMinutes() + ", subCode" + order.getSubCode() + ", Drone code: " +
                            order.getDroneCode() + ", Order price: " + order.getOrderPrice() + "\n";
                writer.write(line);
            }
            JOptionPane.showMessageDialog(this, "Orders written to orders.txt");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage());
        }
    }

    private void writeDronesToFile() {
        ArrayList<Drone> drones = system.getDrones(); // Get the ArrayList of Drones
        Collections.sort(drones, (d1, d2) -> Integer.compare(d1.getDroneCode(), d2.getDroneCode()));
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("drones.txt", false))) {
            for (Drone drone : drones) {
                String managerDetails = "";
                for (Manager m : system.supervisedByManagers(drone)) {
                    managerDetails += "Manger code" + m.getId() + ", " + m.getFirstName() + " " + m.getLastName() + "\n";
                }
                String status = "";
                if(drone.isStatus())
                    status = "taken";
                else
                    status = "free";
                
                String line = "";
                if(drone instanceof DistanceDrone) {
                    line = "Drone code: " + drone.getDroneCode() + ", Status: " + status + ", Battery: " + drone.getBattery()
                                    + ", maxDistance: " + ((DistanceDrone)drone).getMaxDistance() + ", extraPrice: " + ((DistanceDrone)drone).getExtraPrice() 
                                    + "\nManagers: \n" + managerDetails; 
                }
                else if(drone instanceof ExpressDrone) {
                    line = "Drone code: " + drone.getDroneCode() + ", Status: " + status + ", Battery: " + drone.getBattery()
                                    + ", maxDistance: " + ((ExpressDrone)drone).getSpeed() + ", extraPrice" + ((ExpressDrone)drone).getExtraPrice() 
                                    + "\nManagers: \n" + managerDetails;

                }
                else {
                    line = "Drone code: " + drone.getDroneCode() + ", Status: " + status + ", Battery: " + drone.getBattery() + "\nManagers: \n" + managerDetails;
                }
                writer.write(line);
            }
            JOptionPane.showMessageDialog(this, "Drones and Managers written to drones.txt");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage());
        }
        
    }
    

    
    


    // פונקציית עזר  

    private ArrayList<Subscription> convertTreeMapToArrayList(Hashtable<Integer, Subscription> hashtable) {

        ArrayList<Subscription> toReturn = new ArrayList<Subscription>();
        Iterator<Subscription> itS = hashtable.values().iterator();
        while(itS.hasNext()) {
            Subscription s = itS.next();
            toReturn.add(s);
        }
        return toReturn;
    }
}
