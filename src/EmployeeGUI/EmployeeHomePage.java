package EmployeeGUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;


import Classes.Employee;

import DataBase.DB_Main;
import LogInGUI.LogInScreen;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class EmployeeHomePage extends JFrame {
	
	private Employee employee;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7043855653067167361L;
	private JFrame employeeFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeHomePage window = new EmployeeHomePage("TEST");
					window.employeeFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeeHomePage(String userEmail) {

		this.employee = DB_Main.db_RecordShop.getEmployeeByEmail(userEmail);
				
		
		initialize();
		

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		employeeFrame = new JFrame("Yuval Shai & Adi Hemo");
		employeeFrame.setBounds(100, 100, 666, 567);
		employeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		employeeFrame.getContentPane().setLayout(null);
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		employeeFrame.setLocation(dim.width/2-employeeFrame.getSize().width/2, dim.height/2-employeeFrame.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		employeeFrame.setIconImage(icon.getImage());
		
		JLabel welcomeEmployeeLabel = new JLabel("");
		//Image logo Icon
		Image logo = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		welcomeEmployeeLabel.setIcon(new ImageIcon(logo));
		welcomeEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeEmployeeLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		welcomeEmployeeLabel.setBounds(10, 26, 630, 120);
		employeeFrame.getContentPane().add(welcomeEmployeeLabel);
		
		JButton addNewCustomerBTN = new JButton("Add New Customer");
		addNewCustomerBTN.setBackground(new Color(204, 216, 206));
		//Image smallAddUserBlack Icon
		Image smallAddUserBlack = new ImageIcon(this.getClass().getResource("/smallAddUserBlack.png")).getImage();
		addNewCustomerBTN.setIcon(new ImageIcon(smallAddUserBlack));
		addNewCustomerBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		addNewCustomerBTN.setBounds(331, 260, 241, 94);
		employeeFrame.getContentPane().add(addNewCustomerBTN);
		addNewCustomerBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AddNewCustomerScreen addNewCustomerScreen = new AddNewCustomerScreen(employee.getEmail());
				addNewCustomerScreen.getFrame().setVisible(true);
				employeeFrame.dispose();
				
			}
		});

		JButton showOrderListBTN = new JButton("Show Orders List");
		showOrderListBTN.setBackground(new Color(204, 216, 206));
		//Image package Icon
		Image package2 = new ImageIcon(this.getClass().getResource("/smallPackage.png")).getImage();
		showOrderListBTN.setIcon(new ImageIcon(package2));
		showOrderListBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		showOrderListBTN.setBounds(78, 160, 241, 94);
		employeeFrame.getContentPane().add(showOrderListBTN);
		showOrderListBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				OrderListScreen2 orderList = new OrderListScreen2(employee.getEmail());
				orderList.getFrame().setVisible(true);
				employeeFrame.dispose();
				
			}
		});
		
		JButton showAllShopRecordsBTN = new JButton("Show All Shop Records");
		showAllShopRecordsBTN.setBackground(new Color(204, 216, 206));
		//Image smallRecord Icon
		Image smallRecord = new ImageIcon(this.getClass().getResource("/record2.png")).getImage();
		showAllShopRecordsBTN.setIcon(new ImageIcon(smallRecord));
		showAllShopRecordsBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		showAllShopRecordsBTN.setBounds(331, 160, 241, 94);
		employeeFrame.getContentPane().add(showAllShopRecordsBTN);
		showAllShopRecordsBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RecordListScreen2 recordList = new RecordListScreen2(employee.getEmail());
				recordList.getFrame().setVisible(true);
				employeeFrame.dispose();
				
			}
		});
		
		JButton showCustomersListBTN = new JButton("Show Customers List");
		showCustomersListBTN.setBackground(new Color(204, 216, 206));
		//Image smallCustomer Icon
		Image smallCustomer = new ImageIcon(this.getClass().getResource("/smallCustomer.png")).getImage();
		showCustomersListBTN.setIcon(new ImageIcon(smallCustomer));
		showCustomersListBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CustomerListScreen2 customerListScreen2 = new CustomerListScreen2(employee.getEmail());
				customerListScreen2.getFrame().setVisible(true);
				employeeFrame.dispose();	
			}
		});
		showCustomersListBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		showCustomersListBTN.setBounds(78, 260, 241, 94);
		employeeFrame.getContentPane().add(showCustomersListBTN);
		
		JButton logOutBTN = new JButton("Log-Out");
		logOutBTN.setForeground(Color.DARK_GRAY);
		//Image backToMenu Icon
		Image backToMenu = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		logOutBTN.setIcon(new ImageIcon(backToMenu));
		logOutBTN.setBackground(new Color(204, 216, 206));
		logOutBTN.setFont(new Font("Tahoma", Font.BOLD, 15));
		logOutBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				LogInScreen logIn = new LogInScreen();
				logIn.getFrame().setVisible(true);
				employeeFrame.dispose();
				
			}
		});
		logOutBTN.setBounds(253, 455, 150, 50);
		employeeFrame.getContentPane().add(logOutBTN);
		
		JButton btnNewButton = new JButton("Add New Record To Shop");
		btnNewButton.setBackground(new Color(204, 216, 206));
		//Image plus Icon
		Image plus2 = new ImageIcon(this.getClass().getResource("/plus2.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(plus2));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AddNewRecordScreen addNewRecordScreen = new AddNewRecordScreen(employee.getEmail());
				addNewRecordScreen.getFrame().setVisible(true);
				employeeFrame.dispose();
			}
		});
		btnNewButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		btnNewButton.setBounds(78, 364, 494, 67);
		employeeFrame.getContentPane().add(btnNewButton);
		
		JLabel grey_backgroundLabel = new JLabel("");
		//Image grey_backgroundLabel Icon
		Image grey_background = new ImageIcon(this.getClass().getResource("/greyBackgroundToAllScreens11.png")).getImage();
		grey_backgroundLabel.setIcon(new ImageIcon(grey_background));
		grey_backgroundLabel.setBounds(-120, 0, 770, 528);
		employeeFrame.getContentPane().add(grey_backgroundLabel);
	}

	// get Frame
	public JFrame getFrame() {
		return employeeFrame;
	}
}