package exp5;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class ClientFileThread extends Thread{
	private Socket socket = null;
	private JFrame chatViewJFrame = null;
	static String userName = null;
	static PrintWriter out = null;  // 普通消息的发送（Server.java传来的值）
	static DataInputStream fileIn = null;
	static DataOutputStream fileOut = null;
	static DataInputStream fileReader = null;
	static DataOutputStream fileWriter = null;
	
	public ClientFileThread(String userName, JFrame chatViewJFrame, PrintWriter out) {
		ClientFileThread.userName = userName;
		this.chatViewJFrame = chatViewJFrame;
		ClientFileThread.out = out;
	}
	
	// 客户端接收文件
	public void run() {
		try {
			InetAddress addr = InetAddress.getByName(null);  // 获取主机地址
			socket = new Socket(addr, 8090);  // 客户端套接字
			fileIn = new DataInputStream(socket.getInputStream());  // 输入流
			fileOut = new DataOutputStream(socket.getOutputStream());  // 输出流
			// 接收文件
			while(true) {
				String textName = fileIn.readUTF();
				long totleLength = fileIn.readLong();
				int result = JOptionPane.showConfirmDialog(chatViewJFrame, "是否接受？", "提示",
														   JOptionPane.YES_NO_OPTION);
				int length = -1;
				byte[] buff = new byte[1024];
				long curLength = 0;
				// 提示框选择结果，0为确定，1位取消
				if(result == 0){
//					out.println("【" + userName + "选择了接收文件！】");
//					out.flush();
					File userFile = new File("C:\\Users\\Samven\\Desktop\\接受文件\\" + userName);
					if(!userFile.exists()) {  // 新建当前用户的文件夹
						userFile.mkdir();
					}
					File file = new File("C:\\Users\\Samven\\Desktop\\接受文件\\" + userName + "\\"+ textName);
					fileWriter = new DataOutputStream(new FileOutputStream(file));
					while((length = fileIn.read(buff)) > 0) {  // 把文件写进本地
						fileWriter.write(buff, 0, length);
						fileWriter.flush();
						curLength += length;
//						out.println("【接收进度:" + curLength/totleLength*100 + "%】");
//						out.flush();
						if(curLength == totleLength) {  // 强制结束
							break;
						}
					}
					out.println("【" + userName + "接收了文件！】");
					out.flush();
					// 提示文件存放地址
					JOptionPane.showMessageDialog(chatViewJFrame, "文件存放地址：\n" +
							"C:\\Users\\Samven\\Desktop\\接受文件\\" +
							userName + "\\" + textName, "提示", JOptionPane.INFORMATION_MESSAGE);
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
	
	// 客户端发送文件
	static void outFileToServer(String path) {
		try {
			File file = new File(path);
			fileReader = new DataInputStream(new FileInputStream(file));
			fileOut.writeUTF(file.getName());  // 发送文件名字
			fileOut.flush();
			fileOut.writeLong(file.length());  // 发送文件长度
			fileOut.flush();
			int length = -1;
			byte[] buff = new byte[1024];
			while ((length = fileReader.read(buff)) > 0) {  // 发送内容
				
				fileOut.write(buff, 0, length);
				fileOut.flush();
			}
			
			out.println("【" + userName + "已成功发送文件！】");
			out.flush();
		} catch (Exception e) {}
	}
}
