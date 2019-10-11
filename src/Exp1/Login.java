package Exp1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login {

	public static void main(String[] args) {
		Frame f = new Frame("登录");

		Label label1 = new Label("用户名:");
		Label label2 = new Label("密码:    ");
		JTextField text1 = new JTextField(15);
		JPasswordField text2 = new JPasswordField(15);
		Button button1 = new Button("确定登录"); 
        
        Panel p1 = new Panel();
		Panel p2 = new Panel();
		p1.add(label1);
		p1.add(text1);
		p2.add(label2);
		p2.add(text2);

        Panel p3 = new Panel();
		p3.setLayout(new BorderLayout());
		p3.add(p1, "North");
        p3.add(p2, "South");

        Panel p4 = new Panel();
		p4.add(button1);
        
        Panel p5 = new Panel();
		p5.setLayout(new BorderLayout());
		p5.add(p3, "North");
		p5.add(p4, "South");
		
		f.setLayout(new FlowLayout());
		Panel p6 = new Panel();
		p6.add(p5);
		f.add(p6);
		f.setBackground(Color.gray);
		f.setSize(500, 500);//窗体大小
		f.setLocation(400, 200);//窗体位置
		f.setVisible(true);//可见
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
        });
        
		button1.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
			 // 获取文本框的值
			//  String tf_str = text1.getText().trim();
			 // 清空数据
			//  text1.setText("");
			 //获取光标
			//  text1.requestFocus();
			 }
			 });
	}
	
}
