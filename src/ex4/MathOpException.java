package ex4;

public class MathOpException extends Exception{
	public static final int SQRT_NEG = 1;
	public static final int LOG_NOT_POS = 2;
	public static final int ERR_EXP = 3;
	
	private String message = "";
	public MathOpException(int code) {
		switch(code) {
		case SQRT_NEG:
			message = "负数开根号";
			break;
		case LOG_NOT_POS:
			message = "非正数求对数";
			break;
		case ERR_EXP:
			message = "不能解析的表达式";
			break;
		default:
			message = "不支持的错误";
			break;
		}
	}
	
	public String getMessage() {
		return message;
	}
	
}
