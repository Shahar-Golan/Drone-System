package HW2;

public class Order {
	private int orderCode;
	private int day;
	private int month;
	private int hour;
	private int minutes;
	private int subCode;
	private int droneCode;
	private double orderPrice;
	public Order(int orderCode, int day, int month, int hour, int minutes, int subCode, int droneCode,
			double orderPrice) {
		super();
		this.orderCode = orderCode;
		this.day = day;
		this.month = month;
		this.hour = hour;
		this.minutes = minutes;
		this.subCode = subCode;
		this.droneCode = droneCode;
		this.orderPrice = orderPrice;
	}
	public int getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getSubCode() {
		return subCode;
	}
	public void setSubCode(int subCode) {
		this.subCode = subCode;
	}
	public int getDroneCode() {
		return droneCode;
	}
	public void setDroneCode(int droneCode) {
		this.droneCode = droneCode;
	}
	public double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	@Override
	public String toString() {
		return "orderCode=" + orderCode + ", day=" + day + ", month=" + month + ", hour=" + hour + ", minutes="
				+ minutes + ", subCode=" + subCode + ", droneCode=" + droneCode + ", orderPrice=" + orderPrice + "]";
	}

	public String toStringPanel() {
		return "orderCode= ---" + orderCode + "---\n, day=" + day + ", month=" + month + "\nhour=" + hour + ", minutes="
		+ minutes + "\nsubCode=" + subCode + ", droneCode=" + droneCode + ", orderPrice=" + orderPrice + "]";
	}
	
	

}
