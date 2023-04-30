package module;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import misc.Constant;
import misc.PharmacyDb;
import misc.Validation;

public class SearchMedicine extends JFrame implements ItemListener,ActionListener,KeyListener,MouseListener{
	String comboBoxValue;
	String fieldValue="";
	JPanel headingContainer;
	JLabel heading;
	JPanel searchByContainer;
	JPanel container;
	JComboBox searchByComboBox;
	String list[] = { "Select","DrugName", "DrugBarcode" };
	JLabel searchByLabel;
	JTextField searchField;
	JPanel tableContainer;
	DefaultTableModel tableModel;
	JTable table;
	Container c;
	Connection connection;
	JButton searchButton;
	JButton deleteButton;
	String barcode;
	int row;
	
	public SearchMedicine(String hName) {

		
		
		connection=PharmacyDb.getConnection();
		
		/* *************adding Coloumn to Table*************** */
		tableModel=new DefaultTableModel();
		getTableColoumnModel();
		
		
		/* ****************creating object********** */
		heading = new JLabel(hName);
		searchByLabel=new JLabel("Search By");
		searchByComboBox=new JComboBox(list);
		searchField=new JTextField();
		headingContainer = new JPanel();
		searchByContainer = new JPanel();
		tableContainer =new JPanel();
		 table=new JTable(tableModel);
		container = new JPanel();
		Dimension d=new Dimension();
		deleteButton=new JButton("Delete");
		searchButton=new JButton("Search");
		deleteButton.setVisible(false);
		deleteButton.setEnabled(true);
		table.addMouseListener(this);
		
		/* *************Heading Container   & Heading*************** */
		heading.setFont(Constant.HEADING_FONT);
		heading.setForeground(Color.WHITE);
		headingContainer.setSize(1100, 50);
		headingContainer.setLocation(0, 0);
		headingContainer.setBackground(Constant.BACKGROUND_HEADING_LIGHT);
		headingContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		headingContainer.add(heading, new BorderLayout().NORTH);
		
		
		
		/* *************Container *************** */
		
		
		container.setSize(150, 80);
		
		
		
		
		

	/* *************Search By Container & Label*************** */
		searchByLabel.setFont(Constant.HEADING2_FONT);
		searchByLabel.setForeground(Color.WHITE);
		searchByComboBox.setSize(150, 80);
		searchByContainer.setBounds(0, 51, 1100, 40);
		searchByComboBox.addItemListener(this);
		searchButton.addActionListener(this);
		searchButton.setEnabled(false);
		deleteButton.addActionListener(this);
		d.setSize(175, 20);
		searchField.setPreferredSize(d);
		searchField.setEnabled(false);
		searchField.addKeyListener(this);
		searchByContainer.setBackground(Constant.BACKGROUND_HEADING_DARK);
		
		searchByContainer.add(searchByLabel);
		searchByContainer.add(searchByComboBox);
		searchByContainer.add(searchField);
		searchByContainer.add(searchButton);
		searchByContainer.add(deleteButton);
		

		
		tableContainer.setBounds(10, 100, 1060, 350);
		tableContainer.setLayout(new BorderLayout());
		tableContainer.add(new JScrollPane(table));
		table.setBackground(Color.CYAN);
	
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1100, 500);
		c = getContentPane();
		c.setLayout(null);
		c.add(headingContainer);
		c.add(searchByContainer);
		c.add(tableContainer);
		addWindowListener(new Validation());
	
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		comboBoxValue=searchByComboBox.getSelectedItem().toString();
		
		if(comboBoxValue.equals("DrugName")|| comboBoxValue.equals("DrugBarcode")) {
			searchField.setEnabled(true);
		}
		
		if(comboBoxValue.equals("Select")) {
			searchField.setEnabled(false);
		}
		
	}

	private  void getData(PreparedStatement ps)  {

		try {
		ResultSet resultSet = ps.executeQuery();
		if(!resultSet.isBeforeFirst()) {
			JOptionPane.showMessageDialog(null,"No Record Found");
		}

		while (resultSet.next()) {
			String name = resultSet.getString("drugName");
			String type = resultSet.getString("drugType");
			String barCode = resultSet.getString("drugBarcode");
			String quantity = resultSet.getString("drugQuantity");
			String suplier_contact = resultSet.getString("supplierContact");
			String sale_price = resultSet.getString("drugSalePrice");
			String cost_price = resultSet.getString("drugCostPrice");
			Date mfg = resultSet.getDate("mfg");
			Date exp = resultSet.getDate("EXP");
			

			Object[] data = { name, type,  barCode, suplier_contact, sale_price, cost_price, mfg, exp,quantity };

			tableModel.addRow(data);

		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		fieldValue=searchField.getText();
		clearTable();

		
		if(   e.getSource()==searchButton && comboBoxValue.equals("DrugName")){
			String query="Select * from medicine where drugName like '%"+fieldValue+"%';";
			PreparedStatement ps;
		try {
			ps = connection.prepareStatement(query);
			getData(ps);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
	}
		
		if(  e.getSource()==searchButton && comboBoxValue.equals("DrugBarcode")){
			String query="Select * from medicine where drugBarcode like '%"+fieldValue+"%'";
			PreparedStatement ps;
			
		try {
			ps = connection.prepareStatement(query);
			getData(ps);
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
		
	}
		
		 if(e.getSource()==deleteButton) {
			 int input = JOptionPane.showConfirmDialog(null, "Are You Sure to Delete ?");
				if(input==0) {
			String query="delete from medicine where drugBarcode='"+barcode+"'";
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.execute();
				tableModel.removeRow(row);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	}

		
	/**
	 * Removes all the rows in the table
	 */
	public void clearTable() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.getDataVector().removeAllElements();
		revalidate();
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		String s=searchField.getText();
		if(s.length()>0) {
			searchButton.setEnabled(true);
		}
		if(s.length()<1) {
			searchButton.setEnabled(false);
		}
		
		
	}
	
	public  void  getTableColoumnModel() {
		
		tableModel.addColumn("Name");
		tableModel.addColumn("Type");
		tableModel.addColumn("Bar_Code");
		tableModel.addColumn("Supplier_Contact");
		tableModel.addColumn("Sale_Price");
		tableModel.addColumn("Cost_Price");
		tableModel.addColumn("Mfg.Date");
		tableModel.addColumn("Exp.Date");
		tableModel.addColumn("Quantity");

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		deleteButton.setEnabled(true);
		 row=table.getSelectedRow();
		 barcode=tableModel.getValueAt(row,2).toString();	
		 System.out.println(row);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	

}