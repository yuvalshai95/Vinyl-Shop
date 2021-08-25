package AdminGUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Classes.Record;
import DataBase.DB_Main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class RecordListScreen {

	private JFrame recordsListFrame;
	private JTable table;
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
					RecordListScreen window = new RecordListScreen();
					window.recordsListFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RecordListScreen() {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		recordsListFrame = new JFrame("Yuval Shai & Adi Hemo");
		recordsListFrame.setBounds(100, 100, 780, 646);
		recordsListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		recordsListFrame.getContentPane().setLayout(null);
		
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		recordsListFrame.setLocation(dim.width/2-recordsListFrame.getSize().width/2, dim.height/2-recordsListFrame.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		recordsListFrame.setIconImage(icon.getImage());
		
		JLabel recordsInShopLabel = new JLabel("Records In Shop");
		recordsInShopLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recordsInShopLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		recordsInShopLabel.setBounds(10, 11, 733, 50);
		recordsListFrame.getContentPane().add(recordsInShopLabel);
		
		JButton returnToMenuBTN = new JButton("Return to Menu");
		//Image backToMenu Icon
		Image img3 = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(img3));
		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(326, 546, 169, 50);
		recordsListFrame.getContentPane().add(returnToMenuBTN);
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AdminHomePage adminHomePage = new AdminHomePage();
				adminHomePage.getFrame().setVisible(true);
				recordsListFrame.dispose();
				
			}
		});
		
		JScrollPane recodsInShopScrollPane = new JScrollPane();
		recodsInShopScrollPane.setBounds(10, 72, 744, 216);
		recordsListFrame.getContentPane().add(recodsInShopScrollPane);
		
		table = new JTable();
		table.setBorder(new EmptyBorder(0, 0, 0, 0));
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		//lock table cell
		table.setDefaultEditor(Object.class, null);
		
		//Table Design
		tableDesign();
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				index = table.getSelectedRow(); //selected row on mouse click
				int selectedRecordId = (int) model.getValueAt(index, 0); // save selected record id
				
				record = DB_Main.db_RecordShop.getRecordByID(selectedRecordId); // get record data from sql by id
				
				recordDescriptionField.setText(record.songsToString());
				
				
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
		for(Record record :  DB_Main.db_RecordShop.getRecordsListFromSQL() ) {
			
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
		descriptionScrollPane.setBounds(209, 370, 377, 149);
		recordsListFrame.getContentPane().add(descriptionScrollPane);
		
		recordDescriptionField = new JTextArea();
		recordDescriptionField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		
		//lock description field
		recordDescriptionField.setEditable(false);
		
		descriptionScrollPane.setViewportView(recordDescriptionField);
		
		JLabel recordDescriptionLabel = new JLabel("Selected Record Description");
		recordDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recordDescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		recordDescriptionLabel.setBounds(10, 298, 733, 61);
		recordsListFrame.getContentPane().add(recordDescriptionLabel);
		
		JLabel recordIconLabel1 = new JLabel("");
		
		//Image record Icon
		Image img = new ImageIcon(this.getClass().getResource("/record.png")).getImage();
		recordIconLabel1.setIcon(new ImageIcon(img));
		
		recordIconLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		recordIconLabel1.setBounds(44, 370, 122, 133);
		recordsListFrame.getContentPane().add(recordIconLabel1);
		
		JLabel recordIconLabel2 = new JLabel("");
		
		//Image record Icon
		Image img2 = new ImageIcon(this.getClass().getResource("/record.png")).getImage();
		recordIconLabel2.setIcon(new ImageIcon(img2));
		
		recordIconLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		recordIconLabel2.setBounds(621, 370, 122, 133);
		recordsListFrame.getContentPane().add(recordIconLabel2);
	}
	
	// get Frame
	public JFrame getFrame() {
		return recordsListFrame;
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
	
	public void tableDesign() {
		
		//Table Design
		table.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 14)); // Table Colums Header Font and Size
		table.getTableHeader().setBackground(new Color(11,57,206)); // Table Colums Header Background color
		table.getTableHeader().setForeground(Color.white); // Table Colums Header font color 
		table.setFont(new Font("Tahoma", Font.PLAIN, 17)); // Table row font
		table.setBackground(new Color(212,212,212)); // Table row background color
		
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
