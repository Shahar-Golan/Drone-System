package Modified;

import javax.swing.*;

import HW2.DistanceDrone;
import HW2.Drone;
import HW2.ExpressDrone;
import HW2.Manager;
import HW2.Order;
import HW2.Subscription;
import HW2.systemDataBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class OrderEntryWindow extends JFrame {
    private JTextField priceField, monthField, minutesField;
    public OrderEntryWindow(systemDataBase system, Manager admin, Drone drone, Subscription subscriber, int day, int hour) {
        setTitle("New Order Entry");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        priceField = new JTextField(10);
        monthField = new JTextField(10);
        minutesField = new JTextField(10);

        JButton submitButton = new JButton("Submit Order");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double price = Double.parseDouble(priceField.getText());
                    int month = Integer.parseInt(monthField.getText());
                    int minutes = Integer.parseInt(minutesField.getText());

                    // Validation checks
                    if (price < 0 )
                        throw new IllegalArgumentException("Price need to be positive.");
                    if(month < 1 || month > 12 )
                        throw new IllegalArgumentException("Month should be between 1-12.");
                    if(minutes < 0 || minutes > 59)
                        throw new IllegalArgumentException("Minutes should be between 0-59.");

                    double orderPrice = price + calculateExtraOrderPrice(drone); 
	                Order newOrder = new Order(generateOrderCode(), day, month, hour, minutes, subscriber.getSubCode(), drone.getDroneCode(), orderPrice);
	                admin.addOrder(newOrder);		// מוסיף את ההזמנה שתהיה תחת המנהל
					system.addOrder(newOrder);		// מוסיף את ההזמנה גם למערך ההזמנות הכללי

	                // Update drone status to "busy"
	                drone.setStatus(false);

                    boolean isFound = false;
                    for(Map.Entry<Integer, ArrayList<Order>> entry : system.getSubscriberOrders().entrySet()) {	// רץ על האוסף של המנוי וההזמנות ששייכות אליו	
						if(entry.getKey() == subscriber.getSubCode()){ 					// בודק האם יש כפילות
							isFound = true;
						}
					}
					if(!isFound) {													// במידה והמנוי לא נמצא באוסף יש להוסיף אותו
						ArrayList<Order> addedOneOrder = new ArrayList<Order>();
						addedOneOrder.add(newOrder);								// יצרתי אוסף חדש שמכיל את ההזמנה החדשה שנוצרה
						system.getSubscriberOrders().put(subscriber.getSubCode(), addedOneOrder);		// הכנסתי אותו להאש מאפ
					}
					else {															// אם המנוי כבר קיים באוסף, נוסיף את ההזמנה למערך המשוייך אליו
						system.getSubscriberOrders().get(subscriber.getSubCode()).add(newOrder);		// פה הוספתי באמצעות שליפה
					} 


                    JOptionPane.showMessageDialog(OrderEntryWindow.this, "Order added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(OrderEntryWindow.this, "Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(OrderEntryWindow.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // פונקציה מתרגיל קודם

            private double calculateExtraOrderPrice(Drone drone) {
                if (drone instanceof ExpressDrone) {
                ExpressDrone expressDrone = (ExpressDrone) drone;
                return expressDrone.getExtraPrice(); 
                } else if (drone instanceof DistanceDrone) {
                    DistanceDrone distanceDrone = (DistanceDrone) drone;
                    return distanceDrone.getExtraPrice(); 
                } else {
                    // Default pricing for general drones
                    return 0.0; 
                }
            }
        });

        setLayout(new GridLayout(4, 2));
        add(new JLabel("Price:"));
        add(priceField);
        add(new JLabel("Month:"));
        add(monthField);
        add(new JLabel("Minutes:"));
        add(minutesField);
        add(submitButton);

    }


    // פונצקיה  מתרגיל קודם
    
	public int generateOrderCode() {
	    // You can use a combination of current timestamp and a random number for uniqueness
	    long timestamp = System.currentTimeMillis();
	    int random = (int) (Math.random() * 1000); // You can adjust the range as needed
	    return (int) (timestamp + random);
	}
}



