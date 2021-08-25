package CustomerGUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Classes.Customer;
import Classes.Order;
import Classes.Record;
import DataBase.DB_Main;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.awt.Color;
import java.awt.Component;

public class MyCartScreen {

	private JFrame myCartFrame;
	private JTable MyCartTable;
	private JTextArea cartDescriptionTextArea;
	private JTextArea totalPriceTextArea;
	private Customer customer;
	private DefaultTableModel model;
	private int index = -5;
	private Record record;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyCartScreen window = new MyCartScreen("TEST");
					window.myCartFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyCartScreen(String customerEmail) {
		
		this.customer = 
				DB_Main.db_RecordShop.
				getCustomerByEmail(customerEmail);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		myCartFrame = new JFrame("Yuval Shai & Adi Hemo");
		myCartFrame.setBounds(100, 100, 660, 761);
		myCartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myCartFrame.getContentPane().setLayout(null);
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		myCartFrame.setLocation(dim.width/2-myCartFrame.getSize().width/2,
				dim.height/2-myCartFrame.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		myCartFrame.setIconImage(icon.getImage());
		
		JLabel myCartLabel = new JLabel("My Cart: ");
		myCartLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		myCartLabel.setBounds(10, 70, 122, 37);
		myCartFrame.getContentPane().add(myCartLabel);
		
		JScrollPane MyCartScrollPane = new JScrollPane();
		MyCartScrollPane.setBounds(10, 118, 624, 211);
		myCartFrame.getContentPane().add(MyCartScrollPane);
		
		MyCartTable = new JTable();
		
		//Table design
		tableDesign();
		
		MyCartTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		//lock table cell
		MyCartTable.setDefaultEditor(Object.class, null);
		
		MyCartTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				index = MyCartTable.getSelectedRow();//selected row on mouse click
				
				int selectedRecordId = (int) model.getValueAt(index, 0); // save selected record id
				
				record = DB_Main.db_RecordShop.getRecordByID(selectedRecordId);// get record data from sql by id
				
				cartDescriptionTextArea.setText(record.songsToString()); // set record description
				
			}
		});
		
		// headers for the table
        String[] columns = new String[] {
            "ID", "Name", "Release Year", "Artist", "Price" 
        };
        
        this.model = new DefaultTableModel();
        this.model.setColumnIdentifiers(columns);
        this.MyCartTable.setModel(model);
        
        //Set customer shopping cart to data from sql
        customer.setRecordsInShoppingCart
        ( DB_Main.db_RecordShop.getRecordsInShoppingCartFromSql(  customer.getEmail()  )  );
        
        // fill the table with data
		for(Record record :customer.getRecordsInShoppingCart() ) {
			
			Object[] row = new Object[5];
			
			row[0] = record.getRecordID();
			row[1] = record.getRecordName();
			row[2] = record.getReleaseYear();
			row[3] = record.getArtist();
			row[4] = record.getPrice2();
			
			model.addRow(row);
			
			//center table row font
			centerAllTableRows();
			
			// re-size table column width according to the context
			resizeColumnWidth(MyCartTable);

		}
		

		
		MyCartScrollPane.setViewportView(MyCartTable);
		
		JLabel lblCartDiscription = new JLabel("Record Description:");
		lblCartDiscription.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblCartDiscription.setBounds(10, 353, 296, 37);
		myCartFrame.getContentPane().add(lblCartDiscription);
		
		JScrollPane cartDiscriptionscrollPane = new JScrollPane();
		cartDiscriptionscrollPane.setBounds(10, 401, 416, 235);
		myCartFrame.getContentPane().add(cartDiscriptionscrollPane);
		
		cartDescriptionTextArea = new JTextArea();
		
		//lock description field
		cartDescriptionTextArea.setEditable(false);
		
		cartDescriptionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 19));
		cartDiscriptionscrollPane.setViewportView(cartDescriptionTextArea);
		cartDescriptionTextArea.setColumns(10);
		
		JLabel totalPriceLabel = new JLabel("Total Price:");
		totalPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		totalPriceLabel.setBounds(445, 542, 164, 37);
		myCartFrame.getContentPane().add(totalPriceLabel);
		
		totalPriceTextArea = new JTextArea();
		totalPriceTextArea.setBackground(new Color(255, 255, 255));
		totalPriceTextArea.setForeground(Color.BLACK);
		
		//lock description field
		totalPriceTextArea.setEditable(false);
		
		totalPriceTextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		totalPriceTextArea.setBounds(445, 590, 166, 37);
		
		// Set Total Price Field
		totalPriceTextArea.setText
		(String.valueOf(customer.customerTotalCartPrice2()));
		
		myCartFrame.getContentPane().add(totalPriceTextArea);
		totalPriceTextArea.setColumns(10);
		
		JButton returnToMenuBTN = new JButton("Return To Menu");
		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		//Image back Icon
		Image image = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(image));

		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(22, 659,156, 45);
		myCartFrame.getContentPane().add(returnToMenuBTN);
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CustomerHomePage customerHomePage = new CustomerHomePage(customer.getEmail());
				customerHomePage.getFrame().setVisible(true);
				myCartFrame.dispose();
				
			}
		});
		
		JButton removeFromCartBTN = new JButton("Remove From Cart");
		//Image removeIcon Icon
		Image removeIcon = new ImageIcon(this.getClass().getResource("/removeSong.png")).getImage();
		removeFromCartBTN.setIcon(new ImageIcon(removeIcon));
		removeFromCartBTN.setForeground(new Color(255, 255, 255));
		removeFromCartBTN.setBackground(new Color(222, 36, 73));
		removeFromCartBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		removeFromCartBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { 
				
				if(index !=-5) {
					
					// Remove record from customer shopping cart sql
					DB_Main.db_RecordShop.
					removeRecordFromCustomerShoppingCartSql(customer.getEmail(), record.getRecordID());
					
					// Remove record from Java my cart arraylist
					customer.removeRecordFromMyCart(record);
					
					index = -5; // reset index
					
					int rowCount = model.getRowCount();
					
					// Remove table rows one by one from the end of the table
					for (int i = rowCount - 1; i >= 0; i--) {
					    model.removeRow(i);
					}
					
			        //Set customer shopping cart to data from sql
			        customer.setRecordsInShoppingCart
			        ( DB_Main.db_RecordShop.getRecordsInShoppingCartFromSql(  customer.getEmail()  )  );
					
					// Create the  new table
					for(Record record :customer.getRecordsInShoppingCart()) {
						
						Object[] row = new Object[5];
						
						row[0] = record.getRecordID();
						row[1] = record.getRecordName();
						row[2] = record.getReleaseYear();
						row[3] = record.getArtist();
						row[4] = record.getPrice2();
						
						model.addRow(row);
						
						//center table row font
						centerAllTableRows();
						
						// re-size table column width according to the context
						resizeColumnWidth(MyCartTable);

					}
					
					// Set Total Price Field
					totalPriceTextArea.setText
					(String.valueOf(customer.customerTotalCartPrice2()));
					
					// Reset Description
					cartDescriptionTextArea.setText("");
					
					// Show to user message dialog
					JOptionPane.showMessageDialog
					(myCartFrame, "Record "+record.getRecordName()+ " Removed Successfully");
					
				}
				else {
					JOptionPane.showMessageDialog
					(myCartFrame, "No Record Selected","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		removeFromCartBTN.setBounds(445, 340, 177, 45);
		myCartFrame.getContentPane().add(removeFromCartBTN);
		
		JButton placeOrderBTN = new JButton("Place Order");
		//Image payment Icon
		Image payment = new ImageIcon(this.getClass().getResource("/payment.png")).getImage();
		placeOrderBTN.setIcon(new ImageIcon(payment));

		placeOrderBTN.setForeground(new Color(0, 0, 0));
		placeOrderBTN.setBackground(new Color(220, 220, 220));
		placeOrderBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Check if customer cart is empty
				if(!customer.getRecordsInShoppingCart().isEmpty()) {
					
					Date date = new Date();
					Order newOrder = new Order(DB_Main.db_RecordShop.getMaxOrderID()+1,date, customer.customerTotalCartPrice(), customer);
					
					// Insert order to SQL
					DB_Main.db_RecordShop.insertOrderIntoSql(newOrder);
					
					for(Record r : customer.getRecordsInShoppingCart()) {
						newOrder.addRecord(r);
						
						// Insert records to order in SQL
						DB_Main.db_RecordShop.insertRecordToOrderSql(newOrder.getOrderID(), r.getRecordID());
					}
					
				
					// Reset Shopping Cart
					customer.getRecordsInShoppingCart().clear();
					
					// delete all records from customer shopping cart sql
					DB_Main.db_RecordShop.
					deleteRecordsFromCustomerShoppingCart(customer.getEmail());
					
					int rowCount = model.getRowCount();
					
					// Remove table rows one by one from the end of the table
					for (int i = rowCount - 1; i >= 0; i--) {
					    model.removeRow(i);
					}
					
					// Create the  new table
					for(Record record :customer.getRecordsInShoppingCart()) {
						
						Object[] row = new Object[5];
						
						row[0] = record.getRecordID();
						row[1] = record.getRecordName();
						row[2] = record.getReleaseYear();
						row[3] = record.getArtist();
						row[4] = record.getPrice2();
						
						model.addRow(row);
						
						//center table row font
						centerAllTableRows();

						// re-size table column width according to the context
						resizeColumnWidth(MyCartTable);

					}
					
					// Set Total Price Field
					totalPriceTextArea.setText
					(String.valueOf(customer.customerTotalCartPrice2()));
					
					// Reset Description
					cartDescriptionTextArea.setText("");
					
					// Rest Index
					index = -5;
					
					JOptionPane.showMessageDialog
					(myCartFrame, "Your Order Is Confirmed");
				}
				else
					JOptionPane.showMessageDialog
					(myCartFrame, "Your Orders Is Empty!","Error",JOptionPane.ERROR_MESSAGE);
			}
		});
		placeOrderBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		placeOrderBTN.setBounds(483, 659, 139, 45);
		myCartFrame.getContentPane().add(placeOrderBTN);
		
		JLabel helloCustomerLabel = new JLabel(customer.getfullName());
		helloCustomerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		helloCustomerLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		helloCustomerLabel.setBounds(10, 21, 624, 37);
		myCartFrame.getContentPane().add(helloCustomerLabel);
		
		JLabel lblNewLabel = new JLabel("");
		//Image cart Icon
		Image img2 = new ImageIcon(this.getClass().getResource("/cart.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img2));

		lblNewLabel.setBounds(532, 21, 112, 86);
		myCartFrame.getContentPane().add(lblNewLabel);
		
		JButton goToMyOrdersBTN = new JButton("Go to my orders");
		goToMyOrdersBTN.setBackground(new Color(220, 220, 220));
		goToMyOrdersBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		//Image smallPackage Icon
		Image smallPackage = new ImageIcon(this.getClass().getResource("/smallPackage.png")).getImage();
		goToMyOrdersBTN.setIcon(new ImageIcon(smallPackage));

		goToMyOrdersBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		        // Set customer orders from SQL
		        customer.setCustomerOrdersList( 
		        		DB_Main.db_RecordShop.getOrdersListFromSqlByCustomerEmail( customer.getEmail() ) );
				
				// Check if customer order is is empty
				if(customer.getCustomerOrdersList().isEmpty()) {
					
					//Message order list is empty
					JOptionPane.showMessageDialog
					(myCartFrame, "Your Order List Is Empty!","Error",JOptionPane.ERROR_MESSAGE);
				}
				else { // Show the customer his orders
					
					MyOrdersScreen myOrdersScreen = new MyOrdersScreen(customer.getEmail());
					myOrdersScreen.getFrame().setVisible(true);
					myCartFrame.dispose();
				}
			}
		});
		goToMyOrdersBTN.setBounds(252, 659, 162, 45);
		myCartFrame.getContentPane().add(goToMyOrdersBTN);
	}
	
	// get Frame
	public JFrame getFrame() {
		return myCartFrame;
	}
	
	public void tableDesign() {
		
		//Table Design
		MyCartTable.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 14)); // Table Colums Header Font and Size
		MyCartTable.getTableHeader().setBackground(new Color(11,57,206)); // Table Colums Header Background color
		MyCartTable.getTableHeader().setForeground(Color.white); // Table Colums Header font color 
		MyCartTable.setFont(new Font("Tahoma", Font.PLAIN, 18)); // Table row font
		MyCartTable.setBackground(new Color(212,212,212)); // Table row background color
		
	}
	
	// center table row font and color AlternateRows
	public void centerAllTableRows() {
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		 for(int x=0; x<MyCartTable.getColumnCount(); x++){
			 MyCartTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	        }
		 
		 // Color AlternateRows
		// UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		// defaults.putIfAbsent("Table.alternateRowColor", Color.LIGHT_GRAY);
	}
	
	// re-size table column width according to the context
	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 15; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > 300)
	            width=300;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}

}
