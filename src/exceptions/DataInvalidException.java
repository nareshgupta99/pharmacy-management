package exceptions;

public class DataInvalidException extends Exception {
	
	String msg;
	
	public DataInvalidException(String msg) {
		super(msg);
		this.msg=msg;
	}
	
	public String getMessage() {
		return this.msg;
	}

}
