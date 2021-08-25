package EmployeeGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import Classes.Customer;
import Classes.Employee;

import Classes.UserAddress;
import DataBase.DB_Main;
import Enum.City;
import Enum.PhoneAreaCode;
import Exceptions.UserNameIsNotValid;
import Exceptions.UserPasswordIsNotValid;

public class AddNewCustomerScreen {

	private JFrame addNewCustomerFrame;
	private JTextField emailTextField;
	private JPasswordField passwordTextField;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField streetNameTextField;
	private JTextField homeNumberTextField;
	private JTextField phoneNumberTextField;
	private Employee employee;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddNewCustomerScreen window = new AddNewCustomerScreen("TEST");
					window.addNewCustomerFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddNewCustomerScreen(String employeeEmail) {

		this.employee = 
				DB_Main.db_RecordShop.getEmployeeByEmail(employeeEmail);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addNewCustomerFrame = new JFrame("Yuval Shai & Adi Hemo");
		addNewCustomerFrame.setBounds(100, 100, 841, 582);
		addNewCustomerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addNewCustomerFrame.getContentPane().setLayout(null);
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		addNewCustomerFrame.setLocation(dim.width/2-addNewCustomerFrame.getSize().width/2, dim.height/2-addNewCustomerFrame.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		addNewCustomerFrame.setIconImage(icon.getImage());
		
		JLabel addNewCustomerFromLabel = new JLabel("Add New Cutomer From");
		addNewCustomerFromLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		addNewCustomerFromLabel.setBounds(207, 11, 341, 67);
		addNewCustomerFrame.getContentPane().add(addNewCustomerFromLabel);
		
		JLabel customerEmailLabel = new JLabel("Email:");
		customerEmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		customerEmailLabel.setBounds(49, 101, 94, 31);
		addNewCustomerFrame.getContentPane().add(customerEmailLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		passwordLabel.setBounds(49, 165, 134, 31);
		addNewCustomerFrame.getContentPane().add(passwordLabel);
		
		JLabel firstNameLabel = new JLabel("First Name: ");
		firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		firstNameLabel.setBounds(49, 228, 140, 31);
		addNewCustomerFrame.getContentPane().add(firstNameLabel);
		
		JLabel lastNameLabel = new JLabel("Last Name: ");
		lastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lastNameLabel.setBounds(354, 228, 140, 31);
		addNewCustomerFrame.getContentPane().add(lastNameLabel);
		
		JLabel cirtyLabel = new JLabel("City:");
		cirtyLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		cirtyLabel.setBounds(49, 302, 66, 31);
		addNewCustomerFrame.getContentPane().add(cirtyLabel);
		
		JLabel streetNameLabel = new JLabel("Street Name:");
		streetNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		streetNameLabel.setBounds(274, 302, 134, 31);
		addNewCustomerFrame.getContentPane().add(streetNameLabel);
		
		JLabel homeNumberLabel = new JLabel("Home Number:");
		homeNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		homeNumberLabel.setBounds(576, 302, 157, 31);
		addNewCustomerFrame.getContentPane().add(homeNumberLabel);
		
		JLabel phoneNumberLabel = new JLabel("Phone Number:");
		phoneNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		phoneNumberLabel.setBounds(49, 368, 169, 31);
		addNewCustomerFrame.getContentPane().add(phoneNumberLabel);
		
		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		emailTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(checkUserEmail(emailTextField.getText()) == true){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    emailTextField.setBorder(border);
				}
				else if(checkUserEmail(emailTextField.getText()) == false) {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					emailTextField.setBorder(border);
				}
				
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(checkUserEmail(emailTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    emailTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					emailTextField.setBorder(border);
				}
			}
		});
		emailTextField.setBounds(132, 101, 205, 30);
		addNewCustomerFrame.getContentPane().add(emailTextField);
		emailTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(checkUserPassword(passwordTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    passwordTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					passwordTextField.setBorder(border);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(checkUserPassword(passwordTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    passwordTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					passwordTextField.setBorder(border);
				}
			}
		});
		passwordTextField.setColumns(10);
		passwordTextField.setBounds(159, 166, 205, 30);
		addNewCustomerFrame.getContentPane().add(passwordTextField);
		
		firstNameTextField = new JTextField();
		firstNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		firstNameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(checkOnlyLetters(firstNameTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    firstNameTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					firstNameTextField.setBorder(border);
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(checkOnlyLetters(firstNameTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    firstNameTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					firstNameTextField.setBorder(border);
				}
			}
		});
		firstNameTextField.setColumns(10);
		firstNameTextField.setBounds(174, 228, 150, 30);
		addNewCustomerFrame.getContentPane().add(firstNameTextField);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lastNameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(checkOnlyLetters(lastNameTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    lastNameTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					lastNameTextField.setBorder(border);
				}

			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(checkOnlyLetters(lastNameTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    lastNameTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					lastNameTextField.setBorder(border);
				}
			}
			
		});
		lastNameTextField.setColumns(10);
		lastNameTextField.setBounds(477, 229, 150, 30);
		addNewCustomerFrame.getContentPane().add(lastNameTextField);
		
