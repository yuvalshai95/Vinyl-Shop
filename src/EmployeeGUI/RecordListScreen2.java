package EmployeeGUI;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Classes.Employee;

import Classes.Record;
import DataBase.DB_Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.SwingConstants;

public class RecordListScreen2 {

	private JFrame recordsListFrame2;
	private JTable table;
	private JTextArea recordDescriptionField;
	private Employee employee;
	private Record record = null;
	private DefaultTableModel model;
	private int index = -5;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordListScreen2 window = new RecordListScreen2("TEST");
					window.recordsListFrame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RecordListScreen2(String employeeEmail) {

		this.employee = 
				DB_Main.db_RecordShop.getEmployeeByEmail(employeeEmail);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		recordsListFrame2 = new JFrame("Yuval Shai & Adi Hemo");
		recordsListFrame2.setBounds(100, 100, 780, 646);
		recordsListFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		recordsListFrame2.getContentPane().setLayout(null);
		
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		recordsListFrame2.setLocation(dim.width/2-recordsListFrame2.getSize().width/2, dim.height/2-recordsListFrame2.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		recordsListFrame2.setIconImage(icon.getImage());
		
		JLabel recordsInShopLabel = new JLabel("Records In Shop");
		recordsInShopLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recordsInShopLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		recordsInShopLabel.setBounds(10, 11, 744, 50);
		recordsListFrame2.getContentPane().add(recordsInShopLabel);
		
		JButton returnToMenuBTN = new JButton("Return to Menu");
		//Image backToMenu Icon
		Image backToMenu = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(backToMenu));
		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(10, 534, 157, 50);
		recordsListFrame2.getContentPane().add(returnToMenuBTN);
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EmployeeHomePage employeeHomePage = new EmployeeHomePage(employee.getEmail());
				employeeHomePage.getFrame().setVisible(true);
				recordsListFrame2.dispose();
				
			}
		});
		
		JScrollPane recodsInShopScrollPane = new JScrollPane();
		recodsInShopScrollPane.setBounds(10, 72, 744, 186);
		recordsListFrame2.getContentPane().add(recodsInShopScrollPane);
		
		table = new JTable();
		
		//Table design
		tableDesign();
		
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		//lock table cell
		table.setDefaultEditor(Object.class, null);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				index = table.getSelectedRow();
				int selectedRecordId = (int) model.getValueAt(index, 0);
				
				record = DB_Main.db_RecordShop.getRecordByID(selectedRecordId);
				
				recordDescriptionField.setText
				(record.toString());
			}
		});
		
		// headers for the table
        String[] columns = new String[] {
            "ID", "Name", "Release Year", "Artist", "Price" 
        };
        
        this.model = new DefaultTableModel();
        this.model.setColumnIdentifiers(columns);
        this.table.setModel(model);
        
        // fill the table with data
		for(Record record : DB_Main.db_RecordShop.getRecordsListFromSQL() ) {
			
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
			resizeColumnWidth(table);
		}
		recodsInShopScrollPane.setViewportView(table);
		
		JScrollPane descriptionScrollPane = new JScrollPane();
		descriptionScrollPane.setBounds(209, 340, 377, 149);
		recordsListFrame2.getContentPane().add(descriptionScrollPane);
		
		recordDescriptionField = new JTextArea();
		recordDescriptionField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		
		//lock description field
		recordDescriptionField.setEditable(false);
		
		descriptionScrollPane.setViewportView(recordDescriptionField);
		
		JLabel recordDescriptionLabel = new JLabel("Selected Record Description");
		recordDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recordDescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		recordDescriptionLabel.setBounds(10, 269, 744, 61);
		recordsListFrame2.getContentPane().add(recordDescriptionLabel);
		
		JButton addNewRecordBTN = new JButton("Add New Record");
		//Image plus Icon
		Image plus = new ImageIcon(this.getClass().getResource("/plus2.png")).getImage();
		addNewRecordBTN.setIcon(new ImageIcon(plus));
		addNewRecordBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AddNewRecordScreen addNewRecordScreen = new AddNewRecordScreen(employee.getEmail());
				addNewRecordScreen.getFrame().setVisible(true);
				recordsListFrame2.dispose();
				
			}
		});
		addNewRecordBTN.setBackground(new Color(220, 220, 220));
		addNewRecordBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		addNewRecordBTN.setBounds(586, 534, 157, 50);
		recordsListFrame2.getContentPane().add(addNewRecordBTN);
		
		JLabel recordLabel1 = new JLabel("");
		//Image record Icon
		Image img1 = new ImageIcon(this.getClass().getResource("/record.png")).getImage();
		recordLabel1.setIcon(new ImageIcon(img1));
		recordLabel1.setBounds(44, 340, 140, 149);
		recordsListFrame2.getContentPane().add(recordLabel1);
		
		JLabel recordLabel2 = new JLabel("");
		//Image record Icon
		Image img2 = new ImageIcon(this.getClass().getResource("/record.png")).getImage();
		recordLabel2.setIcon(new ImageIcon(img2));
		recordLabel2.setBounds(621, 340, 122, 133);
		recordsListFrame2.getContentPane().add(recordLabel2);
	}
	
	//get Frame
	public JFrame getFrame() {
		return recordsListFrame2;
	}
	

	public void tableDesign() {
		
		//Table Design
		table.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 14)); // Table Colums Header Font and Size
		table.getTableHeader().setBackground(new Color(11,57,206)); // Table Colums Header Background color
		table.getTableHeader().setForeground(Color.white); // Table Colums Header font color 
		table.setFont(new Font("Tahoma", Font.PLAIN, 18)); // Table row font
		table.setBackground(new Color(212,212,212)); // Table row background color
		
	}
	
	// center table row font and color AlternateRows
	public void centerAllTableRows() {
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		 for(int x=0; x<table.getColumnCount(); x++){
			 table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
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