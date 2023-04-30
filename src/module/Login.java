package module;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.Timer;

import misc.PharmacyDb;
import misc.Security;

public class Login extends Security implements ActionListener {

	Timer timer;
	JProgressBar progress;
	JWindow welcome;
	JComboBox mode;
	JButton reset = new JButton("Reset");
    JFrame loginFrame;
    JLabel l1,userLabel,passwordLabel;
    JTextField userField;
    JPasswordField passwordField;
	JButton login = new JButton("Login");
    JLabel background;
    Connection con;
	 PreparedStatement st;
	 ResultSet rs;
	 String []values={"Admin","User"}; 
	 private String role;
	 public static boolean splashVisible=true;
	 public static int progessValue=25;
	public Login() {
		loginFrame=new JFrame("LOGIN FORM");
		Font f = new Font("Arial", Font.BOLD, 12);
		l1=new JLabel("MODE");
		userLabel=new JLabel("USER NAME");
		passwordLabel=new JLabel("PASSWORD");
		mode=new JComboBox(values);
		background=new JLabel("",new ImageIcon(getClass().getClassLoader().getResource("PHARMACYIMAGE1.jpg")),JLabel.CENTER);
//		background=new JLabel("",new ImageIcon("resource/PHARMACYIMAGE1.jpg"),JLabel.CENTER);
		
		userField=new JTextField(); 
		login=new JButton("LOGIN");		
		Font ft1=new Font("Arial Black",Font.BOLD,18);
		
		
		loginFrame.setLayout(null);
		loginFrame.setSize(900,500);
		loginFrame.setResizable(false);
		loginFrame.setBackground(Color.CYAN);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
         background.setBounds(0,0,900,500);
     
        
         l1.setBounds(220,120,140,30);
	   l1.setFont(ft1);
         l1.setForeground(Color.WHITE);
     
          mode.setSelectedItem("Select Mode"); 
          mode.setBounds(420,120,100, 25);
          mode.setFont(ft1);
        
          userLabel.setBounds(220,200,140,30);
          userLabel.setForeground(Color.WHITE);
          userLabel.setFont(ft1);
         
          
	   userField.setBounds(420,200,180,30);
	   userField.setFont(ft1);

          
	   passwordLabel.setBounds(220,280,140,40);
	   passwordLabel.setForeground(Color.WHITE);
	   passwordLabel.setFont(ft1);
         
         passwordField=new JPasswordField();
         passwordField.setBounds(420,280,180,30);
         passwordField.setFont(ft1);
              

         login.setBounds(340,400,120,40);
         login.setFont(ft1);
         login.setBackground(Color.BLUE);
         login.setForeground(Color.WHITE);    
         login.addActionListener(this);      

         loginFrame.add(l1);
         loginFrame.add(userLabel);
         loginFrame.add(mode);
         loginFrame.add(userField);
         loginFrame.add(passwordLabel);
         loginFrame.add(passwordField);
         loginFrame.add(login);
         loginFrame.add(background);
     
       

		splashScreen(splashVisible);
	}

	public void splashScreen(boolean splashVisible) {
		welcome = new JWindow();
		JPanel panel = new JPanel();
		JLabel heading = new JLabel("PHARMACY MANAGEMENT");
		ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("splash.jpg"));
		JLabel label=new JLabel(icon,JLabel.CENTER);
		
		welcome.setSize(600, 416);
		welcome.setLocationRelativeTo(null);
		welcome.setVisible(splashVisible);

		progress = new JProgressBar(25, 100);
		progress.setForeground(Color.ORANGE);
		
		panel.setBackground(Color.WHITE);

		panel.add(heading);
		panel.add(label);
		
		
		
		welcome.add(BorderLayout.CENTER, panel);
		welcome.add(BorderLayout.PAGE_END, progress);		
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		welcome.revalidate();
		timer = new Timer(1000, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login ) {
			JFrame message = new JFrame();
			if (userField.getText().isEmpty() || passwordField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(message, "user name and password cannot empty", "warning",
						JOptionPane.WARNING_MESSAGE);
			} else {
				try {

					Connection con = PharmacyDb.getConnection();
					String sql = "Select * from user where user_name=? and password=? and role=?";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, userField.getText());
					ps.setString(2, passwordField.getText());
					ps.setString(3, mode.getSelectedItem().toString());
					ResultSet rs = ps.executeQuery();

					if (rs.next()) {
						JOptionPane.showMessageDialog(message, "Login Successfull");
						role=mode.getSelectedItem().toString();
						Security.setUserName(userField.getText());
						Security.setRole(role);

						loginFrame.dispose();
						HomePage hp = new HomePage();
					} else {
						JOptionPane.showMessageDialog(message, "Invalid userName and Password");
					}
				} catch (Exception a) {
					a.printStackTrace();
				}
			}

		}
		
				
		
		
		
		

		else if (e.getSource() == reset) {
			userField.setText("");
			passwordField.setText("");
		}

		else {
			int value = progress.getValue();
			if (value == 100) {

				welcome.dispose();
				loginFrame.setVisible(true);
				timer.stop();
			} else {
				progress.setValue(value + progessValue);

			}

		}

	}

}
