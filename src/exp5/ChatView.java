package exp5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ChatView {
	String userName;  //由客户端登录时设置
	JTextField text;
	JTextArea textArea;
	ClientReadAndPrint.ChatViewListen listener;
	
	// 构造函数
	public ChatView(String userName) {
		this.userName = userName ;
		init();
	}
	// 初始化函数
	void init() {
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
		JButton openFileBtn = new JButton("发送文件");
		jp.add(text);
		jp.add(button);
		jp.add(openFileBtn);
		
		// 设置“打开文件”监听
		openFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFileOpenDialog(jf);
			}
		});
		
		// 设置“发送”监听
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
	// “打开文件”调用函数
	void showFileOpenDialog(JFrame parent) {
		// 创建一个默认的文件选择器
		JFileChooser fileChooser = new JFileChooser();
		// 设置默认显示的文件夹
		fileChooser.setCurrentDirectory(new File("C:/Users/Samven/Desktop"));
		// 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
//        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("(txt)", "txt"));
        // 设置默认使用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.setFileFilter(new FileNameExtensionFilter("(txt)", "txt"));
		// 打开文件选择框（线程将被堵塞，知道选择框被关闭）
		int result = fileChooser.showOpenDialog(parent);  // 对话框将会尽量显示在靠近 parent 的中心
		// 点击确定
		if(result == JFileChooser.APPROVE_OPTION) {
			// 获取路径
			File file = fileChooser.getSelectedFile();
			String path = file.getAbsolutePath();
			ClientFileThread.outFileToServer(path);
		}
	}
}

