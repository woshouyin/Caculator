package ex6;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Stack;

public class Caculator {

	private static final DecimalFormat opdf = new DecimalFormat("0.###########");
	private double m = 0;
	private double ans = 0;
	
	ArrayDeque<Object> ms = new ArrayDeque<Object>();
	Stack<String> opBuffer = new Stack<String>();
	Stack<Object> preSeq = new Stack<Object>();
	boolean errFlag = false;
	
	private Integer getOpRank(String op) {
		switch(op) {
		case "(":
			return -1;
		case "+":
		case "-":
			return 1;
		case "*":
		case "/":
			return 2;
		case "^":
			return 3;
		default:
			return 0;
		
		}
	
	}
	
	private int getOpArgNum(String op) {
		switch (op) {
		case "+":
		case "-":
		case "*":
		case "/":
		case "^":
		case "pow":
			return 2;

		default:
			return 1;
		}
	}
	
	private void put(Double num) {
		ms.push(num);
	}
	
	private void put(String op) {
		if(op.equals(")")) {
			String s;
			while(!(s = opBuffer.pop()).equals("(")) {
				ms.push(s);
			}
			if(!opBuffer.isEmpty() && getOpRank(opBuffer.lastElement())==0) {
				ms.push(opBuffer.pop());
			}
		}else if(op.equals(",")){
			while(!opBuffer.lastElement().equals("(")) {
				ms.push(opBuffer.pop());
			}
		}else{
			while(!opBuffer.isEmpty() 
					&& getOpRank(op) > 0
					&& getOpRank(op) <= getOpRank(opBuffer.lastElement())) {
				ms.push(opBuffer.pop());
			}
			opBuffer.push(op);
		}
	}
	
	private void endPut() {
		while(!opBuffer.isEmpty()) {
			ms.push(opBuffer.pop());
		}
	}
	
	private void put(char c) {
		put(Character.toString(c));
	}
	
	private boolean isNumPart(char c) {
		return (c >= '0' && c <= '9') || c == '.';
	}
	
	private boolean isSign(char c) {
		return c == '+' || c == '-' || c == '*' 
				|| c == '/' || c == '(' || c == ')'
				|| c == ',';
	}
	
	private boolean isWordPart(char c) {
		return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
	}
	
	private void prepare(String str) throws CaculateException {
		char[] cs = str.replace('×', '*').replace('÷', '/').toCharArray();
		Stack<Object> tmpPreSeq = new Stack<Object>();
		for(int i = 0; i < cs.length && !errFlag;) {
			boolean isNum = false;
			boolean dotMode = false;
			double num = 0;
			double t = 1;
			while(i < cs.length && isNumPart(cs[i])) {
				isNum = true;
				if(cs[i] >= '0' && cs[i] <= '9') {
					if(!dotMode) {
						num = num * 10 + (cs[i] - '0');
					}else {
						t *= 10;
						num += (cs[i] - '0') / t;
					}
				}else {
					if(dotMode) {
						throw new CaculateException("错误的小数点位置");
					}else {
						dotMode = true;
					}
				}
				i++;
			}
			if(isNum) {
				tmpPreSeq.push(num);
			}
			boolean isSign = false;
			while(i < cs.length && isSign(cs[i])) {
				isSign = true;
				if(cs[i] == '+' || cs[i] == '-') {
					boolean cont = true;
					while(cs[i] == '+' || cs[i] == '-') {
						cont = !(cont ^ (cs[i++] == '+'));
					}
					if(cont) {
						tmpPreSeq.push('+');
					}else {
						tmpPreSeq.push('-');
					}
				}else {
					tmpPreSeq.push(cs[i++]);
				}
			}
			
			
			boolean isWord = false;
			StringBuilder sb = new StringBuilder(); 
			while(!isSign && i < cs.length && isWordPart(cs[i])) {
				isWord = true;
				sb.append(cs[i++]);
			}
			if(isWord) {
				String s = sb.toString();
				if(s.equals("M")) {
					tmpPreSeq.push(m);
				}else if(s.equals("ANS")) {
					tmpPreSeq.push(ans);
				}else {
					tmpPreSeq.push(sb.toString());
				}
			}
			
			
		}
		
		Object ls = tmpPreSeq.pop();
		preSeq.push(ls);
		while(!tmpPreSeq.isEmpty()) {
			Object nw = tmpPreSeq.pop();
			if(isStart(ls) && isEnd(nw)) {
				preSeq.push('*');
			}
			preSeq.push(nw);
			ls = nw;	
		}
	}
	
