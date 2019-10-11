package exp5;

import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientFileThread extends Thread{
	private Socket socket = null;
	private JFrame chatViewJFrame = null;
	static String userName = null;
	static PrintWriter out = null;  // 普通消息的发送（到服务端）
	static DataInputStream fileIn = null;
	static DataOutputStream fileOut = null;
//	static DataInputStream fileReader = null;
	static DataOutputStream fileWriter = null;
	
	public ClientFileThread(String userName, JFrame chatViewJFrame, PrintWriter out) {
		ClientFileThread.userName = userName;
		this.chatViewJFrame = chatViewJFrame;
		ClientFileThread.out = out;
	}
	
	public void run() {
		try {
			InetAddress addr = InetAddress.getByName(null);  // 获取主机地址
			socket = new Socket(addr, 8090);  // 客户端套接字
			fileIn = new DataInputStream(socket.getInputStream());  // 输入流（服务器）
			fileOut = new DataOutputStream(socket.getOutputStream());  // 输出流（服务器）
			// 接受文件
			while(true) {
				String textName = fileIn.readUTF();
				double totleLength = fileIn.readLong();
				int result = JOptionPane.showConfirmDialog(chatViewJFrame, "是否接受？", "提示",
														   JOptionPane.YES_NO_OPTION);
				int length = -1;
				byte[] buff = new byte[1024];
				double curLength = 0;
				// 提示框选择结果，0为确定，1位取消
				if(result == 0){
					out.println("【" + userName + "选择了接收文件！】");
					out.flush();
					File file = new File("C:\\Users\\Samven\\Desktop\\接受文件\\(" +
							 userName + ")" + textName);
					fileWriter = new DataOutputStream(new FileOutputStream(file));
					while((length = fileIn.read(buff)) > 0) {  // 把文件写进本地
						fileWriter.write(buff, 0, length);
						fileWriter.flush();
						curLength += length;
						out.println("【接收进度:" + curLength/totleLength*100 + "%】");
						out.flush();
						if(curLength == totleLength) {  // 强制结束
							break;
						}
					}
					out.println("【" + userName + "成功接收文件！】");
					out.flush();
					// 提示文件存放地址
					JOptionPane.showMessageDialog(chatViewJFrame, "文件存放地址：\n" +
							"C:\\Users\\Samven\\Desktop\\接受文件\\(" +
							userName + ")" + textName, "提示", JOptionPane.INFORMATION_MESSAGE);
				}
				else {  // 不接受文件
					while((length = fileIn.read(buff)) > 0) {
						curLength += length;
						if(curLength == totleLength) {  // 强制结束
							break;
						}
					}
				}
				fileWriter.close();
			}
		} catch (Exception e) {}
	}
	
	static void outFileToServer(String path) {
		try {
			fileOut.writeUTF(path);  // 发送文件路径
			out.println("【" + userName + "已成功发送文件！】");
			out.flush();
		} catch (Exception e) {}
	}
}
