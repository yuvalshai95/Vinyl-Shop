package Classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.Date;

import Enum.PhoneAreaCode;
import Exceptions.UserNameIsNotValid;
import Exceptions.UserPasswordIsNotValid;

public class Employee extends User implements Comparable<Employee>,Serializable{


	private static final long serialVersionUID = 7924195132943269710L;
	private Date startDate;    //Date the employee started working in the shop

	
	//Constructor
	public Employee(int id,String email, String userPassword, String firstName, 
			String lastName,UserAddress address,
			PhoneAreaCode phoneAreaCode, 
			String phoneNumber, Date startDate) throws UserNameIsNotValid,UserPasswordIsNotValid{
		
		super(id,email, userPassword, firstName, lastName, address, phoneAreaCode, phoneNumber);
		setStartDate(startDate);
	}

	//Getters
	public String getStartDate() {
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
		return dt.format(startDate);
	}
	
	public String getStartDate2() {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		return dt.format(startDate);
	}

	//Setters
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	//toString - print
		@Override
		public String toString() {
			SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
			return super.toString()
					+"\nStarting Working Date: " + dt.format(startDate) +"\n";
		}


	//CompareTo by First Name
	@Override
	public int compareTo(Employee o) {
		
		return this.getFirstName().compareTo(o.getFirstName());
	}

}
