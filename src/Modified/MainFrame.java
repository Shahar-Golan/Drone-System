package Modified;

import javax.swing.*;

import HW2.ChiefManager;
import HW2.DistanceDrone;
import HW2.Drone;
import HW2.ExpressDrone;
import HW2.Manager;
import HW2.Order;
import HW2.Subscription;
import HW2.systemDataBase;
import HW4.connClass;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class MainFrame extends JFrame {
    private systemDataBase system;

    public MainFrame() {
        system = initializeSystem(); // Initialize the system

        setTitle("System Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton chiefManagerButton = new JButton("Chief Manager");
        chiefManagerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter username:");
                String password = JOptionPane.showInputDialog("Enter password:");
                boolean isValid = false;

                // check the feilds in my data base
                try {
                    isValid = checkInDataBase(username, password);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                if (isValid) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    // Open the Chief Manager window
                    ChiefManagerPanel chiefManagerPanel = new ChiefManagerPanel(system);
                    chiefManagerPanel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            }
        });
        
        JButton managerButton = new JButton("Manager");
        managerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String managerID = JOptionPane.showInputDialog("Enter Manager ID::");
                Manager manager = findAdminById(system, managerID);
                if (manager != null) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    // Open the Manager window
                    ManagerPanel managerPanel = new ManagerPanel(system, manager);
                    managerPanel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Manager ID.");
                }
            }
        });
        
        JButton subscriberButton = new JButton("Subscriber");
        subscriberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog("Enter Subscription Code:");
                // cast from string to int
                int subCode = Integer.parseInt(s);
                Subscription subscriber = findSubscriberByCode(system, subCode);
                if (subscriber != null) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    // Open the Subscriber window
                    SubscriptionPanel subscriptionPanel = new SubscriptionPanel(system, subscriber);
                    subscriptionPanel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid subscriber code.");
                }
            }
        });
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the MainFrame window
            }
        });

        JPanel panel = new JPanel();
        panel.add(chiefManagerButton);
        panel.add(managerButton);
        panel.add(subscriberButton);
        panel.add(closeButton);
        
        add(panel);
    }


