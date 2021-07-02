package AdminGUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Classes.Employee;
import DataBase.DB_Main;
import Enum.City;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;

import javax.swing.SwingConstants;

public class SearchEmployeeScreen {

	private JFrame searchEmployeeFrame;
	private JTable searchEmployeeTable;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchEmployeeScreen window = new SearchEmployeeScreen();
					window.searchEmployeeFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SearchEmployeeScreen() {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		searchEmployeeFrame = new JFrame("Yuval Shai & Adi Hemo");
		searchEmployeeFrame.setBounds(100, 100, 1107, 492);
		searchEmployeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		searchEmployeeFrame.getContentPane().setLayout(null);
		
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		searchEmployeeFrame.setLocation(dim.width/2-searchEmployeeFrame.getSize().width/2, dim.height/2-searchEmployeeFrame.getSize().height/2);
		
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		searchEmployeeFrame.setIconImage(icon.getImage());
		
		JLabel searchEmployeeLabel = new JLabel("Search Employee");
		searchEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		searchEmployeeLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		searchEmployeeLabel.setBounds(22, 11, 1059, 63);
		searchEmployeeFrame.getContentPane().add(searchEmployeeLabel);
		
		JLabel searchEmployeeByCityLabel = new JLabel("Search Employee By City: ");
		searchEmployeeByCityLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		searchEmployeeByCityLabel.setBounds(22, 92, 234, 25);
		searchEmployeeFrame.getContentPane().add(searchEmployeeByCityLabel);
		
		JComboBox cityComboBox = new JComboBox();
		cityComboBox.setBounds(266, 88, 150, 31);
		searchEmployeeFrame.getContentPane().add(cityComboBox);
		cityComboBox.setModel(new DefaultComboBoxModel(City.values()));
		
		
		JScrollPane searchEmployeeScrollPane = new JScrollPane();
		searchEmployeeScrollPane.setBounds(22, 141, 1059, 238);
		searchEmployeeFrame.getContentPane().add(searchEmployeeScrollPane);
		
		searchEmployeeTable = new JTable();
		
		//lock table cell
		searchEmployeeTable.setDefaultEditor(Object.class, null);
		
        //Table Design
        tableDesign();
		
		// Headers for the table
		 String[] columns = new String[] {
		            "ID", "Employee Name", 
		            "Phone Number", "Email","Address", 
		            "Start Working Date" 
		        };
		 
	        model = new DefaultTableModel();
	        model.setColumnIdentifiers(columns);
	        searchEmployeeTable.setModel(model);
	        searchEmployeeTable.setFont(new Font("Tahoma", Font.PLAIN, 17));
	        searchEmployeeTable.setRowHeight(22);

		searchEmployeeScrollPane.setViewportView(searchEmployeeTable);
		
		JButton returnToMenuBTN = new JButton("Return To Menu");
		//Image search Icon
		Image img1 = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(img1));
		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AdminHomePage adminHomePage = new AdminHomePage();
				adminHomePage.getFrame().setVisible(true);
				searchEmployeeFrame.dispose();
				
			}
		});
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(489, 392, 169, 50);
		searchEmployeeFrame.getContentPane().add(returnToMenuBTN);
		
		JButton searchBTN = new JButton("Search!");
		//Image search Icon
		Image search = new ImageIcon(this.getClass().getResource("/search.png")).getImage();
		searchBTN.setIcon(new ImageIcon(search));
		searchBTN.setBackground(new Color(176, 224, 230));
		searchBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// If no employee exist from the selected city
				if( DB_Main.db_RecordShop.isEmployeeFromCityEmpty
						( cityComboBox.getSelectedItem().toString() ) ) {
					
					// Reset Table
					// get table tow count
					int rowCount = model.getRowCount();
					
					for (int i = rowCount - 1; i >= 0; i--) {// Remove table rows one by one from the end of the table
					    model.removeRow(i);
					}
					
					// Show Error message - no employee from that city
					JOptionPane.showMessageDialog
					(searchEmployeeFrame,"There Is No Employee From That City","Error",JOptionPane.ERROR_MESSAGE);
				}
				else { // There is an employee to show from the selected city

				// get table tow count
				int rowCount = model.getRowCount();
				
				// Remove table rows one by one from the end of the table
				for (int i = rowCount - 1; i >= 0; i--) {
				    model.removeRow(i);
				}

					// Search employee from selected city
					for(Employee employee : DB_Main.db_RecordShop.getEmployeeFromSqlByCity
							( cityComboBox.getSelectedItem().toString()) ) {

						// print employee data to table
						Object[] row = new Object[6];
						
						row[0] = employee.getId();
						row[1] = employee.getfullName();
						row[2] = employee.getFullPhoneNumber();
						row[3] = employee.getEmail();
						row[4] = employee.getAddress().toString();
						row[5] = employee.getStartDate();
						
						model.addRow(row);
						
						// Center all table rows
						centerAllTableRows();
						
						// re-size table column width according to the context
						resizeColumnWidth(searchEmployeeTable);
						
			        }
				}
				
			}
		});
		searchBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchBTN.setBounds(441, 85, 128, 40);
		searchEmployeeFrame.getContentPane().add(searchBTN);
		
		JLabel search2Label = new JLabel("");
		//Image search Icon
		Image img = new ImageIcon(this.getClass().getResource("/search2.png")).getImage();
		search2Label.setIcon(new ImageIcon(img));
		search2Label.setBounds(936, 29, 84, 88);
		searchEmployeeFrame.getContentPane().add(search2Label);
	}
	
	//get Frame
	public JFrame getFrame() {
		return searchEmployeeFrame;
	}
	
	public void tableDesign() {
		
		//Table Design
		searchEmployeeTable.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 14)); // Table Colums Header Font and Size
		searchEmployeeTable.getTableHeader().setBackground(new Color(11,57,206)); // Table Colums Header Background color
		searchEmployeeTable.getTableHeader().setForeground(Color.white); // Table Colums Header font color 
		searchEmployeeTable.setFont(new Font("Tahoma", Font.PLAIN, 18)); // Table row font
		searchEmployeeTable.setBackground(new Color(212,212,212)); // Table row background color
		
	}
	
	// center table row font and color AlternateRows
	public void centerAllTableRows() {
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		 for(int x=0; x<searchEmployeeTable.getColumnCount(); x++){
			 searchEmployeeTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
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
