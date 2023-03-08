package module;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import misc.Constant;
import misc.PharmacyDb;
import misc.Security;
import misc.Validation;

public class UserList extends JFrame {

	String userName;
	JPanel headingContainer;
	JPanel tableContainer;
	JLabel heading;
	JTable table;
	Container c;
	

	Connection con = PharmacyDb.getConnection();
	DefaultTableModel tableModel = new DefaultTableModel();

	public UserList() {

		getTableColoumnModel();
		PreparedStatement ps;
		java.time.LocalDate d = java.time.LocalDate.now();
		String date = d.toString();
		getData();

		/* ****************creating object********** */
		heading = new JLabel("DELETE USER");
		headingContainer = new JPanel();
		tableContainer = new JPanel();
		table = new JTable(tableModel);

		headingContainer.setSize(700, 50);
		headingContainer.setLocation(0, 0);
		headingContainer.setBackground(Constant.BACKGROUND_HEADING_LIGHT);
		headingContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
// tableContainer

		tableContainer.setBounds(5, 52, 670, 400);
		tableContainer.setLayout(new BorderLayout());
		tableContainer.add(new JScrollPane(table));
		table.setBackground(Color.CYAN);

		heading.setFont(Constant.HEADING_FONT);
		heading.setForeground(Color.WHITE);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		c = getContentPane();
		c.setLayout(null);
		headingContainer.add(heading, new BorderLayout().NORTH);

		c.add(headingContainer);

		c.add(tableContainer);
		addWindowListener(new Validation());

		validate();

		setLocationRelativeTo(null);
		setVisible(true);

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
	private void getData() {
		userName=Security.getUserName();

		try {
			PreparedStatement ps = con
					.prepareStatement("select user_name,Dob,Address,Phone,Salary,Gender,Role from user where user_name!='"+userName+"'");
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("user_name");
				String dob = resultSet.getString("Dob");
				String address = resultSet.getString("Address");
				String salary = resultSet.getString("Salary");
				String gender = resultSet.getString("Gender");
				String role = resultSet.getString("Role");

				Object[] data = { name, dob, address, salary, gender, role };

				tableModel.addRow(data);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getTableColoumnModel() {
		tableModel.addColumn("User");
		tableModel.addColumn("Dob");
		tableModel.addColumn("Address");
		tableModel.addColumn("Salary");
		tableModel.addColumn("Gender");
		tableModel.addColumn("Role");

	}

}