	private boolean isStart(Object obj) {
		return (obj instanceof Double) 
				|| (obj instanceof Character && obj.equals('('))
				|| (obj instanceof String);
	}
	
	private boolean isEnd(Object obj) {
		return (obj instanceof Double) 
				|| (obj instanceof Character && obj.equals(')'));
	}
	
	private void complite() throws CaculateException {
		boolean black = false;
		int sig = 1;
		Object ls = null;
		while(!preSeq.isEmpty()) {
			Object obj = preSeq.pop();
			//System.out.println(obj);
			if(obj instanceof Character) {
				Character c = (Character)obj;
				if(black) {
					put(c);
					if(c.equals('+') || c.equals('-') || c.equals('*') 
							|| c.equals('/') || c.equals('^')) {
						black = false;
					}
				}else {
					if(c.equals('(')) {
						put(c);
					}else if(c.equals('-')) {
						if(ls instanceof Character && ls.equals('(')) {
							put(0.0);
							put(c);
						}else {
							sig = -1;
						}
					}else if(!c.equals('+')) {
						throw new CaculateException("不允许的符号位置");
					}
				}
			}else if(obj instanceof String) {
				String word = (String) obj;
				put(word);
			}else if(obj instanceof Double) {
				Double num = (Double)obj;
				put(sig * num);
				sig = 1;
				black = true;
			}
			ls = obj;
		}
		endPut();
	}

	private double doOpration(String op, Stack<Double> args) {
		Double[] as = new Double[args.size()];
		args.toArray(as);
		switch(op) {
		case "+":
			return as[1] + as[0];
		case "-":
			return as[1] - as[0];
		case "*":
			return as[1] * as[0];
		case "/":
			return as[1] / as[0];
		case "^":
		case "pow":
			return Math.pow(as[1], as[0]);
		case "sin":
			return Math.sin(as[0]);
		case "cos":
			return Math.cos(as[0]);
		case "tan":
			return Math.tan(as[0]);
		case "log":
			return Math.log(as[0]);
		case "sqrt":
			return Math.sqrt(as[0]);
		case "exp":
			return Math.exp(as[0]);
		default:
			return 0;
		}
	}
	
	private double compute() {
		Stack<Double> nb = new Stack<Double>();
		Stack<Double> args = new Stack<Double>();
		while(!ms.isEmpty()) {
			Object obj = ms.removeLast();
			if(obj instanceof Double) {
				nb.push((Double) obj);
			}else {
				String op = (String)obj;
				for(int i = 0; i < getOpArgNum(op); i++) {
					args.push(nb.pop());
				}
				nb.push(doOpration(op, args));
				args.clear();
			}
		}
		ans = nb.pop();
		return ans;
	}
	
	public String doCalc(String str) throws CaculateException {
		clear();
		prepare(str);
		complite();
		Double res = compute();
		return opdf.format(res);
	}
	
	public void print() {
		System.out.print("ms:");
		ArrayDeque<Object> msc = (ArrayDeque<Object>)ms.clone();
		while(!msc.isEmpty()) {
			System.out.print(msc.removeLast() + "  ");
		}
		System.out.println();
		System.out.print("ops:");
		Stack<String> opc = (Stack<String>) opBuffer.clone();
		while(!opc.isEmpty()) {
			System.out.print(opc.pop() + "  ");
		}
		System.out.println();
		System.out.print("pre:");
		Stack<Object> pre = (Stack<Object>) preSeq.clone();
		while(!pre.isEmpty()) {
			System.out.print(pre.pop() + " ");
		}
		System.out.println();
		System.out.println();
	}
	
	public double mPlus() {
		m += ans;
		return m;
	}
	
	public double mMinus() {
		m -= ans;
		return m;
	}
	
	public void mClear() {
		m = 0;
	}
	
	public double mRead() {
		return m;
	}
	
	public void clear() {
		preSeq.clear();
		ms.clear();
		opBuffer.clear();
	}
}
