package module;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import exceptions.DataInvalidException;
import misc.PharmacyDb;
import misc.Security;
import misc.Validation;

public class ChangePassword extends JFrame implements ActionListener {
	Container c;
	JLabel oldPasswordLabel, newPasswordLabel, confirmPasswordLabel, bg;
	JPasswordField oldPassword, newPassword;
	JPasswordField confirmPassword;
	JButton b1, b2;
	Font ft1;
	Connection con;
	PreparedStatement st;
	ResultSet rs;
	int result;
	
	public ChangePassword() {
		
		ImageIcon ico = new ImageIcon("image3.jpg");
		JLabel label = new JLabel(ico);
		Dimension size = label.getPreferredSize();
		label.setBounds(0, 0, size.width, size.height);
		con = PharmacyDb.getConnection();
		setTitle("Change Password");
		setSize(700, 550);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		c = getContentPane();

		ft1 = new Font("Verdana", Font.BOLD, 18);

		oldPasswordLabel = new JLabel("Old Password");
		oldPasswordLabel.setFont(ft1);
		oldPasswordLabel.setBounds(100, 50, 150, 30);
		c.add(oldPasswordLabel);

		oldPassword = new JPasswordField();
		oldPassword.setFont(ft1);
		oldPassword.setBounds(300, 50, 170, 30);
		c.add(oldPassword);

		newPasswordLabel = new JLabel("New Password");
		newPasswordLabel.setFont(ft1);
		newPasswordLabel.setBounds(100, 130, 150, 30);
		c.add(newPasswordLabel);

		newPassword = new JPasswordField();
		newPassword.setFont(ft1);
		newPassword.setBounds(300, 130, 170, 30);
		c.add(newPassword);

		confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setFont(ft1);
		confirmPasswordLabel.setBounds(100, 210, 190, 30);
		c.add(confirmPasswordLabel);

		confirmPassword = new JPasswordField();
		confirmPassword.setFont(ft1);
		confirmPassword.setBounds(300, 210, 170, 30);
		c.add(confirmPassword);

		b1 = new JButton("Change");
		b1.setFont(ft1);
		b1.addActionListener(this);
		b1.setBounds(130, 380, 120, 40);
		c.add(b1);

		b2 = new JButton("Close");
		b2.setFont(ft1);
		b2.addActionListener(this);
		b2.setBounds(300, 380, 120, 40);
		c.add(b2);
		c.add(label);

		addWindowListener(new Validation());

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == b2)
			dispose();

		else if (ae.getSource() == b1) {
			String un, op, np, cp;
			op = oldPassword.getText();
			np = newPassword.getText();
			cp = confirmPassword.getText();

			if (np.equals(cp)) {
				try {
						Validation.checkPassword(cp);
						st = con.prepareStatement("update user set password=? where user_name=?");
						st.setString(1, np);
						st.setString(2, Security.getUserName());
						st.executeUpdate();
						JOptionPane.showMessageDialog(null, "Password changed Successfully");
						oldPassword.setText("");
						newPassword.setText("");
						confirmPassword.setText("");
					
					} catch (DataInvalidException e) {
						JOptionPane.showMessageDialog(null,e.getMessage()," Error",JOptionPane.WARNING_MESSAGE);
				} catch (SQLException e) {
						e.printStackTrace();
					}

				}

			} else {
				JOptionPane.showMessageDialog(null, "New password & Confirm Password does not match");
			}

		}

	}
