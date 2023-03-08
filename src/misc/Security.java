package misc;

public class Security {
	private static String userName;
	private static  String  role;
	
	
	protected static void setUserName(String un) {
		userName=un;
	}
	
	protected static void setRole(String r) {
		role=r;
	}
	
	public static String getUserName() {
		return userName;
		
	}
	public static  String getRole() {
		return role;
		
	}
	
	

}
