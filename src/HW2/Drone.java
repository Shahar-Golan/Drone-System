package HW2;

import java.util.ArrayList;

public class Drone {
	protected int droneCode;
	protected boolean status;
	protected double battery;
	protected ArrayList<Manager> managers;
	
	public Drone(int droneCode, boolean status, double battery) {
		this.droneCode = droneCode;
		this.status = status;
		this.battery = battery;
		this.managers = new ArrayList<Manager>();
	}

	public Drone(int droneCode, ArrayList<Manager> managers) {		// הבנאי הזה מקבל רק קוד ואוסף
		this.droneCode = droneCode;
		this.managers = managers;
	}

	// add Manager function
	public void addManager(Manager newManager)
	{
		if(newManager == null) {
			System.out.println("ERROR : null manager!!!");
			return;
		}
		for(Manager m : managers) {
			if(m.getId() == newManager.getId()){
				System.out.println("The manager is already here!");
				return;
			}
		}
		managers.add(newManager);
	}

	public int getDroneCode() {
		return droneCode;
	}
	public void setDroneCode(int droneCode) {
		this.droneCode = droneCode;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public double getBattery() {
		return battery;
	}
	public void setBattery(double battery) {
		this.battery = battery;
	}
	@Override
	public String toString() {
		return "droneCode=  ---" + droneCode + "---  status=" + status + ", battery=" + battery;
	}
	
	

}
