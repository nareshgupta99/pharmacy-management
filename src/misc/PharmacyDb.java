package misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PharmacyDb {

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/pharmecy_management";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "root";
	private static String barcode;
	private static int code;
	private final static String SALE = "create Table if not exists sale(saleId integer auto_increment primary key , drugBarcode varchar(255),drugQuantity integer,foreign key(drugBarcode) references medicine(drugBarcode)) ";
	private final static String USER = "create table if not exists user(user_name varchar(255) primary key,password varchar(255),role char(5) );";
	private final static String MEDICINE = " create table if not exists medicine(DrugName Varchar(255),drugBarcode varchar(255) primary key, supplierName varchar(255),drugCostPrice float,drugPurpose varchar(255),drugSalePrice float,supplierContact char(12),mfg Date,EXP Date ,drugQuantity integer,companyNames varchar(255),drugType varchar(255) );";

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return con;
	}

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
	

}
