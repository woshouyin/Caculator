package ex6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EmptyStackException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ex4.MathOpException;

public class CalcFrame extends JFrame{
	
	private static final HashMap<String, String> TCMAP;
	static {
		TCMAP = new HashMap<String, String>();
		for (int i = 0; i < 10; i++) {
			TCMAP.put(Integer.toString(i), Integer.toString(i));
		}
		String[] funcs = {"sqrt", "log", "sin", "cos", "tan", "exp", "pow"};
		for(String f : funcs) {
			TCMAP.put(f, f + "(");
		}
		String[] sts = {"C", "+", "-", "×", "÷", "^", ".", "=", "M+", "M-", "MC", "M", "ANS"};
		for(String st : sts) {
			TCMAP.put(st, st);
		}
		TCMAP.put("←", "BACKSPACE");
		TCMAP.put("C", "CLEAR");
	}
	
	
	private JPanel basicPanel;
	private JPanel sciencePanel;
	private Caculator calc = new Caculator();
	private boolean eqMode = false;
	private JTextArea disp;
	private StringBuilder dispStr = new StringBuilder();
	
	public CalcFrame() {
		init();
	}
	
	private void init() {
		JMenuBar jmb = new JMenuBar();
		JMenu jm = new JMenu("模式");
		JMenuItem jmiBas = new JMenuItem("基础型");
		JMenuItem jmiSin = new JMenuItem("科学型");
		jmiBas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeToBasic();
			}
		});
		jmiSin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeToScience();
			}
		});
		jm.add(jmiBas);
		jm.add(jmiSin);
		jmb.add(jm);
		this.setJMenuBar(jmb);
		disp = new JTextArea();
		JScrollPane jsp = new JScrollPane(disp);
		//jsp.setPreferredSize(new DimenSion());
		jsp.setPreferredSize(new Dimension(0, 100));
		disp.setBorder(BorderFactory.createLineBorder(Color.black));
		disp.setFont(new Font("Times New Roman", Font.BOLD, 40));
		disp.setEditable(false);
		this.add(jsp, BorderLayout.NORTH);
		
		createBasicPanel();
		createSciencePanel();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(300, 300, 0, 0);
		changeToBasic();
		disp.addKeyListener(new CKeyListener());
		this.setVisible(true);
	}
	
	private void changeToBasic() {
		setSize(400, 500);
		remove(sciencePanel);
		add(basicPanel, BorderLayout.CENTER);
		validate();
		repaint();
	}
	private void changeToScience() {
		setSize(800, 500);
		remove(basicPanel);
		add(sciencePanel, BorderLayout.CENTER);
		validate();
		repaint();
	}
	private void createBasicPanel() {
		basicPanel = new JPanel();
		GridLayout gl = new GridLayout(4, 4);
		String[] bts = {"+", "7", "8", "9"
						, "-", "4", "5", "6"
						, "×", "1", "2", "3"
						, "÷", ".", "0", "="};
		basicPanel.setLayout(gl);
		for (String t : bts) {
			basicPanel.add(getButton(t));
		}
	}
	
	private void createSciencePanel() {
		sciencePanel = new JPanel();
		GridLayout gl = new GridLayout(4, 8);
		String[] ts = {"M", "M+", "C", "←", "+", "7", "8", "9"
						,"MC", "M-", "sin", "cos", "-", "4", "5", "6"
						, "tan","log","exp", "pow", "×", "1", "2", "3"
						, "(", ")",",","ANS", "÷", ".", "0", "="};
		sciencePanel.setLayout(gl);
		for (String t : ts) {
			sciencePanel.add(getButton(t));
		}
	}
	
	private JButton getButton(String tag) {
		JButton jb = new JButton(tag);
		jb.setActionCommand(TCMAP.get(tag));
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				input(e.getActionCommand());
			}
		});
		jb.setFocusable(false);
		return jb;
	}
	
	void input(String inp) {
		switch (inp) {
		case "CLEAR":
			dispStr.setLength(0);
			break;
		case "BACKSPACE":
			if(dispStr.length() > 0)
				dispStr.setLength(dispStr.length() - 1);
			break;
		case "M+":
			calc.mPlus();
			break;
		case "M-":
			calc.mMinus();
			break;
		case "MC":
			calc.mClear();
			break;
		case "=":
			eqMode = true;
			String exc = dispStr.toString().split("=")[0];
			dispStr.setLength(0);
			dispStr.append(exc).append(" = ");
			try {
				dispStr.append(calc.doCalc(exc));
			} catch (CaculateException e) {
				dispStr.append("符号错误");
				e.printStackTrace();
			} catch (EmptyStackException e) {
				dispStr.append("堆栈错误");
				e.printStackTrace();
			} catch (Exception e) {
				dispStr.append("未知错误");
				e.printStackTrace();
			}
			break;
		case "EXIT":
			System.exit(0);
			break;
		default:
			if(eqMode) {
				dispStr.setLength(0);
				eqMode = false;
			}
			dispStr.append(inp);
			break;
		}
		
		disp.setText(dispStr.toString());
	}
	
	private class CKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			String msg = null;
			char c = e.getKeyChar();
			switch (c) {
			case '*':
				msg = "×";
				break;
			case '/':
				msg = "÷";
				break;
			case '+':
			case '-':
			case '(':
			case ')':
			case ',':
			case '.':
			case '=':
				msg = Character.toString(c);
			default:
				if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9') {
					msg = Character.toString(c);
				}
				break;
			}
			if(msg != null)
				input(msg);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			String msg = null;
			switch(e.getKeyCode()) {
			case KeyEvent.VK_BACK_SPACE:
				msg = "BACKSPACE";
				break;
			case KeyEvent.VK_ENTER:
				msg = "=";
				break;
			case KeyEvent.VK_ESCAPE:
				msg = "EXIT";
				break;
			default:
				break;
			}
			if(msg != null)
				input(msg);
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
		
	}
	
}
