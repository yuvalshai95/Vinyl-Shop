package AdminGUI;

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
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import Classes.Employee;

import Classes.UserAddress;
import DataBase.DB_Main;
import Enum.City;
import Enum.PhoneAreaCode;
import Exceptions.UserNameIsNotValid;
import Exceptions.UserPasswordIsNotValid;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.UIManager;

public class AddNewEmployeeScreen {

	private JFrame addNewEmployeeFrame;
	private JTextField emailTextField;
	private JPasswordField passwordTextField;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField streetNameTextField;
	private JTextField homeNumberTextField;
	private JTextField phoneNumberTextField;
	private JTextField dateTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddNewEmployeeScreen window = new AddNewEmployeeScreen();
					window.addNewEmployeeFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddNewEmployeeScreen() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addNewEmployeeFrame = new JFrame("Yuval Shai & Adi Hemo");
		addNewEmployeeFrame.setBounds(100, 100, 841, 639);
		addNewEmployeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addNewEmployeeFrame.getContentPane().setLayout(null);
		
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		addNewEmployeeFrame.setLocation(dim.width/2-addNewEmployeeFrame.getSize().width/2, dim.height/2-addNewEmployeeFrame.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		addNewEmployeeFrame.setIconImage(icon.getImage());
		
		JLabel addNewEmployeeFromLabel = new JLabel("Add New Employee Form");
		addNewEmployeeFromLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addNewEmployeeFromLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		addNewEmployeeFromLabel.setBounds(20, 11, 794, 67);
		addNewEmployeeFrame.getContentPane().add(addNewEmployeeFromLabel);
		
		JLabel employeeEmailLabel = new JLabel("Email:");
		employeeEmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		employeeEmailLabel.setBounds(49, 101, 94, 31);
		addNewEmployeeFrame.getContentPane().add(employeeEmailLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		passwordLabel.setBounds(49, 165, 134, 31);
		addNewEmployeeFrame.getContentPane().add(passwordLabel);
		
		JLabel firstNameLabel = new JLabel("First Name: ");
		firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		firstNameLabel.setBounds(49, 228, 140, 31);
		addNewEmployeeFrame.getContentPane().add(firstNameLabel);
		
		JLabel lastNameLabel = new JLabel("Last Name: ");
		lastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lastNameLabel.setBounds(354, 228, 140, 31);
		addNewEmployeeFrame.getContentPane().add(lastNameLabel);
		
		JLabel cirtyLabel = new JLabel("City:");
		cirtyLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		cirtyLabel.setBounds(49, 302, 66, 31);
		addNewEmployeeFrame.getContentPane().add(cirtyLabel);
		
		JLabel streetNameLabel = new JLabel("Street Name:");
		streetNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		streetNameLabel.setBounds(274, 302, 134, 31);
		addNewEmployeeFrame.getContentPane().add(streetNameLabel);
		
		JLabel homeNumberLabel = new JLabel("Home Number:");
		homeNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		homeNumberLabel.setBounds(576, 302, 161, 31);
		addNewEmployeeFrame.getContentPane().add(homeNumberLabel);
		
		JLabel phoneNumberLabel = new JLabel("Phone Number:");
		phoneNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		phoneNumberLabel.setBounds(49, 368, 169, 31);
		addNewEmployeeFrame.getContentPane().add(phoneNumberLabel);
		
		JLabel startingWorkingDateLabel = new JLabel("Starting Working Date:");
		startingWorkingDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		startingWorkingDateLabel.setBounds(49, 436, 265, 31);
		addNewEmployeeFrame.getContentPane().add(startingWorkingDateLabel);
		
		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		emailTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(checkUserEmail(emailTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    emailTextField.setBorder(border);
				}
				else {
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
		addNewEmployeeFrame.getContentPane().add(emailTextField);
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
		addNewEmployeeFrame.getContentPane().add(passwordTextField);
		
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
		addNewEmployeeFrame.getContentPane().add(firstNameTextField);
		
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
		addNewEmployeeFrame.getContentPane().add(lastNameTextField);
		
		JComboBox cityComboBox = new JComboBox();
		cityComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cityComboBox.setBounds(112, 307, 123, 31);
		addNewEmployeeFrame.getContentPane().add(cityComboBox);
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
		addNewEmployeeFrame.getContentPane().add(streetNameTextField);
		
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
		addNewEmployeeFrame.getContentPane().add(homeNumberTextField);
		
		JComboBox phoneAreaCodeComboBox = new JComboBox();
		phoneAreaCodeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		phoneAreaCodeComboBox.setBounds(241, 368, 123, 31);
		addNewEmployeeFrame.getContentPane().add(phoneAreaCodeComboBox);
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
		addNewEmployeeFrame.getContentPane().add(phoneNumberTextField);
		
		JLabel commaLabel = new JLabel("-");
		commaLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		commaLabel.setBounds(375, 376, 33, 14);
		addNewEmployeeFrame.getContentPane().add(commaLabel);
		
		dateTextField = new JTextField();
		dateTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(checkDate(dateTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    dateTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					dateTextField.setBorder(border);
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(checkDate(dateTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    dateTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					dateTextField.setBorder(border);
				}
			}
		});
		dateTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dateTextField.setHorizontalAlignment(SwingConstants.CENTER);
		dateTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dateTextField.setText("");
			}
		});
		dateTextField.setText("dd-mm-yyyy");
		dateTextField.setColumns(10);
		dateTextField.setBounds(290, 437, 174, 30);
		addNewEmployeeFrame.getContentPane().add(dateTextField);
		
		JButton submitBTN = new JButton("Submit");
		//Image okIcon Icon
		Image okIcon = new ImageIcon(this.getClass().getResource("/okIcon.png")).getImage();
		submitBTN.setIcon(new ImageIcon(okIcon));
		submitBTN.setForeground(new Color(0, 0, 0));
		submitBTN.setBackground(new Color(220, 220, 220));
		submitBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Check if all fields are valid
				if(checkUserEmail(emailTextField.getText())
						&&checkUserPassword(passwordTextField.getText())
						&&checkOnlyLetters(firstNameTextField.getText())
						&&checkOnlyLetters(lastNameTextField.getText())
						&&checkOnlyLetters(streetNameTextField.getText())
						&&checkOnlyDigits(homeNumberTextField.getText())
						&&checkOnlyDigits2(phoneNumberTextField.getText())
						&&checkDate(dateTextField.getText())) {
					
					// New User Address with fields values
					UserAddress ua = new UserAddress
							((City)cityComboBox.getSelectedItem(),
									streetNameTextField.getText(),homeNumberTextField.getText());
					Date dateFormat;
					try {
						dateFormat = new SimpleDateFormat("dd-MM-yyyy").parse(dateTextField.getText());
						
						int employeeID = DB_Main.db_RecordShop.getMaxEmployeeID() + 1;
						
						// New Employee with fields Values
						Employee employeeToAdd = new Employee( employeeID, 
								emailTextField.getText(),
								passwordTextField.getText(), 
								firstNameTextField.getText(), 
								lastNameTextField.getText(), 
								ua, 
								(PhoneAreaCode)phoneAreaCodeComboBox.getSelectedItem(), 
								phoneNumberTextField.getText(), 
								dateFormat  );
						
						// Check if employee not exist then add to shop employee list
						if(!DB_Main.db_RecordShop.isContainEmployee(employeeToAdd.getEmail())) {
							
							DB_Main.db_RecordShop.addEmployeeToSql(employeeToAdd);// Add to employee list sql database
							
							//Message user added
							JOptionPane.showMessageDialog(addNewEmployeeFrame,"Employee "+employeeToAdd.getfullName()+ " Saved Successfuly!");
							
							// Re-Open screen
							AddNewEmployeeScreen addNewEmployeeScreen2 = new AddNewEmployeeScreen();
							addNewEmployeeScreen2.getFrame().setVisible(true);
							addNewEmployeeFrame.dispose();

						}
						else 
							// User already in the system
							JOptionPane.showMessageDialog
							(addNewEmployeeFrame,"Employee With "+employeeToAdd.getEmail()+ " Alredy Exist!","Error",JOptionPane.ERROR_MESSAGE);
						
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UserNameIsNotValid e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UserPasswordIsNotValid e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					

					
				}
				else
					// Message wrong details
					  JOptionPane.showMessageDialog
					  (addNewEmployeeFrame,"One Or More Of The Details You Entered Are Invalid!"
							  ,"Error",JOptionPane.ERROR_MESSAGE);
				
			}
		});
		submitBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		submitBTN.setBounds(370, 520, 143, 50);
		addNewEmployeeFrame.getContentPane().add(submitBTN);
		
		JButton returnToMenuBTN = new JButton("Return To Menu");
		
		//Image backToMenu icon
		Image img2 = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(img2));
		
		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(35, 521, 154, 50);
		addNewEmployeeFrame.getContentPane().add(returnToMenuBTN);
		
		JLabel addLabel = new JLabel("");
		
		//Image addBlack Icon
		Image img3 = new ImageIcon(this.getClass().getResource("/addBlack.png")).getImage();
		addLabel.setIcon(new ImageIcon(img3));
		
		addLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addLabel.setBounds(608, 54, 158, 133);
		addNewEmployeeFrame.getContentPane().add(addLabel);
		
		JLabel formLabel = new JLabel("");
		
		//Image form Icon
		Image img = new ImageIcon(this.getClass().getResource("/form2.png")).getImage();
		formLabel.setIcon(new ImageIcon(img));
		
		formLabel.setBounds(648, 402, 135, 175);
		addNewEmployeeFrame.getContentPane().add(formLabel);
		
		JLabel datePatternLabel = new JLabel("dd-mm-yyyy");
		datePatternLabel.setForeground(new Color(192, 192, 192));
		datePatternLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		datePatternLabel.setHorizontalAlignment(SwingConstants.CENTER);
		datePatternLabel.setBounds(329, 467, 95, 24);
		addNewEmployeeFrame.getContentPane().add(datePatternLabel);
		
		JLabel charactersLabel = new JLabel("6 or  more characters");
		charactersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		charactersLabel.setForeground(Color.LIGHT_GRAY);
		charactersLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		charactersLabel.setBounds(374, 172, 139, 24);
		addNewEmployeeFrame.getContentPane().add(charactersLabel);
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AdminHomePage adminHomePage = new AdminHomePage();
				adminHomePage.getFrame().setVisible(true);
				addNewEmployeeFrame.dispose();	
			}
		});
		
	}
	
	
	//get Frame
	public JFrame getFrame() {
		return addNewEmployeeFrame;
	}
	
	// ___________________________RGX:____________________________________
	
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
	
	// RGX check Only Letters And Digits
	boolean checkOnlyLettersAndDigits(String input) {

		String regex = "[a-zA-Z0-9]{3,}";
		if(!input.matches(regex)) {
			return false;			
		}
		
		return true;
	}
	
	// RGX check Only Digits
	boolean checkOnlyDigits(String input) {

		String regex = "[0-9]{1,5}";
		if(!input.matches(regex)) {
			return false;			
		}
		
		return true;
	}
	
	// RGX check Only Letters And Digits
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
	// RGX check Only Letters
	boolean checkDate(String input) { 
		
						// 0+ [1-9] ае 1+ [0-9] ае 2+ [0-9] ае 3+ [0-1]
		String regex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-([2][0-9]{3})$";
		if(!input.matches(regex)) {
			return false;			
		}
		
		return true;
	}
}
