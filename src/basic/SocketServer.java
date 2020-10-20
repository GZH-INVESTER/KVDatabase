package basic;

import java.io.*;
import java.net.*;

import com.model.KV;

public class SocketServer {

	public static void main(String[] args) {
		HKV oper = new HKV();//ʵ����HKV
		// �ļ����ݼ���
		oper.upLoad(args);
		// req��ʾ����client���Ͷ����ʽ��ʵ��
		KV req;
		try {
			// ����������
			ServerSocket serverSocket = new ServerSocket(8080);
			int count = 0;// ��¼���������������ʹ���̳߳غ�����Ҫ������
			System.out.println("Server starts up :D");
			Socket socket = null;
			String res=null;
			// ������ʼ�յȴ��ͻ��˷��͵�����
			while (true) {
				socket = serverSocket.accept();
				++count;
				System.out.println("���������:" + count + "����");
				OutputStream os = null;
				try {
					InputStream is = socket.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);
					req=(KV) ois.readObject();//�����л�
					System.out.println("�ͻ��˷��͵Ķ���" + req);
					socket.shutdownInput();// �����׽��ֵ�������
					//��ʼ����hashMap
					int method=req.getMethod();//�����ж�
					if(method == 1) {
						oper.add(req.getKey(), req.getValue());
						res="��ӳɹ�";
					}
					else if(method == 2) {
						oper.edit(req.getKey(), req.getValue());
						res="�޸ĳɹ�";
					}
					else if(method==4) {
						res=oper.findK(req.getN());
					}
					else {
						res=req.getKey()+"��ֵΪ"+oper.find(req.getKey());
					}
					//����client����
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