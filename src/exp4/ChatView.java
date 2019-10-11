package exp4;

import javax.swing.*;

public class ChatView {
	static String userName;  //由客户端登录时设置
	JTextField text;
	JTextArea textArea;
	ClientReadAndPrint.ChatViewListen listener;
	
	// 构造函数
	public ChatView() {
		JFrame jf = new JFrame("客户端");
		jf.setBounds(500,200,400,330);  //设置坐标和大小
		jf.setResizable(false);  // 缩放为不能缩放
		
		JPanel jp = new JPanel();
		JLabel lable = new JLabel("用户：" + userName);
		textArea = new JTextArea("***************登录成功，欢迎来到多人聊天室！****************\n",12, 35);
		textArea.setEditable(false);  // 设置为不可修改
		JScrollPane scroll = new JScrollPane(textArea);  // 设置滚动面板（装入textArea）
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // 显示垂直条
		jp.add(lable);
		jp.add(scroll);
		
		text = new JTextField(20);
		JButton button = new JButton("发送");
		jp.add(text);
		jp.add(button);
		
		// 设置监听
		listener = new ClientReadAndPrint().new ChatViewListen();
		listener.setJTextField(text);  // 调用PoliceListen类的方法
		listener.setJTextArea(textArea);
		listener.setChatViewJf(jf);
		text.addActionListener(listener);  // 文本框添加监听
		button.addActionListener(listener);  // 按钮添加监听
		
		jf.add(jp);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置右上角关闭图标的作用
		jf.setVisible(true);  // 设置可见
	}
}

