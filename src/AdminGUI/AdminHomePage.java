package AdminGUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import LogInGUI.LogInScreen;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;

public class AdminHomePage extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6434442724813946035L;
	private JFrame adminframe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminHomePage window = new AdminHomePage();
					window.adminframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminHomePage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		adminframe = new JFrame("Yuval Shai & Adi Hemo");
		adminframe.setBounds(100, 100, 789, 483);
		adminframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminframe.getContentPane().setLayout(null);
		

		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		adminframe.setLocation(dim.width/2-adminframe.getSize().width/2,
				dim.height/2-adminframe.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		adminframe.setIconImage(icon.getImage());
		
		JLabel logoLabel = new JLabel("");
		//Image logo Icon
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		logoLabel.setIcon(new ImageIcon(img));
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		logoLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		logoLabel.setBounds(0, 11, 773, 123);
		adminframe.getContentPane().add(logoLabel);
		
		// Employee List BTN
		JButton EmployeeListBtn = new JButton("Employees List");
		//Image smallEmployee Icon
		Image smallEmployee = new ImageIcon(this.getClass().getResource("/smallEmployee.png")).getImage();
		EmployeeListBtn.setIcon(new ImageIcon(smallEmployee));
		EmployeeListBtn.setForeground(new Color(0, 0, 0));
		EmployeeListBtn.setBackground(new Color(204, 216, 206));
		EmployeeListBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		EmployeeListBtn.setBounds(75, 286, 293, 58);
		adminframe.getContentPane().add(EmployeeListBtn);
		EmployeeListBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EmployeeListScreen ep = new EmployeeListScreen();
				ep.getFrame().setVisible(true);
				adminframe.dispose();
				
			}
		});
		
		// Customer List BTN
		JButton customerListBTN = new JButton("Customers List");
		//Image smallCustomer Icon
		Image smallCustomer = new ImageIcon(this.getClass().getResource("/smallCustomer.png")).getImage();
		customerListBTN.setIcon(new ImageIcon(smallCustomer));
		customerListBTN.setBackground(new Color(204, 216, 206));
		customerListBTN.setForeground(new Color(0, 0, 0));
		customerListBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		customerListBTN.setBounds(399, 214, 293, 58);
		adminframe.getContentPane().add(customerListBTN);
		customerListBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CustomerListScreen customerList = new CustomerListScreen();
				customerList.getFrame().setVisible(true);
				adminframe.dispose();
				
			}
		});
		
		// Order List BTN
		JButton orderListBTN = new JButton("Orders List");
		//Image smallPackage Icon
		Image smallPackage = new ImageIcon(this.getClass().getResource("/smallPackage.png")).getImage();
		orderListBTN.setIcon(new ImageIcon(smallPackage));
		orderListBTN.setForeground(new Color(0, 0, 0));
		orderListBTN.setBackground(new Color(204, 216, 206));
		orderListBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				OrderListScreen orderList = new OrderListScreen();
				orderList.getFrame().setVisible(true);
				adminframe.dispose();
				
			}
		});
		orderListBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		orderListBTN.setBounds(76, 217, 293, 58);
		adminframe.getContentPane().add(orderListBTN);
		
		// Record List BTN
		JButton recordListBTN = new JButton("Records List");
		//Image smallRecord Icon
		Image smallRecord = new ImageIcon(this.getClass().getResource("/record2.png")).getImage();
		recordListBTN.setIcon(new ImageIcon(smallRecord));
		recordListBTN.setForeground(new Color(0, 0, 0));
		recordListBTN.setBackground(new Color(204, 216, 206));
		recordListBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		recordListBTN.setBounds(76, 145, 293, 58);
		adminframe.getContentPane().add(recordListBTN);
		recordListBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RecordListScreen recordList = new RecordListScreen();
				recordList.getFrame().setVisible(true);
				adminframe.dispose();
				
			}
		});

		// Add New Employee BTN
		JButton addNewEmployeeBTN = new JButton("Add New Employee");
		//Image plus Icon
		Image plus = new ImageIcon(this.getClass().getResource("/plus2.png")).getImage();
		addNewEmployeeBTN.setIcon(new ImageIcon(plus));
		addNewEmployeeBTN.setForeground(new Color(0, 0, 0));
		addNewEmployeeBTN.setBackground(new Color(204, 216, 206));
		addNewEmployeeBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		addNewEmployeeBTN.setBounds(399, 286, 293, 58);
		adminframe.getContentPane().add(addNewEmployeeBTN);
		addNewEmployeeBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AddNewEmployeeScreen addNewEmployeeFrame = new AddNewEmployeeScreen();
				addNewEmployeeFrame.getFrame().setVisible(true);
				adminframe.dispose();
				
			}
		});
		
		
		// Remove Employee BTN
		JButton searchEmployeeBTN = new JButton("Search Employee");
		//Image search Icon
		Image search = new ImageIcon(this.getClass().getResource("/search.png")).getImage();
		searchEmployeeBTN.setIcon(new ImageIcon(search));
		searchEmployeeBTN.setForeground(new Color(0, 0, 0));
		searchEmployeeBTN.setBackground(new Color(204, 216, 206));
		searchEmployeeBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SearchEmployeeScreen searchEmployeeScreen = new SearchEmployeeScreen();
				searchEmployeeScreen.getFrame().setVisible(true);
				adminframe.dispose();
			}
		});
		searchEmployeeBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		searchEmployeeBTN.setBounds(399, 145, 293, 58);
		adminframe.getContentPane().add(searchEmployeeBTN);
		
		// Log Out BTN
		JButton logOutBTN = new JButton("Log-Out");
		logOutBTN.setForeground(Color.DARK_GRAY);
		logOutBTN.setBackground(new Color(204, 216, 206));
		//Image backToMenu Icon
		Image img2 = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		logOutBTN.setIcon(new ImageIcon(img2));
		logOutBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		logOutBTN.setBounds(302, 373, 150, 50);
		adminframe.getContentPane().add(logOutBTN);
		
		JLabel grey_backgroundLabel = new JLabel("");
		//Image grey_backgroundLabel Icon
		Image grey_background = new ImageIcon(this.getClass().getResource("/greyBackgroundToAllScreens10.png")).getImage();
		grey_backgroundLabel.setIcon(new ImageIcon(grey_background));
		grey_backgroundLabel.setBounds(-22, 0, 795, 481);
		adminframe.getContentPane().add(grey_backgroundLabel);
		logOutBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				LogInScreen logInScreen = new LogInScreen();
				logInScreen.getFrame().setVisible(true);
				adminframe.dispose();
				
			}
		});
	}
	
	public JFrame getFrame() {
		return adminframe;
	}
	
}
