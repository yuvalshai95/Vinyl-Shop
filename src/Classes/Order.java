package Classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Comparable<Order>,Serializable{
	
	private static final long serialVersionUID = 1397798868401621267L;
	private int orderID;
	private Date orderDate;
	private double totalPrice;
	private ArrayList<Record> recordsListInOrder;
	private Customer customer;

	
	
	// Constructor
	public Order(int orderID,Date orderDate, double totalPrice, 
			Customer customer) {
		
		setOrderID(orderID);
		setOrderDate(orderDate);
		setTotalPrice(totalPrice);
		this.recordsListInOrder = new ArrayList<Record>();
		setCustomer(customer);

	}

	
	// Getters
	public int getOrderID() {
		return orderID;
	}

	public String getOrderDate() {
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
		return dt.format(orderDate);
	}
	
	public String getOrderDate2() {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		return dt.format(orderDate);
	}

	public double getTotalPrice() {
		return totalPrice;
	}
	
	public String getTotalPrice2() {
		String x = String.format("%.1f",this.totalPrice);
		return x;
	}
	
	

	public ArrayList<Record> getRecordsListInOrder() {
		return recordsListInOrder;
	}

	public Customer getCustomer() {
		return customer;
	}


	// Setters
	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}


	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	public void setRecordsListInOrder(ArrayList<Record> recordsListInOrder) {
		this.recordsListInOrder = recordsListInOrder;
	}


		// toString
		@Override
		public String toString() {
			SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
			return "Order number: [" + orderID +"] from " + dt.format(orderDate) 
					+"\nTotal Price: " + totalPrice
					+"\nOrder for: " + customer.getfullName()
					+ "\nRecords List:\n" + recordsListInOrder.toString()+"\n";
		}
	
	
	
	// Add record to the order
	public void addRecord(Record record) {
		recordsListInOrder.add(record);
	}
	
	// Remove record to the order
	public void removeRecord(Record record) {
		recordsListInOrder.remove(record);
	}


	//CompareTo by Price
	@Override
	public int compareTo(Order o) {

		return (int) (this.getTotalPrice() - o.getTotalPrice());
	}

}