		JComboBox cityComboBox = new JComboBox();
		cityComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cityComboBox.setBounds(112, 307, 123, 31);
		addNewCustomerFrame.getContentPane().add(cityComboBox);
		cityComboBox.setModel(new DefaultComboBoxModel(City.values()));
		
		streetNameTextField = new JTextField();
		streetNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		streetNameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(checkOnlyLetters(streetNameTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    streetNameTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					streetNameTextField.setBorder(border);
				}

			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(checkOnlyLetters(streetNameTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    streetNameTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					streetNameTextField.setBorder(border);
				}
			}
		});
		streetNameTextField.setColumns(10);
		streetNameTextField.setBounds(418, 302, 134, 30);
		addNewCustomerFrame.getContentPane().add(streetNameTextField);
		
		homeNumberTextField = new JTextField();
		homeNumberTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		homeNumberTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(checkOnlyDigits(homeNumberTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    homeNumberTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					homeNumberTextField.setBorder(border);
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(checkOnlyDigits(homeNumberTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    homeNumberTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					homeNumberTextField.setBorder(border);
				}
			}
		});
		homeNumberTextField.setColumns(10);
		homeNumberTextField.setBounds(735, 302, 79, 30);
		addNewCustomerFrame.getContentPane().add(homeNumberTextField);
		
		JComboBox phoneAreaCodeComboBox = new JComboBox();
		phoneAreaCodeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		phoneAreaCodeComboBox.setBounds(241, 368, 123, 31);
		addNewCustomerFrame.getContentPane().add(phoneAreaCodeComboBox);
		phoneAreaCodeComboBox.setModel(new DefaultComboBoxModel(PhoneAreaCode.values()));
		
		phoneNumberTextField = new JTextField();
		phoneNumberTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		phoneNumberTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(checkOnlyDigits2(phoneNumberTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    phoneNumberTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					phoneNumberTextField.setBorder(border);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(checkOnlyDigits2(phoneNumberTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    phoneNumberTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					phoneNumberTextField.setBorder(border);
				}
			}
		});
		phoneNumberTextField.setColumns(10);
		phoneNumberTextField.setBounds(405, 368, 174, 30);
		addNewCustomerFrame.getContentPane().add(phoneNumberTextField);
		
		JLabel commaLabel = new JLabel("-");
		commaLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		commaLabel.setBounds(375, 376, 33, 14);
		addNewCustomerFrame.getContentPane().add(commaLabel);
		
		JButton submitBTN = new JButton("Submit");
		//Image okIcon Icon
		Image okIcon = new ImageIcon(this.getClass().getResource("/okIcon.png")).getImage();
		submitBTN.setIcon(new ImageIcon(okIcon));
		submitBTN.setBackground(new Color(220, 220, 220));
		submitBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Check if all fields are valid
				if(checkUserEmail(emailTextField.getText())
						&&checkUserPassword(passwordTextField.getText())
						&&checkOnlyLetters(firstNameTextField.getText())
						&&checkOnlyLetters(lastNameTextField.getText())
						&&checkOnlyLetters(streetNameTextField.getText())
						&&checkOnlyDigits(homeNumberTextField.getText())
						&&checkOnlyDigits2(phoneNumberTextField.getText())) {
					
					// New User Address with fields values
					UserAddress ua = new UserAddress
							((City)cityComboBox.getSelectedItem(),
									streetNameTextField.getText(),homeNumberTextField.getText());
					
					try {
						
						// New Customer with fields Values
						Customer customerToAdd = new Customer(DB_Main.db_RecordShop.getMaxCustomerID()+1,
								emailTextField.getText(),
								passwordTextField.getText(), 
								firstNameTextField.getText(), 
								lastNameTextField.getText(), 
								ua, 
								(PhoneAreaCode)phoneAreaCodeComboBox.getSelectedItem(), 
								phoneNumberTextField.getText());
						
						
						// Check if Customer not exist add to shop Customer list
						if(!DB_Main.db_RecordShop.isContainCustomer(customerToAdd.getEmail())) {
							
							// Add to customer list sql database
							DB_Main.db_RecordShop.addCustomerToSql(customerToAdd);
							
							//Message user added
							JOptionPane.showMessageDialog(addNewCustomerFrame,"Customer "+customerToAdd.getfullName()+ " Saved Successfuly!");
							
							// Re-open screen
							AddNewCustomerScreen addNewCustomerScreen2 = new AddNewCustomerScreen(employee.getEmail());
							addNewCustomerScreen2.getFrame().setVisible(true);
							addNewCustomerFrame.dispose();

						}
						else
							// User already in the system
							JOptionPane.showMessageDialog
							(addNewCustomerFrame,"Customer "+customerToAdd.getfullName()+ " Alredy Exist!","Error",JOptionPane.ERROR_MESSAGE);
						
					} 
					catch (UserNameIsNotValid e1) { e1.printStackTrace(); } 
					catch (UserPasswordIsNotValid e1) { e1.printStackTrace(); }
				}
				else
					  JOptionPane.showMessageDialog
					  (addNewCustomerFrame,"One Or More Of The Details You Entered Are Invalid!"
							  ,"Error",JOptionPane.ERROR_MESSAGE);
				

			}
		});
		submitBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		submitBTN.setBounds(327, 467, 143, 50);
		addNewCustomerFrame.getContentPane().add(submitBTN);
		
		JButton returnToMenuBTN = new JButton("Return To Menu");
		//Image back Icon
		Image img = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(img));

		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(49, 467, 157, 50);
		addNewCustomerFrame.getContentPane().add(returnToMenuBTN);
		
		JLabel lblNewLabel = new JLabel("New label");
		
		lblNewLabel.setBounds(618, 44, 134, 144);
		addNewCustomerFrame.getContentPane().add(lblNewLabel);
		//Image addBlack Icon
		Image img2 = new ImageIcon(this.getClass().getResource("/addBlack.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img2));
		
		JLabel lblNewLabel_1 = new JLabel("");
		//Image form Icon
		Image form = new ImageIcon(this.getClass().getResource("/form2.png")).getImage();
		lblNewLabel_1.setIcon(new ImageIcon(form));
		lblNewLabel_1.setBounds(645, 341, 140, 202);
		addNewCustomerFrame.getContentPane().add(lblNewLabel_1);
		
		JLabel charactersLabel = new JLabel("6 or more characters");
		charactersLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		charactersLabel.setForeground(Color.LIGHT_GRAY);
		charactersLabel.setBounds(374, 178, 140, 14);
		addNewCustomerFrame.getContentPane().add(charactersLabel);
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EmployeeHomePage employeeHomePage = new EmployeeHomePage(employee.getEmail());
				employeeHomePage.getFrame().setVisible(true);
				addNewCustomerFrame.dispose();	
			}
		});
		
	}
	
	
	//get Frame
	public JFrame getFrame() {
		return addNewCustomerFrame;
	}
	
	// RGX User Email
		boolean checkUserEmail(String input) {

			String regex = "^[A-Za-z0-9.%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
			if(!input.matches(regex)) {
				return false;			
			}
			
			return true;
		}
		
		// RGX User Password
		boolean checkUserPassword(String input) {

			String regex = "^[a-zA-Z0-9][\\$#\\+{}:\\?\\.,~@!*\"a-zA-Z0-9]{5,}";
			if(!input.matches(regex)) {
				return false;			
			}
			
			return true;
		}

		// RGX check Only Digits for home number
		boolean checkOnlyDigits(String input) {

			String regex = "[0-9]{1,5}";
			if(!input.matches(regex)) {
				return false;			
			}
			
			return true;
		}
		
		// RGX check Only Digits for phone number
		boolean checkOnlyDigits2(String input) {

			String regex = "[0-9]{7}";
			if(!input.matches(regex)) {
				return false;			
			}
			
			return true;
		}
			// RGX check Only Letters
		boolean checkOnlyLetters(String input) {

			String regex = "[a-zA-Z]{2,}";
			if(!input.matches(regex)) {
				return false;			
			}
			
			return true;
		}
}