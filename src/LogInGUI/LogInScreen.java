package LogInGUI;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import Classes.Customer;
import Classes.Employee;

import CustomerGUI.CustomerHomePage;
import DataBase.DB_Main;

import EmployeeGUI.EmployeeHomePage;

import java.awt.Font;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.Border;
import AdminGUI.AdminHomePage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.*;

public class LogInScreen {
	
	
	private static Employee employeeObj = null;
	private static Customer customerObj = null;
	private JFrame logInFrame;
	private static Statement stmt = null; 
	private static ResultSet rs = null;
	private JTextField tfEmailLoginPane;
	private JPasswordField tfPasswordLoginPane;
	private Image backgroundImage;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInScreen window = new LogInScreen();
					window.logInFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LogInScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		logInFrame = new JFrame("Yuval Shai & Adi Hemo");
		logInFrame.setBounds(100, 100, 1054, 677);
		logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logInFrame.getContentPane().setLayout(null);

		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		logInFrame.setLocation(dim.width/2-logInFrame.getSize().width/2, dim.height/2-logInFrame.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		logInFrame.setIconImage(icon.getImage());
		

		

		
		JLabel lblNewLabel = new JLabel("L O G I N");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 31));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(275, 224, 459, 48);
		logInFrame.getContentPane().add(lblNewLabel);
		
		tfEmailLoginPane = new JTextField();
		tfEmailLoginPane.setBounds(468, 304, 165, 25);
		logInFrame.getContentPane().add(tfEmailLoginPane);
		tfEmailLoginPane.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
				// RGX user Name
				 tfEmailLoginPane.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent arg0) {
						
						if(checkUserName( tfEmailLoginPane.getText())){
						    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
						     tfEmailLoginPane.setBorder(border);
						}
					
						else{
							Border border = BorderFactory.createLineBorder(Color.RED, 2);
							 tfEmailLoginPane.setBorder(border);
						}
						
					}
					
					@Override
					public void keyReleased(KeyEvent e) {
						if(checkUserName( tfEmailLoginPane.getText())){
						    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
						     tfEmailLoginPane.setBorder(border);
						}
					
						else{
							Border border = BorderFactory.createLineBorder(Color.RED, 2);
							 tfEmailLoginPane.setBorder(border);
						}
					}
				});
				 tfEmailLoginPane.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setBounds(336, 346, 138, 25);
		logInFrame.getContentPane().add(passwordLabel);
		passwordLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setForeground(Color.WHITE);
		emailLabel.setBounds(336, 300, 103, 25);
		logInFrame.getContentPane().add(emailLabel);
		emailLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		
		
		JButton loginBtn = new JButton("Log-In");
		loginBtn.setForeground(Color.DARK_GRAY);
		loginBtn.setBounds(442, 412, 156, 45);
		logInFrame.getContentPane().add(loginBtn);
		//loginBtn.setIcon(new ImageIcon(OK));
		

		loginBtn.setBackground(new Color(204, 216, 206));
		loginBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		
		// Log In BTN On Click 
		loginBtn.addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent e) {

				String email = tfEmailLoginPane.getText(); 
				String pass = tfPasswordLoginPane.getText();
				
				// RGX for userName and Password
				if(checkUserName(email)&&checkUserPassword(pass)) {
					
					// Checking user permissions  - admin
					if(email.equalsIgnoreCase("ADMIN") && pass.equalsIgnoreCase("ADMIN")) {
						
						JOptionPane.showMessageDialog(logInFrame,"Welcome Admin! ");
						
						AdminHomePage adminHomePage = new AdminHomePage();
						adminHomePage.getFrame().setVisible(true);
						logInFrame.dispose();
					}
					
					// Checking user permissions  - employee
					else if(ExistsEmployee(email,pass)) {
						
						JOptionPane.showMessageDialog(logInFrame,"Welcome "+ employeeObj.getfullName());
						
						EmployeeHomePage employeeHomePage = new EmployeeHomePage(employeeObj.getEmail());
						employeeHomePage.getFrame().setVisible(true);
						logInFrame.dispose();
						
						
					}
					// Checking user permissions  - customer
					else if(ExistsCoustumer(email, pass)) {
						
						JOptionPane.showMessageDialog(logInFrame,"Welcome "+ customerObj.getfullName());
						
						CustomerHomePage customerHomePage = new CustomerHomePage(customerObj.getEmail());
						customerHomePage.getFrame().setVisible(true);
						logInFrame.dispose();

					}
					
					else 
						JOptionPane.showMessageDialog(logInFrame,"Email or Password is not valid!","Error",JOptionPane.ERROR_MESSAGE);
				}
				
				else
					JOptionPane.showMessageDialog(logInFrame,"Email or Password is not valid!","Error",JOptionPane.ERROR_MESSAGE);
			}
		});
		
		tfPasswordLoginPane = new JPasswordField();
		tfPasswordLoginPane.setBounds(468, 346, 165, 25);
		logInFrame.getContentPane().add(tfPasswordLoginPane);
		tfPasswordLoginPane.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		// RGX Password
		tfPasswordLoginPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(checkUserPassword(tfPasswordLoginPane.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    tfPasswordLoginPane.setBorder(border);
				}
			
				else{
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					tfPasswordLoginPane.setBorder(border);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(checkUserPassword(tfPasswordLoginPane.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    tfPasswordLoginPane.setBorder(border);
				}
			
				else{
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					tfPasswordLoginPane.setBorder(border);
				}
			}
		});
		tfPasswordLoginPane.setColumns(10);
		
		JLabel brownBackgroundLabel = new JLabel("");
		//Image brownBackground Icon
		 Image brownBackground = new ImageIcon(this.getClass().getResource("/brownBackground3.png")).getImage();
		brownBackgroundLabel.setIcon(new ImageIcon(brownBackground));
		brownBackgroundLabel.setBounds(275, 201, 459, 279);
		logInFrame.getContentPane().add(brownBackgroundLabel);
		

		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(0, 28, 1038, 173);
		logInFrame.getContentPane().add(logoLabel);
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		//Image logo Icon
		Image logo = new ImageIcon(this.getClass().getResource("/logo3.png")).getImage();
		logoLabel.setIcon(new ImageIcon(logo));
				 
				 JLabel backgroundLabel = new JLabel("");
				 
				//Image background Icon
				 Image background = new ImageIcon(this.getClass().getResource("/loginBackground6.png")).getImage();
				 backgroundLabel.setIcon(new ImageIcon(background));

				 backgroundLabel.setBounds(0, -11, 1080, 670);
				 logInFrame.getContentPane().add(backgroundLabel);


		
	}
	


	// Check If Exists Customer
	private boolean ExistsCoustumer(String email, String pass) {
		
		DB_Main.db_RecordShop.openConnection(); //Open DataBase Connection
		
		//get customer by email and password from sql
		String query ="SELECT *\r\n"
					+ "FROM Customer\r\n"
					+ "where Email = '"+email+"' and UserPassword = '"+pass+"'";
		
		try {
			
			stmt = DB_Main.db_RecordShop.getCon().createStatement();
			rs = stmt.executeQuery(query); 
			
			// if the RS is not empty
			if(rs.next() == true) {
				
					customerObj = DB_Main.db_RecordShop.getCustomerByEmail(email); // Save customer data in object
					return true; // return true customer exists
					
				}else	
					return false; // return false customer not exists
			

		} catch (SQLException e) { e.printStackTrace(); }
		
		finally { DB_Main.db_RecordShop.closeAll(); } // Close all connection 
		
		return false;
	}
	

	
	// Check If Exists Employee
	private boolean ExistsEmployee(String email, String pass) {
		
		DB_Main.db_RecordShop.openConnection(); //Open DataBase Connection
		
		//get employee by email and password from sql
		String query = "SELECT *\r\n"
					+ "FROM Employee\r\n"
					+ "where Email = '"+email+"' and UserPassword = '"+pass+"'";
		
		try {
			
			stmt = DB_Main.db_RecordShop.getCon().createStatement();
			rs = stmt.executeQuery(query);

			// if the RS is not empty
			if(rs.next() == true) {
				
				employeeObj = DB_Main.db_RecordShop.getEmployeeByEmail(email); // Save employee data in object
				return true; // return true employee exists
				
			}else
				return false; // return false employee not exists
			

		} catch (SQLException e) { e.printStackTrace(); }
		
		finally { DB_Main.db_RecordShop.closeAll(); } // Close all connection 
		
		return false;
	}
	
	// RGX UserName
	boolean checkUserName(String input) {
		String regex = "^[a-zA-Z0-9][\\$#\\+{}:\\?\\.,~@\"a-zA-Z0-9 ]+$";
		if(!input.matches(regex)) {
			return false;			
		}
		
		return true;
	}
	
	
	// RGX User Password
	boolean checkUserPassword(String input) {
		String regex = "^[a-zA-Z0-9][\\$#\\+{}:\\?\\.,~@\"a-zA-Z0-9 ]{4,}";
		if(!input.matches(regex)) {
			return false;			
		}
		
		return true;
	}
	
	
	// get Frame
	public JFrame getFrame() {
		return logInFrame;
	}
	
	  
}