///////////////////// ADDED FUNCTIONS!!! ///////////////////////


    // Method to check username and password
    private boolean checkInDataBase(String username, String password) throws ClassNotFoundException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = connClass.getConn();
        String sql = "SELECT count(*) FROM Cheif_Managers WHERE userName = ? AND password = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);

        rs = stmt.executeQuery();

        if (rs.next() && rs.getInt(1) == 1) {
            return true;
        }
        return false;
    } catch (SQLException e) {
        System.out.println(e);
        return false;
    } finally {
        // Close resources
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}


    public static Manager findAdminById(systemDataBase s, String adminId) {
	    for (Manager admin : s.getManagers()) {
	        if (admin.getId().equals(adminId)) {
	            return admin;
	        }
	    }
	    return null;
	}

    // Helper method to find a subscriber by subscription code
	public static Subscription findSubscriberByCode(systemDataBase s, int subCode) {
		for(Map.Entry<Integer,Subscription> entry : s.getSubscribers().entrySet()) {
            Subscription val=entry.getValue();
			if(val.getSubCode() == subCode){
				return val; 
			}
		}
		System.out.println("The code does not found!");
	    return null;
	}


    // Method to initialize the system
    private static systemDataBase initializeSystem() {
        // Your initialization code here
       ChiefManager mainAdmin1 = new ChiefManager("1", "Ross", "Geller", "system", "12345");
       ChiefManager mainAdmin2 = new ChiefManager("2", "Monica", "Geller", "admin2", "password2");
       Manager admin3 = new Manager("3", "Chandler", "Bing");
       Manager admin4 = new Manager("4", "Joey", "Tribbiani");
       Manager admin5 = new Manager("5", "Rachel", "Green");
	   systemDataBase ourSystem = new systemDataBase(mainAdmin1);

       // Add administrators to the administrators' collection
       ourSystem.addManager(mainAdmin1);
	   ourSystem.addManager(mainAdmin2);
       ourSystem.addManager(admin3);
       ourSystem.addManager(admin4);
       ourSystem.addManager(admin5);
	   
       // פה אני יוצר טבלה חדשה, ואז מוסיף אליה את שני המנהלים הראשיים

       Connection conn;
        try{
            conn = connClass.getConn();
            connClass.createTable();
    
            connClass.insertChiefManager(mainAdmin1);
            connClass.insertChiefManager(mainAdmin2);
            
            conn.close();
        } catch(Exception e){
            System.out.println(e);
        }

       // create 5 mangers that will be in the text file
       Manager admin6 = new Manager("6", "David", "Beckham");
       Manager admin7 = new Manager("7", "Ashley", "Cole");
       Manager admin8 = new Manager("8", "Leo", "Messi");
       Manager admin9 = new Manager("9", "Leroy", "Sane");
       Manager admin10 = new Manager("10", "Mo", "Salah");
       
       // add them to the text file
       writeObjectToFile("SystemManagers.txt", admin6);
       writeObjectToFile("SystemManagers.txt", admin7);
       writeObjectToFile("SystemManagers.txt", admin8);
       writeObjectToFile("SystemManagers.txt", admin9);
       writeObjectToFile("SystemManagers.txt", admin10);
       


       // Create an array of subscribers
       Subscription subscriber1 = new Subscription(1,"Phoebe", "Buffay", "Address1", "123456789");
       Subscription subscriber2 = new Subscription(2,"Ross", "Geller", "Address2", "987654321");
       Subscription subscriber3 = new Subscription(3,"Monica", "Geller", "Address3", "111223344");
       Subscription subscriber4 = new Subscription(4,"Joey", "Tribbiani", "Address4", "555666777");
       Subscription subscriber5 = new Subscription(5,"Rachel", "Green", "Address5", "999888777");
       Subscription subscriber6 = new Subscription(6,"Chandler", "Bing", "Address6", "111223344");
       Subscription subscriber7 = new Subscription(7,"Janice", "Hosenstein", "Address7", "555666777");
       Subscription subscriber8 = new Subscription(8,"Gunther", "CoffeeGuy", "Address8", "999888777");
       Subscription subscriber9 = new Subscription(9,"Ursula", "Buffay", "Address9", "111223344");
       Subscription subscriber10 = new Subscription(10,"Richard", "Burke", "Address10", "555666777");
       Subscription subscriber11 = new Subscription(11,"Dudi", "Amsalem", "Address11", "29814498");
       Subscription subscriber12 = new Subscription(12,"Richard", "Ramirez", "Address12", "71923256");
     
       // Add subscribers to the system (have to, because I create orders list for sub1, sub2 );
       ourSystem.addSubscription(subscriber1);
       ourSystem.addSubscription(subscriber2);

       // add subscribers to the subscribers file
       writeObjectToFile("members.txt", subscriber3);
       writeObjectToFile("members.txt", subscriber4);
       writeObjectToFile("members.txt", subscriber5);
       writeObjectToFile("members.txt", subscriber6);
       writeObjectToFile("members.txt", subscriber7);
       writeObjectToFile("members.txt", subscriber8);
       writeObjectToFile("members.txt", subscriber9);
       writeObjectToFile("members.txt", subscriber10);
       writeObjectToFile("members.txt", subscriber11);
       writeObjectToFile("members.txt", subscriber12);

       
	   
	   ArrayList<Order> ordersForSub1 = new ArrayList<Order>();
	   ArrayList<Order> ordersForSub2 = new ArrayList<Order>();
	   
	   // יוצר 10 הזמנות שונות
	   for(int i=1; i<10; i++) {
		int subCode = (i%2 == 0) ? 2: 1;	// פה אני רוצה בכוונה לשייך את ההזמנות למנויים 1 ו-2
		Order order = new Order(i, 2*i, i, i, i*3, subCode, i, i);
		ourSystem.addOrder(order);		// שולח אותן למערך הזמנות
		if(i%2 == 0)
			ordersForSub2.add(order);		// שולח למערך של המנוי 2
		else
			ordersForSub1.add(order);
	   }

	   // שולח את המערך להאש טייבל
	   ourSystem.getSubscriberOrders().put(1, ordersForSub1);
	   ourSystem.getSubscriberOrders().put(2, ordersForSub2);


       
       boolean status;
       for (int i = 0; i < 9; i++) {
           // Example: create 10 different drones
           if (i < 3) {
           	status =(i%2==0);
               Drone d = new Drone(i + 1, status, 100.0 - i * 5);
			   ourSystem.addDrone(d);		// מוסיף רחפן שיהיה חלק מהמערכת
			   admin3.addDrone(d);			// מוסיף רחפן שיהיה תחת האחריות של המנהל
           }
           // Example: create 10 different express drones
           else if (i < 6) {
           	status =(i%2!=0);
               Drone d = new ExpressDrone(i + 1, status, 80.0 - (i - 10) * 4, 50.0 - (i - 10) * 2, 10.0 - (i - 10));
			   ourSystem.addDrone(d);
			   admin4.addDrone(d);
           }
           // Example: create 10 different distance drones
           else {
           	status =(i%2==0);
               Drone d = new DistanceDrone(i + 1, status, 90.0 - (i - 20) * 4, 200.0 - (i - 20) * 10, 15.0 - (i - 20) * 0.5, 2);
			   ourSystem.addDrone(d);
			   admin5.addDrone(d);
           }
       }
       return ourSystem;		// מחזיר את המערכת לאחר שהכל אותחל
   }

   public static void writeObjectToFile(String filename, Object obj) {
        boolean exists = false;
        String identifier = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String currentLine;

            if (obj instanceof Manager) {
                identifier = ((Manager) obj).getId();
            } else if (obj instanceof Subscription) {
                identifier = String.valueOf(((Subscription) obj).getSubCode());
            }

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith(identifier + ",")) {
                    exists = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        if (!exists) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                String line = "";
                if (obj instanceof Manager) {
                    Manager manager = (Manager) obj;
                    line = manager.getId() + ", " + manager.getFirstName() + ", " + manager.getLastName() + "\n";
                } else if (obj instanceof Subscription) {
                    Subscription subscription = (Subscription) obj;
                    line = subscription.getSubCode() + ", " + subscription.getFirstName() + ", " + subscription.getLastName() + ", " + subscription.getAddress() + ", " + subscription.getPhone() + "\n";
                }

                writer.write(line);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }





    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }
}
