package exp5;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
	static ServerSocket server = null;
	static Socket socket = null;
	static List<Socket> list = new ArrayList<Socket>();  // 存储客户端
	
	public static void main(String[] args) {
		MutiChat mutiChat = new MutiChat();  // 新建聊天系统界面
		try {
			// 在服务器端对客户端开启文件传输的线程
			ServerFileThread serverFileThread = new ServerFileThread();
			serverFileThread.start();
			server = new ServerSocket(8081);  // 服务器端套接字（只能建立一次）
			// 等待连接并开启相应线程
			while (true) {
				socket = server.accept();  // 等待连接
				list.add(socket);  // 添加当前客户端到列表
				// 在服务器端对客户端开启相应的线程
				ServerReadAndPrint readAndPrint = new ServerReadAndPrint(socket, mutiChat);
				readAndPrint.start();
			}
		} catch (IOException e1) {
			e1.printStackTrace();  // 出现异常则打印出异常的位置
		}
	}
}

/**
 *  服务器端读写类线程
 *  用于服务器端读取客户端的信息，并把信息发送给所有客户端
 */
class ServerReadAndPrint extends Thread{
	Socket nowSocket = null;
	MutiChat mutiChat = null;
	BufferedReader in =null;
	PrintWriter out = null;
	// 构造函数
	public ServerReadAndPrint(Socket s, MutiChat mutiChat) {
		this.mutiChat = mutiChat;  // 获取多人聊天系统界面
		this.nowSocket = s;  // 获取当前客户端
	}
	
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(nowSocket.getInputStream()));  // 输入流
			// 获取客户端信息并把信息发送给所有客户端
			while (true) {
				String str = in.readLine();
				// 发送给所有客户端
				for(int i = 0; i < Server.list.size(); i++) {
					Socket socket = Server.list.get(i);
					out = new PrintWriter(socket.getOutputStream());  // 对每个客户端新建相应的socket套接字
					if(socket == nowSocket) {  // 发送给当前客户端
						out.println("(你)" + str);
					}
					else {  // 发送给其它客户端
						out.println(str);
					}
					out.flush();  // 清空out中的缓存
				}
				// 调用自定义函数输出到图形界面
				mutiChat.setTextArea(str);
			}
		} catch (Exception e) {
			Server.list.remove(nowSocket);  // 线程关闭，移除相应套接字
		}
	}
}