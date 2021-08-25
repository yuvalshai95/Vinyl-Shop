package DataBase;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import Classes.Customer;
import Classes.Employee;
import Classes.Order;
import Classes.Record;
import Classes.Song;
import Classes.UserAddress;
import Enum.City;
import Enum.PhoneAreaCode;
import Exceptions.UserNameIsNotValid;
import Exceptions.UserPasswordIsNotValid;



public class DB_RecordShop {
	
	static String connectionUrl = "jdbc:sqlserver://localhost;databaseName=RecordShop;integratedSecurity=true;"; 
	
	 static ResultSet rs = null;
	 static Connection con = null;  
	 static Statement stmt = null;
	private static DB_RecordShop instance;
	
	private DB_RecordShop() {
		
	}
	
	public static DB_RecordShop getInstance() {
		if(instance == null) {
			instance = new DB_RecordShop();
		}
		return instance;
	}
	
	// Open connection
	public void openConnection() {  
		try {
			
			System.out.println("Trying to set a connection to sql server...");
			
			// Load the driver class
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			// Create the connection using the static getConnection method
			con = DriverManager.getConnection(connectionUrl);  
		} 
		catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }  
		
		System.out.println("Connection Established!");
		
	}
	
	// close all connections
	public void closeAll() {
		
		if(stmt !=null)
			try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
		
		if(rs !=null)
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
		
		
		if(con !=null)
			try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
	}
	
	// Getters
	public Connection getCon() {
		return con;
	}
	

	public static Statement getStmt() {
		return stmt;
	}
	
	
	//Setters
	public static void setCon(Connection con) {
		DB_RecordShop.con = con;
	}

	public static void setStmt(Statement stmt) {
		DB_RecordShop.stmt = stmt;
	}

	
	// Execute Statement
	public void executeStatement(String query) {
		
		try {
			
			stmt = con.createStatement(); // Create a statement class to execute the SQL query
			
			// Check query type
			if( 	query.contains("INSERT") || 
					query.contains("insert") ||
					query.contains("Insert") ||
					query.contains("DELETE") || 
					query.contains("delete") ||
					query.contains("Delete") ||
					query.contains("UPDATE") ||
					query.contains("update") ||
					query.contains("Update") ) { 
				
				stmt.executeUpdate(query); // execute the SQL statement and get the result in a Resultset
			}
			
			else
				rs = stmt.executeQuery(query); // execute the SQL statement and get the result in a Resultset
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Get employee By Email
	public Employee getEmployeeByEmail(String email) {
		
		Employee employee = null; // Create employee object
		
		// Get employee information from SQL by email
		String query = "SELECT *\r\n"
					 + "FROM Employee e\r\n"
					 + "WHERE e.Email = '"+email+"'";
		
		// Open connection to database and send query
		openConnection();
		executeStatement(query); // send query
		
		 
		try {
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs!=null) { // for each row if not null use the getString method
					
					// Create User Address using SQL data
					UserAddress ua = new UserAddress(City.GetEnumValueFromString(rs.getString("City"))
							, rs.getString("Street"), 
							rs.getString("HomeNumber"));
					
					// Create PhoneAreaCode using SQL data
					PhoneAreaCode phc = PhoneAreaCode.GetEnumValueFromString(rs.getString("PhoneNumber").substring(0,3));
					
					String number = rs.getString("PhoneNumber").substring(3); // get last 7 digits from phone number in SQL
					
					// Create employee using SQL data
					employee = new Employee
							(		rs.getInt("EmployeeID"),
									rs.getString("Email"), 
									rs.getString("UserPassword"), 
									rs.getString("FirstName"), 
									rs.getString("LastName"), 
									ua, phc, number, rs.getDate("startWorkingDate")  );
				}
			}
		} 
		
		catch (SQLException e) { e.printStackTrace(); } 
		catch (UserNameIsNotValid e) { e.printStackTrace(); } 
		catch (UserPasswordIsNotValid e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return employee; // return employee object
		
	}

	// Get customer by email
	public Customer getCustomerByEmail(String email) {
		
		Customer customer = null; //Create customer object
		
		// Get customer information from SQL by email
		String query = "SELECT *\r\n"
					 + "FROM Customer C\r\n"
					 + "WHERE C.Email = '"+email+"'";
		
		// Open connection to database and send query
		openConnection();
		executeStatement(query); // send query
		
		try {
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs!=null) { // for each row if not null use the getString method
					
					// Create User Address using SQL data
					UserAddress ua = new UserAddress(City.GetEnumValueFromString(rs.getString("City"))
							, rs.getString("Street"), 
							rs.getString("HomeNumber"));
					
					// Create PhoneAreaCode using SQL data
					PhoneAreaCode phc = PhoneAreaCode.GetEnumValueFromString(rs.getString("PhoneNumber").substring(0,3));
					
					String number = rs.getString("PhoneNumber").substring(3);// get last 7 digits from phone number is SQL
	
					// Create customer using SQL data
					customer = new Customer
							(		rs.getInt("CustomerID"),
									rs.getString("Email"), 
									rs.getString("UserPassword"), 
									rs.getString("FirstName"), 
									rs.getString("LastName"), 
									ua, phc, number  );
				}
			}
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		catch (UserNameIsNotValid e) { e.printStackTrace(); } 
		catch (UserPasswordIsNotValid e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections

		return customer; // Return customer object
	}
	
	// get records list from SQL
	public ArrayList<Record> getRecordsListFromSQL(){
		
		ArrayList<Record> recordsToReturn = new ArrayList<>();
		
		// get all records from SQL 
		String query = "SELECT *\r\n"
					 + "FROM Record\r\n"
					 + "ORDER BY RecordName";
		
		openConnection(); // open connection to SQL
		executeStatement(query); // send query

		try {
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs != null) { // Check the RS is not null
					
					//get all songs in record from sql
					ArrayList<Song> songsInRecord = getSongsInRecordFromSqlByID(rs.getInt("RecordID"));
					
					// open all connection again because former method (getSongsInRecordFromSQL) closed all connections
					openConnection(); 
					
					//get all records from sql once again because former method (getSongsInRecordFromSQL) changed the ResultSet
					executeStatement(query); // send query

					while(rs.next()) { // Iterate the resultSet 
						
						if(rs != null) {
							
							// new record from sql data
							Record record = new Record
									(  rs.getInt("RecordID"),
											rs.getString("RecordName"), 
											rs.getInt("ReleaseYear"), 
											rs.getString("Artist"), 
											(double)rs.getFloat("price"), 
											songsInRecord  );
							
							recordsToReturn.add(record); // add new record to list
						}
					}
				}
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return recordsToReturn; // return record list
	}

	// get songs in record from sql by record id
	public ArrayList<Song> getSongsInRecordFromSqlByID(int recordID){
		

		ArrayList<Song> SongsToReturn = new ArrayList<>();
		
		// get all songs in record from sql
		String query = "SELECT *\r\n"
				     + "FROM SONG S\r\n"
				      + "WHERE EXISTS (SELECT SongID\r\n"
				      + "				FROM SongInRecord SIR\r\n"
				     + "				WHERE SIR.SongID = S.SongID AND SIR.RecordID = "+ recordID +")";
		
		try {

		executeStatement(query); // send query

			while(rs.next()) { // Iterate through the Resultset
				
				if(rs !=null) { // Check the RS is not null
					
					Song song = new Song(rs.getInt("SongID"),rs.getString("SongName")); // Create song using sql RS
					
					SongsToReturn.add(song); // Add the song to the return list
					
				}
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		
		return SongsToReturn; // Return the song list
		
	}
	
	// get record by id from sql
	public Record getRecordByID(int recordID) {
		
		Record record = null; // Create a record object
		
		// get a record by id from sql
		String query = "SELECT *\r\n"
				     + "FROM Record R\r\n"
				     + "WHERE R.RecordID = " + recordID;
		try {
			
		openConnection(); // open connection to SQL
		executeStatement(query); // send query

	
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs != null) { // Check the RS is not null
					
					// get a a list of song in a given record by id
					ArrayList<Song> songsInRecord = getSongsInRecordFromSqlByID(rs.getInt("RecordID"));
					
					// open all connection again because former method (getSongsInRecordFromSQL) closed all connections
					openConnection(); 
					
					// get all records from sql once again because former method (getSongsInRecordFromSQL) changed the ResultSet
					executeStatement(query); // send query
					
					while(rs.next()) { // Iterate through the Resultset
						
						if(rs != null) { // Check the RS is not n
							
							// create the record using the rs data from sql
							record = new Record
									(  rs.getInt("RecordID"),
											rs.getString("RecordName"), 
											rs.getInt("ReleaseYear"), 
											rs.getString("Artist"), 
											(double)rs.getFloat("price"), 
											songsInRecord  );
						}
					}
				}
				
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // close all connections
		
		return record; // return the record object
		
	}
	
	// get orders list from sql
	public ArrayList<Order> getOrdersListFromSQL(){
		
		ArrayList<Order> ordersToReturn = new ArrayList<>();
		
		//get all orders from SQL
		String query = "SELECT *\r\n"
					 + "FROM Orders\r\n"
					 + "ORDER BY OrderID";
		
		openConnection(); // open connection to SQL
		executeStatement(query); // send query
		
		try {
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs != null) { // Check the RS is not null
					
					// create customer object by email from sql
					Customer customer = getCustomerByEmail(rs.getString("CustomerEmail"));
					
					// open all connection again because former method (getCustomerByEmail) closed all connections
					openConnection(); 

					//get all records from sql once again because former method (getCustomerByEmail) changed the ResultSet
					executeStatement(query); // send query
					

					

					while(rs.next()) { // Iterate through the Resultset
						
						if(rs != null) { // Check the RS is not null
							
							// create an order object from the rs data from sql
							Order order = new Order(  rs.getInt("OrderID"), 
													rs.getDate("OrderDate"), 
													rs.getFloat("totalPrice"), 
													customer  );
							
							ordersToReturn.add(order); // add that order to the return list
							
							
						}
					}
				}
				
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return ordersToReturn; // return the order list
	}
	
	// get order list from sql by customer email
	public ArrayList<Order>getOrdersListFromSqlByCustomerEmail(String customerEmail){
		
		ArrayList<Order> ordersToReturn = new ArrayList<>();
		
		//get all orders from SQL
		String query = "SELECT *\r\n"
				     + "FROM Orders\r\n"
				     + "WHERE CustomerEmail = '"+customerEmail+"'"
				     + "ORDER BY OrderID";
		
		openConnection(); // open connection to SQL
		executeStatement(query); // send query
		
		try {
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs != null) { // Check the RS is not null
					
					// create customer object by email from sql
					Customer customer = getCustomerByEmail(rs.getString("CustomerEmail"));
					
					// open all connection again because former method (getCustomerByEmail) closed all connections
					openConnection(); 
					
					//get all records from sql once again because former method (getCustomerByEmail) changed the ResultSet
					executeStatement(query); // send query
					
					while(rs.next()) { // Iterate through the Resultset
						
						if(rs != null) { // Check the RS is not null
							
							// create an order object from the rs data from sql
							Order order = new Order(  rs.getInt("OrderID"), 
													rs.getDate("OrderDate"), 
													rs.getFloat("totalPrice"), 
													customer  );
							
							ordersToReturn.add(order); // add the order to the return list
							
						}
					}
					
				}
				
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return ordersToReturn; // return the order list
		
	}
	
	// get record in order from sql by order id
	public ArrayList<Record>getRecordsInOrderFromSqlByID(int orderID){
		
		ArrayList<Record> recordsToReturn = new ArrayList<>();
		
		String query = "SELECT *\r\n"
				+ "FROM Record r\r\n"
				+ "WHERE EXISTS (SELECT RecordID\r\n"
				+ "				FROM RecordInOrder RIO\r\n"
				+ "				WHERE RIO.RecordID = r.RecordID AND RIO.OrderID = "+orderID+")";
		
		try {
			
		openConnection(); // open connection to SQL
		executeStatement(query); // send query
		
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs != null) { // Check the RS is not null
					
					// get a song list in record from sql by record id
					ArrayList<Song> songsInRecord = getSongsInRecordFromSqlByID(rs.getInt("RecordID"));
					
					// open all connection again because former method (getSongsInRecordFromSQL) closed all connections
					openConnection(); 
					
					//get all records from sql once again because former method (getSongsInRecordFromSQL) changed the ResultSet
					executeStatement(query); // send query
					
					while(rs.next()) { // Iterate through the Resultset
						
						if(rs != null) { // Check the RS is not null
							
							// create a record object from the rs data from sql
							Record record = new Record
									(  rs.getInt("RecordID"),
											rs.getString("RecordName"), 
											rs.getInt("ReleaseYear"), 
											rs.getString("Artist"), 
											(double)rs.getFloat("price"), 
											songsInRecord  );
							
							recordsToReturn.add(record); // add the record object to the return list
						}
					}
					
				}
				
			}
		
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		 return recordsToReturn; // return the list
	}
	
	//get order by id
	public Order getOrderByID(int orderID) {
		
		Order order = null; // create an order object
		
		// get an order by id
		String query = "SELECT *\r\n"
				+ "FROM Orders O\r\n"
				+ "WHERE O.OrderID = "+orderID;
		
		try {
			
			openConnection(); // open connection to sql
			executeStatement(query); // send query
			
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs != null) { // Check the RS is not null
					
					// create a customer object by email from sql data
					Customer customer = getCustomerByEmail(rs.getString("CustomerEmail"));
					
					// open all connection again because former method (getCustomerByEmail) closed all connections
					openConnection(); 
					
					//get all records from sql once again because former method (getCustomerByEmail) changed the ResultSet
					executeStatement(query); // send query
					
					while(rs.next()) { // Iterate through the Resultset
						
						if(rs !=null) { // Check the RS is not null
							
							// create an order from the rs data from sql
							order = new Order
									(  rs.getInt("OrderID"), 
									rs.getDate("OrderDate"), 
									rs.getFloat("totalPrice"), 
									customer  );
							
						}
					}
					
				}
			}
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // close all connections
		
		return order; // return the order
		
	}
	
	//get employee list from sql
	public ArrayList<Employee> getEmployeeListFromSql(){
		
		ArrayList<Employee> employeesToReturn = new ArrayList<>(); // create an employee list to return
		
		// get all employees from sql
		String query = "SELECT *\r\n"
					 + "FROM Employee\r\n"
					 + "ORDER BY FirstName";
		
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs !=null) { // Check the RS is not null
					
					// Create User Address using SQL data
					UserAddress ua = new UserAddress(City.GetEnumValueFromString(rs.getString("City"))
							, rs.getString("Street"), 
							rs.getString("HomeNumber"));
					
					// Create PhoneAreaCode using SQL data
					PhoneAreaCode phc = PhoneAreaCode.GetEnumValueFromString(rs.getString("PhoneNumber").substring(0,3));
					
					String number = rs.getString("PhoneNumber").substring(3); // get last 7 digits from phone number in SQL
					
					// Create an employee from the rs data from sql
					Employee employee = new Employee
							(		rs.getInt("EmployeeID"),
									rs.getString("Email"), 
									rs.getString("UserPassword"), 
									rs.getString("FirstName"), 
									rs.getString("LastName"), 
									ua, phc, number, rs.getDate("startWorkingDate")  );
					
					employeesToReturn.add(employee); // add the employee to the return list

				}
			}
		} 
		catch (SQLException | UserNameIsNotValid | UserPasswordIsNotValid e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return employeesToReturn; // return the list

	}
	
	// Remove employee from sql database by id
	public void removeEmployeeFromSqlByID(int employeeID) {
		
		// remove employee from employee table by employee id
		String query = "DELETE FROM Employee \r\n"
				      + "WHERE EmployeeID = "+ employeeID;
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		closeAll(); // Close all connections
		
		
	}
	
	//get customer list from sql
	public ArrayList<Customer> getCustomerListFromSql() {
		
		ArrayList<Customer> customersToReturn = new ArrayList<>(); // Create a customer list to return
		
		// get all customers from the database
		String query = "SELECT *\r\n"
					 + "FROM Customer\r\n"
					 + "ORDER BY FirstName";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
			
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs != null) { // Check the RS is not null
					
					// Create User Address using SQL data
					UserAddress ua = new UserAddress(City.GetEnumValueFromString(rs.getString("City"))
							, rs.getString("Street"), 
							rs.getString("HomeNumber"));
					
					// Create PhoneAreaCode using SQL data
					PhoneAreaCode phc = PhoneAreaCode.GetEnumValueFromString(rs.getString("PhoneNumber").substring(0,3));
					
					String number = rs.getString("PhoneNumber").substring(3);// get last 7 digits from phone number is SQL
	
					// Create customer using SQL data
					Customer customer = new Customer
							(		rs.getInt("CustomerID"),
									rs.getString("Email"), 
									rs.getString("UserPassword"), 
									rs.getString("FirstName"), 
									rs.getString("LastName"), 
									ua, phc, number  );
					
					// Add the customer to the list
					customersToReturn.add(customer);
					
				}
			}
		} 
		catch (SQLException | UserNameIsNotValid | UserPasswordIsNotValid e){ 
			e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return customersToReturn; // Return the list
			
	}
	
	//total customer from sql
	public int totalCustomersInSql() {
		
		int totalCustomers = 0; // integer to show how many customer in the system
		
		// get a count of all the customers in the database
		String query = "SELECT COUNT (*) as totalCustomers\r\n"
				     + "FROM Customer";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
				if(rs != null) { // Check the RS is not null
					
					rs.next(); // get the rs to point on the totalCustomer count
					totalCustomers = (int)rs.getObject(1); // save the count in the integer we created above
				}

		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return totalCustomers; // return the customer counter
	}
	
	//is employee list from a unique city is empty
	public boolean isEmployeeFromCityEmpty(String city) {
		
		// get the employee list by city from sql
		String query = "SELECT *\r\n"
				     + "FROM Employee\r\n"
				     + "WHERE City like '"+city+"'";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs != null) { // Check the RS is not null
					
					return false; // the rs not empty that means there is an employee from that city
				}
				
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return true; // there is no employee from that city
	}
	
	//get employee from sql by city
	public ArrayList<Employee> getEmployeeFromSqlByCity(String city){
		
		ArrayList<Employee> employeesToReturn = new ArrayList<>(); // create an employee list to return
		
		// get an employee list by city from sql
		String query = "SELECT *\r\n"
					+ "FROM Employee\r\n"
					+ "WHERE City LIKE '"+city+"'\r\n"
					+ "ORDER BY FirstName" ;
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs !=null) { // Check the RS is not null
					
					// Create User Address using SQL data
					UserAddress ua = new UserAddress(City.GetEnumValueFromString(rs.getString("City"))
							, rs.getString("Street"), 
							rs.getString("HomeNumber"));
					
					// Create PhoneAreaCode using SQL data
					PhoneAreaCode phc = PhoneAreaCode.GetEnumValueFromString(rs.getString("PhoneNumber").substring(0,3));
					
					String number = rs.getString("PhoneNumber").substring(3); // get last 7 digits from phone number in SQL
					
					// create an employee from the rs data from sql
					Employee employee = new Employee
							(		rs.getInt("EmployeeID"),
									rs.getString("Email"), 
									rs.getString("UserPassword"), 
									rs.getString("FirstName"), 
									rs.getString("LastName"), 
									ua, phc, number, rs.getDate("startWorkingDate")  );
					
					employeesToReturn.add(employee); // add the employee to the return list

				}
			}
		} 
		catch (SQLException | UserNameIsNotValid | UserPasswordIsNotValid e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return employeesToReturn; // return the list

	}
	
	//get max employee id
	public int getMaxEmployeeID() {
		
		int id = 0; // set id
		
		// get the max id from sql
		String query = "SELECT max(EmployeeID) as maxID\r\n"
						+ "FROM Employee";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
			if(rs !=null) { // Check the RS is not null
			
				rs.next(); // get the rs to point on the id count
				id = rs.getInt("maxID"); // set id to max id
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return id; 
		
	}
	
	//get max order id
	public int getMaxOrderID() {
		
		int id = 0; // set id
		
		// get the max id from sql
		String query = "SELECT MAX (OrderID) as maxID\r\n"
				      + "FROM Orders";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		try {
			
			if(rs != null) { // Check the RS is not null
			
				rs.next(); // get the rs to point on the id count
				id = rs.getInt("maxID"); // set id to max id
			} 	
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return id; 
	}
	
	//get max song id
	public int getMaxSongID() {
		
		int id = 0; // set id
		
		// get the max id from sql
		String query = "SELECT MAX (SongID) AS maxID\r\n"
				     + "FROM Song";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		try {
			
			if(rs != null) {  // Check the RS is not null
			
				rs.next(); // get the rs to point on the id count
				id = rs.getInt("maxID"); // set id to max id
			} 	
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return id; 
	}
	
	//get max record id
	public int getMaxRecordID() {
		
		int id = 0; // set id
		
		// get the max id from sql
		String query = "SELECT MAX (RecordID) AS maxID\r\n"
				      + "FROM Record";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		try {
			
			if(rs != null) { // Check the RS is not null
			
				rs.next(); // get the rs to point on the id count
				id = rs.getInt("maxID"); // set id to max id
			} 	
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		 
		return id; 
	}
	
	//is contain employee
	public boolean isContainEmployee(String email) {
		
		// get employee email from sql
		String query = "SELECT e.Email\r\n"
					 + "FROM Employee e\r\n"
					  + "WHERE e.Email like '"+email+"'";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs.getString("Email").equals(email)) // check the email from database is equal to the given email
					return true; // contain employee
				
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return false; // not contain employee
		
	}

	
	// add employee to sql
	public void addEmployeeToSql(Employee employee) {
		
			// insert employee to sql table
			String query = "INSERT INTO Employee\r\n"
							+ "VALUES ('"+employee.getEmail()+"', '"
							+employee.getUserPassword()+"', '"
							+employee.getFirstName()+"', '"
							+employee.getLastName()+"', '"
							+City.GetStringValueFromEnum(employee.getAddress().getCity()) +"', '"
							+employee.getAddress().getStreet()+"', '"
							+employee.getAddress().getHomeNumber()+"', '"
							+employee.getFullPhoneNumber2()+"','"
							+employee.getStartDate2()+"')";
			
			openConnection(); // Open connection to sql
			executeStatement(query); // Send query
			
			closeAll(); // Close all connections

	}
	
	
	// get recrods in shopping cart by customer
	public ArrayList<Record> getRecordsInShoppingCartFromSql(String customerEmail){
		
		ArrayList<Record> recordsToReturn = new ArrayList<>(); // create a record list to return
		
		// get all records in shopping cart by customer 
		String query = "SELECT r.*\r\n"
				+ "FROM Record r join RecordInShoppingCart ric on r.RecordID = ric.RecordID\r\n"
				+ "WHERE ric.CustomerEmail = '"+customerEmail+"'"
				+ "ORDER BY r.RecordName";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs != null) { // Check the RS is not null
					
					//get all songs in record from sql
					ArrayList<Song> songsInRecord = getSongsInRecordFromSqlByID(rs.getInt("RecordID"));
					
					// open all connection again because former method (getSongsInRecordFromSQL) closed all connections
					openConnection(); 
					
					//get all records from sql once again because former method (getSongsInRecordFromSQL) changed the ResultSet
					executeStatement(query); // send query
					
					while(rs.next()) { // Iterate through the Resultset
						
						if(rs != null) { // Check the RS is not null
							
							// new record from sql data
							Record record = new Record
									(  rs.getInt("RecordID"),
											rs.getString("RecordName"), 
											rs.getInt("ReleaseYear"), 
											rs.getString("Artist"), 
											(double)rs.getFloat("price"), 
											songsInRecord  );
							
							recordsToReturn.add(record); // add new record to list
							
						}
					}
				}
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return recordsToReturn; // return the list
		
	}
	
	// remove record from customer shopping cart
	public void removeRecordFromCustomerShoppingCartSql(String customerEmail, int recordID) {
		
		String query = "DELETE FROM RecordInShoppingCart \r\n"
				+ "WHERE CustomerEmail = '"+customerEmail+"' and tableKey in (select top 1 tableKey\r\n"
				+ "                                                              from RecordInShoppingCart\r\n"
				+ "															     where RecordID = "+recordID+" and CustomerEmail = '"+customerEmail+"' )";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		closeAll(); // Close all connections
		
	}
	
	// insert record into order sql
	public void insertRecordToOrderSql(int orderID, int recordID) {
		
		// insert record to order table
		String query ="INSERT INTO RecordInOrder VALUES("+orderID+","+recordID+")";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		closeAll(); // Close all connections
			
	}
	
	
	//insert order into sql
	public void insertOrderIntoSql(Order order) {
		
		// insert order to sql table
		String query = "INSERT INTO Orders\r\n"
				     + "VALUES('"
				     + order.getOrderDate2()+"',"
				     +order.getTotalPrice()+",'"
				     +order.getCustomer().getEmail()+"')";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		closeAll(); // Close all connections
		
	}
	
	// delete record from customer shopping cart sql
	public void deleteRecordsFromCustomerShoppingCart(String customerEmail) {
		
		// delete record from cart table by customer email
		String query = "DELETE FROM RecordInShoppingCart\r\n"
					+ "WHERE CustomerEmail = '"+customerEmail+"'";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		closeAll(); // Close all connections
		
	}
	
	// Insert song into sql
	public void insertSongIntoSql(Song song) {
		
		// Insert song to song table sql
		String query = "INSERT INTO Song VALUES ('"+song.getSongName()+"')";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		closeAll(); // Close all connections
		
	}
	
	
	// Delete song from sql by id
	public void deleteSongFromSqlByID(int id) {
		
		// Delete song from song table by song id
		String query = "DELETE  FROM song\r\n"
				      + "WHERE SongID = " + id;
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		closeAll(); // Close all connections
		
	}
	
	
	// Insert Record Into Sql
	public void insertRecordIntoSql(Record record) {
		
		// Insert record to record table
		String query = "INSERT INTO Record\r\n"
				+ "VALUES ('"+record.getRecordName()+"','"
							 +record.getReleaseYear()+"','"
							 +record.getArtist()+"',"
							 +record.getPrice()+")";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		closeAll(); // Close all connections
		
	}
	
	// Is record in Sql database
	public boolean isRecordInSql(Record record) {
		
		boolean isContain = false; // set isContain false
		
		// get a record from sql
		String query = "SELECT *\r\n"
				+ "FROM Record\r\n"
				+ "WHERE RecordName = '"+record.getRecordName()+
				"' and ReleaseYear = '"+record.getReleaseYear()+
				"' and Artist = '"+record.getArtist()+"'";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
			
			
		if(rs.next() == true) { // check if the rs has a record

				isContain = true; // change isContain value
			} 
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		finally { closeAll(); }// Close all connections
		
		return isContain; // return false no record
	}
	
	// Insert songs into record sql
	public void insertSongsIntoRecordSql(int recordID,int realSongID) {
		
		// Insert song into song in record table
		String query = "INSERT INTO SongInRecord\r\n"
			        	+ "VALUES ("+recordID+","+realSongID+")";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		closeAll(); // Close all connections
		
		
	}
	
	// Songs not in any record in sql
	public ArrayList<Song> songsNotInAnyRecordInSql(){
		
		ArrayList<Song> songsToReturn = new ArrayList<>(); // create a song list to return
		
		// get all song not related to any record in the database
		String query = "SELECT *\r\n"
				+ "FROM Song s\r\n"
				+ "WHERE not exists (select sir.SongID\r\n"
				+ "					from SongInRecord sir\r\n"
				+ "					where s.SongID = sir.SongID)";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
			
			while(rs.next()) { // Iterate through the Resultset
				
				if(rs != null) { // Check the RS is not null
					
					// create a song object from the rs data from sql
					Song song = new Song(rs.getInt("SongID"), rs.getString("SongName"));
					songsToReturn.add(song); // add the song to the list
					
				}
				
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return songsToReturn; // return the list
		
	}
	
	// delete all songs with no record in sql
	public void deleteAllSongsWithNoRecordSql() {
		
		
		ArrayList<Song> songsToDelete = songsNotInAnyRecordInSql(); // get a list of songs to delete
		
		// Iterate through the song list to delete
		for(int i=0;i<songsToDelete.size();i++) {
			
			// delete from song table
			String query = "DELETE  FROM song\r\n"
				     	 + "WHERE SongID = "+ songsToDelete.get(i).getSongID();
			
			openConnection(); // Open connection to sql
			executeStatement(query); // Send query
			closeAll(); // Close all connections
			
		}
		

		
	}
	
	// Is current local password equals to sql password
	public boolean isPasswordEquals(Customer customer,String currPass) {
		
		boolean isEquals = false; // set isEqual false
		
		//get user password from sql by customer email
		String query ="SELECT UserPassword\r\n"
					+ "FROM Customer\r\n"
					+ "WHERE Email = '"+customer.getEmail()+"' and UserPassword = '"+currPass+"'";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
			
			if(rs.next() == true) { // if the rs has data and not null
				
				isEquals = true; // change isEqual value
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return isEquals; // return false
	}
	
	// Update customer password
	public void updateCustomerPassword(String customerEmail,String newPass) {
		
		// update customer password
		String query = "UPDATE Customer\r\n"
				   	+ "SET UserPassword = '"+newPass+"'\r\n"
					+ "WHERE Email = '"+customerEmail+"'";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		closeAll(); // Close all connections

	}
	
	// Insert Record into customer Shopping cart
	public void insertRecordIntoCustomerShoppingCart(int recordID, String customerEmail) {
		
		String query = "INSERT INTO RecordInShoppingCart\r\n"
				      + "VALUES ("+recordID+",'"+customerEmail+"') ";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		closeAll(); // Close all connections
		
	}
	
	//is contain customer
	public boolean isContainCustomer(String email) {
		
		String query = "SELECT *\r\n"
				+ "FROM CUSTOMER \r\n"
				+ "WHERE Email = '"+email+"'";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		
		try {
			
			while(rs.next()) { // Iterate through the Resultset
				
				// check if the email from GUI equals to email from SQL
				if(rs.getString("Email").equals(email)) 
					return true;
				
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return false;
		
	}
	
	// add employee to sql
	public void addCustomerToSql(Customer customer) {
		
			
			String query = "INSERT INTO Customer\r\n"
							+ "VALUES ('"+customer.getEmail()+"', '"
							+customer.getUserPassword()+"', '"
							+customer.getFirstName()+"', '"
							+customer.getLastName()+"', '"
							+City.GetStringValueFromEnum(customer.getAddress().getCity()) +"', '"
							+customer.getAddress().getStreet()+"', '"
							+customer.getAddress().getHomeNumber()+"', '"
							+customer.getFullPhoneNumber2()+"')";
			
			openConnection();
			executeStatement(query);
			
			closeAll();

	}
	
	// get max customer id
	public int getMaxCustomerID() {
		
		int id = 0; // set id
		
		// get the max id from sql
		String query = "SELECT MAX (CustomerID) as maxID\r\n"
				      + "FROM CUSTOMER";
		
		openConnection(); // Open connection to sql
		executeStatement(query); // Send query
		try {
			
			if(rs != null) { // Check the RS is not null
			
				rs.next(); // get the rs to point on the id count
				id = rs.getInt("maxID"); // set id to max id
			} 	
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { closeAll(); } // Close all connections
		
		return id;
	}
	
	
}
