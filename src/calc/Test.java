package calc;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Test {
	public static void main(String[] args) {
		JLabel disp = new JLabel("这是显示器");
		JTextField jtf = new JTextField("我是输入框");
		JButton jb = new JButton("按钮");
		JFrame jf = new JFrame("haha");
		jf.add(disp);
		jf.add(jtf);
		jf.add(jb);
		jf.setLayout(new GridLayout(3, 3));
		jf.setSize(600, 600);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setFont(new Font("Times New Roman", Font.BOLD, 30));
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				disp.setText(Double.toString(Calc.calc(jtf.getText())));
			}
		});;
	}
}
