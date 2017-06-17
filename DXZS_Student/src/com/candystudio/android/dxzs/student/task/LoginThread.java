package com.candystudio.android.dxzs.student.task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.SharedPreferences;

import com.candystudio.android.dxzs.student.app.MyApplication;

public class LoginThread extends Thread {

	public Socket clientSocket;
	public DataInputStream inputStream;
	public DataOutputStream outputStream;
	public int inflag;
	private String ipString;
	private int port;
	private String userIDString, passwordString;

	public LoginThread(String UserName, String Password) {
		Context context = MyApplication.getInstance();

		SharedPreferences sp = context.getSharedPreferences("server",
				Context.MODE_PRIVATE);

		ipString = sp.getString("ip", "none");
		port = Integer.valueOf(sp.getString("port", "none"));

		userIDString = UserName;
		passwordString = Password;
	}

	public void run() {
		try {
			clientSocket = new Socket(ipString, port);
			inputStream = new DataInputStream(clientSocket.getInputStream());
			outputStream = new DataOutputStream(clientSocket.getOutputStream());
			// 081539;123456
			outputStream.writeUTF("gin-" + userIDString + ";"
					+ passwordString);

			while (true) {
				String messageString = inputStream.readUTF();
				if (messageString == null)
					continue;
				else if (messageString.equals("1")) {
					// 通知主线程，登录成功。
					inflag = 1;
					break;
				} else if (messageString.equals("2")) {
					// 通知主线程，登录失败。
					inflag = 2;
					break;
				}
			}
			clientSocket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			inflag = 3;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			inflag = 4;
			e.printStackTrace();

		}

	}

}
