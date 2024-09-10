package HW2;

import java.util.ArrayList;


public class Manager implements Comparable<Manager>{
	protected String id;
	protected String firstName;
	protected String lastName;
	protected ArrayList<Drone> managerDrones;
	protected ArrayList<Order> managerOrders;
	
	public Manager(String id, String firstName, String lastName) {		// בנאי התחלתי לאתחול
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.managerDrones = new ArrayList<Drone>();
		this.managerOrders = new ArrayList<Order>();
	}
	public Manager(String id, ArrayList<Drone> managerDrones,		// בנאי שמקבל כפרמטר את שני האוספים
			ArrayList<Order> managerOrders) {
		this.id = id;
		this.managerDrones = managerDrones;
		this.managerOrders = managerOrders;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public ArrayList<Drone> getManagerDrones() {
		return managerDrones;
	}
	public void setManagerDrones(ArrayList<Drone> managerDrones) {
		this.managerDrones = managerDrones;
	}
	public ArrayList<Order> getManagerOrders() {
		return managerOrders;
	}
	public void setManagerOrders(ArrayList<Order> managerOrders) {
		this.managerOrders = managerOrders;
	}

	public void addDrone(Drone newDrone)
	{
		if(newDrone == null) {
			System.out.println("ERROR : null drone!!!");
			return;
		}
		for(Drone d : managerDrones) {
			if(d.getDroneCode() == newDrone.getDroneCode()){
				System.out.println("The drone is already here!");
				return;
			}
		}
		managerDrones.add(newDrone);
	}
	
	public void addOrder(Order newOrder)
	{
		if(newOrder == null) {
			System.out.println("ERROR : null order!!!");
			return;
		}
		for(Order o : managerOrders) {
			if(o.getOrderCode() == newOrder.getOrderCode()){
				System.out.println("The order is already here!");
				return;
			}
		}
		managerOrders.add(newOrder);
		System.out.println("Order added successfuly ;) ");
	}
	@Override
	public String toString() {
		return "Main Manager id=" + id + ", firstName=  ---" + firstName + "---  , lastName=" + lastName;

	}
	
	// function help us to check if the manager responsible about the drone or not
	public boolean isDroneResponsible(Drone drone) {
		for(Drone d : managerDrones)
			if(d.getDroneCode() == drone.getDroneCode()) return true;
		return false;
	}
	@Override
	public int compareTo(Manager o) {
		return this.getFirstName().compareTo(o.getFirstName());
	}
	
	
	
	

}
