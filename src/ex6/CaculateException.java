package ex6;

public class CaculateException extends Exception{
	public final static int WRONG_EXPRESSION = 0;
	
	private int type;
	private String msg;
	public CaculateException(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String getMessage() {
		return msg;
	}
}
