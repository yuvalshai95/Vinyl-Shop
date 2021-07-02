package EmployeeGUI;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Classes.Customer;
import Classes.Employee;

import DataBase.DB_Main;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Window.Type;

public class CustomerListScreen2 {

	private JFrame customerListFrame2;
	private JTable customerListTable;
	private Employee employee;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerListScreen2 window = new CustomerListScreen2("TEST");
					window.customerListFrame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CustomerListScreen2(String employeeEmail) {

		this.employee = DB_Main.db_RecordShop.getEmployeeByEmail(employeeEmail);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		customerListFrame2 = new JFrame("Yuval Shai & Adi Hemo");
		customerListFrame2.setBounds(100, 100, 836, 382);
		customerListFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		customerListFrame2.getContentPane().setLayout(null);
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		customerListFrame2.setLocation(dim.width/2-customerListFrame2.getSize().width/2, dim.height/2-customerListFrame2.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		customerListFrame2.setIconImage(icon.getImage());
		
		JLabel customerListLabel = new JLabel("Customer List");
		customerListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		customerListLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		customerListLabel.setBounds(10, 22, 800, 26);
		customerListFrame2.getContentPane().add(customerListLabel);
		
		JScrollPane customerListScrollPane = new JScrollPane();
		customerListScrollPane.setBounds(10, 59, 800, 193);
		customerListFrame2.getContentPane().add(customerListScrollPane);
		
		customerListTable = new JTable();
		
		//Table design
		tableDesign();
		
		//lock table cell
		customerListTable.setDefaultEditor(Object.class, null);

		// Headers for the table
		 String[] columns = new String[] {
		            "ID", "Customer Name", 
		            "Phone Number", "Email","Address"};
		 
	        model = new DefaultTableModel();
	        model.setColumnIdentifiers(columns);
	        customerListTable.setModel(model);
	        customerListTable.setFont(new Font("Tahoma", Font.PLAIN, 17));
	        customerListTable.setRowHeight(22);
	        
			for(Customer customer : DB_Main.db_RecordShop.getCustomerListFromSql() ){
				
				Object[] row = new Object[5];
				
				row[0] = customer.getId();
				row[1] = customer.getfullName();
				row[2] = customer.getFullPhoneNumber();
				row[3] = customer.getEmail();
				row[4] = customer.getAddress().toString();
				
				model.addRow(row);
				
				//center table row font
				centerAllTableRows();
				
				// re-size table column width according to the context
				resizeColumnWidth(customerListTable);
				
	        }
		customerListScrollPane.setViewportView(customerListTable);
		
		JButton returnToMenuBTN = new JButton("Return To Menu");
		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		//Image back Icon
		Image img = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(img));
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(321, 272, 158, 47);
		customerListFrame2.getContentPane().add(returnToMenuBTN);
		
		// Customer Counter
		JLabel totalCustomersLabel = new JLabel("Total Customers:");
		totalCustomersLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		totalCustomersLabel.setBounds(633, 263, 145, 50);
		customerListFrame2.getContentPane().add(totalCustomersLabel);
		
		// Customer Counter
		JLabel numLabel = new JLabel("Num");
		numLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		numLabel.setBounds(764, 281, 46, 14);
		customerListFrame2.getContentPane().add(numLabel);
		numLabel.setText( String.valueOf( DB_Main.db_RecordShop.totalCustomersInSql() ) );
		
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EmployeeHomePage employeeHomePage = new EmployeeHomePage(employee.getEmail());
				employeeHomePage.getFrame().setVisible(true);
				customerListFrame2.dispose();	
			}
		});
	}
	
	//get Frame
	public JFrame getFrame() {
		return customerListFrame2;
	}
	
	
	public void tableDesign() {
		
		//Table Design
		customerListTable.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 14)); // Table Colums Header Font and Size
		customerListTable.getTableHeader().setBackground(new Color(11,57,206)); // Table Colums Header Background color
		customerListTable.getTableHeader().setForeground(Color.white); // Table Colums Header font color 
		customerListTable.setFont(new Font("Tahoma", Font.PLAIN, 18)); // Table row font
		customerListTable.setBackground(new Color(212,212,212)); // Table row background color
		
	}
	
	// center table row font and color AlternateRows
	public void centerAllTableRows() {
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		 for(int x=0; x<customerListTable.getColumnCount(); x++){
			 customerListTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
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