package EmployeeGUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Classes.Employee;

import Classes.Record;
import Classes.Song;
import DataBase.DB_Main;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddNewRecordScreen {

	private JFrame addNewRecordframe;
	private JTextField recodNameTextField;
	private JTextField releaseYearTextField;
	private JTextField artistNameTextField;
	private JTextField priceTextField;
	private JTable songsTable;
	private Employee employee;
	private ArrayList<Song> songs = new ArrayList<>();
	private int songIndex = -5; 
	private DefaultTableModel model;
	private int index;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddNewRecordScreen window = new AddNewRecordScreen("TEST");
					window.addNewRecordframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddNewRecordScreen(String employeeEmail) {

		this.employee = 
				DB_Main.db_RecordShop.
				getEmployeeByEmail(employeeEmail);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addNewRecordframe = new JFrame("Yuval Shai & Adi Hemo");
		addNewRecordframe.setBounds(100, 100, 762, 583);
		addNewRecordframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addNewRecordframe.getContentPane().setLayout(null);
		//Center The Frame to Monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		addNewRecordframe.setLocation(dim.width/2-addNewRecordframe.getSize().width/2, dim.height/2-addNewRecordframe.getSize().height/2);
		
		
		// Set Icon to the frame
		ImageIcon icon = new ImageIcon("record.png");
		addNewRecordframe.setIconImage(icon.getImage());
		
		JLabel addNewRecordToShopLabel = new JLabel("Add New Record To Shop");
		addNewRecordToShopLabel.setFont(new Font("Tahoma", Font.BOLD, 33));
		addNewRecordToShopLabel.setBounds(160, 11, 431, 59);
		addNewRecordframe.getContentPane().add(addNewRecordToShopLabel);
		
		JLabel addSongToTheRecodLabel = new JLabel("Add Song To The Record");
		addSongToTheRecodLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		addSongToTheRecodLabel.setBounds(10, 104, 262, 28);
		addNewRecordframe.getContentPane().add(addSongToTheRecodLabel);
		
		JButton addSongBTN = new JButton("Add Song");
		//Image plus Icon
		Image plus = new ImageIcon(this.getClass().getResource("/plus2.png")).getImage();
		addSongBTN.setIcon(new ImageIcon(plus));
		addSongBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Song song = addSong();

				if(song !=null) {
					
					//add song to sql
					DB_Main.db_RecordShop.insertSongIntoSql(song);
	
					songs.add(song);
					
					//Make Table with data
					// headers for the table
					String[] columns = new String[] { "ID", "Name"};
					
					model = new DefaultTableModel();
					model.setColumnIdentifiers(columns);
					songsTable.setModel(model);
					
					if(!songs.isEmpty()) {
						index = model.getRowCount();
						
						//Remove rows one by one from the end of the table
						for (int i = index - 1; i >= 0; i--) {
							model.removeRow(i);
						}
					}
					
					for(int i = 0; i < songs.size(); i++) {

						Object[] row = new Object[2];

						row[0] = i + 1;
						row[1] = songs.get(i).getSongName();

						model.addRow(row);	

						//center table row font
						centerAllTableRows();
						
						// re-size table column width according to the context
						resizeColumnWidth(songsTable);
					}  

				}
				
				
			}
		});
		addSongBTN.setForeground(new Color(0, 0, 0));
		addSongBTN.setBackground(new Color(220, 220, 220));
		addSongBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		addSongBTN.setBounds(279, 95, 121, 50);
		addNewRecordframe.getContentPane().add(addSongBTN);
		
		JLabel recodNameLabel = new JLabel("Record Name:");
		recodNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		recodNameLabel.setBounds(10, 176, 170, 28);
		addNewRecordframe.getContentPane().add(recodNameLabel);
		
		recodNameTextField = new JTextField();
		recodNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		recodNameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				
				if(checkOnlyLettersAndDigits(recodNameTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    recodNameTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					recodNameTextField.setBorder(border);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(checkOnlyLettersAndDigits(recodNameTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    recodNameTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					recodNameTextField.setBorder(border);
				}
			}
		});
		recodNameTextField.setBounds(167, 176, 203, 29);
		addNewRecordframe.getContentPane().add(recodNameTextField);
		recodNameTextField.setColumns(10);
		
		JLabel releaseTearLabel = new JLabel("Release Year: ");
		releaseTearLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		releaseTearLabel.setBounds(10, 229, 152, 28);
		addNewRecordframe.getContentPane().add(releaseTearLabel);
		
		releaseYearTextField = new JTextField();
		releaseYearTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		releaseYearTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(checkOnlyDigits(releaseYearTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    releaseYearTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					releaseYearTextField.setBorder(border);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(checkOnlyDigits(releaseYearTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    releaseYearTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					releaseYearTextField.setBorder(border);
				}
			}
		});
		releaseYearTextField.setColumns(10);
		releaseYearTextField.setBounds(167, 228, 86, 29);
		addNewRecordframe.getContentPane().add(releaseYearTextField);
		
		JLabel artistNameLabel = new JLabel("Artist Name: ");
		artistNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		artistNameLabel.setBounds(10, 281, 139, 28);
		addNewRecordframe.getContentPane().add(artistNameLabel);
		
		artistNameTextField = new JTextField();
		artistNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		artistNameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(checkOnlyLettersAndDigits(artistNameTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    artistNameTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					artistNameTextField.setBorder(border);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(checkOnlyLettersAndDigits(artistNameTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    artistNameTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					artistNameTextField.setBorder(border);
				}
			}
		});
		artistNameTextField.setColumns(10);
		artistNameTextField.setBounds(167, 281, 125, 29);
		addNewRecordframe.getContentPane().add(artistNameTextField);
		
		JLabel priceLabel = new JLabel("Price: ");
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		priceLabel.setBounds(10, 340, 69, 28);
		addNewRecordframe.getContentPane().add(priceLabel);
		
		priceTextField = new JTextField();
		priceTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		priceTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(checkPrice(priceTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    priceTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					priceTextField.setBorder(border);
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(checkPrice(priceTextField.getText())){
				    Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
				    priceTextField.setBorder(border);
				}
				else {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					priceTextField.setBorder(border);
				}
			}
		});
		priceTextField.setColumns(10);
		priceTextField.setBounds(167, 339, 86, 29);
		addNewRecordframe.getContentPane().add(priceTextField);
		
		JScrollPane songsScrollPane = new JScrollPane();
		songsScrollPane.setBounds(424, 151, 300, 259);
		addNewRecordframe.getContentPane().add(songsScrollPane);
		
		songsTable = new JTable();
		
		//Table design
		tableDesign();
		
		songsTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		songsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				songIndex = songsTable.getSelectedRow();
				
			}
		});
		
		//lock table cell
		songsTable.setDefaultEditor(Object.class, null);
		
		songsScrollPane.setViewportView(songsTable);
		
		JLabel songsLabel = new JLabel("Songs Added To Record:");
		songsLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		songsLabel.setBounds(425, 116, 250, 28);
		addNewRecordframe.getContentPane().add(songsLabel);
		
		JButton removeSongBTN = new JButton("Remove Song");
		//Image removeSong Icon
		Image removeSong = new ImageIcon(this.getClass().getResource("/removeSong.png")).getImage();
		removeSongBTN.setIcon(new ImageIcon(removeSong));
		removeSongBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// if song was selected
				if(songIndex != -5) {
					
					//save real song ID for later use to remove from sql
					int SelectedSongIdForSQL = songs.get(songIndex).getSongID();
					
					DB_Main.db_RecordShop.deleteSongFromSqlByID(SelectedSongIdForSQL);
					
					songs.remove(songIndex);
					
					// Create new table after delete
					
					// headers for the table
					String[] columns = new String[] { "ID", "Name"};
					
					model = new DefaultTableModel();
					model.setColumnIdentifiers(columns);
					songsTable.setModel(model);
					
					if(!songs.isEmpty()) {
						index = model.getRowCount();
						
						//Remove rows one by one from the end of the table
						for (int i = index - 1; i >= 0; i--) {
							model.removeRow(i);
						}
					}
					
					for(int i = 0; i < songs.size(); i++) {

						Object[] row = new Object[2];

						row[0] = i + 1;
						row[1] = songs.get(i).getSongName();

						model.addRow(row);
						
						//center table row font
						centerAllTableRows();
						
						// re-size table column width according to the context
						resizeColumnWidth(songsTable);
					} 
					
					songIndex = -5; // Reset song index
				}
				else
					JOptionPane.showMessageDialog(addNewRecordframe,"No Song Was Selected","Error",JOptionPane.ERROR_MESSAGE);
				
				
			}
		});
		removeSongBTN.setForeground(new Color(255, 255, 255));
		removeSongBTN.setBackground(new Color(222, 36, 73));
		removeSongBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		removeSongBTN.setBounds(522, 422, 143, 50);
		addNewRecordframe.getContentPane().add(removeSongBTN);
		
		JButton submitBTN = new JButton("Submit!");
		//Image ok Icon
		Image ok = new ImageIcon(this.getClass().getResource("/okIcon.png")).getImage();
		submitBTN.setIcon(new ImageIcon(ok));
		submitBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if( !songs.isEmpty() && 
						checkOnlyLettersAndDigits(recodNameTextField.getText())
						&& checkOnlyDigits(releaseYearTextField.getText()) 
						&& checkOnlyLettersAndDigits(artistNameTextField.getText()) 
						&& checkPrice(priceTextField.getText()) ) {
					
					
					Record newRecord = new Record
							(DB_Main.db_RecordShop.getMaxRecordID()+1,recodNameTextField.getText(), 
									Integer.parseInt(releaseYearTextField.getText()), 
									artistNameTextField.getText(), 
									Double.parseDouble(priceTextField.getText()),
									songs);
					
					if(!DB_Main.db_RecordShop.isRecordInSql(newRecord)) {
						
						// insert record to sql 
						DB_Main.db_RecordShop.insertRecordIntoSql(newRecord);
						
						// insert songs to record sql
						for(Song song : songs) {
							DB_Main.db_RecordShop.insertSongsIntoRecordSql
							(newRecord.getRecordID(),song.getSongID());
						}
						
						JOptionPane.showMessageDialog
						(addNewRecordframe,"Record "+newRecord.getRecordName()+" Added Successfully!");
						
						AddNewRecordScreen addNewRecordScreen2 = new AddNewRecordScreen(employee.getEmail());
						addNewRecordScreen2.getFrame().setVisible(true);
						addNewRecordframe.dispose();
					}
					else
						JOptionPane.showMessageDialog
						(addNewRecordframe,"Record "+
								newRecord.getRecordName()+ " Alredy Exist!","Error",JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog
					  (addNewRecordframe,"One Or More Of The Details You Entered Are Invalid!"
							  ,"Error",JOptionPane.ERROR_MESSAGE);
				
			}
		});
		submitBTN.setForeground(new Color(0, 0, 0));
		submitBTN.setBackground(new Color(220, 220, 220));
		submitBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		submitBTN.setBounds(281, 483, 143, 50);
		addNewRecordframe.getContentPane().add(submitBTN);
		
		JButton returnToMenuBTN = new JButton("Return To Menu");
		//Image back Icon
		Image img = new ImageIcon(this.getClass().getResource("/backToMenu.png")).getImage();
		returnToMenuBTN.setIcon(new ImageIcon(img));
		returnToMenuBTN.setBackground(new Color(220, 220, 220));
		returnToMenuBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// delete all songs with no record in sql
				DB_Main.db_RecordShop.deleteAllSongsWithNoRecordSql();
				
				EmployeeHomePage employeeHomePage = new EmployeeHomePage(employee.getEmail());
				employeeHomePage.getFrame().setVisible(true);
				addNewRecordframe.dispose();
				
			}
		});
		returnToMenuBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToMenuBTN.setBounds(10, 422, 170, 50);
		addNewRecordframe.getContentPane().add(returnToMenuBTN);
		
		JButton returnToShopBTN = new JButton("Return To Shop Records");
		//Image record Icon
		Image record = new ImageIcon(this.getClass().getResource("/record2.png")).getImage();
		returnToShopBTN.setIcon(new ImageIcon(record));
		returnToShopBTN.setBackground(new Color(220, 220, 220));
		returnToShopBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// delete all songs with no record in sql
				DB_Main.db_RecordShop.deleteAllSongsWithNoRecordSql();
				
				RecordListScreen2 recordListScreen2 = new RecordListScreen2(employee.getEmail());
				recordListScreen2.getFrame().setVisible(true);
				addNewRecordframe.dispose();
				
			}
		});
		returnToShopBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
		returnToShopBTN.setBounds(10, 483, 203, 50);
		addNewRecordframe.getContentPane().add(returnToShopBTN);
		
		
		//Listener when the frame is closed from the "X" Button
		addNewRecordframe.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	
				// delete all songs with no record in sql
				DB_Main.db_RecordShop.deleteAllSongsWithNoRecordSql();
		        
		        System.exit(0);
		    }
		});
		
		
	}

	
	// get Frame
	public JFrame getFrame() {
		return addNewRecordframe;
	}
	
	
	// Add Song Panel
	public Song addSong() {
		
		JPanel Panel = new JPanel(new GridLayout(2,1));
		JLabel songName = new JLabel("Song Name: ");
		JTextField name = new JTextField(5); 
		Panel.add(songName);
		Panel.add(name);
		
		int userChoice = JOptionPane.showConfirmDialog
				(null, Panel, "Add Song To Record", JOptionPane.OK_CANCEL_OPTION);
		
		// user clicked ok button
		if(userChoice == JOptionPane.OK_OPTION) {
			
			String stringName = name.getText();
			
			// song name must be 3 or more characters
			if(stringName.length() <= 2) {
				
				JOptionPane.showMessageDialog
				  (addNewRecordframe,"Song Name Must Be 3 or more characters"
						  ,"Error",JOptionPane.ERROR_MESSAGE);
				return null;
				
			}else {

				// Create song
				Song songToReturn = new Song(DB_Main.db_RecordShop.getMaxSongID()+1,stringName);
				return songToReturn;
			}
		}
		else
			return null;
	}
	

	
	// RGX check Only Letters And Digits
	boolean checkOnlyLettersAndDigits(String input) {

		String regex = "[a-zA-Z0-9]{3,}";
		if(!input.matches(regex)) {
			return false;			
		}
		
		return true;
	}
	
	// RGX check Only Digits
	boolean checkOnlyDigits(String input) {

		String regex = "^(1[9]|[2][0])[0-9][0-9]{1,5}";
		if(!input.matches(regex)) {
			return false;			
		}
		
		return true;
	}
	
	// RGX check Price - double
	boolean checkPrice(String input) {
		String regex = "(\\-?\\d*\\.?\\d{1})";
		if(!input.matches(regex)) {
			return false;			
		}
		
		return true;
	}
	
	
	public void tableDesign() {
		
		//Table Design
		songsTable.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 14)); // Table Colums Header Font and Size
		songsTable.getTableHeader().setBackground(new Color(11,57,206)); // Table Colums Header Background color
		songsTable.getTableHeader().setForeground(Color.white); // Table Colums Header font color 
		songsTable.setFont(new Font("Tahoma", Font.PLAIN, 18)); // Table row font
		songsTable.setBackground(new Color(212,212,212)); // Table row background color
		
	}
	
	// center table row font and color AlternateRows
	public void centerAllTableRows() {
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		 for(int x=0; x<songsTable.getColumnCount(); x++){
			 songsTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
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