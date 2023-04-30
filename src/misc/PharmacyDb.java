package misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import exceptions.DataInvalidException;

public class PharmacyDb {

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/pharmecy_management";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "root";
	private static String barcode;
	private static final String DB_NAME="pharmecy_management";
	private static int code;
	private final static String SALE = "create Table if not exists sale(saleId integer auto_increment primary key , drugBarcode varchar(255),drugSalePrice FLOAT,drugQuantity integer,saleDate Date,foreign key(drugBarcode) references medicine(drugBarcode) on Delete set NULL); ";
	private final static String USER = "create table if not exists user(user_name varchar(255) primary key,password varchar(255),role char(5) );";
	private final static String MEDICINE = " create table if not exists medicine(DrugName Varchar(255),drugBarcode varchar(255) primary key, supplierName varchar(255),drugCostPrice float,drugPurpose varchar(255),drugSalePrice float,supplierContact char(12),mfg Date,EXP Date ,drugQuantity integer,companyNames varchar(255),drugType varchar(255) );";
	private final static String BARCODE_SEQUENCE="create Table if not exists barcode_sequence(code integer)";
	private final static String DATABASE="CREATE DATABASE pharmecy_management";
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(DB_URL,DB_USERNAME, DB_PASSWORD);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return con;
	}
	
	// for creating data base
	
	public static void createDatabase() {
		Connection con = getConnection();
		PreparedStatement ps;
		try {
		ps=con.prepareStatement(DATABASE);	
		ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	// for creating table in data base
	
	public static void createTable() {
		Connection con = getConnection();
		PreparedStatement s, u, m;
		try {
			s = con.prepareStatement(SALE);
			s.execute();
			u = con.prepareStatement(USER);
			u.execute();
			m = con.prepareStatement(MEDICINE);
			m.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// create admin user need to first time 
	
	
	public static void createAdminUser()  {
		 String user ;
		 String  password;
		 String insert= "INSERT INTO user" + "  (user_name, password, role) VALUES " +
		            " (?, ?, ?);";
		 
		Connection con=getConnection();
		ResultSet rs=null;
		try {
			PreparedStatement ps=con.prepareStatement("select * from user");
			rs=ps.executeQuery();
			con.setAutoCommit(false);
			 while(!rs.next()) {
				 user = JOptionPane.showInputDialog("Enter User Name");
				 password= JOptionPane.showInputDialog("Enter User Password");
				 try {
					 ps=con.prepareStatement(insert);
					 ps.setString(1, user);
					 ps.setString(2, password);
					 ps.setString(3, "admin");
					 ps.executeUpdate();
					 con.commit();
					 break;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		 
		
		
	}

	
	//for genarting barcode 
	public static String genrateBarcode(String drugName) {
		Connection con=getConnection();
		String query="Select * from barcode_sequence";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ResultSet set=ps.executeQuery();
			set.next();
			 code=set.getInt("code");
			code+=1;
			barcode="#"+code+""+drugName;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String updateQuery="update barcode_sequence set code="+code;
		try {
			ps=con.prepareStatement(updateQuery);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return barcode;
		
	}
	
	
	// check database is created or not
	
	
	public static void isDbExist()  {
		Connection con=getConnection();
		ResultSet rs=null;
		boolean flag=false;
		try {
			 rs=con.getMetaData().getCatalogs();
			 while (rs.next()) {
		            String databaseName = rs.getString(1);
		            if(databaseName.equals(DB_NAME)) {
		                flag=true;
		            }
		        }
					if(!flag) {
						createDatabase();
					}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	


}
