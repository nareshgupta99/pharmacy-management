package module;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import misc.Constant;
import misc.PharmacyDb;
import misc.Validation;

public class AvilabelDrug extends JFrame implements ItemListener  {

	JPanel headingContainer;
	JPanel sortContainer;
	JPanel tableContainer;
	JLabel heading;
	JLabel sortByLabel;
	JComboBox sortBy;
	JTable table;
	String list[] = { "Quantity",  "Price" };
	Container c;

	Connection con = PharmacyDb.getConnection();
	DefaultTableModel tableModel=new DefaultTableModel();
	public AvilabelDrug()  {

		getTableColoumnModel();
		PreparedStatement ps;
		java.time.LocalDate d=java.time.LocalDate.now();
		String date=d.toString();
		
		try {
			ps = con.prepareStatement("select drugName,drugSalePrice,drugCostPrice,mfg,EXP,drugQuantity from medicine where EXP >= '"+date+"'");
			getData(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/* ****************creating object********** */
		heading = new JLabel("AVILABEL DRUG");
		headingContainer = new JPanel();
		tableContainer = new JPanel();
		sortContainer = new JPanel();
		sortByLabel = new JLabel("Sort By:");
		sortBy = new JComboBox(list);
		table = new JTable(tableModel);

		sortByLabel.setSize(150, 100);
		sortByLabel.setForeground(Color.WHITE);
		sortBy.setSize(150, 80);
		sortBy.addItemListener(this);


		headingContainer.setSize(1100, 50);
		headingContainer.setLocation(0, 0);
		headingContainer.setBackground(Constant.BACKGROUND_HEADING_LIGHT);
		headingContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));


		sortContainer.add(sortByLabel);
		sortContainer.setBounds(0, 51, 1100, 40);
		sortContainer.setBackground(Constant.BACKGROUND_HEADING_DARK);
		sortContainer.add(sortBy);

// tableContainer

		tableContainer.setBounds(10, 100, 1060, 350);
		tableContainer.setLayout(new BorderLayout());
		tableContainer.add(new JScrollPane(table));
		table.setBackground(Color.CYAN);

		heading.setFont(Constant.HEADING_FONT);
		heading.setForeground(Color.WHITE);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1100, 500);
		c = getContentPane();
		c.setLayout(null);
		headingContainer.add(heading, new BorderLayout().NORTH);
		c.add(headingContainer);
		c.add(sortContainer);

		c.add(tableContainer);
		addWindowListener(new Validation());
		validate();

		setLocationRelativeTo(null);
		setVisible(true);

	}

	@Override
	public void itemStateChanged(ItemEvent e){
		String source = sortBy.getSelectedItem().toString();
				
		
		if (source.equals("Price")) {
			clearTable();
			PreparedStatement ps;
			try {
				ps = con.prepareStatement("select * from medicine order by drugCostPrice");
				getData(ps);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
		if (source.equals("Quantity")) {
			clearTable();
			PreparedStatement ps;
			try {
				ps = con.prepareStatement("select * from medicine order by drugQuantity ");
				getData(ps);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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

	/**
	 * geeting data from database
	 */
	private void getData(PreparedStatement ps)  {

		try {
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			String name = resultSet.getString("drugName");
			String quantity = resultSet.getString("drugQuantity");
			String sale_price = resultSet.getString("drugSalePrice");
			String cost_price = resultSet.getString("drugCostPrice");
			Date mfg = resultSet.getDate("mfg");
			Date exp = resultSet.getDate("EXP");
			

			Object[] data = { name,   sale_price, cost_price, mfg, exp,quantity };

			tableModel.addRow(data);

		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void  getTableColoumnModel() {
		tableModel.addColumn("Name");
		tableModel.addColumn("Sale_Price");
		tableModel.addColumn("Cost_Price");
		tableModel.addColumn("Mfg.Date");
		tableModel.addColumn("Exp.Date");
		tableModel.addColumn("Quantity");
		
	}
	

}
