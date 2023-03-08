package module;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import misc.PharmacyDb;
import misc.Security;
import misc.Validation;

public class ResetPassword extends JFrame implements ActionListener {
	Container c;
	JLabel userNameLabel, newPasswordLabel, confirmPasswordLabel, bg;
	JPasswordField newPassword;
	JPasswordField confirmPassword;
	JTextField userName;
	JButton b1, b2;
	ImageIcon img;
	Font ft1;
	Connection con;
	PreparedStatement st;
	int result;

	public ResetPassword() {
		con = PharmacyDb.getConnection();
		setTitle("Reset Password");
		setSize(700, 550);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		c = getContentPane();

		ft1 = new Font("Verdana", Font.BOLD, 18);

		userNameLabel = new JLabel("User Name");
		userNameLabel.setFont(ft1);
		userNameLabel.setBounds(100, 50, 150, 30);
		c.add(userNameLabel);

		userName = new JTextField();
		userName.setFont(ft1);
		userName.setBounds(300, 50, 170, 30);
		c.add(userName);

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

		addWindowListener(new Validation());

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == b2)
			dispose();

		else if (ae.getSource() == b1) {
			String un, op, np, cp;
			np = newPassword.getText();
			cp = confirmPassword.getText();

			if (np.equals(cp)) {
				try {
					st = con.prepareStatement("update user set password=? where user_name=? and role!=?");
					st.setString(1, np);
					st.setString(2, userName.getText());
					st.setString(3, "admin");
					result = st.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();

				}
			} else {
				JOptionPane.showMessageDialog(null, "New password & Confirm Password does not match");
			}
			if (result == 1) {
				JOptionPane.showMessageDialog(null, "Password changed Successfully");
				newPassword.setText("");
				confirmPassword.setText("");
				userName.setText("");
			}
			else if(result==0) {
				JOptionPane.showMessageDialog(null, "Invalid User Name");
			}

		}

	}
}
