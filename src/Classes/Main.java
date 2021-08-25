//package Classes;
//
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.text.DateFormat;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Scanner;
//
//
//import Enum.City;
//import Enum.PhoneAreaCode;
//import Exceptions.UserNameIsNotValid;
//import Exceptions.UserPasswordIsNotValid;
//
//
//public class Main {
//	
//	public static UserAddress userAddress;
//	public static Customer customer;
//	public static Employee employee;
//	public static RecordsShop recordShop = RecordsShop.getInstance("Adi&Yuval Shop");
//	
//	// ---------------------
//		private static void importDataFromTxtFile() {
//	        try {
//	            
//	            FileReader reader = new FileReader("MOCK_DATA.txt");
//	            Scanner in = new Scanner(reader);
//	          
//	            while (in.hasNextLine()) {
//	                
//	                String type = in.next(); 
//	                
//	                if (type.equals("UserAddress")) {
//	                	String x = in.next();
//	                	City city=City.GetEnumValueFromString(x);
//
//	                    String street = in.next();
//	                    String homeNumber = in.next();
//	                    
//	                    userAddress = new UserAddress(city, street, homeNumber);
//	                    
//	                }
//	                
//	                else if (type.equals("Customer")) {
//	                    String email = in.next();
//	                    String userPassword = in.next();
//	                    String firstName = in.next();
//	                    String lastName = in.next();
//	                    String areaCode = in.next();
//	                    PhoneAreaCode phoneAreaCode = PhoneAreaCode.GetEnumValueFromString(areaCode);
//	                    String number = in.next();
//	                    
//	                    try {
//							customer = new Customer(email, userPassword, 
//									firstName, lastName, userAddress, phoneAreaCode, number);
//							
//							
//							recordShop.addNewCustomer(customer);
//						} catch (UserNameIsNotValid e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (UserPasswordIsNotValid e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} 
//
//	                }
//	                
//	                else if (type.equals("Employee")) {
//	                    String email = in.next();
//	                    String userPassword = in.next();
//	                    String firstName = in.next();
//	                    String lastName = in.next();
//	                    String areaCode = in.next();
//	                    PhoneAreaCode p = PhoneAreaCode.GetEnumValueFromString(areaCode);
//	                    String number = in.next();
//	                    String dateString = in.next();
//	                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");	       
//	                    try {
//							Date date = df.parse(dateString);
//							
//							try {
//								employee = new Employee(email, userPassword, firstName, 
//										lastName, userAddress, p, number, date);
//								
//							
//								recordShop.addNewEmployee(employee);
//							} catch (UserNameIsNotValid | UserPasswordIsNotValid e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//	                }
//
//	            } 
//	            in.close();
//	            reader.close();
//	        } catch (IOException e) {
//	            System.out.println(e.getMessage());
//	        }
//	    }
//		
//	//------------------------------------------------------------------------------	
//
//	public static void serialize(String fileName) {
//	        
//	        try {
//	            
//	            FileOutputStream fileOut = 
//	                    new FileOutputStream(fileName);
//	            
//	            ObjectOutputStream out =
//	                    new ObjectOutputStream(fileOut);
//	        
//	    
//	            out.writeObject(recordShop);
//	            
//	            out.close();
//	            fileOut.close();
//	            System.out.println("Serialized Data is saved to file");
//	            
//	        } catch (IOException e) {
//	            e.printStackTrace();
// 
//	        }
//	    }
//
//	
//	//------------------------------------------------------------------------------
//	
//	public static void deserialize(String fileName) {
//	
//	try {
//		
//		FileInputStream fileIn = 
//				new FileInputStream(fileName);
//		
//		ObjectInputStream in = 
//				new ObjectInputStream(fileIn);
//		
//		recordShop = (RecordsShop) in.readObject();
//
//			fileIn.close();
//			in.close();
//		
//	} catch (IOException | ClassNotFoundException e) {
//					System.out.println(e.getMessage());
//
//	}
//
//}
//	
//	public static void main(String[] args) {
//		
//		importDataFromTxtFile();
//		
//		 
//		
//		// Create new Song List
//		ArrayList<Song> songsToRecord = new ArrayList<>();
//		
//		Song papercut = new Song("Papercut");
//		Song oneStepCloser = new Song("One Step Closer");
//		Song inTheEnd = new Song("In The End");
//		Song runaway = new Song("Run Away");
//		
//		songsToRecord.add(papercut);
//		songsToRecord.add(oneStepCloser);
//		songsToRecord.add(inTheEnd);
//		songsToRecord.add(runaway);
//		
//		//Create Record Linkin-Park
//		Record linkinPark_Hybrid_Theory = new Record("Hybrid Theory", 2000, "Linkin Park", 100,songsToRecord);
//		
//		recordShop.addNewRecord(linkinPark_Hybrid_Theory);
//		
//		
//		// Create new Song List
//		ArrayList<Song> songsToRecord2 = new ArrayList<>();
//		
//		Song complicated = new Song("Complicated");
//		Song mobile = new Song("Mobile");
//		Song naked = new Song("Naked");
//		Song tomorrow = new Song("Tomorrow");
//		
//		songsToRecord2.add(complicated);
//		songsToRecord2.add(mobile);
//		songsToRecord2.add(naked);
//		songsToRecord2.add(tomorrow);
//		
//		//Create Record Avril Lavigne
//		Record avrilLavigne_Let_Go = new Record("Let Go", 2002, "Avril Lavigne", 30,songsToRecord2);
//		
//		recordShop.addNewRecord(avrilLavigne_Let_Go);
//		
//		// Make serialize file with data
//		serialize("Assignment3.ser");
//
//	}
//}
