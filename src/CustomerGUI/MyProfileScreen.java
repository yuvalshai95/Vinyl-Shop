package CustomerGUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Classes.Customer;

import DataBase.DB_Main;
import Exceptions.UserPasswordIsNotValid;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;

public class MyProfileScreen {

	private JFrame myProfileFrame;
	private Customer customer;
	private JTextArea profileDetailsTextArea;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyProfileScreen window = new MyProfileScreen("TEST");
					window.myProfileFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyProfileScreen(String customerEmail) {

		this.customer = 
				DB_Main.db_RecordShop.getCustomerByEmail(customerEmail);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		myProfileFrame = new JFrame("Yuval Shai & Adi Hemo");
		myProfileFrame.setBounds(100, 100, 531, 400);
		myProfileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myProfileFrame.getContentPane().setLayout(null);
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		myProfileFrame.setLocation(dim.width/2-myProfileFrame.getSize().width/2,
				dim.height/2-myProfileFrame.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		myProfileFrame.setIconImage(icon.getImage());
		
		JLabel helloCustomerLabel = new JLabel("Hello "+customer.getfullName());
		helloCustomerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		helloCustomerLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		helloCustomerLabel.setBounds(91, 23, 387, 45);
		myProfileFrame.getContentPane().add(helloCustomerLabel);
		
		JButton returnToMenuBTN = new JButton("Return To Menu");
		//Image back Icon
		Image image = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(image));

		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CustomerHomePage customerHomePage = new CustomerHomePage(customer.getEmail());
				customerHomePage.getFrame().setVisible(true);
				myProfileFrame.dispose();	
			}
		});
		returnToMenuBTN.setBounds(10, 299, 157, 50);
		myProfileFrame.getContentPane().add(returnToMenuBTN);
		
		JLabel userProfileLabel = new JLabel("You'r User Profile Details:");
		userProfileLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userProfileLabel.setBounds(79, 101, 251, 25);
		myProfileFrame.getContentPane().add(userProfileLabel);
		
		JButton changePasswordBTN = new JButton("Change Password");
		//Image smallPassword Icon
		Image smallPassword = new ImageIcon(this.getClass().getResource("/smallPassword.png")).getImage();
		changePasswordBTN.setIcon(new ImageIcon(smallPassword));
		changePasswordBTN.setBackground(new Color(220, 220, 220));
		changePasswordBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JPanel Panel = new JPanel(new GridLayout(3,1));
				JLabel currentPassword = new JLabel("Enter Current Password: ");
				JTextField currPass = new JTextField(5); 
				JLabel newPassword = new JLabel("Enter New Password: ");
				JTextField newPass = new JTextField(5); 
				JLabel checkNewPassword = new JLabel("Validate New Password: ");
				JTextField checkPass = new JTextField(5);
				
				Panel.add(currentPassword);
				Panel.add(currPass);
				Panel.add(newPassword);
				Panel.add(newPass);
				Panel.add(checkNewPassword);
				Panel.add(checkPass);
				
				int userChoice = JOptionPane.showConfirmDialog
						(null, Panel, "Change Password", JOptionPane.OK_CANCEL_OPTION);
				
				if(userChoice == JOptionPane.OK_OPTION) { // Customer clicked ok
					
					// if user entered wrong password
					if(!DB_Main.db_RecordShop.isPasswordEquals(customer, currPass.getText().toString())) {
						
						// show message password is wrong
						JOptionPane.showMessageDialog
						(myProfileFrame,"Current Password Is Wrong!","Error",JOptionPane.ERROR_MESSAGE);
					}
					
					// newPassword and checkNewPassword are not the same
					else if(!newPass.getText().toString().equals(checkPass.getText().toString())) {
						
						// show message new password and check password are incorrect
						JOptionPane.showMessageDialog
						(myProfileFrame,"Your New Password And Password Validation Are Incorrect!","Error",JOptionPane.ERROR_MESSAGE);
						
					
					} else {// customer entered his old password right and passed password validation 
						
						// Update customer password Sql by email
						DB_Main.db_RecordShop.updateCustomerPassword(customer.getEmail(), newPass.getText().toString());
						
						//show message password changed
						JOptionPane.showMessageDialog
						(myProfileFrame,"Your Password Has Changed");
					}
				}				
			}
		});
		changePasswordBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		changePasswordBTN.setBounds(332, 299, 173, 50);
		myProfileFrame.getContentPane().add(changePasswordBTN);
		
		JScrollPane profileDetailsScrollPane = new JScrollPane();
		profileDetailsScrollPane.setBounds(79, 137, 354, 151);
		myProfileFrame.getContentPane().add(profileDetailsScrollPane);
		
		profileDetailsTextArea = new JTextArea();
		
		//lock description field
		profileDetailsTextArea.setEditable(false);
		
		profileDetailsTextArea.setText(customer.toStringToMyProfile());
		
		profileDetailsTextArea.setFont(new Font("Tahoma", Font.PLAIN, 19));
		profileDetailsScrollPane.setViewportView(profileDetailsTextArea);
		
		JLabel logoMyProfileLabel = new JLabel("");
		
		//Image myProfile Icon
		Image img = new ImageIcon(this.getClass().getResource("/myProfileLogo.png")).getImage();
		logoMyProfileLabel.setIcon(new ImageIcon(img));
		
		logoMyProfileLabel.setBounds(21, 0, 87, 87);
		myProfileFrame.getContentPane().add(logoMyProfileLabel);
	}
	
	//get Frame
	public JFrame getFrame() {
		return myProfileFrame;
	}
}
