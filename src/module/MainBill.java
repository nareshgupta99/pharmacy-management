package module;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import exceptions.DataInvalidException;
import misc.Constant;
import misc.PharmacyDb;
import misc.TempBill;
import misc.Validation;

public class MainBill extends JFrame implements ActionListener {
	String comboBoxValue;
	String fieldValue = "";
	JPanel headingContainer;
	JLabel heading;
	JTextField totalField;
	JPanel tableContainer;
	DefaultTableModel tableModel;
	JTable table;
	Container c;
	Connection connection;
	JButton genrate;
	JButton sale;
	double total=0.0;
	List<TempBill> list;

	public MainBill(List<TempBill> list) {
		
		this.list=list;
		connection = PharmacyDb.getConnection();

		/* *************adding Coloumn to Table*************** */
		tableModel = new DefaultTableModel();
		getTableColoumnModel();

		/* ****************creating object********** */
		heading = new JLabel("BILL DRUGS");
		sale=new JButton("Back to Sales");
		totalField = new JTextField();
		headingContainer = new JPanel();
		tableContainer = new JPanel();
		table = new JTable(tableModel);
		Dimension d = new Dimension();

		genrate = new JButton("Genrate");
		genrate.addActionListener(this);

		/* *************Heading Container & Heading*************** */
		heading.setFont(Constant.HEADING_FONT);
		heading.setForeground(Color.WHITE);
		headingContainer.setSize(1100, 50);
		headingContainer.setLocation(0, 0);
		headingContainer.setBackground(Constant.BACKGROUND_HEADING_LIGHT);
		headingContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		headingContainer.add(heading, new BorderLayout().NORTH);

		/* *************Container *************** */

		genrate.setBounds(700,390,120,40);
		sale.setBounds(500, 390, 180, 40);
		totalField.setBounds(20,380,140,60);
		totalField.setEditable(false);
		totalField.setBackground(Color.WHITE);
		/* *************Search By Container & Label*************** */
		tableContainer.setBounds(10, 60, 1060, 310);
		tableContainer.setLayout(new BorderLayout());
		tableContainer.add(new JScrollPane(table));
		table.setBackground(Color.CYAN);
		sale.addActionListener(this);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1100, 500);
		c = getContentPane();
		c.setLayout(null);
		c.add(headingContainer);
		c.add(genrate);
		c.add(sale);
		c.add(tableContainer);
		c.add(totalField);
		getData(list);
		addWindowListener(new Validation());
		setLocationRelativeTo(null);

		setVisible(true);

	}

		private void getData(List<TempBill> list) {
			Iterator i=list.iterator();
			while(i.hasNext()) {
				Vector v=new Vector();
				TempBill b=(TempBill) i.next();
				getTotalAmt(b.price,b.qty);
				
				
				v.add(b.barCode);
				v.add(b.name);
				v.add(b.price);
				v.add(b.qty);
				tableModel.addRow(v);
			}
			totalField.setText(""+total);
			
		
	}
		
		public void getTotalAmt(float price,int qty) {
			total+=price*qty;
		}

	/**
	 * Removes all the rows in the table
	 */
	public void clearTable() {
		System.out.println("clear");
		DefaultTableModel dm = tableModel;
		dm.getDataVector().removeAllElements();
		revalidate();
	}
	public void getTableColoumnModel() {
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Bar Code");
		tableModel.addColumn("Name");
		tableModel.addColumn("Price");
		tableModel.addColumn("Quantity");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==genrate) {
			Iterator i=	list.iterator();
			while(i.hasNext()) {
				TempBill t=(TempBill) i.next();
				String barCode=t.barCode;
				int qty=t.qty;
				try {
					updateData(barCode,qty);
				} catch (DataInvalidException e1) {
					 JOptionPane.showMessageDialog(null,e1.getMessage());
				}
				
			}
			
		}
		if(e.getSource()==sale) {
			Sales.setSaleVisible(true);
			list.clear();
			dispose();
		}
		
	}
	
	public void updateData(String barCode,int qty) throws DataInvalidException {
		String query="Select drugQuantity from medicine where drugBarcode='"+barCode+"'";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int q= rs.getInt("drugQuantity");
			if(q<qty) {
				throw new  DataInvalidException("Drug Quantity is less only"+q+"is Availabel");
			}
			q=q-qty;
			String updateQuery="update medicine set drugQuantity="+q+" where drugBarcode='"+barCode+"'";
			PreparedStatement s=connection.prepareStatement(updateQuery);
			int result=s.executeUpdate();
			
				updateSaleTable();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateSaleTable() throws SQLException {
		Iterator i=list.iterator();
		Date date=new  Date(new java.util.Date().getTime());
				
		while(i.hasNext()) {
			TempBill b=(TempBill) i.next();
			String query="insert into sale(drugBarcode,drugSalePrice,drugQuantity,saleDate) values (?,?,?,?);";
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, b.barCode);
			ps.setFloat(2, b.price);
			ps.setInt(3, b.qty);
			ps.setDate(4, date);
			ps.executeUpdate();

		}
		 JOptionPane.showMessageDialog(null,"Bill generated");
		 clearTable();
//		 list.clear();
	}
	

}