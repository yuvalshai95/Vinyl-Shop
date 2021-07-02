package CustomerGUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Classes.Customer;

import Classes.Order;
import DataBase.DB_Main;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;

public class MyOrdersScreen {

	private JFrame myOrdersFrame;
	private JTable myOrdersTable;
	private JTextArea OrderDetailsTextArea;
	private Customer customer;
	private DefaultTableModel model;
	private int index;
	private Order order;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyOrdersScreen window = new MyOrdersScreen("TEST");
					window.myOrdersFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyOrdersScreen(String customerEmail) {
		
		this.customer = 
				DB_Main.db_RecordShop.getCustomerByEmail(customerEmail);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		myOrdersFrame = new JFrame("Yuval Shai & Adi Hemo");
		myOrdersFrame.setBounds(100, 100, 707, 606);
		myOrdersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myOrdersFrame.getContentPane().setLayout(null);
		
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		myOrdersFrame.setLocation(dim.width/2-myOrdersFrame.getSize().width/2,
				dim.height/2-myOrdersFrame.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		myOrdersFrame.setIconImage(icon.getImage());
		
		JLabel myOrderLabel = new JLabel("My Orders:");
		myOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		myOrderLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		myOrderLabel.setBounds(10, 11, 671, 56);
		myOrdersFrame.getContentPane().add(myOrderLabel);
		
		JScrollPane myOrdersScrollPane = new JScrollPane();
		myOrdersScrollPane.setBounds(10, 73, 671, 190);
		myOrdersFrame.getContentPane().add(myOrdersScrollPane);
		
		myOrdersTable = new JTable();
		
		//Table design
		tableDesign();
		
		myOrdersTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		//lock table cell
		myOrdersTable.setDefaultEditor(Object.class, null);
		
		myOrdersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				index = myOrdersTable.getSelectedRow(); // save selected row
				int selectedOrderID = (int)model.getValueAt(index, 0); // save selected order id
				order = DB_Main.db_RecordShop.getOrderByID(selectedOrderID); // get order from sql by id
				
				//Set all records from order to the records list
				order.setRecordsListInOrder( DB_Main.db_RecordShop.getRecordsInOrderFromSqlByID( order.getOrderID() ) );
				
				OrderDetailsTextArea.setText
				(order.toString());
				
			}
		});
		
		// headers for the table
        String[] columns = new String[] {
            "ID", "Order Date","Price" 
        };
        
        this.model = new DefaultTableModel();
        this.model.setColumnIdentifiers(columns);
        this.myOrdersTable.setModel(model);
        
        // Set customer orders from SQL
        customer.setCustomerOrdersList( 
        		DB_Main.db_RecordShop.getOrdersListFromSqlByCustomerEmail( customer.getEmail() ) );
        
        // fill the table with data 
		for(Order order :customer.getCustomerOrdersList()) {
			
			Object[] row = new Object[3];
			
			row[0] = order.getOrderID();
			row[1] = order.getOrderDate();
			row[2] = order.getTotalPrice2();

			
			model.addRow(row);
			
			//center table row font
			centerAllTableRows();
			
			// re-size table column width according to the context
			resizeColumnWidth(myOrdersTable);
		}
		
		
		
		myOrdersScrollPane.setViewportView(myOrdersTable);
		
		JLabel lblOrder = new JLabel("Order Details: ");
		lblOrder.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrder.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblOrder.setBounds(10, 262, 671, 56);
		myOrdersFrame.getContentPane().add(lblOrder);
		
		JScrollPane orderDetailsScrollPane = new JScrollPane();
		orderDetailsScrollPane.setBounds(154, 314, 377, 160);
		myOrdersFrame.getContentPane().add(orderDetailsScrollPane);
		
		OrderDetailsTextArea = new JTextArea();
		OrderDetailsTextArea.setFont(new Font("Tahoma", Font.PLAIN, 19));
		
		//lock description field
		OrderDetailsTextArea.setEditable(false);
		
		orderDetailsScrollPane.setViewportView(OrderDetailsTextArea);
		
		JButton returnToMenuBTN = new JButton("Return To Menu");
		//Image search Icon
		Image img = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(img));

		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(280, 500, 161, 50);
		myOrdersFrame.getContentPane().add(returnToMenuBTN);
		
		JLabel packageLabel1 = new JLabel("");
		//Image package Icon
		Image package1 = new ImageIcon(this.getClass().getResource("/package.png")).getImage();
		packageLabel1.setIcon(new ImageIcon(package1));
		packageLabel1.setBounds(10, 342, 134, 114);
		myOrdersFrame.getContentPane().add(packageLabel1);
		
		JLabel packageLabel2 = new JLabel("");
		//Image package Icon
		Image package2 = new ImageIcon(this.getClass().getResource("/package.png")).getImage();
		packageLabel2.setIcon(new ImageIcon(package2));
		packageLabel2.setBounds(554, 342, 134, 114);
		myOrdersFrame.getContentPane().add(packageLabel2);
		
		
		

		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CustomerHomePage customerHomePage = new CustomerHomePage(customer.getEmail());
				customerHomePage.getFrame().setVisible(true);
				myOrdersFrame.dispose();
				
			}
		});
	}
	
	// get Frame
	public JFrame getFrame() {
		return myOrdersFrame;
	}
	
	public void tableDesign() {
		
		//Table Design
		myOrdersTable.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 14)); // Table Colums Header Font and Size
		myOrdersTable.getTableHeader().setBackground(new Color(11,57,206)); // Table Colums Header Background color
		myOrdersTable.getTableHeader().setForeground(Color.white); // Table Colums Header font color 
		myOrdersTable.setFont(new Font("Tahoma", Font.PLAIN, 18)); // Table row font
		myOrdersTable.setBackground(new Color(212,212,212)); // Table row background color
		
	}
	
	// center table row font and color AlternateRows
	public void centerAllTableRows() {
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		 for(int x=0; x<myOrdersTable.getColumnCount(); x++){
			 myOrdersTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
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