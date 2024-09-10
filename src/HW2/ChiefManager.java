package HW2;

import java.util.ArrayList;

public class ChiefManager extends Manager {
	private String userName;
	private String password;
	public ChiefManager(String id, String firstName, String lastName,			// בנאי שמטפל במנהל ראשי שמקבל מחרוזות
			String userName, String password) {
		super(id, firstName, lastName);
		this.userName = userName;
		this.password = password;
	}
	
	public ChiefManager(String id, ArrayList<Drone> managerDrones, ArrayList<Order> managerOrders) {		//  .בנאי שמטפל במנהל ראשי אשר מקבל את האוספים
		super(id, managerDrones, managerOrders);															// ככה לא נצטרך לשלוח מצביע ריק סתם.
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "ChiefManager " + "Id = " + getId()
				+ ", FirstName=  ---" + getFirstName() + "---  , LastName=" + getLastName();
	}
	
	

}
