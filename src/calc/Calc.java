package calc;

public class Calc {
	public static double calc(String str) {
		String[] strs = str.split("\\(");
		String op = strs[0];
		double num = Double.parseDouble(strs[1].split("\\)")[0]);
		double ret = 0;
		switch (op) {
		case "sin":
			ret = Math.sin(num);
			break;
		case "cos":
			ret = Math.cos(num);
			break;
		case "log":
			ret = Math.log(num);
			break;
		case "exp":
			ret = Math.exp(num);
			break;

		default:
			ret = num;
			break;
		}
		return ret;
	}
}
