package module;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import misc.Validation;

public class ChangePassword extends JFrame implements ActionListener
{
	Container c;
	JLabel l1,l2,l3,l4,bg;
	JTextField t1;
	JPasswordField p1,p2,p3;
	JButton b1,b2;
	ImageIcon img;
	Font ft1;
	 Connection con;
	 PreparedStatement st;
	 ResultSet rs;
         

ChangePassword()
{
	 
         setTitle("Change Password");
         setSize(700,550);
	 setResizable(false);
	 setLocationRelativeTo(null);
	  setLayout(null);
	  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  
         
         c=getContentPane();

         ft1=new Font("Verdana",Font.BOLD,18);
	
        
	l1=new JLabel("User Name");
        l1.setFont(ft1);
        l1.setBounds(100,50,130,30);
        c.add(l1);
 
        t1=new JTextField();
        t1.setFont(ft1);
        t1.setBounds(300,50,120,40);
        c.add(t1);

        l2=new JLabel("Old Password");
        l2.setFont(ft1);
        l2.setBounds(100,130,150,30);
        c.add(l2);
       
         p1=new JPasswordField();
         p1.setFont(ft1);
         p1.setBounds(300,130,120,40);
         c.add(p1);
 
      
        l3=new JLabel("New Password");
        l3.setFont(ft1);
        l3.setBounds(100,210,150,30);
        c.add(l3);
       
         p2=new JPasswordField();
         p2.setFont(ft1);
         p2.setBounds(300,210,120,40);
         c.add(p2);
 
        l4=new JLabel("Confirm Password");
        l4.setFont(ft1);
        l4.setBounds(100,290,190,30);
        c.add(l4);
       
         p3=new JPasswordField();
         p3.setFont(ft1);
         p3.setBounds(300,290,120,40);
         c.add(p3);
        
        b1=new JButton("Change");
        b1.setFont(ft1);
        b1.addActionListener(this);
        b1.setBounds(130,380,120,40);
        c.add(b1);
    
        b2=new JButton("Close");
        b2.setFont(ft1);
        b2.addActionListener(this);
        b2.setBounds(300,380,120,40);
        c.add(b2);
         
        addWindowListener(new Validation());

//        img=new ImageIcon("IMAGE3.png");
//	bg=new JLabel("",img,JLabel.CENTER);
//	bg.setBounds(0,0,700,550);
//        c.add(bg);
//        setVisible(true);
    }



      public void actionPerformed(ActionEvent ae)
      {

            if(ae.getSource()==b2)
              dispose();
         
           else if(ae.getSource()==b1)
            {
               String un,op,np,cp;
               un=t1.getText();          
               op=p1.getText();
               np=p2.getText();
               cp=p3.getText();
       
               if(np.equals(cp))
               {
                  try
                 {            
                  st=con.prepareStatement("select count(*) from Login1 where USERNAME=? and PASSWORD=?");
                  st.setString(1,un);
                  st.setString(2,op);
                  rs=st.executeQuery();
                  rs.next();
                  int co=rs.getInt(1);
                  if(co>0)
                  {
               
                     st=con.prepareStatement("update Login1 set PASSWORD=? where USERNAME=?");
                     st.setString(1,np);
                     st.setString(2,un);
                     st.executeUpdate();
                     JOptionPane.showMessageDialog(null,"Password changed Successfully");
                      t1.setText("");
                     p1.setText("");
                     p2.setText("");
                     p3.setText("");
                  }
                  else
                  {
                     JOptionPane.showMessageDialog(null,"Invalid UserName/Password");
                  }
                }
                catch(Exception e)
                {
                   System.out.print(e);
  

                }
                     
              }
                 else
                 {
                    JOptionPane.showMessageDialog(null,"New password & Confirm Password does not match");
                 }
              


         }
       
       }    
   }




