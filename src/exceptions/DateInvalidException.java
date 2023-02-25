package exceptions;

public class DateInvalidException extends Exception {
	
	String msg;
	
	 public DateInvalidException (String msg){
		 super(msg);
		 this.msg=msg;
	 }
	 
	 public String getMessage() {
		 return this.msg;
	 }

}
