package Classes;



import java.io.Serializable;

import Enum.PhoneAreaCode;
import Exceptions.UserNameIsNotValid;

import Exceptions.UserPasswordIsNotValid;

public abstract class User implements Serializable {

	private static final long serialVersionUID = 3099239676429330769L;
	private  int id;
	private String email;
	private String userPassword;
	private String firstName;
	private String lastName;
	private UserAddress address;
	private PhoneAreaCode phoneAreaCode;
	private String phoneNumber;
	
	//Constructor
	public User(int id,String email, String userPassword, String firstName, String lastName, 
			UserAddress address,PhoneAreaCode phoneAreaCode, String phoneNumber)
					throws UserNameIsNotValid,UserPasswordIsNotValid {
		setId(id);
		setEmail(email);
		setUserPassword(userPassword);
		setFirstName(firstName);
		setLastName(lastName);
		setAddress(address);
		setPhoneAreaCode(phoneAreaCode);
		setPhoneNumber(phoneNumber);
		
	}

	//Getters

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public UserAddress getAddress() {
		return address;
	}

	public PhoneAreaCode getPhoneAreaCode() {
		return phoneAreaCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getFullPhoneNumber() {
		
		return PhoneAreaCode.GetStringValueFromEnum(phoneAreaCode) + "-" + phoneNumber;
	}
	
	public String getFullPhoneNumber2() {
		
		return PhoneAreaCode.GetStringValueFromEnum(phoneAreaCode) + phoneNumber;
	}
	
	public String getfullName() {
		return firstName + " " + lastName;
	}

	
	//Setters
	public void setEmail(String email)throws UserNameIsNotValid {
		if(email!=null || email.length()>4) {
			this.email = email;
				
		}
		else {
			throw new UserNameIsNotValid("Email Is Not Valid");
		}
		
	}

	public void setUserPassword(String userPassword)throws UserPasswordIsNotValid {
		if(userPassword.length()>=5) {
			this.userPassword = userPassword;
		}
		else {
			throw new UserPasswordIsNotValid("Password Must be 5 Or More Letters");
		}
		
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress(UserAddress address) {
		this.address = address;
	}

	public void setPhoneAreaCode(PhoneAreaCode phoneAreaCode) {
		this.phoneAreaCode = phoneAreaCode;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	
	//toString - print
		@Override
		public String toString() {
			return "ID: " + id 
					+ "\nEmail: " + email 
					+ "\nName: " + getfullName()
					+ "\nAddress: " + address.getCity2() +", "+ address.getStreet() +" "+ address.getHomeNumber()
					+ "\nPhone Number: " + getFullPhoneNumber();
		}

}
