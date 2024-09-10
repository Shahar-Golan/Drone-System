package HW2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;




public class systemDataBase {
    private ChiefManager headManager;
    private ArrayList<Manager> managers;
    private Hashtable<Integer, Subscription> subscribers;
    private ArrayList<Order> orders;
    private ArrayList<Drone> drones;
    private HashMap<Integer, ArrayList<Order>> subscriberOrders;


    public systemDataBase(ChiefManager headManager) {                   // בנאי שמקבל מנהל ראשי, ומאתחל את שאר המערכים
        this.headManager = headManager;
        this.managers = new ArrayList<Manager>();
        this.subscribers = new Hashtable<Integer, Subscription>();
        this.orders = new ArrayList<Order>();
        this.drones = new ArrayList<Drone>();
        this.subscriberOrders = new HashMap<Integer, ArrayList<Order>>();
    }

    public ArrayList<Order> subscriberPersonalOrders(int subCode) {                     // סעיף א, מחזירה אוסף של ההזמנות שמשוייכות למנוי מתוך HASHTABLE
        for (HashMap.Entry<Integer, ArrayList<Order>> m : subscriberOrders.entrySet()) {
            if(m.getKey() == subCode) {
                System.out.println("The orders found");
                return m.getValue();
            }    
        }
        return null;
    }

    public ArrayList<Drone> responsibleDrones(Manager manager) {            // סעיף ב, מחזירה אוסף רחפנים שהמנהל אחראי עליהם
        for(Manager m : managers) {
            if(m.getId().equals(manager.getId())) {     // בודק שהוא נמצא במערך
                System.out.println("The manager found");
                if(manager.getManagerDrones() != null) {
                    return manager.getManagerDrones();
                }
                else {
                    System.out.println("the manager is not responsible for any drones");
                }
            }
        }
        System.out.println("The manager not found");
        return null;
    }

    public ArrayList<Manager> supervisedByManagers(Drone drone) {            // סעיף ג, מחזירה אוסף של מנהלים שאחראיים על רחפן כלשהו
        ArrayList<Manager> toReturn = new ArrayList<Manager>();
        for(Manager m : managers) {
            for(Drone d : m.getManagerDrones()) {
                if(d.getDroneCode() == drone.getDroneCode()) {
                    toReturn.add(m);
                }
            }
        }
        if(toReturn.size() > 0) {
            System.out.println("There are " + toReturn.size() + " managers responsible for the drone");
            return toReturn;
        }
        else {
            System.out.println("The drone has no manger or it is not exist");
            return null;
        }
    }

    // add drone function
	public void addDrone(Drone newDrone)
	{
		if(newDrone == null) {
			System.out.println("ERROR : null drone!!!");
			return;
		}
		for(Drone d : drones) {
			if(d.getDroneCode() == newDrone.getDroneCode()){
				System.out.println("The drone is already here!");
				return;
			}
		}
		drones.add(newDrone);
	}

    // add Manager function
	public void addManager(Manager newManager)
	{
		if(newManager == null) {
			System.out.println("ERROR : null manager!!!");
			return;
		}
		for(Manager m : managers) {
			if(m.getId().equals(newManager.getId())){
				System.out.println("The manager is already here!");
				return;
			}
		}
		managers.add(newManager);
	}

    // add order function
	public void addOrder(Order newOrder)
	{
		if(newOrder == null) {
			System.out.println("ERROR : null drone!!!");
			return;
		}
		for(Order d : orders) {
			if(d.getDroneCode() == newOrder.getDroneCode()){
				System.out.println("The drone is already here!");
				return;
			}
		}
		orders.add(newOrder);
	}

    // add subscription function
	public void addSubscription(Subscription newSubscription)
	{
		if(newSubscription == null) {
			System.out.println("ERROR : null subscription!!!");
			return;
		}
		for(Map.Entry<Integer, Subscription> entry : subscribers.entrySet()) {
            Subscription val=entry.getValue();
			if(val.getSubCode() == newSubscription.getSubCode()){           // בודק האם המנוי נמצא כדי לא להוסיף פעמיים לטבלה
				System.out.println("The subscription is already here!");
				return;
			}
		}
        subscribers.put(newSubscription.getSubCode(), newSubscription);     // מוסיף באמצעות PUT
	}

    public ChiefManager getHeadManager() {
        return headManager;
    }

    public void setHeadManager(ChiefManager headManager) {
        this.headManager = headManager;
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public Hashtable<Integer, Subscription> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Hashtable<Integer, Subscription> subscribers) {
        this.subscribers = subscribers;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Drone> getDrones() {
        return drones;
    }

    public void setDrones(ArrayList<Drone> drones) {
        this.drones = drones;
    }

    public HashMap<Integer, ArrayList<Order>> getSubscriberOrders() {
        return subscriberOrders;
    }

    public void setSubscriberOrders(HashMap<Integer, ArrayList<Order>> subscriberOrders) {
        this.subscriberOrders = subscriberOrders;
    }

    
}
