package AdminGUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Classes.Employee;

import DataBase.DB_Main;

import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class EmployeeListScreen {

	private JFrame EMPframe;
	private JTable employeeListTable;
	private JScrollPane employeeListScrollPane;
	private JButton removeEmployeeBTN;
	private DefaultTableModel model;
	private Employee employee;
	private int index = -5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeListScreen window = new EmployeeListScreen();
					window.EMPframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeeListScreen() {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		EMPframe = new JFrame("Yuval Shai & Adi Hemo");
		EMPframe.setBounds(100, 100, 1107, 417);
		EMPframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EMPframe.getContentPane().setLayout(null);
		
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		EMPframe.setLocation(dim.width/2-EMPframe.getSize().width/2, dim.height/2-EMPframe.getSize().height/2);
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		EMPframe.setIconImage(icon.getImage());
		
		JLabel employeeListLabel = new JLabel("Employee List");
		employeeListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		employeeListLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		employeeListLabel.setBounds(10, 11, 1071, 45);
		EMPframe.getContentPane().add(employeeListLabel);
		
		employeeListScrollPane = new JScrollPane();
		employeeListScrollPane.setBounds(10, 67, 1071, 229);
		EMPframe.getContentPane().add(employeeListScrollPane);
		
		employeeListTable = new JTable();
		
		//lock table cell
		employeeListTable.setDefaultEditor(Object.class, null);
		
		//Table design
		tableDesign();
		
		employeeListTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				index = employeeListTable.getSelectedRow();
				String employeeEmail = (String) model.getValueAt(index, 3);
				employee = DB_Main.db_RecordShop.getEmployeeByEmail(employeeEmail);
			}
		});
		
		// Headers for the table
		 String[] columns = new String[] {
		            "ID", "Employee Name", 
		            "Phone Number", "Email","Address", 
		            "Start Working Date" 
		        };
		 
	        model = new DefaultTableModel();
	        model.setColumnIdentifiers(columns);
	        employeeListTable.setModel(model);
	        employeeListTable.setFont(new Font("Tahoma", Font.PLAIN, 17));
	        employeeListTable.setRowHeight(22);
	        
	        // fill the table with data
			for(Employee employee : DB_Main.db_RecordShop.getEmployeeListFromSql()) {
				
				Object[] row = new Object[6];
				
				row[0] = employee.getId();
				row[1] = employee.getfullName();
				row[2] = employee.getFullPhoneNumber();
				row[3] = employee.getEmail();
				row[4] = employee.getAddress().toString();
				row[5] = employee.getStartDate();
				
				model.addRow(row);
				
				//center table row font
				centerAllTableRows();
				
				// re-size table column width according to the context
				resizeColumnWidth(employeeListTable);
				
	        }
			
			
			employeeListScrollPane.setViewportView(employeeListTable);
			
		JButton returnToMenuBTN = new JButton("Return To Menu");
		returnToMenuBTN.setForeground(new Color(0, 0, 0));
		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		//Image backToMenu Icon
		Image img2 = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(img2));
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(20, 307, 165, 60);
		EMPframe.getContentPane().add(returnToMenuBTN);
		
		removeEmployeeBTN = new JButton("Remove Employee");
		
		//Image remove Icon
		Image img = new ImageIcon(this.getClass().getResource("/removeBlack.png")).getImage();
		removeEmployeeBTN.setIcon(new ImageIcon(img));
		
		removeEmployeeBTN.setForeground(new Color(0, 0, 0));
		removeEmployeeBTN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				// Check if employee was selected from the table
				if(index !=-5) {
					
					DB_Main.db_RecordShop.removeEmployeeFromSqlByID(employee.getId()); //remove selected employee
					
					index = -5; // reset index
					
					int rowCount = model.getRowCount();
					
					// Remove table rows one by one from the end of the table
					for (int i = rowCount - 1; i >= 0; i--) {
					    model.removeRow(i);
					}
					
					// Create the new table and fill it with data
					for(Employee employee : DB_Main.db_RecordShop.getEmployeeListFromSql() ) {
						
						Object[] row = new Object[6];
						
						row[0] = employee.getId();
						row[1] = employee.getfullName();
						row[2] = employee.getFullPhoneNumber();
						row[3] = employee.getEmail();
						row[4] = employee.getAddress().toString();
						row[5] = employee.getStartDate();
						
						model.addRow(row);
						
						//center table row font
						centerAllTableRows();
						
						// re-size table column width according to the context
						resizeColumnWidth(employeeListTable);
						
			        }
					
					// Show delete message
					JOptionPane.showMessageDialog(EMPframe, "Employee " + employee.getfullName() + " Was Deleted Successfully!!");
				
				}
				else
					// Show error message
					JOptionPane.showMessageDialog(EMPframe, "No Employee Was Selected","Error",JOptionPane.ERROR_MESSAGE);

			}
	
		});
		removeEmployeeBTN.setBackground(new Color(240, 128, 128));
		removeEmployeeBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		removeEmployeeBTN.setBounds(472, 307, 172, 60);
		EMPframe.getContentPane().add(removeEmployeeBTN);
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AdminHomePage adminHomePage = new AdminHomePage();
				adminHomePage.getFrame().setVisible(true);
				EMPframe.dispose();	
			}
		});
	}
	
	//get Frame
	public JFrame getFrame() {
		return this.EMPframe;
	}
	
	public void tableDesign() {
		
		//Table Design
		employeeListTable.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 14)); // Table Colums Header Font and Size
		employeeListTable.getTableHeader().setBackground(new Color(11,57,206)); // Table Colums Header Background color
		employeeListTable.getTableHeader().setForeground(Color.white); // Table Colums Header font color 
		employeeListTable.setFont(new Font("Tahoma", Font.PLAIN, 18)); // Table row font
		employeeListTable.setBackground(new Color(212,212,212)); // Table row background color
		
	}
	
	// center table row font and color AlternateRows
	public void centerAllTableRows() {
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		 for(int x=0; x<employeeListTable.getColumnCount(); x++){
			 employeeListTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
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
