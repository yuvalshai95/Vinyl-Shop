package Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.DoubleStream;

import Enum.PhoneAreaCode;
import Exceptions.UserNameIsNotValid;
import Exceptions.UserPasswordIsNotValid;

public class Customer  extends User implements Comparable<Customer>,Serializable{

	
	private static final long serialVersionUID = -8576783054330095560L;
	private ArrayList<Record> recordsInShoppingCart;
	private ArrayList<Order>customerOrdersList;
	

	public Customer(int id,String email, String userPassword, String firstName, 
			String lastName, UserAddress address,
			PhoneAreaCode phoneAreaCode, String phoneNumber)
					throws UserNameIsNotValid,UserPasswordIsNotValid {
		
		super(id,email, userPassword, firstName, lastName, address, phoneAreaCode, phoneNumber);
		
		this.recordsInShoppingCart = new ArrayList<>();
		this.customerOrdersList = new ArrayList<>();
	}

	//Getters
	public ArrayList<Record> getRecordsInShoppingCart() {
		return recordsInShoppingCart;
	}
	
	
	public ArrayList<Order> getCustomerOrdersList() {
		return customerOrdersList;
	}
	
	
	//Setters
	public void setCustomerOrdersList(ArrayList<Order> customerOrdersList) {
		this.customerOrdersList = customerOrdersList;
	}

	
	public void setRecordsInShoppingCart(ArrayList<Record> recordsInShoppingCart) {
		this.recordsInShoppingCart = recordsInShoppingCart;
	}

		//toString - print
		@Override
		public String toString() {
			return super.toString() 
					+"\n"+ customerOrdersListToString()
					+"\n\n" + recordsInShoppingCartToString() + "\n";
		}


	// CompareTo by First Name
	@Override
	public int compareTo(Customer o) {

		return this.getFirstName().compareTo(o.getFirstName());
	}
	
	// Add record to my cart
	public void addNewRecordToMyCart(Record r) {
		this.recordsInShoppingCart.add(r);	
	}
	
	// Add New Order To My Order List
	public void addNewOrderToMyCustomerOrderList(Order o) {
		this.customerOrdersList.add(o);
	}
	
	// Remove Order From My Order List
	public void removeOrderFromCustomerOrderList(Order o) {
		this.customerOrdersList.remove(o);
	}
	
	// Remove record from my cart
	public void removeRecordFromMyCart(Record r) {
		this.recordsInShoppingCart.remove(r);	
	}
	
	// Total Customer Cart Price with Stream
	public double customerTotalCartPrice() {
		
		return  this.recordsInShoppingCart.stream().mapToDouble(Record::getPrice).sum();
		 
	}
	
	public String customerTotalCartPrice2() {
		
		double y = this.recordsInShoppingCart.stream().mapToDouble(Record::getPrice).sum();
		
		String x = String.format("%.1f",y);
		
		return x;
		 
	}

	// Print customer Orders list
		public String customerOrdersListToString() {
			String s = "Your Orders List: \n";
			for (Order order: customerOrdersList) {
				s += order.toString() + "\n";
			}
			return s;
		}
		
		// Print customer Shopping cart list
		public String recordsInShoppingCartToString() {
			String s = "Your Shopping Cart List: \n";
			for (Record record: recordsInShoppingCart) {
				s += record.toString() + "\n";
			}
			return s;
		}
		
		// toString - to profile customer GUI
				public String toStringToMyProfile() {
					return super.toString();
				}
}
