package exp5;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class MultiChat {
	JTextArea textArea;
	
	// 用于向文本区域添加信息
	void setTextArea(String str) {
		textArea.append(str+'\n');
		textArea.setCaretPosition(textArea.getDocument().getLength());  // 设置滚动条在最下面
	}
	
	// 构造函数
	public MultiChat() {
		init();
	}
	
	void init() {
		JFrame jf = new JFrame("服务器端");
		jf.setBounds(500,100,450,500);  // 设置窗口坐标和大小
		jf.setResizable(false);  // 设置为不可缩放
		
		JPanel jp = new JPanel();  // 新建容器
		JLabel lable = new JLabel("==欢迎来到多人聊天系统（服务器端）==");
		textArea = new JTextArea(23, 38);  // 新建文本区域并设置长宽
		textArea.setEditable(false);  // 设置为不可修改
		JScrollPane scroll = new JScrollPane(textArea);  // 设置滚动面板（装入textArea）
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  // 显示垂直条
		jp.add(lable);
		jp.add(scroll);
		
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		jf.add(jp);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置关闭图标作用
		jf.setVisible(true);  // 设置可见
	}
}
