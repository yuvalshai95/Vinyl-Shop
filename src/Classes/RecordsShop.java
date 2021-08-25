package Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import Enum.City;

public class RecordsShop implements Serializable {


	private static final long serialVersionUID = -5138379888095525394L;
	private static RecordsShop instance;
	private String shopName;
	private ArrayList<Record> recordsList;
	private ArrayList<Order> ordersList;
	private ArrayList<Customer> customersList;
	private ArrayList<Employee> employeesList;
	
	private RecordsShop(String shopName) {
		setShopName(shopName);
		recordsList = new ArrayList<>();
		ordersList = new ArrayList<>();
		customersList = new ArrayList<>();
		employeesList = new ArrayList<>();
		
	}
	
	 public static RecordsShop getInstance(String shopName) {
		 if (instance == null) {
			 instance = new RecordsShop(shopName);
		 }
		 return instance;
	 }
	 
	 // Getters
	public String getShopName() {
		return shopName;
	}

	public ArrayList<Record> getRecordsList() {
		return recordsList;
	}

	public ArrayList<Order> getOrderssList() {
		return ordersList;
	}

	public ArrayList<Customer> getCustomersList() {
		return customersList;
	}

	public ArrayList<Employee> getEmployeesList() {
		return employeesList;
	}

	// get Record By ID
	public Record getRecordByID(int ID) {
		for (Record r : this.recordsList) {
			if (r.getRecordID() == ID) {
				return r;
			}
		}
		return null;
	}
	

	// get Customer By ID
	public Customer getCustomerByID(int ID) {
		for (Customer c : this.customersList) {
			if (c.getId() == ID) 
				return c;	
		}
		return null;
	}
	
	// get Order By ID
		public Order getOrderByID(int ID) {
			for (Order o : this.ordersList) {
				if (o.getOrderID() == ID) {
					return o;
				}
			}
			return null;
		}
	
	
	// Setters
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	// Add song to record
	public void addSongToRecord(Song song, int recordID) {
		for (Record r: recordsList) 
			if (r.getRecordID() == recordID) 
				r.addSong(song);
	}
	
	// Remove song from record
	public void removeSongfromRecord(Song song, int recordID) {
		for (Record r: recordsList) 
			if (r.getRecordID() == recordID) 
				r.removeSong(song);
	}
	
	// Add record to order
	public void addRecordToOrder(Record record, int orderID) {
		this.recordsList.add(record);
		for (Order o: ordersList) 
			if (o.getOrderID() == orderID) 
				o.addRecord(record);
	}
	
	// Remove record from order
	public void removeRecordFromOrder(Record record, int orderID) {
		this.recordsList.remove(record);
		for (Order o: ordersList) 
			if (o.getOrderID() == orderID) 
				o.removeRecord(record);
	}
	
	// Add Record to list
	public void addNewRecord(Record r) {
		this.recordsList.add(r);
	}
	
	//Add Order to customer order list
	public void addNewOrder(Order o,Customer c) {
		this.ordersList.add(o);
		c.addNewOrderToMyCustomerOrderList(o);
	}
	
	// Search employee by unique value(City)
	public ArrayList<Employee> searchEmployeeFromCity(City city) {
		ArrayList<Employee> employeeList = new ArrayList<>();
		for(Employee e :employeesList ) {
			if(e.getAddress().getCity().equals(city)) {
				employeeList.add(e);	
			}
		}
		
		Collections.sort(employeeList); //sort employee by first name
		return employeeList;
	}
		
	//Show sorted Employee List
	public ArrayList<Employee> showSortedEmployeeList() {
		Collections.sort(this.employeesList);
		return this.employeesList;
	}
	
	//Show sorted Customer List
	public ArrayList<Customer> showSortedCustomerList() {
		Collections.sort(this.customersList);
		return this.customersList;
	}
	
	//Show sorted Order List
	public ArrayList<Order> showSortedOrderList() {
		Collections.sort(this.ordersList);
		return this.ordersList;
	}
	
	//Show sorted Record List
	public ArrayList<Record> showSortedRecordList() {
		Collections.sort(this.recordsList);
		return this.recordsList;
	}
	
	//Add New Employee to employee list
	public void addNewEmployee(Employee e) {
		this.employeesList.add(e);
	}
	
	//Remove Employee from employee list
	public void removeEmployee(Employee e) {
		this.employeesList.remove(e);
	}
	
	//Add New Customer to customer list
	public void addNewCustomer(Customer c) {
		this.customersList.add(c);
	}
	
	//Remove Record from list by id using lambda
	public void removeRecordFromList(int id) {
		this.recordsList.removeIf(r -> r.getRecordID()==id);
	}
	
	//Show Customer My Orders
	public ArrayList<Order> showCustomerMyOrders(Customer c){
		
		ArrayList<Order> ordersToShow = new ArrayList<>();
		
		for(Order o : this.ordersList) {
			if(o.getCustomer().getId() == c.getId()) {
				ordersToShow.add(o);
			}
		}
		
		Collections.sort(ordersToShow); //sort order by price
		return ordersToShow;
	}
	
	//Get Customer By Email
	public Customer getCustomerByEmail(String email) {
		
		for (Customer c : this.customersList) {
			if (c.getEmail().equals(email)) 
				return c;	
		}
		
		return null;
	}
	
	//Get Employee By Email
	public Employee getEmployeeByEmail(String email) {
		
		for (Employee e : this.employeesList) {
			if (e.getEmail().equals(email)) 
				return e;	
		}
		
		return null;
	}

	//toString - print
	@Override
	public String toString() {
		return "RecordsShop [shopName=" + shopName + ", recordsList=" + recordsList + ", ordersList=" + ordersList
				+ ", customersList=" + customersList + ", employeesList=" + employeesList + "]";
	}
	

	// Check user last index
	public int checkMaxID() {
		int id = 0;
		for (Employee e : employeesList) {
			if (id < e.getId())
				id= e.getId();
		}
		for(Customer c: customersList) {
			if (id < c.getId())
				id= c.getId();
		}
		return id;
	}
	
	// Check record last index
	public int checkRecordMaxID() {
		int id = 0;
		for (Record r : recordsList) {
			if (id < r.getRecordID())
				id= r.getRecordID();
		}
		return id;
	}
	
	// Check order last index
	public int checkOrderMaxID() {
		int id = 0;
		for (Order o : ordersList) {
			if (id < o.getOrderID())
				id= o.getOrderID();
		}
		return id;
	}
	
	
//    public int compare(String o1, String o2) {              
//        return o1.compareToIgnoreCase(o2);
//    }

}
