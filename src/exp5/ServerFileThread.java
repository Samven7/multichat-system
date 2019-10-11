package exp5;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServerFileThread extends Thread{
	ServerSocket server = null;
	Socket socket = null;
	static List<Socket> list = new ArrayList<Socket>();  // 存储客户端
	
	public void run() {
		try {
			server = new ServerSocket(8090);
			while(true) {
				socket = server.accept();
				list.add(socket);
				// 开启文件传输线程
				FileReadAndWrite fileReadAndWrite = new FileReadAndWrite(socket);
				fileReadAndWrite.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class FileReadAndWrite extends Thread {
	private Socket nowSocket = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	
	public FileReadAndWrite(Socket socket) {
		this.nowSocket = socket;
	}
	public void run() {
		try {
			input = new DataInputStream(nowSocket.getInputStream());  // 输入流
			while (true) {
				String path = input.readUTF();  // 接受文件路径
				File file = new File(path);
				for(int i = 0; i < ServerFileThread.list.size(); i++) {
					Socket socket = ServerFileThread.list.get(i);
					output = new DataOutputStream(socket.getOutputStream());  // 输出流
					DataInputStream fileReader = new DataInputStream(new FileInputStream(file));
					if(socket != nowSocket) {  // 发送给其它客户端
						output.writeUTF(file.getName());  // 发送文件名字
						output.flush();
						output.writeLong(file.length());  // 发送文件长度
						output.flush();
						int length = -1;
						byte[] buff = new byte[1024];
						while ((length = fileReader.read(buff)) > 0) {  // 发送内容
							output.write(buff, 0, length);
							output.flush();
						}
					}
					fileReader.close();
				}
			}
		} catch (Exception e) {
			ServerFileThread.list.remove(nowSocket);  // 线程关闭，移除相应套接字
		}
	}
}