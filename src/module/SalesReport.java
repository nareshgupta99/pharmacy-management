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

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import misc.Constant;
import misc.PharmacyDb;
import misc.Validation;

public class SalesReport extends JFrame implements ItemListener {

	JPanel headingContainer;
	JPanel sortContainer;
	JPanel tableContainer;
	JLabel heading;
	JLabel sortByLabel;
	JLabel totalSale;
	JTextField totalField;
	JComboBox sortBy;
	JTable table;
	String list[] = {"Select", "Quantity", "Price","Date" };
	Container c;
	double amt = 0;

	Connection con = PharmacyDb.getConnection();
	DefaultTableModel tableModel = new DefaultTableModel();

	public SalesReport() {

		getTableColoumnModel();
		PreparedStatement ps;
		String query = "select m.drugName,m.drugCostPrice,m.drugSalePrice ,s.drugBarcode ,s.drugQuantity,s.saleDate FROM medicine as m inner join sale as s on m.drugBarcode=s.drugBarcode";
		try {
			ps = con.prepareStatement(query);
			getData(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/* ****************creating object********** */
		heading = new JLabel("Sales Report");
		headingContainer = new JPanel();
		tableContainer = new JPanel();
		sortContainer = new JPanel();
		sortByLabel = new JLabel("Sort By:");
		sortBy = new JComboBox(list);
		table = new JTable(tableModel);
		totalField = new JTextField();
		totalSale = new JLabel("Total Sale");

		totalSale.setBounds(10, 400, 70, 40);
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

		tableContainer.setBounds(10, 100, 1060, 290);
		tableContainer.setLayout(new BorderLayout());
		tableContainer.add(new JScrollPane(table));
		table.setBackground(Color.CYAN);
		totalField.setBounds(90, 400, 160, 40);
		totalField.setEditable(false);
		totalField.setBackground(Color.WHITE);

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
		c.add(totalSale);
		c.add(totalField);
		totalField.setText("" + this.amt);
		setLocationRelativeTo(null);
		addWindowListener(new Validation());

		validate();

		setVisible(true);

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		String source = sortBy.getSelectedItem().toString();
		
		if (source.equals("Price")) {
			clearTable();
			PreparedStatement ps;
			try {
				ps = con.prepareStatement("select m.drugName,m.drugCostPrice,m.drugSalePrice ,s.drugBarcode ,s.drugQuantity,s.saleDate FROM medicine as m  inner join sale as s on m.drugBarcode=s.drugBarcode order by m.drugSalePrice desc");
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
				ps = con.prepareStatement("select m.drugName,m.drugCostPrice,m.drugSalePrice ,s.drugBarcode ,s.drugQuantity,s.saleDate FROM medicine as m  inner join sale as s on m.drugBarcode=s.drugBarcode order by m.drugQuantity");
				getData(ps);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
		if (source.equals("Date")) {
			clearTable();
			PreparedStatement ps;
			try {
				ps = con.prepareStatement("select m.drugName,m.drugCostPrice,m.drugSalePrice ,s.drugBarcode ,s.drugQuantity,s.saleDate FROM medicine as m  inner join sale as s on m.drugBarcode=s.drugBarcode order by s.saleDate desc");
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
	private void getData(PreparedStatement ps) {

		try {
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				String barCode = resultSet.getString("drugBarcode");
				String name = resultSet.getString("drugName");
				String quantity = resultSet.getString("drugQuantity");
				String cost_price = resultSet.getString("drugCostPrice");
				String sale_price = resultSet.getString("drugSalePrice");
				String date = resultSet.getString("saleDate");
				getTotalSale(resultSet.getFloat("drugSalePrice"), resultSet.getInt("drugQuantity"));
				Object[] data = { barCode, name, cost_price, sale_price, quantity,date };

				tableModel.addRow(data);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getTableColoumnModel() {
		tableModel.addColumn("Bar_Code");
		tableModel.addColumn("Name");
		tableModel.addColumn("Cost_Price");
		tableModel.addColumn("Sale_Price");
		tableModel.addColumn("Quantity");
		tableModel.addColumn("Date");

	}

	private void getTotalSale(float p, int qty) {
		float temp = p * qty;
		amt += temp;

	}


}
