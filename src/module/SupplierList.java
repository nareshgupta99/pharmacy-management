package module;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import misc.Constant;
import misc.PharmacyDb;
import misc.Validation;

public class SupplierList extends JFrame {

	JPanel headingContainer;
	JPanel tableContainer;
	JLabel heading;
	JLabel sortByLabel;
	JTable table;
	Container c;

	Connection con = PharmacyDb.getConnection();
	DefaultTableModel tableModel = new DefaultTableModel();

	public SupplierList() {

		getTableColoumnModel();
		PreparedStatement ps;

		try {
			ps = con.prepareStatement("select * from medicine");
			getData(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/* ****************creating object********** */
		heading = new JLabel("SUPPLIER");
		headingContainer = new JPanel();
		tableContainer = new JPanel();
		addWindowListener(new Validation());
		table = new JTable(tableModel);

		headingContainer.setSize(800, 50);
		headingContainer.setLocation(0, 0);
		headingContainer.setBackground(Constant.BACKGROUND_HEADING_LIGHT);
		headingContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

// tableContainer

		tableContainer.setBounds(10, 60, 760, 350);
		tableContainer.setLayout(new BorderLayout());
		tableContainer.add(new JScrollPane(table));
		table.setBackground(Color.CYAN);

		heading.setFont(Constant.HEADING_FONT);
		heading.setForeground(Color.WHITE);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 500);
		c = getContentPane();
		c.setLayout(null);
		headingContainer.add(heading, new BorderLayout().NORTH);
		c.add(headingContainer);

		c.add(tableContainer);

		validate();

		setLocationRelativeTo(null);
		setVisible(true);

	}

	/* ***** Removes all the rows in the table ********* */

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
				String supplierName = resultSet.getString("supplierName");
				String suplier_contact = resultSet.getString("supplierContact");

				Object[] data = { supplierName, suplier_contact };

				tableModel.addRow(data);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getTableColoumnModel() {
		tableModel.addColumn("Supplier Name");
		tableModel.addColumn("Supplier Contact");

	}

}
