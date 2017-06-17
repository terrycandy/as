package com.candystudio.android.dxzs.student.tool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.candystudio.android.dxzs.student.app.MyApplication;
import com.candystudio.android.dxzs.student.model.LocationClass;

import android.content.Context;
import android.content.SharedPreferences;

public class SendStrGetIntThread extends Thread {

	public Socket clientSocket;
	public DataInputStream inputStream;
	public DataOutputStream outputStream;
	private String ipString;
	private int port;
	private String reportString;
	public int result;
	public String resObj;

	public SendStrGetIntThread(String ReportString) {
		Context context = MyApplication.getInstance();
		SharedPreferences sp = context.getSharedPreferences("server",
				Context.MODE_PRIVATE);
		ipString = sp.getString("ip", "none");
		port = Integer.valueOf(sp.getString("port", "none"));
		reportString = ReportString;

	}

	public void run() {
		try {
			clientSocket = new Socket(ipString, port);
			inputStream = new DataInputStream(clientSocket.getInputStream());
			outputStream = new DataOutputStream(clientSocket.getOutputStream());
			outputStream.writeUTF(reportString);

			String messageString = inputStream.readUTF();
			if (messageString.equals("1")) {
				// 通知主线程，登录成功。
				result = 1;
				resObj = "";
			} else if (messageString.equals("2")) {
				// 通知主线程，登录失败。
				result = 2;
			}
			clientSocket.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			result = 3;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result = 4;
			e.printStackTrace();

		}
	}
}