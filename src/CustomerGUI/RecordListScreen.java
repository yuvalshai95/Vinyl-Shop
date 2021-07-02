package CustomerGUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Classes.Customer;

import Classes.Record;
import DataBase.DB_Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class RecordListScreen {

	private JFrame recordListFrame;
	private JTable recordsTable;
	private Customer customer;
	private Record record = null;
	private DefaultTableModel model;
	private int index;
	private JTextArea recordDescriptionField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordListScreen window = new RecordListScreen("TEST");
					window.recordListFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RecordListScreen(String customerEmail) {
		
		this.customer = 
				DB_Main.db_RecordShop.getCustomerByEmail(customerEmail);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		recordListFrame = new JFrame("Yuval Shai & Adi Hemo");
		recordListFrame.setBounds(100, 100, 780, 646);
		recordListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		recordListFrame.getContentPane().setLayout(null);
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		recordListFrame.setLocation(dim.width/2-recordListFrame.getSize().width/2,
				dim.height/2-recordListFrame.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		recordListFrame.setIconImage(icon.getImage());
		
		JLabel recordsInShopLabel = new JLabel("Records In Shop");
		recordsInShopLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recordsInShopLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		recordsInShopLabel.setBounds(10, 11, 728, 50);
		recordListFrame.getContentPane().add(recordsInShopLabel);
		
		JButton returnToMenuBTN = new JButton("Return to Menu");
		//Image back Icon
		Image img3 = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(img3));

		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(31, 545, 154, 50);
		recordListFrame.getContentPane().add(returnToMenuBTN);
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CustomerHomePage customerHomePage = new CustomerHomePage(customer.getEmail());
				customerHomePage.getFrame().setVisible(true);
				recordListFrame.dispose();
				
			}
		});
		
		JScrollPane recodsInShopScrollPane = new JScrollPane();
		recodsInShopScrollPane.setBounds(10, 72, 744, 216);
		recordListFrame.getContentPane().add(recodsInShopScrollPane);
		
		recordsTable = new JTable();
		
		//Table design
		tableDesign();
		
		recordsTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		//lock table cell
		recordsTable.setDefaultEditor(Object.class, null);
		
		recordsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				index = recordsTable.getSelectedRow();
				int selectedRecordId = (int) model.getValueAt(index, 0);
				
				record = DB_Main.db_RecordShop.getRecordByID(selectedRecordId);
				
				recordDescriptionField.setText(record.toString());
				
			}
		});
		
		// headers for the table
        String[] columns = new String[] {
            "ID", "Name", "Release Year", "Artist", "Price" 
        };
        
        this.model = new DefaultTableModel();
        this.model.setColumnIdentifiers(columns);
        this.recordsTable.setModel(model);
        
        // fill the table
		for(Record record :DB_Main.db_RecordShop.getRecordsListFromSQL()) {
			
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
			resizeColumnWidth(recordsTable);
		}
		

		recodsInShopScrollPane.setViewportView(recordsTable);
		
		JScrollPane descriptionScrollPane = new JScrollPane();
		descriptionScrollPane.setBounds(209, 370, 377, 149);
		recordListFrame.getContentPane().add(descriptionScrollPane);
		
		recordDescriptionField = new JTextArea();
		recordDescriptionField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		//lock description field
		recordDescriptionField.setEditable(false);
		
		
		descriptionScrollPane.setViewportView(recordDescriptionField);
		
		JLabel recordDescriptionLabel = new JLabel("Selected Record Description");
		recordDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recordDescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		recordDescriptionLabel.setBounds(10, 299, 744, 61);
		recordListFrame.getContentPane().add(recordDescriptionLabel);
		
		JButton addToCartBTN = new JButton("Add To Cart");
		//Image plus Icon
		Image plus = new ImageIcon(this.getClass().getResource("/plus.png")).getImage();
		addToCartBTN.setIcon(new ImageIcon(plus));

		addToCartBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(record !=null) {
					
					// Insert Record into customer Shopping cart sql 
					DB_Main.db_RecordShop.
					insertRecordIntoCustomerShoppingCart
					(record.getRecordID(), customer.getEmail());

					// show message record added to cart
					JOptionPane.showMessageDialog
					(recordListFrame, 
					"Record " + record.getRecordName() + " was added to your cart successfully!");
				}
				else
				JOptionPane.showMessageDialog
				(recordListFrame,"No Record Was Selected","Error",JOptionPane.ERROR_MESSAGE);
			}
		});
		addToCartBTN.setBackground(new Color(220, 220, 220));
		addToCartBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		addToCartBTN.setBounds(331, 545, 143, 50);
		recordListFrame.getContentPane().add(addToCartBTN);
		
		JButton myCartBTN = new JButton("My Cart");
		myCartBTN.setForeground(Color.WHITE);
		//Image search Icon
		Image img5 = new ImageIcon(this.getClass().getResource("/cart2.png")).getImage();
		myCartBTN.setIcon(new ImageIcon(img5));

		myCartBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Set customer records shopping cart from sql
				customer.setRecordsInShoppingCart(DB_Main.db_RecordShop.getRecordsInShoppingCartFromSql(customer.getEmail()));
				
				if(customer.getRecordsInShoppingCart().isEmpty()) {
					JOptionPane.showMessageDialog(recordListFrame, "Your Cart Is Empty!","Error",JOptionPane.ERROR_MESSAGE);
				}else {
					
					MyCartScreen myCartScreen = new MyCartScreen(customer.getEmail());
					myCartScreen.getFrame().setVisible(true);
					recordListFrame.dispose();
				}

			}
		});
		myCartBTN.setBackground(new Color(70, 130, 180));
		myCartBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		myCartBTN.setBounds(604, 545, 143, 50);
		recordListFrame.getContentPane().add(myCartBTN);
		
		JLabel recordLabel1 = new JLabel("");
		//Image record Icon
		Image img = new ImageIcon(this.getClass().getResource("/record.png")).getImage();
		recordLabel1.setIcon(new ImageIcon(img));
		recordLabel1.setBounds(44, 370, 122, 133);
		recordListFrame.getContentPane().add(recordLabel1);
		
		JLabel recordLabel2 = new JLabel("");
		//Image record Icon
		Image img2 = new ImageIcon(this.getClass().getResource("/record.png")).getImage();
		recordLabel2.setIcon(new ImageIcon(img));
		recordLabel2.setBounds(621, 370, 122, 133);
		recordListFrame.getContentPane().add(recordLabel2);
		

	}
	
	//get Frame
	public JFrame getFrame() {
		return recordListFrame;
	}
	
	public void tableDesign() {
		
		//Table Design
		recordsTable.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 14)); // Table Colums Header Font and Size
		recordsTable.getTableHeader().setBackground(new Color(11,57,206)); // Table Colums Header Background color
		recordsTable.getTableHeader().setForeground(Color.white); // Table Colums Header font color 
		recordsTable.setFont(new Font("Tahoma", Font.PLAIN, 18)); // Table row font
		recordsTable.setBackground(new Color(212,212,212)); // Table row background color
		
	}
	
	// center table row font and color AlternateRows
	public void centerAllTableRows() {
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		 for(int x=0; x<recordsTable.getColumnCount(); x++){
			 recordsTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
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
