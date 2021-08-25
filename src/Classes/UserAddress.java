package Classes;

import java.io.Serializable;

import Enum.City;

public class UserAddress implements Serializable {
	

	private static final long serialVersionUID = -2148926394753792691L;
	private City city;
	private String street;
	private String homeNumber;
	
	
	
	//Constructor
	public UserAddress(City city,String street ,String homeNumber ) {
		setCity(city);
		setStreet(street);
		setHomeNumber(homeNumber);
	}
	
	
	//Getters
	public String getHomeNumber() {
		return homeNumber;
	}

	public String getStreet() {
		return street;
	}

	public City getCity() {
		return city;
	}
	
	public String getCity2() {
		return City.GetStringValueFromEnum(city);
	}

	//Setters
	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(City city) {
		this.city = city;
	}
	

	//toString - print 
	@Override
	public String toString() {
		return city + ", " + street + " "+ homeNumber ;
	}

}
