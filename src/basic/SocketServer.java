package basic;

import java.io.*;
import java.net.*;

import com.model.KV;

public class SocketServer {

	public static void main(String[] args) {
		HKV oper = new HKV();//实例化HKV
		// 文件数据加载
		oper.upLoad(args);
		// req表示符合client发送对象格式的实例
		KV req;
		try {
			// 服务器启动
			ServerSocket serverSocket = new ServerSocket(8080);
			int count = 0;// 记录请求的数量，本来使用线程池好像需要，弃用
			System.out.println("Server starts up :D");
			Socket socket = null;
			String res=null;
			// 服务器始终等待客户端发送的请求
			while (true) {
				socket = serverSocket.accept();
				++count;
				System.out.println("请求的数量:" + count + "个！");
				OutputStream os = null;
				try {
					InputStream is = socket.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);
					req=(KV) ois.readObject();//反序列化
					System.out.println("客户端发送的对象：" + req);
					socket.shutdownInput();// 禁用套接字的输入流
					//开始操作hashMap
					int method=req.getMethod();//操作判断
					if(method == 1) {
						oper.add(req.getKey(), req.getValue());
						res="添加成功";
					}
					else if(method == 2) {
						oper.edit(req.getKey(), req.getValue());
						res="修改成功";
					}
					else if(method==4) {
						res=oper.findK(req.getN());
					}
					else {
						res=req.getKey()+"的值为"+oper.find(req.getKey());
					}
					//返给client数据
					os = socket.getOutputStream();
					os.write( res.getBytes() );
					os.flush();
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (os != null) {
							os.close();
						}
						if (socket != null) {
							socket.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				oper.download(args);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}