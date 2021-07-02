package AdminGUI;

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
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Classes.Order;
import Classes.Record;
import DataBase.DB_Main;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;

public class OrderListScreen {

	private JFrame orderListFrame;
	private JTextArea orderDescriptionTextArea;
	private JTable orderListTable;
	private int index;
	private DefaultTableModel model;
	private Order order = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderListScreen window = new OrderListScreen();
					window.orderListFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OrderListScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		orderListFrame = new JFrame("Yuval Shai & Adi Hemo");
		orderListFrame.setBounds(100, 100, 744, 600);
		orderListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		orderListFrame.getContentPane().setLayout(null);
		
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		orderListFrame.setLocation(dim.width/2-orderListFrame.getSize().width/2, dim.height/2-orderListFrame.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		orderListFrame.setIconImage(icon.getImage());
		
		JLabel orderListLabel = new JLabel("Order Description");
		orderListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		orderListLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		orderListLabel.setBounds(18, 258, 700, 43);
		orderListFrame.getContentPane().add(orderListLabel);
		
		JLabel orderListLabel_1 = new JLabel("Order List");
		orderListLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		orderListLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		orderListLabel_1.setBounds(10, 22, 708, 43);
		orderListFrame.getContentPane().add(orderListLabel_1);
		
		JScrollPane orderDescriptionScrollPane = new JScrollPane();
		orderDescriptionScrollPane.setBounds(178, 312, 377, 160);
		orderListFrame.getContentPane().add(orderDescriptionScrollPane);
		
		orderDescriptionTextArea = new JTextArea();
		orderDescriptionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 19));
		
		//lock description field
		orderDescriptionTextArea.setEditable(false);
		
		orderDescriptionScrollPane.setViewportView(orderDescriptionTextArea);
		orderDescriptionTextArea.setColumns(10);
		
		JButton returnToMenuBTN = new JButton("Return To Menu");
		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		//Image backToMenu Icon
		Image img2 = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(img2));
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(292, 500, 176, 50);
		orderListFrame.getContentPane().add(returnToMenuBTN);
		
		JScrollPane orderListScrollPane = new JScrollPane();
		orderListScrollPane.setBounds(10, 76, 708, 171);
		orderListFrame.getContentPane().add(orderListScrollPane);
		
		orderListTable = new JTable();
		
		//lock table cell
		orderListTable.setDefaultEditor(Object.class, null);
		
		// Table design
		tableDesign();
		
		// Mouse clicked on a table row listener
		orderListTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				index = orderListTable.getSelectedRow(); //selected row on mouse click
				int selectedOrderId = (int) model.getValueAt(index, 0); // save selected order id
				
				order = DB_Main.db_RecordShop.getOrderByID(selectedOrderId); // get order data from sql by id
				
				// Set all java records from order to the records list from sql
				order.setRecordsListInOrder( DB_Main.db_RecordShop.getRecordsInOrderFromSqlByID( order.getOrderID() ) );
				
				// Set record description
				orderDescriptionTextArea.setText( order.toString() );
				
			}
		});
		
		
		// headers for the table
		String[] columns = new String[] {
				"ID", "Customer Name", "Phone Number",
				"Order Date","Total Price"};
		
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        orderListTable.setModel(model);
        orderListTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
        orderListTable.setRowHeight(22);
        
        // fill the table with data
        for (Order order : DB_Main.db_RecordShop.getOrdersListFromSQL() ) {
        	
        	Object[] row = new Object[5];
        	
        	row[0] = order.getOrderID();
        	row[1] = DB_Main.db_RecordShop.getOrderByID(order.getOrderID()).getCustomer().getfullName();
			row[2] = DB_Main.db_RecordShop.getOrderByID(order.getOrderID()).getCustomer().getFullPhoneNumber();
			row[3] = order.getOrderDate();
			row[4] = order.getTotalPrice2();
			
			model.addRow(row);
			
			//center table row font
			centerAllTableRows();
			
			// re-size table column width according to the context
			resizeColumnWidth(orderListTable);
			
        	
        }
		
		orderListScrollPane.setViewportView(orderListTable);
		
		JLabel packageLabel1 = new JLabel("");
		//Image package Icon
		Image package1 = new ImageIcon(this.getClass().getResource("/package.png")).getImage();
		packageLabel1.setIcon(new ImageIcon(package1));
		packageLabel1.setBounds(18, 338, 134, 114);
		orderListFrame.getContentPane().add(packageLabel1);
		
		JLabel packageLabel2 = new JLabel("");
		//Image package Icon
		Image package2 = new ImageIcon(this.getClass().getResource("/package.png")).getImage();
		packageLabel2.setIcon(new ImageIcon(package2));
		packageLabel2.setBounds(584, 338, 134, 114);
		orderListFrame.getContentPane().add(packageLabel2);
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AdminHomePage adminHomePage = new AdminHomePage();
				adminHomePage.getFrame().setVisible(true);
				orderListFrame.dispose();
				
			}
		});
	}
	
	//get Frame
	public JFrame getFrame() {
		return orderListFrame;
	}
	
	public void tableDesign() {
		
		//Table Design
		orderListTable.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 14)); // Table Colums Header Font and Size
		orderListTable.getTableHeader().setBackground(new Color(11,57,206)); // Table Colums Header Background color
		orderListTable.getTableHeader().setForeground(Color.white); // Table Colums Header font color 
		orderListTable.setFont(new Font("Tahoma", Font.PLAIN, 18)); // Table row font
		orderListTable.setBackground(new Color(212,212,212)); // Table row background color
		
	}
	
	// center table row font and color AlternateRows
	public void centerAllTableRows() {
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		 for(int x=0; x<orderListTable.getColumnCount(); x++){
			 orderListTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
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
