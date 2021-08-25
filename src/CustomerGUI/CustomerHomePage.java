package CustomerGUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Classes.Customer;
import DataBase.DB_Main;
import LogInGUI.LogInScreen;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerHomePage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2304492776234833194L;
	private JFrame customerFrame;
	private Customer customer;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerHomePage window = new CustomerHomePage("TEST");
					window.customerFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CustomerHomePage(String customerEmail) {
		
		this.customer = 
				DB_Main.db_RecordShop.getCustomerByEmail(customerEmail);

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		customerFrame = new JFrame("Yuval Shai & Adi Hemo");
		customerFrame.setBounds(100, 100, 703, 455);
		customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		customerFrame.getContentPane().setLayout(null);
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		customerFrame.setLocation(dim.width/2-customerFrame.getSize().width/2,
				dim.height/2-customerFrame.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		customerFrame.setIconImage(icon.getImage());
		
		JButton showAllShopRecordsBTN = new JButton("Show All Shop Records");
		//Image smallRecord Icon
		Image smallRecord = new ImageIcon(this.getClass().getResource("/record2.png")).getImage();
		showAllShopRecordsBTN.setIcon(new ImageIcon(smallRecord));
		showAllShopRecordsBTN.setForeground(new Color(0, 0, 0));
		showAllShopRecordsBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		showAllShopRecordsBTN.setBounds(98, 240, 238, 89);
		showAllShopRecordsBTN.setBackground(new Color(204, 216, 206));
		customerFrame.getContentPane().add(showAllShopRecordsBTN);
		showAllShopRecordsBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RecordListScreen recordListScreen = new RecordListScreen(customer.getEmail());
				recordListScreen.getFrame().setVisible(true);
				customerFrame.dispose();	
			}
		});
		
		JButton showMyOrdersBTN = new JButton("Show My Orders");
		//Image smallPackage Icon
		Image smallPackage = new ImageIcon(this.getClass().getResource("/smallPackage.png")).getImage();
		showMyOrdersBTN.setIcon(new ImageIcon(smallPackage));
		showMyOrdersBTN.setBackground(new Color(204, 216, 206));
		showMyOrdersBTN.setForeground(new Color(0, 0, 0));
		showMyOrdersBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        // Set customer orders from SQL
		        customer.setCustomerOrdersList( 
		        		DB_Main.db_RecordShop.getOrdersListFromSqlByCustomerEmail( customer.getEmail() ) );
				
				// Check if customer order is is empty
				if(customer.getCustomerOrdersList().isEmpty()) {
					JOptionPane.showMessageDialog
					(customerFrame, "Your Order List Is Empty!","Error",JOptionPane.ERROR_MESSAGE);
				}
				else { // Show Customer his orders
					
					MyOrdersScreen myOrdersScreen = new MyOrdersScreen(customer.getEmail());
					myOrdersScreen.getFrame().setVisible(true);
					customerFrame.dispose();
				}

				
			}
		});
		showMyOrdersBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		showMyOrdersBTN.setBounds(346, 240, 238, 89);
		customerFrame.getContentPane().add(showMyOrdersBTN);
		
		JButton myProfileBTN = new JButton("My Profile");
		//Image smallProfile Icon
		Image smallProfile = new ImageIcon(this.getClass().getResource("/smallProfile.png")).getImage();
		myProfileBTN.setIcon(new ImageIcon(smallProfile));
		myProfileBTN.setBackground(new Color(204, 216, 206));
		myProfileBTN.setForeground(new Color(0, 0, 0));
		myProfileBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		myProfileBTN.setBounds(98, 140, 238, 89);
		customerFrame.getContentPane().add(myProfileBTN);
		myProfileBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MyProfileScreen myProfileScreen = new MyProfileScreen(customer.getEmail());
				myProfileScreen.getFrame().setVisible(true);
				customerFrame.dispose();	
			}
		});
		
		JButton myCartBTN = new JButton("My Cart");
		//Image smallCart Icon
		Image smallCart = new ImageIcon(this.getClass().getResource("/cart2.png")).getImage();
		myCartBTN.setIcon(new ImageIcon(smallCart));
		myCartBTN.setBackground(new Color(204, 216, 206));
		myCartBTN.setForeground(new Color(0, 0, 0));
		myCartBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Set customer records shopping cart from sql
				customer.setRecordsInShoppingCart(DB_Main.db_RecordShop.getRecordsInShoppingCartFromSql(customer.getEmail()));
				
				if(customer.getRecordsInShoppingCart().isEmpty()) {
					JOptionPane.showMessageDialog(customerFrame, "Your Cart Is Empty!","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					MyCartScreen myCartScreen = new MyCartScreen(customer.getEmail());
					myCartScreen.getFrame().setVisible(true);
					customerFrame.dispose();
				}

			}
		});
		myCartBTN.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		myCartBTN.setBounds(350, 140, 238, 89);
		customerFrame.getContentPane().add(myCartBTN);
		
		JButton logOutBTN = new JButton("Log-Out");
		//Image log out Icon
		Image img = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		logOutBTN.setIcon(new ImageIcon(img));

		logOutBTN.setForeground(Color.DARK_GRAY);
		logOutBTN.setBackground(new Color(204, 216, 206));
		logOutBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		logOutBTN.setBounds(284, 351, 143, 50);
		customerFrame.getContentPane().add(logOutBTN);
		
		JLabel logoLabel = new JLabel("");
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Image Logo Icon
		Image logoIcon = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		logoLabel.setIcon(new ImageIcon(logoIcon));
		
		logoLabel.setBounds(10, 11, 667, 118);
		customerFrame.getContentPane().add(logoLabel);
		
		JLabel grey_backgroundLabel = new JLabel("");
		//Image grey_backgroundLabel Icon
		Image grey_background = new ImageIcon(this.getClass().getResource("/greyBackgroundToAllScreens10.png")).getImage();
		grey_backgroundLabel.setIcon(new ImageIcon(grey_background));
		grey_backgroundLabel.setBounds(-65, 0, 762, 439);
		customerFrame.getContentPane().add(grey_backgroundLabel);
		logOutBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				LogInScreen logIn = new LogInScreen();
				logIn.getFrame().setVisible(true);
				customerFrame.dispose();
				
			}
		});
	}
	
	// get Frame
	public JFrame getFrame() {
		return customerFrame;
	}
}
