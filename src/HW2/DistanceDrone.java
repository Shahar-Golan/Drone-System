package HW2;

public class DistanceDrone extends Drone{
	private double maxDistance;
	private double extraPrice;
	private int batteriesNum;
	public DistanceDrone(int droneCode, boolean status, double battery, double maxDistance, double extraPrice,
			int batteriesNum) {
		super(droneCode, status, battery);
		this.maxDistance = maxDistance;
		this.extraPrice = extraPrice;
		this.batteriesNum = batteriesNum;
	}
	public double getMaxDistance() {
		return maxDistance;
	}
	public void setMaxDistance(double maxDistance) {
		this.maxDistance = maxDistance;
	}
	public double getExtraPrice() {
		return extraPrice;
	}
	public void setExtraPrice(double extraPrice) {
		this.extraPrice = extraPrice;
	}
	public int getBatteriesNum() {
		return batteriesNum;
	}
	public void setBatteriesNum(int batteriesNum) {
		this.batteriesNum = batteriesNum;
	}
	@Override
	public String toString() {
		return "droneCode=  ---" + droneCode + "---  maxDistance=" + maxDistance + ", extraPrice=" + extraPrice + ", batteriesNum="
				+ batteriesNum + ", code=" + getDroneCode() + ", status=" + isStatus() + ", battery="
				+ getBattery() ;
	}
	
	

}
