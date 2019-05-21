package ex4;

public class Calc {
	public static double calc(String str) throws MathOpException{
		String op;
		double num;
		try {
			String[] strs = str.split("\\(");
			op = strs[0];
			num = Double.parseDouble(strs[1].split("\\)")[0]);
		}catch(Exception e) {
			throw new MathOpException(MathOpException.ERR_EXP);
		}
		double ret = 0;
		switch (op) {
		case "sin":
			ret = Math.sin(num);
			break;
		case "cos":
			ret = Math.cos(num);
			break;
		case "log":
			if(num <= 0) 
				throw new MathOpException(MathOpException.LOG_NOT_POS);
			ret = Math.log(num);
			break;
		case "exp":
			ret = Math.exp(num);
			break;
		case "sqrt":
			if(num < 0)
				throw new MathOpException(MathOpException.SQRT_NEG);
			ret = Math.sqrt(num);

		default:
			throw new MathOpException(MathOpException.ERR_EXP);
		}
		return ret;
	}
}
