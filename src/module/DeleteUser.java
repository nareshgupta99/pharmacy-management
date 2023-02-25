package module;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import misc.Constant;
import misc.PharmacyDb;
import misc.Validation;

public class DeleteUser extends JFrame implements MouseListener,ActionListener {

	int row;
	String userName;
	JPanel headingContainer;
	JPanel buttonContainer;
	JPanel tableContainer;
	JLabel heading;
	JTable table;
	Container c;
	JButton deleteButton;

	Connection con = PharmacyDb.getConnection();
	DefaultTableModel tableModel=new DefaultTableModel();
	public DeleteUser()  {

		getTableColoumnModel();
		PreparedStatement ps;
		java.time.LocalDate d=java.time.LocalDate.now();
		String date=d.toString();
			getData();
		
		/* ****************creating object********** */
		heading = new JLabel("DELETE USER");
		headingContainer = new JPanel();
		tableContainer = new JPanel();
		buttonContainer = new JPanel();
		table = new JTable(tableModel);
		deleteButton =new JButton("Delete");
		
		headingContainer.setSize(500, 50);
		headingContainer.setLocation(0, 0);
		headingContainer.setBackground(Constant.BACKGROUND_HEADING_LIGHT);
		headingContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
// tableContainer

		tableContainer.setBounds(10, 100, 470, 260);
		tableContainer.setLayout(new BorderLayout());
		tableContainer.add(new JScrollPane(table));
		table.setBackground(Color.CYAN);

		heading.setFont(Constant.HEADING_FONT);
		heading.setForeground(Color.WHITE);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 400);
		c = getContentPane();
		c.setLayout(null);
		headingContainer.add(heading, new BorderLayout().NORTH);
		
		// Button container
		buttonContainer.setBounds(0, 51, 500, 40);
		buttonContainer.setBackground(Constant.BACKGROUND_HEADING_DARK);
		buttonContainer.add(deleteButton);
		deleteButton.setEnabled(false);
		
		// adding action 
		table.addMouseListener(this);
		deleteButton.addActionListener(this);
		
		
		c.add(headingContainer);
		c.add(buttonContainer);

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
	private void getData()  {

		try {
			PreparedStatement ps = con.prepareStatement("select user_name from user where role != 'admin'");
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			String name = resultSet.getString("user_name");
			

			Object[] data = { name };

			tableModel.addRow(data);

		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void  getTableColoumnModel() {
		tableModel.addColumn("User");
		
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		deleteButton.setEnabled(true);
		 row=table.getSelectedRow();
		 userName=tableModel.getValueAt(row,0).toString();
		 System.out.println(userName);

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
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String query="delete from user where user_name='"+userName+"' And role='user'";
		try {
			PreparedStatement ps=con.prepareStatement(query);
			boolean r=ps.execute();
				clearTable();
				getData();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}




}
